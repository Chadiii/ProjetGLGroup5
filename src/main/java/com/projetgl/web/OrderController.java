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

	@GetMapping("/products")
	public ModelAndView getadmin(@RequestParam(required = false) String name) {
		if (name == null) {
			return new ModelAndView("cproducts", "products", productDAO.findAll());
		} else {
			return new ModelAndView("cproducts", "products", productDAO.findByNameIgnoreCaseContaining(name));
		}
	}

	@PostMapping("/cart/add")
	public ModelAndView addToCart(HttpSession session, @RequestParam(value="quantityInput") Integer quantity, @RequestParam(value="idInput") Integer id) {
		Map<Product, Integer> productCart;
		if(session.getAttribute("cart") != null) {
			productCart = (Map<Product, Integer>) session.getAttribute("cart");
		}else {
			productCart = new HashMap<Product, Integer>();
		}
		addToMap(quantity, id, productCart);
		session.setAttribute("cart", productCart);
		return new ModelAndView("redirect:/products");
	}

	@GetMapping("/cart")
	public ModelAndView getCart(HttpSession session) {
		if(session.getAttribute("cart") == null) {
			return new ModelAndView("redirect:/products");
		}else {
			//Map<Product, Integer> productCart = (Map<Product, Integer>) session.getAttribute("cart");
			return new ModelAndView("cart");
		}
	}

	public void addToMap(Integer quantity, Integer id, Map<Product, Integer> list) {
		int found = 0;
		for (Map.Entry<Product, Integer> entry : list.entrySet()) {
			if(entry.getKey().getId() == id) {
				entry.setValue(entry.getValue() + quantity);
				found = 1;
			}
		}
		if(found == 0) {
			list.put(productDAO.findById(id).get(), quantity);
		}
	}

}
