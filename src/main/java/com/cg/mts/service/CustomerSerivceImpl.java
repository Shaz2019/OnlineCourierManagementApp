package com.cg.mts.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dao.IComplaintDao;
import com.cg.mts.dao.ICourierDao;
import com.cg.mts.dao.ICustomerDao;
import com.cg.mts.dao.IPaymentDao;
import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.entities.Payment;

@Service("customerService")
public class CustomerSerivceImpl implements ICustomerService {

	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private IComplaintDao complaintDao;
	@Autowired
	private ICourierDao courierDao;
	@Autowired
	private IPaymentDao paymentDao;
	@Autowired
	private ShipmentServiceImpl shipmentService;

	@Override
	public Customer addCustomer(Customer customer) {
		customerDao.save(customer);

		return customer;
	}

	@Override
	public Optional<Customer> getCustomer(int customerId) {

		return customerDao.findById(customerId);
	}

	@Override
	public List<Customer> getAllCustomers() {

		return customerDao.findAll();
	}

	@Override
	public void deleteCustomerById(int customerid) {
		customerDao.deleteById(customerid);

		Optional<Customer> customer2 = customerDao.findById(customerid);

		if (customer2.isPresent())
			System.out.println("Deletion unsuceesfull" + customer2.get());
		else
			System.out.println("Deleted successfully");
	}

	@Override
	public boolean updateCustomer(Customer customer) {
		if (customerDao.existsById(customer.getCustomerId())) {
			customerDao.save(customer);
			return true;
		}
		/*
		 * if (customerDao.findById(customer.getCustomerId()).isPresent()) {
		 * customerDao.save(customer); return true; }
		 */
		return false;
	}

	@Override
	public Courier initiateProcess(int senderCustomerId, int receiverCustomerId, int consignmentNo) {
		Optional<Customer> senderCustomer = customerDao.findById(senderCustomerId);
		Optional<Customer> receiverCustomer = customerDao.findById(receiverCustomerId);
		if (senderCustomer.isPresent() && receiverCustomer.isPresent()) {
			Courier courier = new Courier();
			courier.setSender(senderCustomer.get());
			courier.setReceiver(receiverCustomer.get());
			courier.setConsignmentNo(consignmentNo);
			courier.setStatus(CourierStatus.initiated);
			courier.setInitiatedDate(LocalDate.now());
			courier.setDeliveredDate(LocalDate.now().plusDays(5));
			courierDao.save(courier);
			return courier;
		} else
			return null;
	}

	@Override
	public Payment makePayment(int courierId, String mode) {
		if (courierDao.existsById(courierId)) {
			Payment payment = new Payment();
			payment.setPaymentDate(LocalDate.now());
			payment.setPaymentMode(mode);
			payment.setCourier(shipmentService.getShipmentInfo(courierId));
			paymentDao.save(payment);
			return payment;
		} else
			return null;
	}

	@Override
	public CourierStatus checkOnlineTrackingStatus(int courierId) {
		Optional<Courier> courier = courierDao.findById(courierId);
		return courier.get().getStatus();
	}

	@Override
	public Complaint registerComplaint(Complaint complaint) {
		complaintDao.save(complaint);
		return complaint;
	}
	
	public boolean validateUser(int customerId) {
		Optional<Customer> cust1= customerDao.findById(customerId);
		
		if(cust1.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
}
