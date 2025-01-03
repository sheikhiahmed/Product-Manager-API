package com.sheikh.productmanager.filter;

import com.sheikh.productmanager.service.ApiCallService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException; // Add this import

@Component
public class ApiCallLoggingFilter extends OncePerRequestFilter {

    @Autowired
    private ApiCallService apiCallService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { // IOException is resolved here

        filterChain.doFilter(request, response);

        String endpoint = request.getRequestURI();
        String method = request.getMethod();

        apiCallService.incrementCallCount(endpoint, method);
    }
}
