package com.example.microservice.linkservice.controllers;

import com.example.microservice.linkservice.models.Link;
import com.example.microservice.linkservice.services.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/links")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping
    public List<Link> findAll() {
        return linkService.findAll();
    }

    @GetMapping("/{id}") // can delete this line
    public ResponseEntity<Link> findById(@PathVariable String id) {
        try {
            Optional<Link> link = linkService.findById(id);
            if (link.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(link.get());
            }

            throw new RuntimeException("Link not found");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping()
    public ResponseEntity<Link> create(@RequestBody Link link) {
        try {
            Link linkSaved = linkService.save(link);
            return ResponseEntity.status(HttpStatus.CREATED).body(linkSaved);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Link> update(@PathVariable String id, @RequestBody Link link) {
        try {
            Link linkUpdated = linkService.update(id, link);
            return ResponseEntity.status(HttpStatus.OK).body(linkUpdated);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        try {
            linkService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Link deleted");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
