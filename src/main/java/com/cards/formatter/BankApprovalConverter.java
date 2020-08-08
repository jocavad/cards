package com.cards.formatter;

import com.cards.entity.BankApproval;
import com.cards.service.BankApprovalService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;


public class BankApprovalConverter implements Formatter<BankApproval>{

private final BankApprovalService baServ;

    public BankApprovalConverter(BankApprovalService bas) {
        this.baServ = bas;
    }

    @Override
    public String print(BankApproval object, Locale locale) {
        return object.getApprovalId().toString();
    }

    @Override
    public BankApproval parse(String text, Locale locale) throws ParseException {
        return baServ.get(Integer.parseInt(text));
    }
}