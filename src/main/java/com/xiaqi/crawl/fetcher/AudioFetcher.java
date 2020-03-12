package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.audio.AudioFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AudioFetcher extends AbstractFetcher {

    @Autowired
    private AudioFetchFilterComposite audioFetchFilterComposite;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {

    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return false;
    }
}
