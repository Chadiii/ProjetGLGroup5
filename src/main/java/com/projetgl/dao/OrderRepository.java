package com.projetgl.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetgl.model.Order;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByClientMail(String mail);
}
