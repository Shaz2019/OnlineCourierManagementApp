package com.cg.mts.service;

import java.util.List;
import java.util.Optional;

import com.cg.mts.entities.Complaint;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.Customer;
import com.cg.mts.entities.Payment;

public interface ICustomerService {

	public Customer addCustomer(Customer customer);

	public Optional<Customer> getCustomer(int customerId);

	public List<Customer> getAllCustomers();

	public void deleteCustomerById(int customerid);

	public boolean updateCustomer(Customer customer);

	public Courier initiateProcess(int senderCustomerId, int receiverCustomerId, int consignmentNo);

	public Payment makePayment(int courierId, String mode);

	public CourierStatus checkOnlineTrackingStatus(int consignmentno);

	public Complaint registerComplaint(Complaint complaint);

}
