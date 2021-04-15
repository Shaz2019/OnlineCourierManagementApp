package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.Address;

@Repository("addressDao")
public interface IAddressDao extends JpaRepository<Address, Integer> {

}
