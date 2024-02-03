package com.zbf.zbfapibackend.manager;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AiManagerTest {
    
    @Resource
    private AiManager aiManager;
    
    @Test
    void doChat() {
        String response = aiManager.doChat(1651468516836098050L, "邓紫棋");
        System.out.println(response);
    }

}