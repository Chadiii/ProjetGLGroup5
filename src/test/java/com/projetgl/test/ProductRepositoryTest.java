package com.projetgl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.projetgl.ProjetGlGroup5Application;
import com.projetgl.dao.ProductRepository;
import com.projetgl.model.Product;

@SpringBootTest
@ContextConfiguration(classes = ProjetGlGroup5Application.class)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productDAO;

    @Test
    public void testSaveAndCheckAndEdit() {
        var m = productDAO.save(new Product("Lait", 100, 1.8, "Lait demi ecreme"));
        var m2 = productDAO.findById(m.getId());
        assertEquals(m2.get().getName(), "Lait");
        assertEquals(m2.get().getQuantity(), 100);

        m.setQuantity(80);
        productDAO.save(m);
        m2 = productDAO.findById(m.getId());
        assertEquals(m2.get().getQuantity(), 80);
    }

    @Test
    public void testDelete() {
        var m = productDAO.save(new Product("Lait", 100, 1.8, "Lait demi ecreme"));
        productDAO.delete(m);
        var m2 = productDAO.findById(m.getId());
        Assertions.assertThrows(NoSuchElementException.class, ()->
                m2.get()
        );
    }
}