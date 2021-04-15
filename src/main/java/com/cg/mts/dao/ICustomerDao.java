package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.Customer;

@Repository("customerDao")
public interface ICustomerDao extends JpaRepository<Customer, Integer> {

}
