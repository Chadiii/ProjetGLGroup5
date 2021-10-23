package com.projetgl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.projetgl.ProjetGlGroup5Application;
import com.projetgl.dao.ClientRepository;
import com.projetgl.dao.OrderRepository;
import com.projetgl.dao.ProductRepository;
import com.projetgl.model.Client;
import com.projetgl.model.Order;
import com.projetgl.model.Product;

@SpringBootTest
@ContextConfiguration(classes = ProjetGlGroup5Application.class)
public class OrderRepositoryTest {

	@Autowired
	ProductRepository productDAO;
	
	@Autowired
	ClientRepository clientDAO;
	
	@Autowired
	OrderRepository orderDAO;
	
	@Test
	public void saveOrderAndCheck() {
		List<Product> products = new ArrayList<>();
		products.add(productDAO.save(new Product("Lait", 100, 1.8, "Lait demi ecreme")));
		products.add(productDAO.save(new Product("Gateau", 230, 0.6, "Chocolate noir")));
		products.add(productDAO.save(new Product("Gateau", 230, 0.6, "Chocolate noir")));
		
		var client = new Client("sebastien","sebatien@gmail.com","sebatien","123245546700","02/23","332");
		clientDAO.save(client);
		
		var date = new Date();
		
		var order = orderDAO.save(new Order(date, new Date(date.getTime() + (1000 * 60 * 60 * 48)), products, client));
		
		var order2 = orderDAO.findById(order.getId());
		
		assertEquals(order2.get().getOrderDate().compareTo(date), 0);
		
		assertEquals(order2.get().getClient().getName(), "sebastien");
		
		assertEquals(order2.get().getProducts().size(), 3);
	}
	
	@Test
	public void testDelete() {
		List<Product> products = new ArrayList<>();
		products.add(productDAO.save(new Product("Lait", 100, 1.8, "Lait demi ecreme")));
		products.add(productDAO.save(new Product("Gateau", 230, 0.6, "Chocolate noir")));
		products.add(productDAO.save(new Product("Gateau", 230, 0.6, "Chocolate noir")));
		
		var client = clientDAO.save(new Client("sebastien","sebatien@gmail.com","sebatien","123245546700","02/23","332"));
		
		var date = new Date();
		
		var order = orderDAO.save(new Order(date, new Date(date.getTime() + (1000 * 60 * 60 * 48)), products, client));
		
		orderDAO.deleteById(order.getId());
		var order2 = orderDAO.findById(order.getId());
		
		Assertions.assertThrows(NoSuchElementException.class, ()->
		order2.get()
			);
		
		var client2 = clientDAO.findById(client.getId());
		
		Assertions.assertThrows(NoSuchElementException.class, ()->
		client2.get()
			);
	}
}
