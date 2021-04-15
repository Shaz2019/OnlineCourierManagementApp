package com.cg.mts.entities;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="CourierOfficeOutlet")
public class CourierOfficeOutlet {
	
	@Id
	private int officeId;
	
	@JsonFormat(pattern= "HH:mm:ss")
	private LocalTime openingTime;
	
	@JsonFormat(pattern= "HH:mm:ss")
	private LocalTime closingTime;	
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="addressId")
	private Address address;
	
	@JsonIgnore
	@OneToMany(mappedBy="office",cascade = CascadeType.ALL)
	private List<OfficeStaffMember> staffMembers = new ArrayList<>() ;
	
	public CourierOfficeOutlet() {
		
	}


	public CourierOfficeOutlet(int officeId, LocalTime openingTime, LocalTime closingTime) {
		super();
		this.officeId = officeId;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}


	public int getOfficeId() {
		return officeId;
	}


	public void setOfficeId(int officeId) {
		this.officeId = officeId;
	}


	public LocalTime getOpeningTime() {
		return openingTime;
	}


	public void setOpeningTime(LocalTime openingTime) {
		this.openingTime = openingTime;
	}


	public LocalTime getClosingTime() {
		return closingTime;
	}


	public void setClosingTime(LocalTime closingTime) {
		this.closingTime = closingTime;
	}


	public List<OfficeStaffMember> getStaffMembers() {
		return staffMembers;
	}


	public void setStaffMembers(List<OfficeStaffMember> staffMembers) {
		this.staffMembers = staffMembers;
	}

	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}


	

	
	

}
