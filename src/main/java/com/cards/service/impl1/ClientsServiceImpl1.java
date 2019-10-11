package com.cards.service.impl1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.cards.dao.ClientsDAO;
import com.cards.entity.Clients;
import com.cards.service.ClientsService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service("CLI_SERV")
public class ClientsServiceImpl1 implements ClientsService{
    
    private final ClientsDAO cliDao;

    public ClientsServiceImpl1(@Qualifier("CLI_DAO") ClientsDAO cliDao) {
        this.cliDao = cliDao;
    }
    
    @Override
    public List<Clients> getAll() {
        return cliDao.selectAll();
    }

    @Override
    public List<Clients> getRange(Integer start, Integer resnum) {
        return cliDao.selectRange(start, resnum);
    }
    
    @Override
    public Clients get(Integer id) {
        return cliDao.select(id);
    }
    
    @Transactional()
    @Override
    public void remove(Integer id) {
        cliDao.delete(id);
    }

    @Transactional()
    @Override
    public void modify(Clients obj) {
        cliDao.update(obj);
    }
    
    @Transactional()
    @Override
    public void add(Clients obj) {
        cliDao.insert(obj);
    }
    
}
