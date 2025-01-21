package com.example.microservice.tagservice.controllers;

import com.example.microservice.tagservice.models.Tag;
import com.example.microservice.tagservice.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping()
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> findById(@PathVariable String id) {
        try {
            Optional<Tag> tag = tagService.findById(id);
            if (tag.isPresent()) {
                return ResponseEntity.status(200).body(tag.get());
            }
            throw new RuntimeException("Tag not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        try {
            Tag newTag = tagService.save(tag);
            return ResponseEntity.status(201).body(newTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tag> update(@PathVariable String id, @RequestBody Tag tag) {
        try {
            Tag updatedTag = tagService.update(id, tag);
            return ResponseEntity.status(200).body(updatedTag);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            tagService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("OK");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found");
        }
    }
}
