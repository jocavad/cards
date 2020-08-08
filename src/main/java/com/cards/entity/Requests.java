package com.cards.entity;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="REQUESTS"
    ,schema="CARDS"
)
public class Requests implements Serializable {


     private Integer requestId;
     @NotNull(message = "{notNull}")
     private Clients clients;
     @NotNull(message = "{notNull}")
     private Employees employees;
     @NotEmpty(message = "{notNull}")
     @Size(max = 20 ,message = "{maxSize} {max}")
     private String accountNumber;
     @NotNull(message = "{notNull}")
     private LocalDate requestDate;
     private Set<BankApproval> bankApproval = new HashSet<>(0);
     private Set<Cards> cards = new HashSet<>(0);
     
    public Requests() {
    }

	
    public Requests(Integer requestId, Clients clients, Employees employees, String accountNumber, LocalDate requestDate) {
        this.requestId = requestId;
        this.clients = clients;
        this.employees = employees;
        this.accountNumber = accountNumber;
        this.requestDate = requestDate;
    }
    public Requests(Integer requestId, Clients clients, Employees employees, String accountNumber, LocalDate requestDate, Set<BankApproval> bankApproval, Set<Cards> cards) {
       this.requestId = requestId;
       this.clients = clients;
       this.employees = employees;
       this.accountNumber = accountNumber;
       this.requestDate = requestDate;
       this.bankApproval = bankApproval;
       this.cards = cards;
    }
   
    @Id 
    @Column(name="REQUEST_ID", unique=true, nullable=false, precision=5, scale=0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequestSeq")
    @SequenceGenerator(name = "RequestSeq", sequenceName = "CARDS.REQUESTS_SEQ",allocationSize = 1)
    public Integer getRequestId() {
        return this.requestId;
    }
    
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CLIENT_ID", nullable=false)
    public Clients getClients() {
        return this.clients;
    }
    
    public void setClients(Clients clients) {
        this.clients = clients;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMPLOYEE_ID", nullable=false)
    public Employees getEmployees() {
        return this.employees;
    }
    
    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    @Column(name="ACCOUNT_NUMBER", nullable=false, length=60)
    public String getAccountNumber() {
        return this.accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name="REQUEST_DATE", nullable=false, length=7)
    public LocalDate getRequestDate() {
        return this.requestDate;
    }
    
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="requests")
    public Set<BankApproval> getBankApproval() {
        return this.bankApproval;
    }
    
    public void setBankApproval(Set<BankApproval> bankApproval) {
        this.bankApproval = bankApproval;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="requests")
    public Set<Cards> getCards() {
        return this.cards;
    }
    
    public void setCards(Set<Cards> cards) {
        this.cards = cards;
    }
}


