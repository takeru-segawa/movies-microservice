package com.example.microservice.share.services.impl;

import com.example.microservice.share.services.BaseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseServiceImpl<T, ID> implements BaseService<T, ID> {
    private final MongoRepository<T, ID> repository;

    @Autowired
    public BaseServiceImpl(MongoRepository<T, ID> repository) {
        this.repository = repository;
    }

//    @Override
//    public T save(T entity) {
//        try {
//            return repository.save(entity);
//        }
//        catch (Exception e) {
//            throw new RuntimeException("Error saving " + entity, e);
//        }
//    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            return repository.findById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid ID: " + id, e);
        } catch (Exception e) {
            throw new RuntimeException("Error finding entity", e);
        }
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(ID id) {
        try {
            if (!repository.existsById(id)) {
                throw new EntityNotFoundException("Entity not found with ID: " + id);
            }
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Cannot find entity: " + id);
        }
        catch (Exception e) {
            throw new RuntimeException("Error deleting entity: " + e.getMessage(), e);
        }
    }

}
