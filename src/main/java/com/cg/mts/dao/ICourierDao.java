package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.Courier;
import com.cg.mts.entities.CourierStatus;

@Repository("courierDao")
public interface ICourierDao extends JpaRepository<Courier, Integer>{

	@Query("Select c.status from Courier c where c.courierId = :Id")
	CourierStatus getStatus(@Param("Id")int courierId);
}
