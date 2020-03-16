package com.xiaqi.fetch;

import com.xiaqi.crawl.fetcher.ImageFetcher;
import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.utils.URLUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mvc.xml")
public class ImageFetchTest {

    @Autowired
    private ImageFetcher imageFetcher;

    @Test
    public void test() throws IOException {
        final String imageUrl = "";
        FetchMetaData fetchMetaData = URLUtils.getFetchMetaData(imageUrl);
        imageFetcher.fetch(fetchMetaData);
    }
}
