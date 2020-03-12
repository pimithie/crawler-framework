package com.xiaqi.crawl.filter.fetch.video;

import com.xiaqi.crawl.filter.fetch.AbstractFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoFetchFilterComposite extends AbstractFetchFilterComposite<VideoFetchFilter> {

    @Autowired
    public VideoFetchFilterComposite(List<VideoFetchFilter> filters) {
        super(filters);
    }

}
