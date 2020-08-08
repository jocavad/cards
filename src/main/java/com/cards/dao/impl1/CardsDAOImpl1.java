package com.cards.dao.impl1;

import com.cards.dao.CardsDAO;
import com.cards.entity.Cards;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("CRD_DAO")
public class CardsDAOImpl1 implements CardsDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Cards> selectAll() {
        return em.createQuery("select c "
                            + "from Cards c",Cards.class).getResultList();
    }
  
    @Override
    public List<Cards> selectRange(Integer start, Integer resnum) {
        return em.createQuery("select c "
                            + "from Cards c "
                            + "join fetch c.requests "
                            + "Order By c.cardId ",Cards.class)
                .setFirstResult(start)
                .setMaxResults(resnum).getResultList();
    }
    
    @Override
    public Cards select(Integer id) {
         return em.createQuery("select c "
                            + "from Cards c "
                            + "left join fetch c.requests "
                            + "where c.cardId = :id "
                            + "Order By c.cardId " ,Cards.class).setParameter("id", id).getSingleResult();
//        return em.find(Cards.class, id);
    }

    @Override
    public void delete(Integer id) {
        em.remove(em.find(Cards.class, id));
    }

    @Override
    public void update(Cards obj) {
       Cards crd1 = em.find(Cards.class, obj.getCardId());
       crd1.setIssueDate(obj.getIssueDate());
       crd1.setPin(obj.getPin());
    }

    @Override
    public void insert(Cards obj) {
        em.persist(obj);
    }
    
}
