package com.example.tutorial.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "api_request_log")
public class ApiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String apiUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @CurrentTimestamp
    private LocalDateTime ts;
}
