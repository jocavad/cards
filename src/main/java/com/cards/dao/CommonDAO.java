package com.cards.dao;

import java.util.List;

/**
 * @param <T>
 */
public interface CommonDAO <T> {
    
   public List<T> selectAll();
   
   public List<T> selectRange(Integer start, Integer resnum);
   
   public T select(Integer id);
   
   public void delete(Integer id);
   
   public void update(T obj);
   
   public void insert(T obj);
    
}
