package com.cards.dto;

import java.io.Serializable;

public class BankApprovalEmbeddedDTO implements Serializable {

    public Integer approvalId;

    public BankApprovalEmbeddedDTO() {
    }

    public Integer getApprovalId() {
        return this.approvalId;
    }
    
    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

}


