package com.cards.dto;

import java.io.Serializable;

public class ClientsEmbeddedDTO implements Serializable {

    public Integer clientId;
     
    public ClientsEmbeddedDTO() {
    }

    public Integer getClientId() {
        return this.clientId;
    }
    
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

}


