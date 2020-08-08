package com.cards.dto;

import java.io.Serializable;

public class EmployeesEmbeddedDTO implements Serializable {

    public Integer employeeId;

    public EmployeesEmbeddedDTO() {
    }

    public Integer getEmployeeId() {
        return this.employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

}


