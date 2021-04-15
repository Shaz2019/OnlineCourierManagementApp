package com.cg.mts.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.entities.OfficeStaffMember;
import com.cg.mts.exceptions.OutletClosedException;
import com.cg.mts.exceptions.OutletNotFoundException;
import com.cg.mts.dao.ICourierOfficeOutletDao;


@Service("courierOfficeOutletService")
public class OfficeOutletServiceImpl implements IOfficeOutletService {

	@Autowired
	ICourierOfficeOutletDao officeDao;

	

	//This method is used to  add new office 
	@Override
	public CourierOfficeOutlet addNewOffice(CourierOfficeOutlet officeOutlet) {

		if (!officeDao.existsById(officeOutlet.getOfficeId())) {
			officeDao.save(officeOutlet);
			return officeOutlet;
		}
		return null;
	}

	//This method is used to remove office
	@Override
	public boolean removeNewOffice(int officeId) {

		if(officeDao.existsById(officeId)) {
			officeDao.deleteById(officeId);
			return true;
		}
		return false;
	}

	//This method is used to update office
	@Override
	public boolean updateCourierOfficeOutlet(CourierOfficeOutlet officeOutlet) {
		if (officeDao.existsById(officeOutlet.getOfficeId())) {
			officeDao.save(officeOutlet);
			return true;
		}
		return false;

	}
	
	

	//This method is used to get office info
	@Override
	public CourierOfficeOutlet getOfficeInfo(int officeId) {

		if (officeDao.existsById(officeId)) {
			Optional<CourierOfficeOutlet> courierOffice = officeDao.findById(officeId);
			if (courierOffice.isPresent())
				return courierOffice.get();
		}
		throw new OutletNotFoundException("Courier Id " + officeId + " doesn't exist");
	}

	//This method is get all office data
	@Override
	public List<CourierOfficeOutlet> getAllOfficesData() {

		return officeDao.findAll();
	}

	//This method is used to check office is open.
	@Override
	public boolean isOfficeOpen(CourierOfficeOutlet officeOutlet) {
		if (LocalTime.now().isAfter(officeOutlet.getOpeningTime())
				&& LocalTime.now().isBefore(officeOutlet.getClosingTime())) {
			return true;
		}
		throw new OutletClosedException("Courier Office is closed");
	}

	//This method is used to check office is closed.
	@Override
	public boolean isOfficeClosed(CourierOfficeOutlet officeOutlet) {
		if (LocalTime.now().isBefore(officeOutlet.getOpeningTime())
				&& LocalTime.now().isAfter(officeOutlet.getClosingTime()))
			throw new OutletClosedException("Courier Office is closed");
		return false;
	}

}
