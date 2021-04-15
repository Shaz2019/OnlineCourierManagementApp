package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.OfficeStaffMember;

@Repository("officeStaffMemberDao")
public interface IOfficeStaffMemberDao extends JpaRepository<OfficeStaffMember, Integer> {
	
	@Query("from OfficeStaffMember c where c.empId= :Id")
	OfficeStaffMember getStaffDetails(@Param("Id")int empId);

}