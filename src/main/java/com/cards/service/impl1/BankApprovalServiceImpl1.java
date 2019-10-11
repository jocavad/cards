package com.cards.service.impl1;

import com.cards.dao.BankApprovalDAO;
import com.cards.entity.BankApproval;
import com.cards.service.BankApprovalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BA_SERV")
public class BankApprovalServiceImpl1 implements BankApprovalService {
    
    private final BankApprovalDAO baDao;

    public BankApprovalServiceImpl1(@Qualifier("BA_DAO") BankApprovalDAO baDao) {
        this.baDao = baDao;
    }
    
    @Override
    public List<BankApproval> getAll() {
        return baDao.selectAll();
    }

    @Override
    public List<BankApproval> getRange(Integer start, Integer resnum) {
        return baDao.selectRange(start, resnum);
    }
    
    @Override
    public BankApproval get(Integer id) {
        return baDao.select(id);
    }

    @Transactional()
    @Override
    public void remove(Integer id) {
        baDao.delete(id);
    }

    @Transactional()
    @Override
    public void modify(BankApproval ba) {
        baDao.update(ba);
    }

    @Transactional()
    @Override
    public void add(BankApproval ba) {
        baDao.insert(ba);
    }
    
}
