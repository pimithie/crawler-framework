package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.constant.MimeType;
import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.html.HtmlFetchFilterComposite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HtmlFetcher extends AbstractFetcher{

    @Autowired
    private HtmlFetchFilterComposite htmlFetchFilterComposite;

    @Value("${fetcher.html.save.location}")
    private String htmlSaveLocation;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {
        if (htmlFetchFilterComposite.doFilter(fetchMetaData)) {
            super.doFetch(fetchMetaData);
        }
    }

    @Override
    protected String getSaveLocation() {
        return htmlSaveLocation;
    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return MimeType.TEXT == fetchMetaData.getMimeType();
    }

}
