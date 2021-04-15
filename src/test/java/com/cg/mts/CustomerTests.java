package com.cg.mts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cg.mts.dao.IComplaintDao;
import com.cg.mts.dao.ICourierDao;
import com.cg.mts.dao.ICustomerDao;
import com.cg.mts.dao.IPaymentDao;
import com.cg.mts.entities.Address;
import com.cg.mts.entities.BankAccount;
import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.Payment;
import com.cg.mts.service.CustomerSerivceImpl;

@SpringBootTest
class CustomerTests {
	@Autowired
	CustomerSerivceImpl customerService;
	@MockBean
	ICustomerDao customerDao;
	@MockBean
	IComplaintDao complaintDao;
	@MockBean
	ICourierDao courierDao;
	@MockBean
	IPaymentDao paymentDao;

	@Test
	@DisplayName("Test for adding new customer")
	public void addCustomerTest() {
		Customer customer = new Customer();
		Address address = new Address("Pune", "india", "123", "Maharastra", "north Street", 234324);
		BankAccount bankAccount = new BankAccount(213213, "Tom", "Savings");
		address.setAddressId(11);
		customer.setCustomerId(44);
		customer.setAadharNo(0504);
		customer.setFirstName("Haruki");
		customer.setLastName("Roy");
		customer.setMobileNo(98745);
		customer.setAddress(address);
		customer.setBankAccount(bankAccount);
		Mockito.when(customerDao.save(customer)).thenReturn(customer);
		assertEquals(customer, customerService.addCustomer(customer));

	}

	@Test
    @DisplayName("Test for retrive Customer Details By id")
       public void getPaymentDetailsTest() {
		Customer cs = new Customer(123, 324324, "Kurumi", "Roy", 741524);
           
           Mockito.when(customerDao.findById(100))
           .thenReturn(java.util.Optional.ofNullable(cs));
           
       }

	@Test
	@DisplayName("Test for retriving all Customer Details")
	public void getAllCustomersTest() {
		Mockito.when(customerDao.findAll())
				.thenReturn(java.util.stream.Stream.of(new Customer(), new Customer()).collect(Collectors.toList()));

		assertEquals(2, customerService.getAllCustomers().size());
		verify(customerDao, times(1)).findAll();
	}

    @Test
    @DisplayName("Test for Deleteing Customer by Id")
    public void deleteByIdTestTest() {
    	int customerId = 1;
		customerService.deleteCustomerById(customerId);
        
        Mockito.verify(customerDao, times(1)).deleteById(customerId);
    }


	@Test
	@DisplayName("Test for Updating Customer")
	public void updateCustomerTest() {
        Customer cs = new Customer(101, 324324, "Kurumi", "Roy", 741524);
        customerService.addCustomer(cs);
		Optional<Customer> customer = customerDao.findById(101);
		if (customer.isPresent()) {
			customer.get().setFirstName("Shaz");
			Mockito.doReturn(customerDao.save(customer.get()));

		}
		Optional<Customer> updatedCustomer = customerDao.findById(101);
		if (updatedCustomer.isPresent()) {
			assertThat(updatedCustomer.get().getFirstName().equals("Kurumi"));
		}
	}

	@Test
	@DisplayName("Test for initiating courier")
	public void initiateProcessTest() {
		Courier courier = new Courier();
		Customer cs = new Customer(123, 324324, "Kurumi", "Roy", 741524);
		Customer rs = new Customer(154, 265412, "Suruki", "Roy", 654178);
		courier.setSender(cs);
		courier.setReceiver(rs);
		courier.setConsignmentNo(12);
		courier.setStatus(CourierStatus.initiated);
		courier.setInitiatedDate(LocalDate.now());
		courier.setDeliveredDate(LocalDate.now().plusDays(5));
		courier.setCourierId(1);

		Mockito.when(courierDao.save(courier)).thenReturn(courier);
		assertEquals(courier.getCourierId(), 1);

	}
	/*@Test
	 * @DisplayName("Test for Making Payment")
	public void makePayment() {
		Courier courier = new Courier(11, CourierStatus.initiated, LocalDate.now(), LocalDate.now().plusDays(3));
		Payment payment = new Payment();
		//payment.setPaymentId(15);
		payment.setPaymentMode("cash");
		//payment.setPaymentDate(LocalDate.now());
		payment.setCourier(courier);

		Mockito.when(paymentDao.save(payment)).thenReturn(payment);
		assertEquals(payment, customerService.makePayment(11, "cash"));
	}*/

	@Test
	@DisplayName("Test for checking courier status")
	public void checkCourierStatusTest() {

		Courier courier1 = new Courier();
		courier1.setCourierId(111);
		courier1.setConsignmentNo(112);
		courier1.setStatus(CourierStatus.delivered);
		courier1.setInitiatedDate(LocalDate.of(2018, 11, 16));
		courier1.setDeliveredDate(LocalDate.of(2018, 11, 28));


		Mockito.when(courierDao.findById(courier1.getCourierId())).thenReturn(Optional.of(courier1));
		assertEquals(courier1.getStatus(), customerService.checkOnlineTrackingStatus(courier1.getCourierId()));
	}

	@Test
	@DisplayName("Test for register complaint")
	public void registerComplaintTest() {
		Complaint complaint = new Complaint();
		Courier courier = new Courier(11, CourierStatus.initiated, LocalDate.now(), LocalDate.now().plusDays(3));
		complaint.setShortDescription("courier stck");
		complaint.setDetailedDescription("no update details since last few days");
		complaint.setCourierId(courier);
		complaint.setComplaintId(7);
		Mockito.when(complaintDao.save(complaint)).thenReturn(complaint);
		assertEquals(complaint, customerService.registerComplaint(complaint));
	}

}
