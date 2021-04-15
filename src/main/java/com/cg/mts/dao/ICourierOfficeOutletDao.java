package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.CourierOfficeOutlet;

@Repository("courierOfficeOutletDao")
public interface ICourierOfficeOutletDao  extends JpaRepository<CourierOfficeOutlet, Integer> {

}
