package com.cards.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="EMPLOYEES"
    ,schema="CARDS"
)
public class Employees  implements Serializable {


     private Integer employeeId;
     @Size(max = 20 ,message = "{maxSize} {max}")
     @NotEmpty(message = "{notNull}")
     private String firstName;
     @Size(max = 20 ,message = "{maxSize} {max}")
     @NotEmpty(message = "{notNull}")
     private String lastName;
     @Size(max = 50 ,message = "{maxSize} {max}")
     @NotEmpty(message = "{notNull}")
     private String address;
     @Size(max = 15 ,message = "{maxSize} {max}")
     private String phoneNumber;
     private Set<Requests> requests = new HashSet<>(0);

    public Employees() {
    }

	
    public Employees(Integer employeeId, String firstName, String lastName, String address) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    public Employees(Integer employeeId, String firstName, String lastName, String address, String phoneNumber, Set<Requests> requests) {
       this.employeeId = employeeId;
       this.firstName = firstName;
       this.lastName = lastName;
       this.address = address;
       this.phoneNumber = phoneNumber;
       this.requests = requests;
    }
   
    @Id 
    @Column(name="EMPLOYEE_ID", unique=true, nullable=false, precision=5, scale=0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EmployeesSeq")
    @SequenceGenerator(name = "EmployeesSeq", sequenceName = "CARDS.EMPLOYEES_SEQ",allocationSize = 1)
    public Integer getEmployeeId() {
        return this.employeeId;
    }
    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    
    @Column(name="FIRST_NAME", nullable=false, length=80)
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    @Column(name="LAST_NAME", nullable=false, length=80)
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    @Column(name="ADDRESS", nullable=false, length=200)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="PHONE_NUMBER", length=60)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="employees")
    public Set<Requests> getRequests() {
        return this.requests;
    }
    
    public void setRequests(Set<Requests> requests) {
        this.requests = requests;
    }




}


