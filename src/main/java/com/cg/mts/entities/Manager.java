package com.cg.mts.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="manager")
//@DiscriminatorValue("MGR")
public class Manager extends OfficeStaffMember {
	
	
	
	
	@OneToOne
	@JoinColumn(name="officeId")
	private CourierOfficeOutlet office;
	
	
	public Manager() {
		
	}

	
	
	public CourierOfficeOutlet getOffice() {
		return office;
	}


	public void setOffice(CourierOfficeOutlet office) {
		this.office = office;
	}

	
    /* public Set<OfficeStaffMember> getStaff() {
		return staff;
	}

	public void setStaff(Set<OfficeStaffMember> staff) {
		this.staff = staff;
	}

	public void addStaff(OfficeStaffMember staff) {
		staff.setManager(this);		//this will avoid nested cascade and also serves the purpose to avoid cyclic references. 
		this.getStaff().add(staff);
	}*/

}
