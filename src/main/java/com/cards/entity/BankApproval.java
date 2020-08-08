package com.cards.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="BANK_APPROVAL"
    ,schema="CARDS"
)
public class BankApproval implements Serializable {


     private Integer approvalId;
     @NotNull(message = "{notNull}")
     private Requests requests;
     @NotNull(message = "{notNull}")
     private LocalDate approvalDate;

    public BankApproval() {
    }

	
    public BankApproval(Integer approvalId, Requests requests, LocalDate approvalDate) {
        this.approvalId = approvalId;
        this.requests = requests;
        this.approvalDate = approvalDate;
    }
   
    @Id 
    @Column(name="APPROVAL_ID", unique=true, nullable=false, precision=5, scale=0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BankApprovalSeq")
    @SequenceGenerator(name = "BankApprovalSeq", sequenceName = "CARDS.BANL_APPROVAL_SEQ",allocationSize = 1)
    public Integer getApprovalId() {
        return this.approvalId;
    }
    
    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="REQUEST_ID", nullable=false)
    public Requests getRequests() {
        return this.requests;
    }
    
    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Column(name="APPROVAL_DATE", nullable=false, length=7)
    public LocalDate getApprovalDate() {
        return this.approvalDate;
    }
    
    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

}


