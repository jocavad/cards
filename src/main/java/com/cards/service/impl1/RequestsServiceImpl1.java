package com.cards.service.impl1;

import com.cards.dao.RequestsDAO;
import com.cards.entity.Requests;
import com.cards.service.RequestsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("REQ_SERV")
public class RequestsServiceImpl1 implements RequestsService {
    
    private final RequestsDAO reqDao;

    public RequestsServiceImpl1(@Qualifier("REQ_DAO") RequestsDAO reqDao) {
        this.reqDao = reqDao;
    }
    
    @Override
    public List<Requests> getAll() {
        return reqDao.selectAll();
    }

    @Override
    public List<Requests> getRange(Integer start, Integer resnum) {
        return reqDao.selectRange(start, resnum);
    }
    
    @Override
    public Requests get(Integer id) {
        return reqDao.select(id);
    }

    @Transactional()
    @Override
    public void remove(Integer id) {
        reqDao.delete(id);
    }

    @Transactional()
    @Override
    public void modify(Requests obj) {
        reqDao.update(obj);
    }

    @Transactional()
    @Override
    public void add(Requests obj) {
        reqDao.insert(obj);
    }
    
}
