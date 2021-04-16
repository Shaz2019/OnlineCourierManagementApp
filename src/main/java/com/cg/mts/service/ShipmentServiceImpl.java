package com.cg.mts.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.dao.ICourierDao;
import com.cg.mts.dao.IOfficeStaffMemberDao;
import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.CourierNotFoundException;

@Service("shipmentService")
public class ShipmentServiceImpl implements IShipmentService {

	@Autowired
	ICourierDao courierDao;
	
	@Autowired
	IOfficeStaffMemberDao staffMemberDao;

	//to initiate the shipment
	@Override
	public boolean initiateShipmentTransaction(Courier courier) {
		if(!courierDao.existsById(courier.getCourierId()))	{
			courier.setStatus(CourierStatus.initiated);
			courier.setInitiatedDate(LocalDate.now());
			courier.setDeliveredDate(LocalDate.now().plusDays(7));
			courierDao.save(courier);
			return true;
		}
		return false;
	}

	//to check the status of a particular shipment
	@Override
	public boolean checkShipmentStatus(int courierId) {
		Optional<Courier> courier = courierDao.findById(courierId);
		if(courier.isPresent())
			return true;
		return false;
	}

	//to close the shipment by shipment id
	@Override
	public boolean closeShipmentTransaction(int courierId) {
		if (courierDao.existsById(courierId)) {
			// courierDao.deleteById(courierId);
			Optional<Courier> courier = courierDao.findById(courierId);
			Courier courier1 = courier.get();
			courier1.setStatus(CourierStatus.delivered);
			courier1.setDeliveredDate(LocalDate.now());
			courierDao.save(courier1);
			return true;
		}
		return false;
	}

	//to reject a particular shipment by id
	@Override
	public boolean rejectShipmentTransaction(int courierId) {
		Optional<Courier> courier = courierDao.findById(courierId);
		if(courier.isPresent()) {
			Courier courier1 = courier.get();
			courier1.setStatus(CourierStatus.rejected);
			courierDao.save(courier1);
			return true;
		}
		return false;
	}

	//to update the existing shipment
	@Override
	public boolean updateShipment(Courier courier) {
		if ((courierDao.existsById(courier.getCourierId()))) {
			courierDao.save(courier);
			return true;
		}
		return false;
	}

	//to get the details of particular shipment by courier id
	@Override
	public Courier getShipmentInfo(int courierId) {
		if (courierDao.existsById(courierId)) {
			Optional<Courier> courier = courierDao.findById(courierId);
			if (courier.get() == null) {
				throw new CourierNotFoundException("Courier Not Found!");
			}
			return courier.get();
		}
		return null;
	}

	//to delete particular shipment by courier id
	@Override
	public boolean deleteShipment(int courierId) {
		if (courierDao.existsById(courierId)) {
			courierDao.deleteById(courierId);
			return true;
		}
		return false;
	}

	//to get all the delivered shipments
	@Override
	public List<Courier> getAllDeliveredShipments() {
		Iterable<Courier> iterable = courierDao.findAll();
		List<Courier> list = new ArrayList<>();
		for (Courier c : iterable) {
			if (c.getStatus() == CourierStatus.delivered) {
				list.add(c);
			}
		}
		return list;
	}

	//to get all the delivered shipments by delivered date
	@Override
	public List<Courier> getAllShipmentsByDeliveredDate(LocalDate date) {
		List<Courier> list = new ArrayList<>();
		if (courierDao.count() != 0) {
			Iterable<Courier> iterable = courierDao.findAll();
			
			for (Courier c : iterable) {
				if (c.getDeliveredDate().isEqual(date)) {
					list.add(c);
				}
			}
			return list;
		}
		return list;
	}

	//to get all the shipments available in the database
	@Override
	public List<Courier> getAllShipments() {
		Iterable<Courier> iterable = courierDao.findAll();
		List<Courier> list = new ArrayList<>();
		for (Courier c : iterable) {
			list.add(c);
		}
		return list;
	}

	//to delete all the shipments in the database
	@Override
	public boolean deleteAllShipments() {
		if (courierDao.count() == 0) {
			return false;
		}
		courierDao.deleteAll();
		return true;
	}

	//to delete all the shipments which are delivered on particular date
	@Override
	public boolean deleteAllShipmentsByDeliveredDate(LocalDate date) {
		Iterable<Courier> iterable = courierDao.findAll();
		List<Integer> list = new ArrayList<>();
		for (Courier c : iterable) {
			if (c.getDeliveredDate().isEqual(date)) {
				list.add(c.getCourierId());
			}
		}
		if (list.size() == 0)
			return false;
		for (int i = 0; i < list.size(); i++) {
			courierDao.deleteById(list.get(i));
		}
		return true;
	}
	
	//To validate Login
	public boolean validateUser(int empId) {
		Optional<OfficeStaffMember> staff1= staffMemberDao.findById(empId);
		
		if(staff1.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
