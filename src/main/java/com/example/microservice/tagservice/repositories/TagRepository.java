package com.example.microservice.tagservice.repositories;

import com.example.microservice.tagservice.models.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
}
