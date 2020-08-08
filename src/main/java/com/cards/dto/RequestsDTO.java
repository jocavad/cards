package com.cards.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({ "requestId", "clients", "employees", "accountNumber", "requestDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestsDTO implements Serializable {

    @JsonProperty(value = "requestId")
    public Integer requestId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "clients")
    public ClientsEmbeddedDTO clients;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "employees")
    public EmployeesEmbeddedDTO employees;
    @JsonProperty(value = "accountNumber")
    public String accountNumber;
    @JsonProperty(value = "requestDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate requestDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "bankApproval")
    public Set<BankApprovalEmbeddedDTO> bankApproval = new HashSet<>(0);
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "cards")
    public Set<CardsEmbeddedDTO> cards = new HashSet<>(0);
    
    public RequestsDTO() {
    }

    public RequestsDTO(Integer requestId, ClientsEmbeddedDTO clients, EmployeesEmbeddedDTO employees, String accountNumber, LocalDate requestDate) {
        this.requestId = requestId;
        this.clients = clients;
        this.employees = employees;
        this.accountNumber = accountNumber;
        this.requestDate = requestDate;
    }
    public RequestsDTO(Integer requestId, ClientsEmbeddedDTO clients, EmployeesEmbeddedDTO employees, String accountNumber, LocalDate requestDate, Set<BankApprovalEmbeddedDTO> bankApproval, Set<CardsEmbeddedDTO> cards) {
       this.requestId = requestId;
       this.clients = clients;
       this.employees = employees;
       this.accountNumber = accountNumber;
       this.requestDate = requestDate;
       this.bankApproval = bankApproval;
       this.cards = cards;
    }

    public Integer getRequestId() {
        return this.requestId;
    }
    
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public ClientsEmbeddedDTO getClients() {
        return this.clients;
    }
    
    public void setClients(ClientsEmbeddedDTO clients) {
        this.clients = clients;
    }

    public EmployeesEmbeddedDTO getEmployees() {
        return this.employees;
    }
    
    public void setEmployees(EmployeesEmbeddedDTO employees) {
        this.employees = employees;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getRequestDate() {
        return this.requestDate;
    }
    
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public Set<BankApprovalEmbeddedDTO> getBankApproval() {
        return this.bankApproval;
    }
    
    public void setBankApproval(Set<BankApprovalEmbeddedDTO> bankApproval) {
        this.bankApproval = bankApproval;
    }

    public Set<CardsEmbeddedDTO> getCards() {
        return this.cards;
    }
    
    public void setCards(Set<CardsEmbeddedDTO> cards) {
        this.cards = cards;
    }


}


