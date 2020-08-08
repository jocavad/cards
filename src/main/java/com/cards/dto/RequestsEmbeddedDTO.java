package com.cards.dto;

import java.io.Serializable;

public class RequestsEmbeddedDTO implements Serializable {

    public Integer requestId;

    public RequestsEmbeddedDTO() {
    }

    public Integer getRequestId() {
        return this.requestId;
    }
    
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

}


