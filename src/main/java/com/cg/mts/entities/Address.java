package com.cg.mts.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int addressId;
	
	private String houseNo;
    private String street;
	private String city;
	private String state;
	private String country;
	private int zip;
	
	
	/*@OneToOne                                //(mappedBy="addr")
	@JoinColumn(name="customerid")
	private Customer customer;*/
	
	/*@OneToOne
	@JoinColumn(name="empId")                //(mappedBy="address")
	private OfficeStaffMember officeMem;*/

	/*@OneToOne
	@JoinColumn(name="officeId")             //(mappedBy="addre")
	private CourierOfficeOutlet office;*/

	
	public Address() {
		
	}
	
	
	
	public Address(String houseNo, String street, String city, String state, String country, int zip) {
		super();
	
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zip = zip;
	}



	public int getAddressId() {
		return addressId;
	}



	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}



	public String getHouseNo() {
		return houseNo;
	}



	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}



	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public int getZip() {
		return zip;
	}



	public void setZip(int zip) {
		this.zip = zip;
	}
	
	
	
	/*public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}*/
	/*public OfficeStaffMember getOfficeMem() {
		return officeMem;
	}
	public void setOfficeMem(OfficeStaffMember officeMem) {
		this.officeMem = officeMem;
	}*/
	/*public CourierOfficeOutlet getOffice() {
		return office;
	}
	public void setOffice(CourierOfficeOutlet office) {
		this.office = office;
	}*/
	
	
	
}
