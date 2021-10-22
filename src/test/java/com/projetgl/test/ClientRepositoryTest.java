package com.projetgl.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.projetgl.ProjetGlGroup5Application;
import com.projetgl.dao.ClientRepository;
import com.projetgl.model.Client;

@SpringBootTest
@ContextConfiguration(classes = ProjetGlGroup5Application.class)
public class ClientRepositoryTest {

    @Autowired
    ClientRepository clientDAO;

    @Test
    public void testSaveAndCheckAndEdit() {
        var m = clientDAO.save(new Client("sebastien","sebatien@gmail.com","sebatien","123245546700","02/23","332"));
        var m2 = clientDAO.findById(m.getId());
        assertEquals(m2.get().getName(), "sebastien");

        m.setName("robert");
        clientDAO.save(m);
        m2 = clientDAO.findById(m.getId());
        assertEquals(m2.get().getName(), "robert");
    }

    @Test
    public void testDelete() {
        var m = clientDAO.save(new Client("sebatien","sebatien@gmail.com","sebatien","123245546700","02/23","332"));
        clientDAO.delete(m);
        var m2 = clientDAO.findById(m.getId());
        Assertions.assertThrows(NoSuchElementException.class, ()->
                m2.get()
        );
    }
}
