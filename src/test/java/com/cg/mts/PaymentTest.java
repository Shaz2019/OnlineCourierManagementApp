package com.cg.mts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.LocalDateAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.dao.IPaymentDao;

import com.cg.mts.entities.Payment;

import com.cg.mts.service.PaymentServiceImpl;

@SpringBootTest
public class PaymentTest{
	
	@Autowired
	PaymentServiceImpl paymentService;
	
	@MockBean
	IPaymentDao paymentDao;
	@Test
	@DisplayName("To get AllPayment")
	public void getAllPaymentDetailsTest() {
		
		when(paymentDao.findAll()).thenReturn(Stream.of(new Payment("ByCash", LocalDate.now()), 
				new Payment("ByCard", LocalDate.now())).collect(Collectors.toList()) );
		
		assertEquals(2, paymentService.getAllPaymentDetails().size()); 
	}
	
	
	
	@Test
	@DisplayName("To get PaymentById")
	public void getPaymentDetailsByIdTest() {
		
		Payment p = new Payment();
		
		p.setPaymentId(11);
		p.setPaymentMode("ByCash");
		p.setPaymentDate(LocalDate.now());
		
	    Payment p2=new Payment();
		
		when(paymentDao.save(p)).thenReturn(p);
		
		if(paymentDao.existsById(p.getPaymentId())) {
			
			assertEquals(p2, paymentService.getPaymentDetailsById(p.getPaymentId()));
			
		}
		
		
		}
}