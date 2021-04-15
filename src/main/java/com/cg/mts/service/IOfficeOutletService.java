package com.cg.mts.service;

import java.util.List;
import java.util.Optional;

import com.cg.mts.entities.CourierOfficeOutlet;
import com.cg.mts.exceptions.OutletClosedException;
import com.cg.mts.exceptions.OutletNotFoundException;

public interface IOfficeOutletService {

	public CourierOfficeOutlet addNewOffice(CourierOfficeOutlet officeOutlet);

	public boolean removeNewOffice(int officeId);

	public boolean updateCourierOfficeOutlet(CourierOfficeOutlet officeOutlet);
	
	public CourierOfficeOutlet getOfficeInfo(int officeId) throws OutletNotFoundException;

	public List<CourierOfficeOutlet> getAllOfficesData();

	public boolean isOfficeOpen(CourierOfficeOutlet officeOutlet) throws OutletClosedException;

	public boolean isOfficeClosed(CourierOfficeOutlet officeOutlet) throws OutletClosedException;

}
