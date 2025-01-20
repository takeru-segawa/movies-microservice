package com.example.microservice.apikey.dtos;

import lombok.Data;
import java.util.Set;

@Data
public class APIKeyGenerateRequest {
    private String name;
    private Set<String> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
