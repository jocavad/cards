package com.cards.dao.impl1;

import com.cards.dao.BankApprovalDAO;
import com.cards.entity.BankApproval;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("BA_DAO")
public class BankApprovalDAOImpl1 implements BankApprovalDAO {
    @PersistenceContext
    private EntityManager em;
   
    @Override
    public List<BankApproval> selectAll() {
        return em.createQuery("select ba "
                            + "from BankApproval ba",BankApproval.class).getResultList();
    }

    @Override
    public List<BankApproval> selectRange(Integer start, Integer resnum) {
        return em.createQuery("select c "
                            + "from BankApproval c "
                            + "join fetch c.requests "
                            + "Order By c.approvalId ",BankApproval.class)
                .setFirstResult(start)
                .setMaxResults(resnum).getResultList();
    }
    
    @Override
    public BankApproval select(Integer id) {
        return em.find(BankApproval.class, id);
    }

    @Override
    public void delete(Integer id) {
        em.remove(em.find(BankApproval.class, id));
    }

    @Override
    public void update(BankApproval obj) {
       BankApproval ba1 = em.find(BankApproval.class, obj.getApprovalId());
       ba1.setApprovalDate(obj.getApprovalDate());
    }

    @Override
    public void insert(BankApproval obj) {
        em.persist(obj);
    }
    
}
