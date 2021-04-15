package com.cg.mts.service;

import java.util.List;

import com.cg.mts.entities.Payment;


public interface IPaymentService {
	
	Payment getPaymentDetailsById(int id);
	public List<Payment> getAllPaymentDetails(); 

}
