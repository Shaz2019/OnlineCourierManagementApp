package com.cg.mts.entities;

 

import java.util.ArrayList;
import java.util.List;

 

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 

@JsonIgnoreProperties({"hibernateLazyInitilizer","handler"})
@Entity
@Table(name = "customer")
public class Customer {
   
	@Id
	private int customerId;

 

    private int aadharNo;
    private String firstName;
    private String lastName;
    private int mobileNo;

 

    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;

 
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REFRESH,orphanRemoval = true)
    @JoinColumn(name="bankaccount")
    private BankAccount bankAccount;

    /*@OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accountNo")
    private BankAccount account;*/

 

   //  @OneToMany(mappedBy = "customer",targetEntity = Complaint.class, orphanRemoval = true, 
    	//	 fetch = FetchType.EAGER)
   // private List<Complaint> complaint=new ArrayList<>();

 
    @JsonIgnore
     @OneToMany(mappedBy = "sender",targetEntity = Courier.class,
     fetch = FetchType.LAZY,orphanRemoval = true)
     private List<Courier> courierSender = new ArrayList<>();

 
    public BankAccount getBankAccount() {
		return bankAccount;
	}



	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}



	@JsonIgnore
     @OneToMany(mappedBy = "receiver",targetEntity = Courier.class,
     orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Courier> courierReceiver = new ArrayList<>();

 

    public Customer() {

 

    }

 

    public Customer(int customerId,int aadharNo, String firstName, String lastName, int mobileNo) {
        super();
        this.customerId = customerId;
        this.aadharNo = aadharNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
    }

 

    public int getCustomerId() {
        return customerId;
    }

 

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

 

    public int getAadharNo() {
        return aadharNo;
    }

 

    public void setAadharNo(int aadharNo) {
        this.aadharNo = aadharNo;
    }

 

    public String getFirstName() {
        return firstName;
    }

 

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

 

    public String getLastName() {
        return lastName;
    }

 

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

 

    public int getMobileNo() {
        return mobileNo;
    }

 

    public void setMobileNo(int mobileNo) {
        this.mobileNo = mobileNo;
    }

 

    public Address getAddress() {
        return address;
    }

 

    public void setAddress(Address address) {
        this.address = address;
    }

 

    

 

    public List<Courier> getCourierSender() {
        return courierSender;
    }

 

    public void setCourierSender(List<Courier> courierSender) {
        this.courierSender = courierSender;
    }

 

    public List<Courier> getCourierReceiver() {
        return courierReceiver;
    }

 

    public void setCourierReceiver(List<Courier> courierReceiver) {
        this.courierReceiver = courierReceiver;
    }
    

    

}