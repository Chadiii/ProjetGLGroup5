package com.projetgl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.projetgl.ProjetGlGroup5Application;
import com.projetgl.dao.AdminRepository;
import com.projetgl.model.Admin;


@SpringBootTest
@ContextConfiguration(classes = ProjetGlGroup5Application.class)
public class AdminRepositoryTest {

    @Autowired
    AdminRepository adminDAO;

    @Test
    public void testSaveAndCheckAndEdit() {
        var m = adminDAO.save(new Admin("sebastien", "machin", "machin"));
        var m2 = adminDAO.findById(m.getId());
        assertEquals(m2.get().getName(), "sebastien");

        m.setName("robert");
        adminDAO.save(m);
        m2 = adminDAO.findById(m.getId());
        assertEquals(m2.get().getName(), "robert");
    }

    @Test
    public void testDelete() {
        var m = adminDAO.save(new Admin("clement", "machin", "machin"));
        adminDAO.delete(m);
        var m2 = adminDAO.findById(m.getId());
        Assertions.assertThrows(NoSuchElementException.class, ()->
                m2.get()
        );
    }

    @Test
    public void testLogin() {
        adminDAO.save(new Admin("saber", "login", "password"));
        var m = adminDAO.findByLoginAndPassword("login", "password");
        assertNotNull(m.get());
    }



}
