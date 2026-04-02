package com.example.tutorial.client;

import com.example.tutorial.advice.ApiResponse;
import com.example.tutorial.dto.EmployeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClient {

    private final RestClient restClient;

    public List<EmployeeDTO> getEmployee() {
        try {
            ApiResponse<List<EmployeeDTO>> entity = restClient.get()
                    .uri("/employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<List<EmployeeDTO>>>() {
                    });
            System.out.println("entity = " + entity.getData());
            return entity.getData();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch employees: " + e.getMessage(), e);
        }
    }

    public EmployeeDTO getEmployee(Long id) {
        ApiResponse<EmployeeDTO> employeeDTO = restClient.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return employeeDTO.getData();
    }
}
