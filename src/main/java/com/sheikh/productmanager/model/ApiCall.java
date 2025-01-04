package com.sheikh.productmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


public class ApiCall {
    private String endpoint;
    private String method;
    private int count;

    // Constructor with all fields
    public ApiCall(String endpoint, String method, int count) {
        this.endpoint = endpoint;
        this.method = method;
        this.count = count;
    }

    // Getters and Setters
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
