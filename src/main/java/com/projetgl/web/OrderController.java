
package com.projetgl.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.projetgl.dao.ClientRepository;
import com.projetgl.dao.OrderRepository;
import com.projetgl.dao.ProductRepository;

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
}
