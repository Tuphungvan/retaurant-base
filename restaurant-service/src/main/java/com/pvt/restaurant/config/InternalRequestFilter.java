package com.pvt.restaurant.config;

import com.pvt.restaurant.web.response.ApiResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class InternalRequestFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String gatewayHeader = request.getHeader("X-Gateway");
        String internalHeader = request.getHeader("Internal-Service");

        boolean isFromGateway = "restaurant-gateway".equals(gatewayHeader);
        boolean isFromInternal = "restaurant-internal".equals(internalHeader);

        if (!isFromGateway && !isFromInternal) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            var errorBody = ApiResponse.error(
                    HttpStatus.FORBIDDEN,
                    "Request buộc phải đi qua gateway hoặc từ internal"
            ).getBody();
            response.getWriter().write(objectMapper.writeValueAsString(errorBody));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
