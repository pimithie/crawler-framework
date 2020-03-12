package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.video.VideoFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoFetcher extends AbstractFetcher{

    @Autowired
    private VideoFetchFilterComposite videoFetchFilterComposite;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {

    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return false;
    }
}
