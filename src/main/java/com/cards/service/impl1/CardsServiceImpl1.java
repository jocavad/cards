package com.cards.service.impl1;

import com.cards.dao.CardsDAO;
import com.cards.entity.Cards;
import com.cards.service.CardsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("CRD_SERV")
public class CardsServiceImpl1 implements CardsService {
    
    private final CardsDAO crdDao;

    public CardsServiceImpl1(@Qualifier("CRD_DAO") CardsDAO crdDao) {
        this.crdDao = crdDao;
    }
    
    @Override
    public List<Cards> getAll() {
        return crdDao.selectAll();
    }

    @Override
    public List<Cards> getRange(Integer start, Integer resnum) {
        return crdDao.selectRange(start, resnum);
    }
    
    @Override
    public Cards get(Integer id) {
        return crdDao.select(id);
    }

    @Transactional()
    @Override
    public void remove(Integer id) {
        crdDao.delete(id);
    }

    @Transactional()
    @Override
    public void modify(Cards obj) {
        crdDao.update(obj);
    }

    @Transactional()
    @Override
    public void add(Cards obj) {
        crdDao.insert(obj);
    }
    
}
