package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.constant.MimeType;
import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.image.ImageFetchFilterComposite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageFetcher extends AbstractFetcher {

    @Autowired
    private ImageFetchFilterComposite imageFetcherFilterComposite;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {
        if (imageFetcherFilterComposite.doFilter(fetchMetaData)) {
            // TODO: save the image to local
        }
    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return MimeType.IMAGES == fetchMetaData.getMimeType();
    }
}
