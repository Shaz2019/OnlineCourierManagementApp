package com.cg.mts.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Address;
import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.Payment;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.CustomerNotFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;
import com.cg.mts.service.CustomerSerivceImpl;
import com.cg.mts.service.ICustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	 CustomerSerivceImpl   customerSerivce;
	
	@GetMapping("/login/{customerId}")
	public ResponseEntity<String> validateLogin(@PathVariable("customerId")int customerId,HttpServletRequest request){
		
		
		if(customerSerivce.validateUser(customerId)){
			HttpSession session= request.getSession(true);
			session.setAttribute("userId", customerId);
		}
		else {
			throw new StaffMemberNotFoundException("Incorrect customerId");
		}
		
		return new ResponseEntity<String>("Login Success!",HttpStatus.OK);
		
		
	}
	
	
	@GetMapping("/logout")
	public ResponseEntity<String> logoutUser(HttpServletRequest request){
		HttpSession session= request.getSession();
		
		session.invalidate();
		
		return new ResponseEntity<String>("Logout Success!",HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<String> addNewCustomer(@RequestBody Customer customer) {

		Customer customer2 = customerSerivce.addCustomer(customer);
		if (customer2 == null)
			return new ResponseEntity("Insertion Error!", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<String>("Customer Added Successfully!", HttpStatus.OK);

	}

	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("customerId") int customerId) {
		Optional<Customer> customer = customerSerivce.getCustomer(customerId);
		if (customer == null)
			return new ResponseEntity("Courier Office with id " + customerId + " not found", HttpStatus.BAD_REQUEST);
		else {
			return new ResponseEntity<Customer>(customer.get(), HttpStatus.FOUND);
		}
	}

	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers(HttpServletRequest request) {
HttpSession session= request.getSession();
		
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("username");
		System.out.println("*******************" + userName+"*************************");
		System.out.println("*******************" + userId+"*************************");
		List<Customer> customers = customerSerivce.getAllCustomers();
		if (customers == null)
			return new ResponseEntity("No Customers Available!", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	@PatchMapping("/initiateProcess/{senderCustomerId}/{receiverCustomerId}/{consignmentNo}")
	public ResponseEntity<String> initiateProcess(@PathVariable("senderCustomerId") int senderCustomerId,
			@PathVariable("receiverCustomerId") int receiverCustomerId,
			@PathVariable("consignmentNo") int consignmentNo) {

		Courier courier = customerSerivce.initiateProcess(senderCustomerId, receiverCustomerId, consignmentNo);
		if (courier == null)
			return new ResponseEntity("Insertion Error!", HttpStatus.NOT_FOUND);

		return new ResponseEntity<String>("Courier Added Successfully!", HttpStatus.OK);

	}

	@PatchMapping("{courierId}/{paymentMode}")
	public ResponseEntity<String> makePayment(@PathVariable("courierId") int courierId,
			@PathVariable("paymentMode") String mode) {

		Payment payment = customerSerivce.makePayment(courierId, mode);
		if (payment == null)
			return new ResponseEntity("Insertion Error!", HttpStatus.NOT_FOUND);

		return new ResponseEntity<String>("Payment Added Successfully!", HttpStatus.OK);

	}

	@PostMapping("/complaint")
	public ResponseEntity<String> addNewComplaint(@RequestBody Complaint complaint) {
		Complaint complaint2 = customerSerivce.registerComplaint(complaint);
		if (complaint2 == null)
			return new ResponseEntity("Insertion Error!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>("Complaint Added Successfully!", HttpStatus.OK);
	}

	@DeleteMapping("/{customerid}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("customerid") Integer customerid) {

		customerSerivce.deleteCustomerById(customerid);

		Optional<Customer> customer2 = customerSerivce.getCustomer(customerid);
		// System.out.println("*********************************" + customer2);

		if (customer2.isPresent())
			return new ResponseEntity("Deletion Error!", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<String>("Customer Deleted Successfully!", HttpStatus.OK);

	}

	@GetMapping("courier/{courierId}")
	public ResponseEntity<?> getCourier(@PathVariable("courierId") int courierId) {// throws OutletNotFoundException {

		CourierStatus courier = customerSerivce.checkOnlineTrackingStatus(courierId);
		if (courier == null)
			return new ResponseEntity("Courier Office with id " + courierId + " not found", HttpStatus.BAD_REQUEST);
		else {
			return new ResponseEntity<CourierStatus>(courier, HttpStatus.FOUND);
		}
	}

	@PatchMapping("{customerId}/{firstName}/{lastName}/{mobileNo}")
	public String updateCustomerDetails(@PathVariable(name = "customerId") int customerId,
			@PathVariable(name = "firstName") String firstName, @PathVariable(name = "lastName") String lastName,
			@PathVariable(name = "mobileNo") int mobileNo) {

		Optional<Customer> customer = customerSerivce.getCustomer(customerId);
		// Customer customer1= new Customer();
		Customer customer2 = customer.get();

		if (customer2 == null) {
			throw new CustomerNotFoundException("Customer is not found with id" + customerId,"not found");
		} else {
			customer2.setFirstName(firstName);
			customer2.setLastName(lastName);
			customer2.setMobileNo(mobileNo);
			customerSerivce.updateCustomer(customer2);
			return "Update completed";
		}

	}
}
