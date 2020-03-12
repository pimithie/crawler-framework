package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.filter.fetch.html.HtmlFetchFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HtmlFetcher extends AbstractFetcher{

    @Autowired
    private HtmlFetchFilterComposite htmlFetchFilterComposite;

    @Override
    protected void doFetch(FetchMetaData fetchMetaData) {

    }

    @Override
    public boolean canFatch(FetchMetaData fetchMetaData) {
        return false;
    }

}
