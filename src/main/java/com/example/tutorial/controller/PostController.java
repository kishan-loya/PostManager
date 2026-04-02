package com.example.tutorial.controller;

import com.example.tutorial.advice.ApiResponse;
import com.example.tutorial.client.EmployeeClient;
import com.example.tutorial.dto.EmployeeDTO;
import com.example.tutorial.dto.PostDTO;
import com.example.tutorial.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final EmployeeClient employeeClient;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = postService.updatePost(id, postDTO);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/fetch-employees")
    public ResponseEntity<?> fetchEmployees() {
        List<EmployeeDTO> employeeDTOList = employeeClient.getEmployee();
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @GetMapping("/fetch-employees/{id}")
    public ResponseEntity<?> fetchEmployee(@PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeClient.getEmployee(id);
        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }
}
