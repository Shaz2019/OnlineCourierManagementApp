package com.cg.mts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cg.mts.dao.IComplaintDao;
import com.cg.mts.dao.ICourierDao;
import com.cg.mts.dao.ICourierOfficeOutletDao;
import com.cg.mts.dao.IOfficeStaffMemberDao;
import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.ComplaintNotFoundException;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.DuplicateStaffMemberFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;



@Service
public class ManagerServiceImpl implements IManagerService{

	@Autowired
	IOfficeStaffMemberDao staffMemberDao;
	
	@Autowired
	ICourierDao courierDao;
	
	@Autowired
	IComplaintDao complaintDao;
	
	@Autowired
	ICourierOfficeOutletDao  courierOfficeOutletDao;
	

	

	/*  
	 * This method is use to add  staff Member 
	 * If staff Member already exists then it will throw Duplicate Staff Member Found Exception 
	  */
	
	@Override
	public boolean addStaffMember(OfficeStaffMember staffmember) {    
		
		if(staffMemberDao.existsById(staffmember.getEmpId())) {
			throw new DuplicateStaffMemberFoundException("Staff id "+staffmember.getEmpId()+" is present in database." );
		}
		else {
			staffMemberDao.save(staffmember);
			return true;
		}
	}

	
	
	/*  
	 * This method is use to delete staff Member 
	 * If staff Member does not exists then it will Staff Member Not Found Exception 
	 */
	
	@Override
	public boolean removeStaffMember(int id) {
		Optional<OfficeStaffMember> staff1= staffMemberDao.findById(id);
		if(staff1.isPresent()) {
			staffMemberDao.deleteById(id);
			return true;
		}
		else {
			throw new StaffMemberNotFoundException("Staff id "+id+" is not present in database." );
		}
	
	}
	
	
	/*  
	 * This method is use to get staff Member by giving Staff Member empId.
	 * If staff Member does not exists then it will give Staff Member Not Found Exception 
	 */
	@Override
	public OfficeStaffMember getStaffMember(int empId) {
		Optional<OfficeStaffMember> staff1= staffMemberDao.findById(empId);
		if(staff1.isPresent()) {
			return staff1.get();
		}else {
			throw new StaffMemberNotFoundException("Staff id "+ empId+" is present  not in database." );
		}
	}


	/*  
	 * This method is use to get Courier Status by giving courierId.
	 * If courierId does not exists then it will give Courier Not Found Exception.
	 */
	@Override
	public CourierStatus getCourierStatus(int courierId)  {
		
			Optional<Courier> courier = courierDao.findById(courierId);
			if(courier.isPresent()) {
			return  courier.get().getStatus();
			}else {
			throw new CourierNotFoundException("CourierId is not in database"+courierId);
		}
	}

	
	
	/*  
	 * This method is use to get registered Complaint by giving complaintId.
	 * If complaintId does not exists then it will give Complaint Not Found Exception.
	 */
	@Override
	public Complaint getRegisteredComplaint(int complaintid) {
		Optional<Complaint> complaint = complaintDao.findById(complaintid);
		
		if(complaint.isPresent()) {
			return complaint.get();
		}
		else {
			throw new ComplaintNotFoundException("Not able to find complaintId","Try again");
		}
	}
	

	/*  
	 * This method is use to get all registered Complaint.
	 * If there is no complaint then it will give Complaint Not Found Exception.
	 */
	@Override
	public List<Complaint> getAllComplaints() {
		List<Complaint> lst = complaintDao.getAllComplaint(); 
		if(lst.isEmpty()) {
			throw new ComplaintNotFoundException("No Compalint found","Good Job");
		}
		return lst;
	}

	/*  
	 * This method is use to update staff Member by giving Staff Member object.
	 *  If staff Member does not exist then it will give Staff Member Not Found Exception .
	 */
	@Override
	public OfficeStaffMember updateStaff(OfficeStaffMember staffMember) {
		if(staffMemberDao.existsById(staffMember.getEmpId())) {
			return staffMemberDao.save(staffMember);
		}
		else {
			throw new  StaffMemberNotFoundException("Staff is not found with id"+staffMember.getEmpId());
		}
	}
	

	public boolean validateUser(int empId) {
		Optional<OfficeStaffMember> staff1= staffMemberDao.findById(empId);
		OfficeStaffMember staff2 = new OfficeStaffMember();
		staff2 = staff1.get();
		String str = staff2.getRole();
		String str1 = "MGR";
		
		if(str.equals(str1)) {
			return true;
		}else {
			return false;
		}
	}

}
