package com.cg.mts.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Courier;
import com.cg.mts.exceptions.CourierNotFoundException;
import com.cg.mts.exceptions.StaffMemberNotFoundException;
import com.cg.mts.service.ShipmentServiceImpl;

@RestController
@RequestMapping("/shipments/")
public class ShipmentController {

	@Autowired
	ShipmentServiceImpl shipmentService; 
	
	
	@GetMapping("/login/{empId}")
	public ResponseEntity<String> validateLogin(@PathVariable("empId")int empId,HttpServletRequest request){
		
		
		if(shipmentService.validateUser(empId)){
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
	
	
	
	
	

	//To get the shipment details by shipment id
	@GetMapping("GetById/{shipmentId}")
	public ResponseEntity<Courier> getShipment(@Valid @PathVariable("shipmentId") int courierId) {
		Courier courier = shipmentService.getShipmentInfo(courierId);
		if(courier != null) {
			return new ResponseEntity<Courier>(courier, HttpStatus.OK);
		}
		throw new CourierNotFoundException("Courier Not Found!");
	}

	//to get all the shipments available in the database
	@GetMapping("GetAll")
	public ResponseEntity<?> getAllShipments() {
		List<Courier> list = shipmentService.getAllDeliveredShipments();
		if (list.isEmpty()) {
			return new ResponseEntity<String>("No Shipments Found!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Courier>>(list, HttpStatus.OK);
	}
	
	//to check the status of the shipment
	@GetMapping("checkStatus/{shipmentId}")
	public ResponseEntity<?> getShipmentStatus(@PathVariable("shipmentId") int courierId){
		if(shipmentService.checkShipmentStatus(courierId))
			return new ResponseEntity<String> ("Courier status :" + shipmentService.getShipmentInfo(courierId).getStatus(),
					HttpStatus.OK);
		return new ResponseEntity<String> ("Courier Not Found!", HttpStatus.BAD_REQUEST);
		
	}

	//to get the shipments by their delivery date
	@GetMapping("GetAllByDeliveredDate/{DeliveredDate}")
	public ResponseEntity<?> getAllShipmentsByDeliveredDate(@PathVariable("DeliveredDate") String date1) {
		LocalDate date = LocalDate.parse(date1);
		List<Courier> list = shipmentService.getAllShipmentsByDeliveredDate(date);
		int length = shipmentService.getAllShipmentsByDeliveredDate(date).size();
		if (length == 0) {
			return new ResponseEntity<String>("No Shipments Found!", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Courier>>(list, HttpStatus.OK);
	}
	
	//to add a shipment
	@PostMapping("initiateShipmentTransaction")
	public ResponseEntity<String> initiateShipmentTransaction(@RequestBody Courier courier) {
		if (shipmentService.initiateShipmentTransaction(courier))
			return new ResponseEntity<String>("Courier successfully added", HttpStatus.CREATED);
		return new ResponseEntity<String>("Unable to add Courier!", HttpStatus.EXPECTATION_FAILED);
	}

	//to delete a shipment by id
	@DeleteMapping("DeleteById/{shipmentId}")
	public ResponseEntity<?> deleteShipment(@PathVariable("shipmentId") int courierId) {
		if (shipmentService.deleteShipment(courierId)) {
			return new ResponseEntity<String>("Delete successfull", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Courier Not Found!", HttpStatus.NOT_FOUND);
		}
	}

	//to delete all the shipments
	@DeleteMapping("DeleteAll")
	public ResponseEntity<?> deleteAllShipments() {
		if (shipmentService.deleteAllShipments())
			return new ResponseEntity<String>("All Shipments Deleted!", HttpStatus.OK);
		return new ResponseEntity<String>("No Shipments Found To Delete!", HttpStatus.BAD_REQUEST);
	}

	//to delete all the delivered shipments by delivered date
	@DeleteMapping("DeleteByDeliveredDate/{DeliveredDate}")
	public ResponseEntity<?> deleteAllShipmentsByDeliveredDate(@PathVariable("DeliveredDate") String date1) {
		LocalDate date = LocalDate.parse(date1);
		if (shipmentService.deleteAllShipmentsByDeliveredDate(date)) {
			return new ResponseEntity<String>("Deleted all the Shipments with date " + date1 + " !", HttpStatus.OK);
		}
		return new ResponseEntity<String>("No Shipments Found!", HttpStatus.BAD_REQUEST);
	}

	//to update a particular shipment
	@PutMapping("update")
	public ResponseEntity<?> updateShipment(@RequestBody Courier courier) {
		if (shipmentService.updateShipment(courier))
			return new ResponseEntity<String>("Update successfull", HttpStatus.OK);
		return new ResponseEntity<String>("Unable to update", HttpStatus.NOT_FOUND);
	}
 
	//to reject particular shipment by shipment id
	@PutMapping("rejectingShipment/{shipmentId}")
	public ResponseEntity<?> rejectShipment(@PathVariable("shipmentId") int courierId){
		if(shipmentService.rejectShipmentTransaction(courierId)) {
			return new ResponseEntity<String> ("Shipment rejected", HttpStatus.OK);
		}
		return new ResponseEntity<String> ("Shipment Not Found!", HttpStatus.BAD_REQUEST);
	}
	
	//to close the transaction of the delivered shipment
	@PutMapping("closingShipment/{shipmentId}")
	public ResponseEntity<?> closeShipmentTransaction(@PathVariable("shipmentId") int courierId){
		if(shipmentService.closeShipmentTransaction(courierId)) {
			return new ResponseEntity<String> ("Shipment Closed!", HttpStatus.OK);
		}
		return new ResponseEntity<String> ("Shipment Not Found!", HttpStatus.BAD_REQUEST);
	}
}
