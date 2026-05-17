package com.example.tutorial.mcp;

import com.example.tutorial.dto.PostDTO;
import com.example.tutorial.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMcpTools {

    private final PostService postService;

    @Tool(name = "create_post", description = "Create a new post with the given title and content. Returns the created post including its generated id.")
    public PostDTO createPost(
            @ToolParam(description = "Title of the post") String title,
            @ToolParam(description = "Body content of the post") String content) {
        PostDTO input = new PostDTO(null, title, content);
        return postService.createPost(input);
    }

    @Tool(name = "delete_post", description = "Delete a post by its id. Throws if no post with that id exists.")
    public String deletePost(
            @ToolParam(description = "Id of the post to delete") Long id) {
        postService.deletePost(id);
        return "Post " + id + " deleted";
    }

    @Tool(name = "get_all_posts", description = "Get a paginated list of posts. Returns the posts on the requested page along with pagination metadata.")
    public PageResult getAllPosts(
            @ToolParam(description = "Zero-based page index (default 0)", required = false) Integer page,
            @ToolParam(description = "Page size (default 20)", required = false) Integer size) {
        int p = page == null ? 0 : Math.max(0, page);
        int s = size == null ? 20 : Math.max(1, size);
        Page<PostDTO> result = postService.getAllPosts(PageRequest.of(p, s));
        return new PageResult(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages());
    }

    public record PageResult(
            List<PostDTO> content,
            int page,
            int size,
            long totalElements,
            int totalPages) {}
}
