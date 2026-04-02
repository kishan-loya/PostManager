package com.example.tutorial.repository;

import com.example.tutorial.entity.ApiRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRequestLogRepository extends JpaRepository<ApiRequestLog, Long> {
}
