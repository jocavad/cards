package com.cards.dao.impl1;

import com.cards.dao.ClientsDAO;
import com.cards.entity.Clients;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository("CLI_DAO")
public class ClientsDAOImpl1 implements ClientsDAO {
   @PersistenceContext
   private EntityManager em;
   
   @Override
   public List<Clients> selectAll(){
       return em.createQuery("select c "
                           + "from Clients c",Clients.class).getResultList();
   }
   
   @Override
   public List<Clients> selectRange(Integer start, Integer resnum) {
        return em.createQuery("select c "
                            + "from Clients c "
                            + "Order By c.clientId ",Clients.class)
                .setFirstResult(start)
                .setMaxResults(resnum).getResultList();
   }
 
   @Override
   public Clients select(Integer id){
       return em.find(Clients.class, id);
   }
   
   @Override
   public void delete(Integer id){
       em.remove(em.find(Clients.class, id));
   }
   
   @Override
   public void update(Clients obj){
       Clients cli1 = em.find(Clients.class, obj.getClientId());
       cli1.setAddress(obj.getAddress());
       cli1.setEmail(obj.getEmail());
       cli1.setFirstName(obj.getFirstName());
       cli1.setLastName(obj.getLastName());
       cli1.setPhoneNumber(obj.getPhoneNumber());
   }
   
   @Override
   public void insert(Clients obj){
       em.persist(obj);
   }
   
}
