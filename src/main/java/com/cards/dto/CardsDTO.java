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

@JsonPropertyOrder({ "cardId", "bankApproval", "pin", "issueDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardsDTO implements Serializable {
    
    @JsonProperty(value = "cardId")
    public Integer cardId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "requests")
    public RequestsEmbeddedDTO requests;
    @JsonProperty(value = "pin")
    public Integer pin;
    @JsonProperty(value = "issueDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate issueDate;

    public CardsDTO() {
    }

    public CardsDTO(Integer cardId, RequestsEmbeddedDTO requests, Integer pin, LocalDate issueDate) {
       this.cardId = cardId;
       this.requests = requests;
       this.pin = pin;
       this.issueDate = issueDate;
    }

    public Integer getCardId() {
        return this.cardId;
    }
    
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public RequestsEmbeddedDTO getRequests() {
        return this.requests;
    }
    
    public void setRequests(RequestsEmbeddedDTO requests) {
        this.requests = requests;
    }

    public Integer getPin() {
        return this.pin;
    }
    
    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public LocalDate getIssueDate() {
        return this.issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

}


