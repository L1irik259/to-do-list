package org.example.interfaces;

import java.util.List;

public interface DefaultDao<T> {
    T save(T entity);
    void deleteById(int id);
    void deleteByEntity(T entity);
    T update(T entity);
    T getById(int id);
    List<T> getAll();
}