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

@JsonPropertyOrder({ "approvalId", "approvalDate"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankApprovalDTO implements Serializable {

    @JsonProperty(value = "approvalId")
    public Integer approvalId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY,value = "requests")
    public RequestsEmbeddedDTO requests;
    @JsonProperty(value = "approvalDate")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd/MM/yyyy")
    public LocalDate approvalDate;

    public BankApprovalDTO() {
    }

    public BankApprovalDTO(Integer approvalId, RequestsEmbeddedDTO requests, LocalDate approvalDate) {
        this.approvalId = approvalId;
        this.requests = requests;
        this.approvalDate = approvalDate;
    }

    public Integer getApprovalId() {
        return this.approvalId;
    }
    
    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public RequestsEmbeddedDTO getRequests() {
        return this.requests;
    }
    
    public void setRequests(RequestsEmbeddedDTO requests) {
        this.requests = requests;
    }

    public LocalDate getApprovalDate() {
        return this.approvalDate;
    }
    
    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

}


