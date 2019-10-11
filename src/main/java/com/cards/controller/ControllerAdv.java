package com.cards.controller;

import java.sql.SQLException;
import javax.persistence.PersistenceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ControllerAdv {
    
    @ExceptionHandler({SQLException.class})
    public String errSql(Exception ex){
        System.out.println("sql error message: "+ex.getMessage());
        return "dberror";
    }
    
    @ExceptionHandler({PersistenceException.class})
    public String errPers(Exception ex){
        System.out.println("pers error message: "+ex.getMessage());
        return "dberror";
    }
    
    @ExceptionHandler({DataIntegrityViolationException.class})
    public String errDat(Exception ex){
        System.out.println("dat error message: "+ex.getMessage());
        return "dberror";
    }
    
    @ExceptionHandler({Exception.class})
    public String err(Exception ex){
        System.out.println("exc error message: "+ex.getMessage());
        return "dberror";
    }
    
}
