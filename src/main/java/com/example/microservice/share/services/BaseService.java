package com.example.microservice.share.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID> {
//    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}
