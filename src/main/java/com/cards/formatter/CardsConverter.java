package com.cards.formatter;

import com.cards.entity.Cards;
import com.cards.service.CardsService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;


public class CardsConverter implements Formatter<Cards>{

private final CardsService crdServ;

    public CardsConverter(CardsService cds) {
        this.crdServ = cds;
    }

    @Override
    public String print(Cards object, Locale locale) {
        return object.getCardId().toString();
    }

    @Override
    public Cards parse(String text, Locale locale) throws ParseException {
        return crdServ.get(Integer.parseInt(text));
    }
}