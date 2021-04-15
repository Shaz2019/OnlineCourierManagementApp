package com.cg.mts.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.mts.entities.BankAccount;

@Repository("bankAccountDao")
public interface IBankAccountDao extends JpaRepository<BankAccount,Integer>{

}
