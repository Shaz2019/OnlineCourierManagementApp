package com.cg.mts.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dao.IAddressDao;
import com.cg.mts.dao.IComplaintDao;
import com.cg.mts.dao.ICourierDao;
import com.cg.mts.dao.ICourierOfficeOutletDao;
import com.cg.mts.dao.IOfficeStaffMemberDao;
import com.cg.mts.entities.Address;
import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.ComplaintNotFoundException;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.DuplicateStaffMemberFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;

import com.cg.mts.service.ManagerServiceImpl;


@CrossOrigin
@RestController
@RequestMapping("manager")
public class ManagerController {
	
	@Autowired
	ManagerServiceImpl managerService;
	
	@Autowired
	IAddressDao addressDao;
	
	@Autowired
	ICourierDao courierDao;
	
	@Autowired
	IComplaintDao complaintDao;
	
	@Autowired
	IOfficeStaffMemberDao staffMemberDao;
	
	@Autowired
	ICourierOfficeOutletDao  courierOfficeOutletDao;
	
	
	
	@GetMapping("/login/{empId}")
	public ResponseEntity<String> validateLogin(@PathVariable("empId")int empId,HttpServletRequest request){
		
		
		if(managerService.validateUser(empId)){
			HttpSession session= request.getSession(true);
			session.setAttribute("userId", empId);
		}
		else {
			throw new StaffMemberNotFoundException("Incorrect EmpId");
		}
		
		return new ResponseEntity<String>("Login Success!",HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/logout")
	public ResponseEntity<String> logoutUser(HttpServletRequest request){
		HttpSession session= request.getSession();
		
		session.invalidate();
		
		return new ResponseEntity<String>("Logout Success!",HttpStatus.OK);
	}
	
	



	/*  
	 * This method is use to add  staff Member 
	 * If staff Member already exists then it will throw Duplicate Staff Member Found Exception 
	  */
	@PostMapping
	public String addStaff(@RequestBody  OfficeStaffMember staffmember) {
		

		if(managerService.addStaffMember(staffmember))
		return "Added successfully";
		else {
			throw new DuplicateStaffMemberFoundException(" Staff Member with id "+staffmember.getEmpId()+" is already exists.");
		}
		
	}
	
	
	/*  
	 * This method is use to delete staff Member 
	 * If staff Member does not exists then it will Staff Member Not Found Exception 
	 */
	@DeleteMapping("/{eid}")
	public String  deleteStaff(@PathVariable("eid") int id) {
		if(managerService.removeStaffMember(id)) {
			return "Delete successfull";
		}else
			throw new StaffMemberNotFoundException("Delete  Employee with id "+id+" not found.");
		
	}

	/*  
	 * This method is use to get staff Member by giving Staff Member empId.
	 * If staff Member does not exists then it will give Staff Member Not Found Exception 
	 */
	@GetMapping("{id}")
	public ResponseEntity<?> getStaffMemberDetails(@PathVariable("id")int empid) {
		OfficeStaffMember staff =   managerService.getStaffMember(empid) ;
		if(staff==null) {
			throw new StaffMemberNotFoundException("Staff memeber is not  found with id "+empid);
		}
		else {
			return new ResponseEntity<OfficeStaffMember>(staff,HttpStatus.OK);
		}
	} 
	
	
	/*  
	 * This method is use to get Courier Status by giving courierId.
	 */
	@GetMapping(value="/filterBy/id_type/CourierStatus/{cid}")
	public CourierStatus getCourierStatus(@PathVariable(name= "cid")int courierId) {
		
		return managerService.getCourierStatus(courierId);	
	}
	
	
	/*  
	 * This method is use to get registered Complaint by giving complaintId.
	 * If complaintId does not exists then it will give Complaint Not Found Exception.
	 */
	@GetMapping(value="/filterBy/id_type/complaintId/{cid}")
	public Complaint getRegistedComplaint(@PathVariable(name= "cid")int complaintId) {
		Complaint complaint = managerService.getRegisteredComplaint(complaintId);
		if(complaint!=null) {
			
			return complaint;
		}
		else {
			throw new ComplaintNotFoundException("ComplaintId is not in database"+complaintId,"Not found");
		}
	}
	
	/*  
	 * This method is use to get all registered Complaint.
	 * If there is no complaint then it will give Complaint Not Found Exception.
	 */
	@GetMapping
	public List<Complaint> getAllComplaints(){
		List<Complaint> complaints = managerService.getAllComplaints();
		if(complaints.isEmpty()) {
			throw new ComplaintNotFoundException("No Complaints","Good Job");
		}
		else {
			return complaints;
		}
	}
	
	
	/*  
	 * This method is use to update staff Member Partially(only manager or Office).
	 *  If staff Member does not exist then it will give Staff Member Not Found Exception .
	 */
	@PatchMapping("{staffId}/{mgrId}/{officeId}")
	public String updateStaffManager(@PathVariable(name="staffId")int staffId,@PathVariable(name="mgrId")int mgrId,@PathVariable(name="officeId")int officeId){
		OfficeStaffMember staff = managerService.getStaffMember(staffId);
		OfficeStaffMember mgr = managerService.getStaffMember(mgrId);
		Optional<CourierOfficeOutlet>office = courierOfficeOutletDao.findById(officeId);
		
		if(staff==null) {
			throw new  StaffMemberNotFoundException("Staff is not found with id"+staffId);
		}else {
			staff.setManager(mgr);
			staff.setOffice(office.get());
			managerService.updateStaff(staff);
			return "Update completed";
		}
		
	}
	
	/*  
	 * This method is use to update staff Member address only.
	 *  If staff Member does not exist then it will give Staff Member Not Found Exception .
	 */
	@PatchMapping(value="{staffId}/byHouseNo{houseNo}/byCity{city}/byState{state}/byCountry{country}/byZip{zip}/byStreet{street}")
	public String updateAdress(@PathVariable(name="staffId")int staffId,@PathVariable(name="houseNo")String houseNo,@PathVariable(name="city")String city,@PathVariable(name="state")String state,@PathVariable(name="country")String country,@PathVariable(name="zip")int zip,@PathVariable(name="street")String street){
		
		OfficeStaffMember staff = managerService.getStaffMember(staffId);
		Address address = new Address();
		
		if(staff==null) {
			throw new  StaffMemberNotFoundException("Staff is not found with id"+staffId);
		}else {
			address.setHouseNo(houseNo);
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setCountry(country);
			address.setZip(zip);
			staff.setAddress(address);
			managerService.updateStaff(staff);
			return "Update completed";
		}
		
	}
	
	
	
}
