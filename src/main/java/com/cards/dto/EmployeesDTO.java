package com.cards.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({ "employeeId", "firstName", "lastName", "address", "phoneNumber"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeesDTO implements Serializable {

    @JsonProperty(value = "employeeId")
    public Integer employeeId;
    @JsonProperty(value = "firstName")
    public String firstName;
    @JsonProperty(value = "lastName")
    public String lastName;
    @JsonProperty(value = "address")
    public String address;
    @JsonProperty(value = "phoneNumber")
    public String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "requests")
    public Set<RequestsEmbeddedDTO> requests = new HashSet<>(0);

    public EmployeesDTO() {
    }

    public EmployeesDTO(Integer employeeId, String firstName, String lastName, String address) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    public EmployeesDTO(Integer employeeId, String firstName, String lastName, String address, String phoneNumber, Set<RequestsEmbeddedDTO> requests) {
       this.employeeId = employeeId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.address = address;
       this.phoneNumber = phoneNumber;
       this.requests = requests;
    }

    public Integer getEmployeeId() {
        return this.employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<RequestsEmbeddedDTO> getRequests() {
        return this.requests;
    }
    
    public void setRequests(Set<RequestsEmbeddedDTO> requests) {
        this.requests = requests;
    }

}


