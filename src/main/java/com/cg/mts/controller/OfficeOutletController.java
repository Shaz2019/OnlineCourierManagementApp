package com.cg.mts.controller;

import java.util.List;
import java.util.Optional;

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
import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.exceptions.OutletClosedException;
import com.cg.mts.exceptions.OutletNotFoundException;
import com.cg.mts.service.OfficeOutletServiceImpl;

@RestController
@RequestMapping("CourierOfficeOutlet")
public class OfficeOutletController {

	@Autowired
	OfficeOutletServiceImpl officeOutletService;

	@GetMapping("{officeId}")
	public ResponseEntity<CourierOfficeOutlet> getOfficeData(@PathVariable("officeId") int officeId) {

		CourierOfficeOutlet officeOutlet = officeOutletService.getOfficeInfo(officeId);
		if (officeOutlet == null)
			return new ResponseEntity("Courier Office with id " + officeId + " not found!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<CourierOfficeOutlet>(officeOutlet, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CourierOfficeOutlet>> getAllOfficesInfo() {

		List<CourierOfficeOutlet> offices = officeOutletService.getAllOfficesData();
		if (offices == null)
			return new ResponseEntity("No Office Available!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<List<CourierOfficeOutlet>>(offices, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> addOffice(@RequestBody CourierOfficeOutlet officeOutlet) {
		CourierOfficeOutlet office = officeOutletService.addNewOffice(officeOutlet);
		if (office == null)
			return new ResponseEntity("Insertion Error!", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<String>("Courier Office data added successfully!", HttpStatus.OK);
	}

	@DeleteMapping("{officeId}")
	public ResponseEntity<String> removeOffice(@PathVariable("officeId") int officeId) {

		if (officeOutletService.removeNewOffice(officeId))
			return new ResponseEntity<String>("Courier Office data removed successfully!", HttpStatus.OK);
		return new ResponseEntity("Deletion Error!", HttpStatus.BAD_REQUEST);	
	}

	@PatchMapping(value = "{courierOfficeId}/byHouseNo{houseNo}/byCity{city}/byState{state}/byCountry{country}/byZip{zip}/byStreet{street}")
	public String updateAdress(@PathVariable(name = "courierOfficeId") int courierOfficeId,
			@PathVariable(name = "houseNo") String houseNo, @PathVariable(name = "city") String city,
			@PathVariable(name = "state") String state, @PathVariable(name = "country") String country,
			@PathVariable(name = "zip") int zip, @PathVariable(name = "street") String street) {

		CourierOfficeOutlet courierOffice = officeOutletService.getOfficeInfo(courierOfficeId);
		Address address = new Address();

		if (courierOffice == null) {
			throw new OutletNotFoundException("Courier Office with id " + courierOfficeId + " not found");
		} else {
			address.setHouseNo(houseNo);
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setCountry(country);
			address.setZip(zip);
			courierOffice.setAddress(address);
			officeOutletService.updateCourierOfficeOutlet(courierOffice);
			return "Courier Office address sucessfully updated";
		}

	}

	@GetMapping("/office")
	public String checkOfficeStatus(int officeId) {
		CourierOfficeOutlet coo = officeOutletService.getOfficeInfo(officeId);
		if (officeOutletService.isOfficeOpen(coo))
			return "Courier Office is open";
		else if (officeOutletService.isOfficeClosed(coo))
			return "Courier Office is closed";
		else
			return "Courier Office is open";
	}
}
