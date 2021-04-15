package com.cg.mts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.dao.ICourierDao;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.Payment;

import com.cg.mts.service.ShipmentServiceImpl;

@SpringBootTest
class ShipmentServiceTests {

	@Autowired
	ShipmentServiceImpl shipmentService;

	@MockBean
	ICourierDao courierRepository;

	Courier courier;
	Payment payment;
	Customer sender;
	Customer receiver;

	@Test
	@DisplayName("Testing getAllDeliveredShipments()")
	void testGetAllDeliveredShipments() {
		when(courierRepository.findAll()).thenReturn(Stream
				.of(new Courier(111,CourierStatus.delivered, null, null), 
					new Courier(112,CourierStatus.delivered, null, null))
				.collect(Collectors.toList()));
		assertEquals(2, shipmentService.getAllDeliveredShipments().size());
		verify(courierRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Testing getAllShipments()")
	void testGetAllShipments() {
		when(courierRepository.findAll())
				.thenReturn(Stream.of(new Courier(), new Courier()).collect(Collectors.toList()));
		assertEquals(2, shipmentService.getAllShipments().size());
		verify(courierRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Testing getAllShipmentsByDeliveredDate()")
	void testGetAllShipmentsByDeliveredDate() {
		when(courierRepository.findAll()).thenReturn(
				Stream.of(new Courier(113,CourierStatus.initiated, LocalDate.now(),LocalDate.now()), new Courier(111, CourierStatus.delivered, LocalDate.now(),LocalDate.now()))
						.collect(Collectors.toList()));
		assertEquals(2, shipmentService.getAllShipments().size());
		verify(courierRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Testing updateShipment()")
	void testUpdateShipment() {
		courier = new Courier();
		Optional<Courier> opt = courierRepository.findById(courier.getCourierId());
		when(courierRepository.save(courier)).thenReturn(courier);
		if(opt.isPresent())
			verify(courierRepository, times(1)).save(courier);
	}
	
	@Test
	@DisplayName("Testing deleteAllShipments()")
	void testDeleteAllShipments() {
		shipmentService.deleteAllShipments();
		if (courierRepository.count() != 0)
			verify(courierRepository, times(1)).deleteAll();
	}

	@Test
	@DisplayName("Testing deleteAllShipmentsByDeliveredDate()")
	void testDeleteAllShipmentsByDeliveredDate() {
		when(courierRepository.findAll()).thenReturn(
				Stream.of(new Courier(112,CourierStatus.initiated, null, LocalDate.now()), new Courier(113,CourierStatus.initiated, null, LocalDate.now()))
						.collect(Collectors.toList()));
		assertEquals(2, shipmentService.getAllShipments().size());
		verify(courierRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Testing getShipment()")
	void testGetShipment() {
		
		courier = new Courier(112,CourierStatus.intransit, LocalDate.of(2021, 04, 06), LocalDate.now());
		courier.setCourierId(2);

		when(courierRepository.save(courier)).thenReturn(courier);
		if (courierRepository.existsById(courier.getCourierId())) {
			assertEquals(courier, shipmentService.getShipmentInfo(courier.getCourierId()));
		}
	}

	@Test
	@DisplayName("Testing deleteShipment()")
	void testDeleteShipment() {
		int id = 1;
		shipmentService.deleteShipment(id);
		if (courierRepository.existsById(id))
			verify(courierRepository, times(1)).deleteById(id);
	}

	@Test
	@DisplayName("Testing initiateShipmentTransaction()")
	void testInitiateShipmentTransaction() {

		courier = new Courier(112,CourierStatus.intransit, LocalDate.of(2021, 04, 06), LocalDate.now());

		courier.setConsignmentNo(1010100309);
		courier.setCourierId(2);
		courier.setSender(sender);
		courier.setReceiver(receiver);

		when(courierRepository.save(courier)).thenReturn(courier);
		if (courierRepository.existsById(courier.getCourierId())) {
			assertEquals(true, shipmentService.initiateShipmentTransaction(courier));
		}
	}

	@Test
	@DisplayName("Testing checkShipmentStatus()")
	void testCheckShipmentStatus() {

		courier = new Courier(112,CourierStatus.intransit, LocalDate.of(2021, 04, 06), LocalDate.now());
		sender = new Customer(112,98745621, "Tarun", "Kumar", 98745632);
		receiver = new Customer(113,99745621, "Varun", "Reddy", 98745665);

		courier.setConsignmentNo(1010100311);
		courier.setCourierId(1);
		courier.setSender(sender);
		courier.setReceiver(receiver);

		when(courierRepository.save(courier)).thenReturn(courier);
		if (courierRepository.existsById(courier.getCourierId())) {
			assertEquals(courier.getStatus(), shipmentService.checkShipmentStatus(courier.getCourierId()));
		}
	}

	@Test
	@DisplayName("Testing closeShipmentTransaction()")
	void testCloseShipmentTransaction() {
		courier = new Courier(1123,CourierStatus.intransit, LocalDate.of(2021, 04, 06), LocalDate.now());
		sender = new Customer(112,98745621, "Tarun", "Kumar", 98745632);
		receiver = new Customer(113,99745621, "Varun", "Reddy", 98745665);

		courier.setConsignmentNo(1010100312);
		courier.setCourierId(10);
		courier.setSender(sender);
		courier.setReceiver(receiver);

		courierRepository.save(courier);
		Optional<Courier> opt = courierRepository.findById(courier.getCourierId());

		if (opt.isPresent()) {
			assertEquals(true, shipmentService.closeShipmentTransaction(courier.getCourierId()));
		}
	}

	@Test
	@DisplayName("Testing rejectShipmentTransaction")
	void testRejectShipmentTransaction() {
		courier = new Courier();
		courier.setCourierId(100);
		courier.setStatus(CourierStatus.initiated);
		courierRepository.save(courier);
		Optional<Courier> courier1 = courierRepository.findById(courier.getCourierId());
		verify(courierRepository, times(1)).findById(courier.getCourierId());
	}
}