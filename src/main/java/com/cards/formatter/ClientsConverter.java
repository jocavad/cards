package com.cards.formatter;

import com.cards.entity.Clients;
import com.cards.service.ClientsService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;


public class ClientsConverter implements Formatter<Clients>{

private final ClientsService cliServ;

    public ClientsConverter(ClientsService cls) {
        this.cliServ = cls;
    }
 
    @Override
    public String print(Clients object, Locale locale) {
        return object.getClientId().toString();
    }

    @Override
    public Clients parse(String text, Locale locale) throws ParseException {
        return cliServ.get(Integer.parseInt(text));
    }
}