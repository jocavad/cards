package com.cards.dto;

import java.io.Serializable;

public class CardsEmbeddedDTO implements Serializable {
    
    public Integer cardId;
    
    public CardsEmbeddedDTO() {
    }

    public Integer getCardId() {
        return this.cardId;
    }
    
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

}


