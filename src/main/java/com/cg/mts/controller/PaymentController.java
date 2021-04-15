package com.cg.mts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Payment;
import com.cg.mts.exceptions.EmptyDataException;
import com.cg.mts.exceptions.PaymentNotFoundException;
import com.cg.mts.service.IPaymentService;
import com.cg.mts.service.PaymentServiceImpl;
import com.cg.mts.service.IPaymentService;

@RestController
@RequestMapping("payment")
public class PaymentController {
	
	@Autowired
	PaymentServiceImpl service;
	@GetMapping("{id}")
	public ResponseEntity<?> getPayment(@PathVariable("id") int paymentId) {
		Payment p = service.getPaymentDetailsById(paymentId);
		if (p == null)
			throw new PaymentNotFoundException("Payment with id: " + paymentId + "not found!");

		return new ResponseEntity<Payment>(p, HttpStatus.OK);
	}
	
	@GetMapping
	public List<Payment> getAllPaymentDetails() {
		List<Payment> list = service.getAllPaymentDetails();
		if (list.size() == 0) {
			throw new EmptyDataException("No Payment in database!");
		}
		return list;
	}

}
