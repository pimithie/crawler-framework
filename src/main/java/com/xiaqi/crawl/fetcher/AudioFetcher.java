package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.constant.MimeType;
import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.audio.AudioFetchFilterComposite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AudioFetcher extends AbstractFetcher {

    @Autowired
    private AudioFetchFilterComposite audioFetchFilterComposite;

    @Value("${fetcher.audio.save.location}")
    private String audioSaveLocation;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {
        if (audioFetchFilterComposite.doFilter(fetchMetaData)) {
            super.doFetch(fetchMetaData);
        }
    }

    @Override
    protected String getSaveLocation() {
        return audioSaveLocation;
    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return fetchMetaData.getMimeType() == MimeType.AUDIO;
    }
}
