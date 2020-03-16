package com.xiaqi.crawl.controller;

import com.xiaqi.crawl.controller.entity.CommonResponse;
import com.xiaqi.crawl.executor.TaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @Autowired
    private TaskExecutor taskExecutor;

    @RequestMapping("/crawlResources")
    public CommonResponse crawl(String url) {
        try {
            return CommonResponse.builder()
                    .data(taskExecutor.executeTask(url))
                    .build();
        } catch (Exception e) {
            log.error("crawl url [" + url +  "]fail",e);
            return CommonResponse.getDefaultFailResponse();
        }
    }

}
