package com.projetgl.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projetgl.dao.ClientRepository;
import com.projetgl.dao.OrderRepository;
import com.projetgl.dao.ProductRepository;
import com.projetgl.model.Client;
import com.projetgl.model.Order;
import com.projetgl.model.Product;

@Controller
public class OrderController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	ProductRepository productDAO;

	@Autowired
	OrderRepository orderDAO;

	@Autowired
	ClientRepository clientDAO;

	@GetMapping("/")
	public ModelAndView main() {
		return new ModelAndView("redirect:/products");

	}

	@GetMapping("/products")
	public ModelAndView getadmin(@RequestParam(required = false) String name) {
		if (name == null) {
			return new ModelAndView("cproducts", "products", productDAO.findAll());
		} else {
			return new ModelAndView("cproducts", "products", productDAO.findByNameIgnoreCaseContaining(name));
		}
	}

	@PostMapping("/cart/add")
	public ModelAndView addToCart(HttpSession session, @RequestParam(value = "quantityInput") Integer quantity,
			@RequestParam(value = "idInput") Integer id) {
		Map<Product, Integer> productCart;
		if (session.getAttribute("cart") != null) {
			productCart = (Map<Product, Integer>) session.getAttribute("cart");
		} else {
			productCart = new HashMap<Product, Integer>();
		}
		addToMap(quantity, id, productCart);
		session.setAttribute("cart", productCart);
		return new ModelAndView("redirect:/products");
	}

	@GetMapping("/cart")
	public ModelAndView getCart(HttpSession session) {
		if (session.getAttribute("cart") == null) {
			return new ModelAndView("redirect:/products");
		} else {
			// Map<Product, Integer> productCart = (Map<Product, Integer>)
			// session.getAttribute("cart");
			return new ModelAndView("cart");
		}
	}

	@GetMapping("/cart/delete/{id}")
	public ModelAndView deleteFromCart(HttpSession session, @PathVariable Integer id) {
		if (session.getAttribute("cart") == null) {
			return new ModelAndView("redirect:/products");
		} else {
			Map<Product, Integer> productCart = (Map<Product, Integer>) session.getAttribute("cart");
			removeFromCart(id, productCart);
			if (productCart.size() == 0)
				session.invalidate();
			else
				session.setAttribute("cart", productCart);
			return new ModelAndView("redirect:/cart");
		}
	}

	@GetMapping("/cart/cancel")
	public ModelAndView cancelCart(HttpSession session) {
		if (session.getAttribute("cart") == null) {
			return new ModelAndView("redirect:/products");
		} else {
			session.invalidate();
			return new ModelAndView("redirect:/cart");
		}
	}

	@GetMapping("/cart/confirm")
	public ModelAndView confirmCart(HttpSession session, RedirectAttributes redirectAttributes) {
		if (session.getAttribute("cart") == null) {
			return new ModelAndView("redirect:/products");
		} else {
			Product productWithLessQuantity = verifyStock((Map<Product, Integer>) session.getAttribute("cart"));
			if (productWithLessQuantity != null) {
				redirectAttributes.addFlashAttribute("productWithLessQuantity", productWithLessQuantity);
				return new ModelAndView("redirect:/cart");
			}
			return new ModelAndView("payment");
		}
	}

	@PostMapping("/order/save")
	public ModelAndView saveOrder(HttpSession session, @RequestParam String name, @RequestParam String mail,
			@RequestParam String ccName, @RequestParam String ccNumber, @RequestParam String ccExpiration,
			@RequestParam String ccCCV) {
		if (session.getAttribute("cart") == null) {
			return new ModelAndView("redirect:/products");
		} else {
			Client client = new Client(name, mail, ccName, ccNumber, ccExpiration, ccCCV);
			Map<Product, Integer> productCart = (Map<Product, Integer>) session.getAttribute("cart");
			List<Product> products = new ArrayList<Product>();
			MapToListWithDecrementProductStock(products, productCart);

			client = clientDAO.save(client);
			orderDAO.save(
					new Order(new Date(), new Date(new Date().getTime() + (1000 * 60 * 60 * 48)), products, client));

			session.invalidate();
			return new ModelAndView("redirect:/order");
		}
	}

	@GetMapping("/order")
	public ModelAndView getOrder(HttpSession session, @RequestParam(required = false) String mail) {
		if (mail == null) {
			String mailInSession = (String) session.getAttribute("mailInSession");
			if (mailInSession != null)
				return new ModelAndView("corders", "orders", orderDAO.findByClientMail(mailInSession));
			return new ModelAndView("corders");
		} else {
			session.setAttribute("mailInSession", mail);
			return new ModelAndView("corders", "orders", orderDAO.findByClientMail(mail));
		}
	}

	@GetMapping("/order/cancel/{id}")
	public ModelAndView cancelOrder(HttpSession session, @PathVariable Integer id) {
		Date todayDate = new Date();
		Date shippingDate = orderDAO.findById(id).get().getShippingDate();

		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(todayDate);

		Calendar shippingCal = Calendar.getInstance();
		shippingCal.setTime(shippingDate);

		if (todayCal.get(Calendar.YEAR) == shippingCal.get(Calendar.YEAR)
				&& todayCal.get(Calendar.MONTH) == shippingCal.get(Calendar.MONTH)
				&& todayCal.get(Calendar.DAY_OF_MONTH) == shippingCal.get(Calendar.DAY_OF_MONTH)) {
			return new ModelAndView("corders", "errorMessage", "true");
		} else {
			List<Product> productsInOrder = orderDAO.findById(id).get().getProducts();
			incrementStock(productsInOrder);
			orderDAO.deleteById(id);
			return new ModelAndView("redirect:/order");
		}
	}

	public void addToMap(Integer quantity, Integer id, Map<Product, Integer> list) {
		int found = 0;
		for (Map.Entry<Product, Integer> entry : list.entrySet()) {
			if (entry.getKey().getId() == id) {
				entry.setValue(entry.getValue() + quantity);
				found = 1;
			}
		}
		if (found == 0) {
			list.put(productDAO.findById(id).get(), quantity);
		}
	}

	public void MapToListWithDecrementProductStock(List<Product> listProduct, Map<Product, Integer> list) {
		for (Map.Entry<Product, Integer> entry : list.entrySet()) {
			Product productToDecQuantity = entry.getKey();
			productToDecQuantity.setQuantity(productToDecQuantity.getQuantity() - entry.getValue());
			productDAO.save(productToDecQuantity);
			for (int i = 0; i < entry.getValue(); i++)
				listProduct.add(entry.getKey());
		}
	}

	public Product verifyStock(Map<Product, Integer> list) {
		for (Map.Entry<Product, Integer> entry : list.entrySet()) {
			if (productDAO.findById(entry.getKey().getId()).get().getQuantity() < entry.getValue())
				return entry.getKey();
		}
		return null;
	}

	public void removeFromCart(Integer id, Map<Product, Integer> list) {
		for (Map.Entry<Product, Integer> entry : list.entrySet()) {
			if (entry.getKey().getId() == id) {
				list.remove(entry.getKey());
				break;
			}
		}
	}

	public void incrementStock(List<Product> listProduct) {
		for (Product product : listProduct) {
			product.setQuantity(product.getQuantity() + 1);
		}
	}

}
