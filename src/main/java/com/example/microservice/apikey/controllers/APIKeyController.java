package com.example.microservice.apikey.controllers;

import com.example.microservice.apikey.models.APIKey;
import com.example.microservice.apikey.services.APIKeyService;
import com.example.microservice.apikey.dtos.APIKeyGenerateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/api-keys")
public class APIKeyController {
    @Autowired
    private APIKeyService apiKeyService;

    @PostMapping("/generate")
    public ResponseEntity<APIKey> generateAPIKey(
            @RequestBody APIKeyGenerateRequest request
    ) {
        Set<String> permissions = request.getPermissions() != null
                ? request.getPermissions()
                : Set.of("READ");

        APIKey apiKey = apiKeyService.generateAPIKey(
                request.getName(),
                permissions
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(apiKey);
    }

    @GetMapping
    public ResponseEntity<List<APIKey>> getAllAPIKeys() {
        return ResponseEntity.ok(apiKeyService.getAllAPIKeys());
    }

    @PatchMapping("/{apiKey}/status")
    public ResponseEntity<APIKey> updateAPIKeyStatus(
            @PathVariable String apiKey,
            @RequestParam boolean active
    ) {
        return ResponseEntity.ok(
                apiKeyService.updateAPIKeyStatus(apiKey, active)
        );
    }

    @DeleteMapping("/{apiKey}")
    public ResponseEntity<Void> deleteAPIKey(
            @PathVariable String apiKey
    ) {
        apiKeyService.deleteAPIKey(apiKey);
        return ResponseEntity.noContent().build();
    }
}
