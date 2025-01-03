package com.sheikh.productmanager.service;

import com.sheikh.productmanager.dao.ApiCallRepository;
import com.sheikh.productmanager.model.ApiCall;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiCallService {

    @Autowired
    private ApiCallRepository apiCallRepository;

    @Transactional
    public void incrementCallCount(String endpoint, String method) {
        ApiCall apiCall = apiCallRepository.findByEndpointAndMethod(endpoint, method)
                .orElse(new ApiCall(endpoint, method, 0));
        apiCall.setCount(apiCall.getCount() + 1);
        apiCallRepository.save(apiCall);
    }

    public List<ApiCall> getAllApiCalls() {
        return apiCallRepository.findAll();
    }
}
