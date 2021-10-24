package com.projetgl.web;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@PreDestroy
	public void destroy() {
	}
}
