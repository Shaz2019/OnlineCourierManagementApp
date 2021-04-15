package com.cg.mts.service;

import java.time.LocalDate;
import java.util.List;

import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;

public interface IShipmentService {

	boolean initiateShipmentTransaction(Courier courier);

	boolean checkShipmentStatus(int courierId);

	boolean closeShipmentTransaction(int courierId);

	boolean rejectShipmentTransaction(int courierId);

	boolean updateShipment(Courier courier);

	Courier getShipmentInfo(int courierId);

	boolean deleteShipment(int courierId);

	List<Courier> getAllDeliveredShipments();

	List<Courier> getAllShipmentsByDeliveredDate(LocalDate date);

	List<Courier> getAllShipments();

	boolean deleteAllShipments();

	boolean deleteAllShipmentsByDeliveredDate(LocalDate date);

}
