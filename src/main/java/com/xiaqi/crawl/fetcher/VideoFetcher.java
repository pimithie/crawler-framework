package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.video.VideoFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoFetcher extends AbstractFetcher{

    @Autowired
    private VideoFetchFilterComposite videoFetchFilterComposite;


    @Value("${fetcher.video.save.location}")
    private String videoSaveLocation;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {
        if (videoFetchFilterComposite.doFilter(fetchMetaData)) {
            super.doFetch(fetchMetaData);
        }
    }

    @Override
    protected String getSaveLocation() {
        return videoSaveLocation;
    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return false;
    }
}
