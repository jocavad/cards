package com.cards.formatter;

import com.cards.entity.Requests;
import com.cards.service.RequestsService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;


public class RequestsConverter implements Formatter<Requests>{

private final RequestsService reqServ;

    public RequestsConverter(RequestsService reqServ) {
        this.reqServ = reqServ;
    }

    @Override
    public String print(Requests object, Locale locale) {
        return object.getRequestId().toString();
    }

    @Override
    public Requests parse(String text, Locale locale) throws ParseException {
        return reqServ.get(Integer.parseInt(text));
    }
}