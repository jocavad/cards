package com.cards.formatter;

import com.cards.entity.Employees;
import com.cards.service.EmployeesService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;


public class EmployeesConverter implements Formatter<Employees>{

private final EmployeesService empServ;

    public EmployeesConverter(EmployeesService empServ) {
        this.empServ = empServ;
    }

    @Override
    public String print(Employees object, Locale locale) {
        return object.getEmployeeId().toString();
    }

    @Override
    public Employees parse(String text, Locale locale) throws ParseException {
        return empServ.get(Integer.parseInt(text));
    }
}