package com.cg.mts.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int paymentId;
	
	private String paymentMode;
	private LocalDate paymentDate;
	
    @OneToOne(cascade=CascadeType.ALL)                               
    @JoinColumn(name="courierId")
    private Courier courier;
    
    
    
    public Payment() {
        
    }


	public Payment( String paymentMode, LocalDate paymentDate) {
		super();
		this.paymentMode = paymentMode;
		this.paymentDate = paymentDate;
	}


	public int getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}


	public String getPaymentMode() {
		return paymentMode;
	}


	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}


	public Courier getCourier() {
		return courier;
	}


	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	

}
