package com.cg.mts.entities;

 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

 

@Entity
@Table(name="complaint")
public class Complaint {

 

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int complaintId;
    
    
    private String shortDescription;
    private String detailedDescription;
   
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name="courierId")
    private Courier courierId;
    
    
  
    
    

 

    public Complaint() {
        
    }

 

    public Complaint( String shortDescription, String detailedDescription) {
        super();
    
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        
    }

 

    
    public int getComplaintId() {
        return complaintId;
    }

 

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

 

    

 

    public String getShortDescription() {
        return shortDescription;
    }

 

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

 

    public String getDetailedDescription() {
        return detailedDescription;
    }

 

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }



	public Courier getCourierId() {
		return courierId;
	}



	public void setCourierId(Courier courierId) {
		this.courierId = courierId;
	}
 

}