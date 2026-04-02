package com.example.tutorial.service;

import com.example.tutorial.entity.ApiRequestLog;
import com.example.tutorial.entity.User;
import com.example.tutorial.repository.ApiRequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class APIRequestLogService {

    private final ApiRequestLogRepository apiRequestLogRepository;

    public void addLog(User user, String url) {
        ApiRequestLog apiRequestLog = new ApiRequestLog();
        apiRequestLog.setApiUrl(url);
        apiRequestLog.setUser(user);
        apiRequestLogRepository.save(apiRequestLog);
    }
}
