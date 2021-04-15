package com.cg.mts.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "OfficeStaffMember")
@Inheritance()
//@DiscriminatorColumn(name = "emp_type", discriminatorType = DiscriminatorType.STRING)
//@DiscriminatorValue("EMP")
public class OfficeStaffMember {

	@Id
	private int empId;
	
	
	@NotNull(message = "Name is required")
	@Length(min=3,max=20)
	private String name;
	
	@NotNull(message = "Role is required")
	@Length(min=3,max=8)
	private String role;
	

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressId")
	private Address address;

	@JsonIgnore
	//@JsonFormat(pattern= "HH:mm:ss")
	@ManyToOne()
	@JoinColumn(name = "officeId")
	private CourierOfficeOutlet office;

	
	@ManyToOne()
	@JoinColumn(name="managerId")
	private OfficeStaffMember manager;
	
	@JsonIgnore
	@OneToMany(mappedBy ="manager")
	private Set<OfficeStaffMember> subStaff= new HashSet<>();

	

	public OfficeStaffMember() {

	}
	
	public OfficeStaffMember(int empId, String name, String role) {
		super();
		this.empId = empId;
		this.name = name;
		this.role = role;
		
	}

	

	public int getEmpId() {
		return empId;
	}

	
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public CourierOfficeOutlet getOffice() {
		return office;
	}

	public void setOffice(CourierOfficeOutlet office) {
		this.office = office;
	}

	
	
	
	public OfficeStaffMember getManager() {
		return manager;
	}

	public void setManager(OfficeStaffMember manager) {
		this.manager = manager;
	}

	/*public Set<OfficeStaffMember> getSubStaff() {
		return subStaff;
	}

	public void setSubStaff(Set<OfficeStaffMember> subStaff) {
		this.subStaff = subStaff;
	}*/

	


	
	
	
	/*
	 * public void addEmployee(OfficeStaffMember employee) { employee.setempi(this);
	 * this.getEmployees().add(employee); }
	 */

	/*
	 * public Manager getMgr() { return mgr; }
	 * 
	 * 
	 * public void setMgr(Manager mgr) { this.mgr = mgr; }
	 */

	

}
