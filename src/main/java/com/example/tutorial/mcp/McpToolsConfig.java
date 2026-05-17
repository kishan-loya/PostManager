package com.example.tutorial.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolsConfig {

    @Bean
    public ToolCallbackProvider postTools(PostMcpTools postMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(postMcpTools)
                .build();
    }
}
