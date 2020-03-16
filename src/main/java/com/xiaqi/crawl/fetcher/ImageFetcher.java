package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.constant.MimeType;
import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.image.ImageFetchFilterComposite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageFetcher extends AbstractFetcher {

    @Autowired
    private ImageFetchFilterComposite imageFetcherFilterComposite;

    @Value("${fetcher.image.save.location}")
    private String imageSaveLocation;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {
        if (imageFetcherFilterComposite.doFilter(fetchMetaData)) {
            super.doFetch(fetchMetaData);
        }
    }

    @Override
    protected String getSaveLocation() {
        return imageSaveLocation;
    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return MimeType.IMAGES == fetchMetaData.getMimeType();
    }
}
