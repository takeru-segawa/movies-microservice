package com.example.microservice.linkservice.repositories;

import com.example.microservice.linkservice.models.Link;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends MongoRepository<Link, String> {
}
