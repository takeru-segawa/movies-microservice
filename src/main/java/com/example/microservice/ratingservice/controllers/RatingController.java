package com.example.microservice.ratingservice.controllers;

import com.example.microservice.ratingservice.models.Rating;
import com.example.microservice.ratingservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @GetMapping()
    public List<Rating> findAll() {
        return ratingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> findById(@PathVariable String id) {
        try {
            Optional<Rating> rating = ratingService.findById(id);
            if (rating.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(rating.get());
            }
            throw new RuntimeException("Rating not found");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping()
    public String save(@RequestBody Rating rating) {
        return ratingService.save(rating);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Rating> update(@PathVariable String id, @RequestBody Rating rating) {
        try {
            Rating newRating = ratingService.update(id, rating);
            return ResponseEntity.status(HttpStatus.OK).body(newRating);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            ratingService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
