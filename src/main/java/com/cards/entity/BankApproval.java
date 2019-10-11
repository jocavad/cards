package com.cards.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="BANK_APPROVAL"
    ,schema="CARDS"
    , uniqueConstraints = @UniqueConstraint(columnNames="REQUEST_ID") 
)
public class BankApproval implements Serializable {


     private Integer approvalId;
     @NotNull(message = "{notNull}")
     private Requests requests;
     @NotNull(message = "{notNull}")
     private Date approvalDate;
     private Set<Cards> cards = new HashSet<>(0);

    public BankApproval() {
    }

	
    public BankApproval(Integer approvalId, Requests requests, Date approvalDate) {
        this.approvalId = approvalId;
        this.requests = requests;
        this.approvalDate = approvalDate;
    }
    public BankApproval(Integer approvalId, Requests requests, Date approvalDate, Set<Cards> cards) {
       this.approvalId = approvalId;
       this.requests = requests;
       this.approvalDate = approvalDate;
       this.cards = cards;
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
    @JoinColumn(name="REQUEST_ID", unique=true, nullable=false)
    public Requests getRequests() {
        return this.requests;
    }
    
    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="APPROVAL_DATE", nullable=false, length=7)
    public Date getApprovalDate() {
        return this.approvalDate;
    }
    
    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="bankApproval")
    public Set<Cards> getCards() {
        return this.cards;
    }
    
    public void setCards(Set<Cards> cards) {
        this.cards = cards;
    }




}


