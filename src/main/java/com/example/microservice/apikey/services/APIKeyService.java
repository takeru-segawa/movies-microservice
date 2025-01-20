package com.example.microservice.apikey.services;

import com.example.microservice.apikey.models.APIKey;
import com.example.microservice.apikey.repositories.APIKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class APIKeyService {
    @Autowired
    private APIKeyRepository apiKeyRepository;

    public APIKey generateAPIKey(String name, Set<String> permissions) {
        APIKey apiKey = new APIKey();
        apiKey.setName(name);
        apiKey.setApiKey(generateUniqueKey());
        apiKey.setSecretKey(generateUniqueKey());
        apiKey.setActive(true);
        apiKey.setPermissions(permissions);
        apiKey.setCreatedAt(LocalDateTime.now());
        apiKey.setExpiresAt(LocalDateTime.now().plusYears(1));

        return apiKeyRepository.save(apiKey);
    }

    private String generateUniqueKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public boolean validateAPIKey(String apiKey, String secretKey) {
        return apiKeyRepository.findByApiKeyAndSecretKey(apiKey, secretKey)
                .map(key -> key.isActive() &&
                        key.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }

    public Optional<APIKey> findByApiKey(String apiKey) {
        return apiKeyRepository.findByApiKey(apiKey);
    }

    public List<APIKey> getAllAPIKeys() {
        return apiKeyRepository.findAll();
    }

    public APIKey updateAPIKeyStatus(String apiKey, boolean active) {
        return apiKeyRepository.findByApiKey(apiKey)
                .map(key -> {
                    key.setActive(active);
                    return apiKeyRepository.save(key);
                })
                .orElseThrow(() -> new RuntimeException("API Key not found"));
    }

    public void deleteAPIKey(String apiKey) {
        apiKeyRepository.findByApiKey(apiKey)
                .ifPresent(apiKeyRepository::delete);
    }
}
