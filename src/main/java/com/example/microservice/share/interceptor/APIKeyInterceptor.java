package com.example.microservice.share.interceptor;

import com.example.microservice.apikey.services.APIKeyService;
import com.example.microservice.share.annotation.RequireAPIKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class APIKeyInterceptor implements HandlerInterceptor {

    @Autowired
    private APIKeyService apiKeyService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // Bỏ qua nếu không phải method handler
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireAPIKey requireAPIKey = handlerMethod.getMethodAnnotation(RequireAPIKey.class);

        // Nếu method không yêu cầu API key
        if (requireAPIKey == null) {
            return true;
        }

        String apiKey = request.getHeader("X-API-KEY");
        String secretKey = request.getHeader("X-SECRET-KEY");

        if (apiKey == null || secretKey == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API Key required");
            return false;
        }

        if (!apiKeyService.validateAPIKey(apiKey, secretKey)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
            return false;
        }

        return true;
    }
}
