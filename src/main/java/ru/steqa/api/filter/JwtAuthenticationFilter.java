package ru.steqa.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.steqa.api.config.properties.SecurityProperties;
import ru.steqa.api.exception.HttpException;
import ru.steqa.api.exception.HttpExceptionResponse;
import ru.steqa.api.model.User;
import ru.steqa.api.utility.JwtTokenUtility;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenUtility jwtTokenUtility;
    private final ObjectMapper objectMapper;
    private final SecurityProperties securityProperties;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return securityProperties.getPublicPaths().stream()
                .map(p -> p.endsWith("/**") ? p.substring(0, p.length() - 3) : p)
                .anyMatch(uri::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try {
            String token = getJwtFromRequest(request);
            User user = jwtTokenUtility.validateAccessToken(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (HttpException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            HttpExceptionResponse errorResponse = new HttpExceptionResponse(
                    e.getStatus(),
                    e.getMessage(),
                    request.getServletPath()
            );
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}