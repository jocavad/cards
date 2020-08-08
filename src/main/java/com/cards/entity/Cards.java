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
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="CARDS"
    ,schema="CARDS"
)
public class Cards implements Serializable {


     private Integer cardId;
     @NotNull(message = "{notNull}")
     private Requests requests;
     @Digits(fraction = 0, integer = 5 ,message = "{maxSize} {integer}")
     @NotNull(message = "{notNull}")
     private Integer pin;
     @NotNull(message = "{notNull}")
     private LocalDate issueDate;

    public Cards() {
    }

    public Cards(Integer cardId, Requests requests, Integer pin, LocalDate issueDate) {
       this.cardId = cardId;
       this.requests = requests;
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
    @JoinColumn(name="REQUEST_ID", nullable=false)
    public Requests getRequests() {
        return this.requests;
    }
    
    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Column(name="PIN", nullable=false, precision=5, scale=0)
    public Integer getPin() {
        return this.pin;
    }
    
    public void setPin(Integer pin) {
        this.pin = pin;
    }

    @Column(name="ISSUE_DATE", nullable=false, length=7)
    public LocalDate getIssueDate() {
        return this.issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

}


