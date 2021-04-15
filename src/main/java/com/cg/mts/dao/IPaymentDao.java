package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.Payment;

@Repository("paymentDao")
public interface IPaymentDao extends JpaRepository<Payment, Integer>{
	
	Payment findByPaymentId(int id);

}
