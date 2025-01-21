package com.example.microservice.ratingservice.repositories;

import com.example.microservice.ratingservice.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {
}
