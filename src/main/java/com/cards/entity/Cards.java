package com.cards.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="CARDS"
    ,schema="CARDS"
)
public class Cards implements Serializable {


     private Integer cardId;
     @NotNull(message = "{notNull}")
     private BankApproval bankApproval;
     @Digits(fraction = 0, integer = 5 ,message = "{maxSize} {integer}")
     @NotNull(message = "{notNull}")
     private Integer pin;
     @NotNull(message = "{notNull}")
     private Date issueDate;

    public Cards() {
    }

    public Cards(Integer cardId, BankApproval bankApproval, Integer pin, Date issueDate) {
       this.cardId = cardId;
       this.bankApproval = bankApproval;
       this.pin = pin;
       this.issueDate = issueDate;
    }
   
    @Id 
    @Column(name="CARD_ID", unique=true, nullable=false, precision=5, scale=0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CardsSeq")
    @SequenceGenerator(name = "CardsSeq", sequenceName = "CARDS.CARDS_SEQ",allocationSize = 1)
    public Integer getCardId() {
        return this.cardId;
    }
    
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="APPROVAL_ID", nullable=false)
    public BankApproval getBankApproval() {
        return this.bankApproval;
    }
    
    public void setBankApproval(BankApproval bankApproval) {
        this.bankApproval = bankApproval;
    }

    
    @Column(name="PIN", nullable=false, precision=5, scale=0)
    public Integer getPin() {
        return this.pin;
    }
    
    public void setPin(Integer pin) {
        this.pin = pin;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="ISSUE_DATE", nullable=false, length=7)
    public Date getIssueDate() {
        return this.issueDate;
    }
    
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }




}


