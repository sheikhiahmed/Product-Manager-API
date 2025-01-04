package com.sheikh.productmanager.scheduler;

import com.sheikh.productmanager.model.ApiCall;
import com.sheikh.productmanager.service.ApiCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiCallMetricsScheduler {

    @Autowired
    private ApiCallService apiCallService;

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void logApiCallMetrics() {
        List<ApiCall> apiCalls = apiCallService.getAllApiCalls();
        System.out.println("API Call Metrics:");
        for (ApiCall apiCall : apiCalls) {
            System.out.println("Endpoint: " + apiCall.getEndpoint() +
                    ", Method: " + apiCall.getMethod() +
                    ", Count: " + apiCall.getCount());
        }
    }
}

