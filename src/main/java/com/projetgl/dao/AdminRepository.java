package com.projetgl.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetgl.model.Admin;

@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Optional<Admin> findByLoginAndPassword(String login, String password);
}
