package com.projetgl.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetgl.model.Client;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
