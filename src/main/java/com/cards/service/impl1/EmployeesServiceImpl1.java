package com.cards.service.impl1;

import com.cards.dao.EmployeesDAO;
import com.cards.entity.Employees;
import com.cards.service.EmployeesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("EMP_SERV")
public class EmployeesServiceImpl1 implements EmployeesService {

    private final EmployeesDAO empDao;

    public EmployeesServiceImpl1(@Qualifier("EMP_DAO") EmployeesDAO empDao) {
        this.empDao = empDao;
    }
    
    @Override
    public List<Employees> getAll() {
        return empDao.selectAll();
    }

    @Override
    public List<Employees> getRange(Integer start, Integer resnum) {
        return empDao.selectRange(start, resnum);
    }
    
    @Override
    public Employees get(Integer id) {
        return empDao.select(id);
    }

    @Transactional()
    @Override
    public void remove(Integer id) {
        empDao.delete(id);
    }

    @Transactional()
    @Override
    public void modify(Employees obj) {
        empDao.update(obj);
    }

    @Transactional()
    @Override
    public void add(Employees obj) {
       empDao.insert(obj);
    }
    
}
