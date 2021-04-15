package com.cg.mts.service;

import java.util.List;

import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.DuplicateStaffMemberFoundException;
//import com.cg.mts.exception.ComplaintNotFoundException;
//import com.cg.mts.exception.CourierNotFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;

public interface IManagerService {

	public boolean addStaffMember(OfficeStaffMember staffmember) throws DuplicateStaffMemberFoundException;
	public boolean removeStaffMember(int id);
	
	public OfficeStaffMember getStaffMember(int empid) throws StaffMemberNotFoundException;

	
	public CourierStatus getCourierStatus(int courierId) ;
	
	public Complaint getRegisteredComplaint(int complaintid) ;//throws ComplaintNotFoundException;
	public List<Complaint> getAllComplaints();
	public OfficeStaffMember updateStaff(OfficeStaffMember staffmember);
	
}
