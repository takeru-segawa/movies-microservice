package com.example.microservice.linkservice.services;

import com.example.microservice.linkservice.models.Link;
import com.example.microservice.linkservice.repositories.LinkRepository;
import com.example.microservice.share.services.impl.BaseServiceImpl;
import com.example.microservice.tagservice.models.Tag;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkService extends BaseServiceImpl<Link, String> {
    private final LinkRepository linkRepository;

    @Autowired
    LinkService(LinkRepository linkRepository) {
        super(linkRepository);
        this.linkRepository = linkRepository;
    }

    public Link save(Link link) {
        try {
            return linkRepository.save(link);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation: " + e.getMessage());
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Validation error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the link: " + e.getMessage());
        }
    }

    public Link update(String id, Link link) {
        Optional<Link> linkOptional = linkRepository.findById(id);

        if (!linkOptional.isPresent()) {
            throw new RuntimeException("Link not found");
        }

        Link linkToUpdate = linkOptional.get();

        if (link.getMovieId() != null) { linkToUpdate.setMovieId(link.getMovieId()); }
        if (link.getImdbId() != null) { linkToUpdate.setImdbId(link.getImdbId()); }
        if (link.getTmdbId() != null) { linkToUpdate.setTmdbId(link.getTmdbId()); }

        return linkRepository.save(linkToUpdate);
    }
}
