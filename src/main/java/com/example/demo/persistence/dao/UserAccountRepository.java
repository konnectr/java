package com.example.demo.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.persistence.model.UserAccount;

/**
 * Интерфейс - репозиторий
 * 
 * @author rybakov
 *
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
