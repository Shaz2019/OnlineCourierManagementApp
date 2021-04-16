package com.cg.mts.entities;

 

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 
@JsonIgnoreProperties({"hibernateLazyInitilizer","handler"})
@Entity
public class BankAccount {

 

    @Id
    private int accountNo;
    
    private String accountHolderName;
    private String accountType;

 


    public BankAccount() {

 

    }

 

    public BankAccount(int accountNo, String accountHolderName, String accountType) {
        super();
        this.accountNo = accountNo;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
    }

 

    public int getAccountNo() {
        return accountNo;
    }

 

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

 

    public String getAccountHolderName() {
        return accountHolderName;
    }

 

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

 

    public String getAccountType() {
        return accountType;
    }

 

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}