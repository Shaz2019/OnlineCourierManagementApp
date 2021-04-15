package com.cg.mts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.dao.IComplaintDao;
import com.cg.mts.dao.ICourierDao;
import com.cg.mts.dao.IOfficeStaffMemberDao;
import com.cg.mts.entities.Address;
import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.Manager;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;

import com.cg.mts.service.ManagerServiceImpl;

@SpringBootTest
class ManagerServiceTest {

	@Autowired
	ManagerServiceImpl  managerServices;
	
	
	@MockBean
	ICourierDao courierDao;
	
	@MockBean
	IOfficeStaffMemberDao staffMemberDao;
	
	@MockBean
	IComplaintDao complaintDao;
	
	
	
	@Test
	@DisplayName("Test for adding Staff")
	public void addStaff() {
		OfficeStaffMember staff = new OfficeStaffMember();
		Address address = new Address("12c","MG Road","Motihari","Bihar","India",845401);
		CourierOfficeOutlet office = new CourierOfficeOutlet(112,LocalTime.of(10, 00),LocalTime.of(05, 00));
		OfficeStaffMember staff1 = new OfficeStaffMember(111,"Amit","Mgr");
		
		staff.setEmpId(118);
		staff.setName("Amit");
		staff.setRole("emp");
		staff.setAddress(address);
		staff.setOffice(office);
		staff.setManager(staff1);
		
		when(staffMemberDao.save(staff)).thenReturn(staff);
		assertEquals(true, managerServices.addStaffMember(staff));
		
	}
	
	
	@Test
	@DisplayName("Test for deleting staff")
	public void removeStaff() {
		OfficeStaffMember staff = new OfficeStaffMember();
		Address address = new Address("12c","MG Road","Motihari","Bihar","India",845401);
		CourierOfficeOutlet office = new CourierOfficeOutlet(112,LocalTime.of(10, 00),LocalTime.of(05, 00));
		OfficeStaffMember staff1 = new OfficeStaffMember(111,"Amit","Mgr");
		
		staff.setEmpId(118);
		staff.setName("Amit");
		staff.setRole("emp");
		staff.setAddress(address);
		staff.setOffice(office);
		staff.setManager(staff1);
		
		
		when(staffMemberDao.findById(staff.getEmpId())).thenReturn(Optional.of(staff));
		assertEquals(true, managerServices.removeStaffMember(staff.getEmpId()));
		
	}
	
	@Test
	@DisplayName("Test for get staff")
	public void getStaff() {
		OfficeStaffMember staff = new OfficeStaffMember();
		Address address = new Address("12c","MG Road","Motihari","Bihar","India",845401);
		CourierOfficeOutlet office = new CourierOfficeOutlet(112,LocalTime.of(10, 00),LocalTime.of(05, 00));
		OfficeStaffMember staff1 = new OfficeStaffMember(111,"Amit","Mgr");
		OfficeStaffMember staff2 = new OfficeStaffMember();
		
		staff.setEmpId(118);
		staff.setName("Amit");
		staff.setRole("emp");
		//staff.setAddress(address);
		//staff.setOffice(office);
		//staff.setManager(staff1);
		
		//when(staffMemberRepo.save(staff)).thenReturn(staff);
		when(staffMemberDao.findById(118)).thenReturn(Optional.of(staff));
		//if(staffMemberRepo.existsById(staff.getEmpId())) {
		assertEquals(staff, managerServices.getStaffMember(staff.getEmpId()));
		//}

	}

	@Test
	@DisplayName("Test to get courier status")
	public void getCourierStatus() {
		
		Courier courier1= new Courier();
		Courier courier2= new Courier();
		courier1.setCourierId(111);
		courier1.setConsignmentNo(112);
		courier1.setStatus(CourierStatus.delivered);
		courier1.setInitiatedDate(LocalDate.of(2018, 11, 16));
		courier1.setDeliveredDate(LocalDate.of(2018, 11, 28));
		
		
		when(courierDao.findById(courier1.getCourierId())).thenReturn(Optional.of(courier1));
		
		assertEquals(courier1.getStatus(), managerServices.getCourierStatus(courier1.getCourierId()));
		
		
	}
	
	@Test
	@DisplayName("Test to get Registed Complaint")
	public void getRegistedComplaint() {
		Complaint complaint = new Complaint();
		Complaint complaint1 = new Complaint();
		complaint.setComplaintId(1);
		complaint.setShortDescription("Item is Not Delivered");
		complaint.setShortDescription("Deliveriy date Was 28/02/2021 butstill 1 month , my parcel is not delivered ");
		
		
		
		when(complaintDao.findById(complaint.getComplaintId())).thenReturn(Optional.of(complaint));
		
		assertEquals(complaint, managerServices.getRegisteredComplaint(complaint.getComplaintId()));
	}
	
	@Test
	@DisplayName("Test to get all Registed Complaint")
	public void getAllRegistedComplaint() {
		
		when(complaintDao.getAllComplaint()).thenReturn(Stream.of(
				new Complaint("Item is Not Delivered","Deliveriy date Was 28/02/2021 butstill 1 month , my parcel is not delivered "),
				new Complaint("Item is Not Delivered","Deliveriy date Was 28/02/2021 butstill 1 month , my parcel is not delivered "))
				.collect(Collectors.toList()));
		
		assertEquals(2, managerServices.getAllComplaints().size());

	}
	
	



}
