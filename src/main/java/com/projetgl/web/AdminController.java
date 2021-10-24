package com.projetgl.web;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

import com.projetgl.dao.AdminRepository;
import com.projetgl.dao.ClientRepository;
import com.projetgl.dao.OrderRepository;
import com.projetgl.dao.ProductRepository;
import com.projetgl.model.Admin;
import com.projetgl.model.Product;

@Controller
public class AdminController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	AdminRepository adminDAO;
	
	@Autowired
	ProductRepository productDAO;
	
	@Autowired
	OrderRepository orderDAO;
	
	@Autowired
	ClientRepository clientDAO;
	
	@PostConstruct
	public void init() {
		System.out.println("Start " + this);
		if (adminDAO.count() == 0) {
			adminDAO.save(new Admin("Sebastien", //name
					"admin", //login
					"admin" // password
			));			
		}
		productDAO.save(new Product("Lait", 100, 1.8, "Lait demi ecreme"));
		productDAO.save(new Product("Gateau", 230, 0.6, "Chocolate noir"));
		productDAO.save(new Product("Jus", 230, 0.6, "Jus Mixte"));
	}
	
	@GetMapping("/admin")
	public ModelAndView getadmin(HttpSession session) {
		if(session.getAttribute("active") != null) {
			return new ModelAndView("products", "products", productDAO.findAll());
		}else {
			return new ModelAndView("login");
		}
	}

	@PostMapping("/login")
	public ModelAndView checkAdmin(HttpSession session, @RequestParam String login, @RequestParam String password) {
		
		if(adminDAO.findByLoginAndPassword(login, password).isPresent()) {
			session.setAttribute("active", true);
		}
		return new ModelAndView("redirect:/admin");
	}
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		if(session.getAttribute("active") != null) {
				session.invalidate();
				return new ModelAndView("redirect:/admin");
		}else {
			return new ModelAndView("redirect:/admin");
		}
	}
	
	@GetMapping("/admin/delete/{id}")
    public ModelAndView deleteProduct(HttpSession session, @PathVariable Integer id) {
        if(session.getAttribute("active") != null) {
            productDAO.deleteById(id);
            return new ModelAndView("redirect:/admin");
        }else {
            return new ModelAndView("login");
        }
    }

	@GetMapping("/admin/add")
	public ModelAndView addProduct(HttpSession session) {
		if(session.getAttribute("active") != null) {
			return new ModelAndView("saveProduct");
		}else {
			return new ModelAndView("login");
		}
	}

	@GetMapping("/admin/edit/{id}")
	public ModelAndView editProduct(HttpSession session, @PathVariable Integer id) {
		if(session.getAttribute("active") != null) {
			return new ModelAndView("saveProduct", "product", productDAO.findById(id).get());
		}else {
			return new ModelAndView("login");
		}
	}

	@PostMapping("/saveProduct")
	public ModelAndView saveProduct(HttpSession session, @RequestParam(required=false) Integer id, @RequestParam String name, @RequestParam int quantity, @RequestParam double price, @RequestParam String description) {
		if(session.getAttribute("active") != null) {
			if(id == null) {
				productDAO.save(new Product(name, quantity, price, description));
				return new ModelAndView("redirect:/admin");
			}else {
				Product product = productDAO.findById(id).get();
				product.setName(name);
				product.setQuantity(quantity);
				product.setPrice(price);
				product.setDescription(description);
				productDAO.save(product);
				return new ModelAndView("redirect:/admin");
			}
		}else {
			return new ModelAndView("login");
		}
	}
	
	
	@PreDestroy
	public void destroy() {
	}
}
