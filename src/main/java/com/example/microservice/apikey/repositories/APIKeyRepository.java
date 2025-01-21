package com.example.microservice.apikey.repositories;

import com.example.microservice.apikey.models.APIKey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface APIKeyRepository extends MongoRepository<APIKey, String> {
    Optional<APIKey> findByApiKey(String apiKey);
    Optional<APIKey> findByApiKeyAndSecretKey(String apiKey, String secretKey);
}
