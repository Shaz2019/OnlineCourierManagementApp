package com.cg.mts.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.Complaint;

@Repository("complaintDao")
public interface IComplaintDao extends JpaRepository<Complaint,Integer>{
	
	@Query("FROM Complaint c")
	List<Complaint> getAllComplaint();
	
	@Query("from Complaint c where c.complaintId=:id ")
	Complaint getComplaintById(@Param("id") int complaintId);

}
