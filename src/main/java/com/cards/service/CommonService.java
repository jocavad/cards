package com.cards.service;

import java.util.List;

/**
 * @param <T>
 */
public interface CommonService<T> {
       
   public List<T> getAll();
   
   public List<T> getRange(Integer start, Integer resnum);
   
   public T get(Integer id);
   
   public void remove(Integer id);
   
   public void modify(T obj);
   
   public void add(T obj);
}
