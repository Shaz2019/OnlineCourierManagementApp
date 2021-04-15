package com.cg.mts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dao.IPaymentDao;
import com.cg.mts.entities.Payment;
import com.cg.mts.exceptions.PaymentNotFoundException;

@Service
public class PaymentServiceImpl implements IPaymentService {

	@Autowired
	IPaymentDao paymentDao;
	
	
	@Override
	public Payment getPaymentDetailsById(int paymentId) {
		Optional<Payment> payment = paymentDao.findById(paymentId);
		if (payment.isPresent()) {
			return payment.get();
		} else
			throw new PaymentNotFoundException("Payment with Id "+ paymentId+" is not found!");
	}

	@Override
	public List<Payment> getAllPaymentDetails() {
		List<Payment> list = (List<Payment>) paymentDao.findAll();
		if(list==null) {
			throw new PaymentNotFoundException("Payment not found!");
		}

		return list;
	}

	
	

}
