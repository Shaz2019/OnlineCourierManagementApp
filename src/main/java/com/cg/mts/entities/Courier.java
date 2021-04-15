package com.cg.mts.entities;

 

import java.time.LocalDate;

 

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

 

@Entity
@Table(name="courier")
public class Courier {

 

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int courierId;
    
    @Column(unique = true)
    private int consignmentNo;
    
    private LocalDate initiatedDate;
    
    @Enumerated(EnumType.STRING)
    private CourierStatus status;
    
    private LocalDate deliveredDate;
    
    @ManyToOne()
    @JoinColumn(name= "senderCustomer")
    private Customer sender;
    
    @ManyToOne
    @JoinColumn(name= "receiverCustomer")
    private Customer receiver;
    
    @OneToOne(mappedBy="courierId",targetEntity = Complaint.class, fetch = FetchType.LAZY, orphanRemoval = true)
    private Complaint complaint;
    
   @OneToOne(mappedBy="courier",targetEntity = Payment.class, fetch = FetchType.LAZY, orphanRemoval = true)
    private Payment payment;
    
    public Courier() {
        
    }
    
    public Courier(int consignmentNo, CourierStatus status, LocalDate initiatedDate, LocalDate deliveredDate) {
	
    	super();
    	this.consignmentNo = consignmentNo;
    	this.status = status;
    	this.initiatedDate = initiatedDate;
    	this.deliveredDate = deliveredDate;
	
    }
    
    
    
    public int getCourierId() {
        return courierId;
    }

 

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

 

    public int getConsignmentNo() {
        return consignmentNo;
    }

 

    public void setConsignmentNo(int consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

 

    public LocalDate getInitiatedDate() {
        return initiatedDate;
    }

 

    public void setInitiatedDate(LocalDate initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

 

    public CourierStatus getStatus() {
        return status;
    }

 

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

 

    public LocalDate getDeliveredDate() {
        return deliveredDate;
    }

 

    public void setDeliveredDate(LocalDate deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

 

    public Customer getSender() {
        return sender;
    }

 

    public void setSender(Customer sender) {
        this.sender = sender;
    }

 

    public Customer getReceiver() {
        return receiver;
    }

 

    public void setReceiver(Customer receiver) {
        this.receiver = receiver;
    }
    
	/*
	 * public Payment getPayment() { return payment; }
	 * 
	 * 
	 * 
	 * public void setPayment(Payment payment) { this.payment = payment; }
	 */

}
