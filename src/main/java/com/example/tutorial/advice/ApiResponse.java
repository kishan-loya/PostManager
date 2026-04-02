package com.example.tutorial.advice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ApiResponse<T> {

    private ApiError error;
    private T data;
    @JsonProperty("localDateTime")
    private LocalDateTime dateTime;

    public ApiResponse() {
        this.dateTime = LocalDateTime.now();
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }
}
