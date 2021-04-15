package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.Manager;


@Repository("managerDao")
public interface IManagerDao  extends JpaRepository<Manager, Integer>  {

}

