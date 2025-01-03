package com.sheikh.productmanager.repository;

import com.sheikh.productmanager.model.ApiCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ApiCallRepository extends JpaRepository<ApiCall, Long> {
    Optional<ApiCall> findByEndpointAndMethod(String endpoint, String method);
}

