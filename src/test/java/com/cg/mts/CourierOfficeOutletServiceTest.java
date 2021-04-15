package com.cg.mts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.dao.ICourierOfficeOutletDao;
import com.cg.mts.entities.Address;
import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.OutletClosedException;
import com.cg.mts.exceptions.OutletNotFoundException;

import com.cg.mts.service.OfficeOutletServiceImpl;

@SpringBootTest
class CourierOfficeOutletServiceTest {

	@Test
	void contextLoads() {
	}

	@Autowired
	private OfficeOutletServiceImpl officeOutletService;

	@MockBean
	private ICourierOfficeOutletDao officeOutletDao;

	
	 
	@Test
	@DisplayName("Test for adding office")
	public void addingNewOfficeTest() {

		CourierOfficeOutlet courierOffice = new CourierOfficeOutlet(100, LocalTime.of(9, 0), LocalTime.of(18, 0));
		
		Address address = new Address("1BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);

		courierOffice.setAddress(address);

		when(officeOutletDao.save(courierOffice)).thenReturn(courierOffice);
		assertEquals(courierOffice, officeOutletService.addNewOffice(courierOffice));
	}

	@Test
	@DisplayName("Test for removing office")
	public void removingOfficeTest() {
		
		CourierOfficeOutlet courierOffice = new CourierOfficeOutlet(100, LocalTime.of(9, 0), LocalTime.of(18, 0));
		officeOutletService.removeNewOffice(courierOffice.getOfficeId());
	
		if(officeOutletDao.existsById(courierOffice.getOfficeId())) {
			verify(officeOutletDao, times(1)).deleteById(courierOffice.getOfficeId());
			assertEquals(true, officeOutletService.removeNewOffice(courierOffice.getOfficeId()));
		}
	}
	
	@Test
	@DisplayName("Test for update office")
	public void updateOfficeTest() {
		
		CourierOfficeOutlet courierOffice = new CourierOfficeOutlet(200, LocalTime.of(10, 0), LocalTime.of(20, 0));
		
		Address address = new Address("1BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);
		
		courierOffice.setAddress(address);
		if(officeOutletDao.existsById(courierOffice.getOfficeId())) {
			verify(officeOutletDao, times(1)).deleteById(courierOffice.getOfficeId());
			assertEquals(true, officeOutletService.updateCourierOfficeOutlet(courierOffice));
		}
	}

	@Test
	@DisplayName("Test to get office")
	public void getOfficeTest() {

		CourierOfficeOutlet courierOffice = new CourierOfficeOutlet(200, LocalTime.of(10, 0), LocalTime.of(20, 0));
	
		Address address = new Address("1BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);

		courierOffice.setAddress(address);

			when(officeOutletDao.save(courierOffice)).thenReturn(courierOffice);
	}

	@Test
	@DisplayName("Test to get all office")
	public void getAllOfficesTest() {

		CourierOfficeOutlet courierOffice1 = new CourierOfficeOutlet(200, LocalTime.of(10, 0), LocalTime.of(20, 0));
		CourierOfficeOutlet courierOffice2 = new CourierOfficeOutlet(300, LocalTime.of(11, 0), LocalTime.of(21, 0));
		CourierOfficeOutlet courierOffice3 = new CourierOfficeOutlet(400, LocalTime.of(12, 0), LocalTime.of(22, 0));

		Address address1 = new Address("1BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);

		courierOffice1.setAddress(address1);

		Address address2 = new Address("2BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);

		courierOffice2.setAddress(address2);

		Address address3 = new Address("3BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);

		courierOffice3.setAddress(address3);

		List<CourierOfficeOutlet> listOffice = new ArrayList<>();
		listOffice.add(courierOffice1);
		listOffice.add(courierOffice2);
		listOffice.add(courierOffice3);

		when(officeOutletDao.findAll()).thenReturn(listOffice);
		assertEquals(3, officeOutletService.getAllOfficesData().size());
	}

	@Test
	@DisplayName("Test to check office status")
	public void checkOfficeStatusTest() {

		CourierOfficeOutlet courierOffice = new CourierOfficeOutlet(200, LocalTime.of(7, 0), LocalTime.of(20, 0));

		Address address = new Address("1BC", "Ravi Street", "Sambalpur", "Odisha", "India", 768220);

		courierOffice.setAddress(address);

		if (LocalTime.now().isAfter(courierOffice.getOpeningTime())
				&& LocalTime.now().isBefore(courierOffice.getClosingTime()))
			assertEquals(true, officeOutletService.isOfficeOpen(courierOffice));
		else
			assertEquals(false, officeOutletService.isOfficeClosed(courierOffice));
	}
}
