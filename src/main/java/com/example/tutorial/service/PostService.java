package com.example.tutorial.service;

import com.example.tutorial.dto.PostDTO;
import com.example.tutorial.entity.PostEntity;
import com.example.tutorial.exception.ResourceNotFoundException;
import com.example.tutorial.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public PostDTO getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public PostDTO createPost(PostDTO postDTO) {
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        PostEntity savedPost = postRepository.save(postEntity);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class));
    }

    public PostDTO updatePost(Long id, PostDTO postDTO) {
        PostEntity postEntity = postRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        postDTO.setId(id);
        modelMapper.map(postDTO, postEntity);
        postRepository.save(postEntity);
        return modelMapper.map(postEntity, PostDTO.class);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }
}
