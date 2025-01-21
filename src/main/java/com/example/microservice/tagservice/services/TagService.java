package com.example.microservice.tagservice.services;

import com.example.microservice.share.services.BaseService;
import com.example.microservice.share.services.impl.BaseServiceImpl;
import com.example.microservice.tagservice.models.Tag;
import com.example.microservice.tagservice.repositories.TagRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService extends BaseServiceImpl<Tag, String> {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        super(tagRepository);
        this.tagRepository = tagRepository;
    }

    public Tag save(Tag tag) {
        try {
            return tagRepository.save(tag);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Validation error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the rating: " + e.getMessage());
        }
    }

    public Tag update(String id, Tag tag) {
        Optional<Tag> existingTag = tagRepository.findById(id);
        if (!existingTag.isPresent()) {
            throw new RuntimeException("Tag not found");
        }

        Tag oldTag = existingTag.get();
        if (tag.getUserId() != null) {
            oldTag.setUserId(tag.getUserId());
        }
        if (tag.getMovieId() != null) {
            oldTag.setMovieId(tag.getMovieId());
        }
        if (tag.getTag() != null) {
            oldTag.setTag(tag.getTag());
        }
        if (tag.getTimestamp() != null) {
            oldTag.setTimestamp(tag.getTimestamp());
        }

        return tagRepository.save(oldTag);
    }
}
