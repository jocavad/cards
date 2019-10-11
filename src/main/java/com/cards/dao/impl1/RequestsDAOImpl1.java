package com.cards.dao.impl1;

import com.cards.dao.RequestsDAO;
import com.cards.entity.Requests;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("REQ_DAO")
public class RequestsDAOImpl1 implements RequestsDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Requests> selectAll() {
        return em.createQuery("select r "
                            + "from Requests r",Requests.class).getResultList();
    }

    @Override
    public List<Requests> selectRange(Integer start, Integer resnum) {
        return em.createQuery("select c "
                            + "from Requests c "
                            + "join fetch c.employees "
                            + "join fetch c.clients "
                            + "Order By c.requestId ",Requests.class)
                .setFirstResult(start)
                .setMaxResults(resnum).getResultList();
    }
    
    @Override
    public Requests select(Integer id) {
        return em.find(Requests.class, id);
    }

    @Override
    public void delete(Integer id) {
        em.remove(em.find(Requests.class, id));
    }

    @Override
    public void update(Requests obj) {
       Requests req1 = em.find(Requests.class, obj.getRequestId());
       req1.setAccountNumber(obj.getAccountNumber());
       req1.setRequestDate(obj.getRequestDate());
       req1.setEmployees(obj.getEmployees());
       req1.setClients(obj.getClients());
    }

    @Override
    public void insert(Requests obj) {
        em.persist(obj);
    }
    
}
