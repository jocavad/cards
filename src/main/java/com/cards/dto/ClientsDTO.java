package com.cards.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonPropertyOrder({ "clientId", "firstName", "lastName", "address", "email", "phoneNumber"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientsDTO implements Serializable {

    @JsonProperty(value = "clientId")
    public Integer clientId;
    @JsonProperty(value = "firstName")
    public String firstName;
    @JsonProperty(value = "lastName")
    public String lastName;
    @JsonProperty(value = "address")
    public String address;
    @JsonProperty(value = "email")
    public String email;
    @JsonProperty(value = "phoneNumber")
    public String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "requests")
    public Set<RequestsEmbeddedDTO> requests = new HashSet<>(0);

    public ClientsDTO() {
    }

    public ClientsDTO(Integer clientId, String firstName, String lastName, String address) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    public ClientsDTO(Integer clientId, String firstName, String lastName, String address, String email, String phoneNumber, Set<RequestsEmbeddedDTO> requests) {
       this.clientId = clientId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.address = address;
       this.email = email;
       this.phoneNumber = phoneNumber;
       this.requests = requests;
    }

    public Integer getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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


