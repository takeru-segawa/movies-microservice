package com.example.microservice.ratingservice.services;

import com.example.microservice.ratingservice.models.Rating;
import com.example.microservice.ratingservice.repositories.RatingRepository;
import com.example.microservice.share.services.BaseService;
import com.example.microservice.share.services.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService extends BaseServiceImpl<Rating, String> {
    private final RatingRepository ratingRepository;

    @Autowired
    RatingService(RatingRepository ratingRepository) {
        super(ratingRepository);
        this.ratingRepository = ratingRepository;
    }

    public String save(Rating rating) {
        Rating newRating = ratingRepository.save(rating);

        return newRating.getId();
    }

    public Rating update(String id, Rating rating) {
        Rating oldRating = ratingRepository.findById(id).get();

        if (oldRating == null) {
            throw new RuntimeException("Rating not found with id: " + id);
        }

        if (rating.getUserId() != null) oldRating.setUserId(rating.getUserId());
        if (rating.getRating() != null) oldRating.setRating(rating.getRating());
        if (rating.getMovieId() != null) oldRating.setMovieId(rating.getMovieId());
        if (rating.getTimestamp() != null) oldRating.setTimestamp(rating.getTimestamp());

        ratingRepository.save(oldRating);

        return oldRating;
    }
}
