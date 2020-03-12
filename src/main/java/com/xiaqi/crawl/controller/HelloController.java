package com.xiaqi.crawl.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/say")
    public ServerResponse sayHello(String name) {
        return ServerResponse.builder().code(200).message("hello," + name).build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class ServerResponse {

        int code;

        String message;

        Object data;
    }

}
