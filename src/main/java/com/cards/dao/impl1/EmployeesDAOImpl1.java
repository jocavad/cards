package com.cards.dao.impl1;

import com.cards.dao.EmployeesDAO;
import com.cards.entity.Employees;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("EMP_DAO")
public class EmployeesDAOImpl1 implements EmployeesDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Employees> selectAll() {
        return em.createQuery("select e "
                            + "from Employees e",Employees.class).getResultList();
    }

    @Override
    public List<Employees> selectRange(Integer start, Integer resnum) {
        return em.createQuery("select c "
                            + "from Employees c "
                            + "Order By c.employeeId ",Employees.class)
                .setFirstResult(start)
                .setMaxResults(resnum).getResultList();
    }
    
    @Override
    public Employees select(Integer id) {
                return em.createQuery("select c "
                            + "from Employees c "
                            + "left join fetch c.requests "
                            + "where c.employeeId = :id "
                            + "Order By c.employeeId " ,Employees.class).setParameter("id", id).getSingleResult();
//        return em.find(Employees.class, id);
    }

    @Override
    public void delete(Integer id) {
        em.remove(em.find(Employees.class, id));
    }

    @Override
    public void update(Employees obj) {
       Employees emp1 = em.find(Employees.class, obj.getEmployeeId());
       emp1.setAddress(obj.getAddress());
       emp1.setFirstName(obj.getFirstName());
       emp1.setLastName(obj.getLastName());
       emp1.setPhoneNumber(obj.getPhoneNumber());
    }

    @Override
    public void insert(Employees obj) {
        em.persist(obj);
    }
    
}
