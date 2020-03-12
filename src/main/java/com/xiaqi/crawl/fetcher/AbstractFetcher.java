package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class AbstractFetcher implements Fetcher {

    @Override
    public boolean fetch(FetchMetaData fetchMetaData) {
        try {
            preFetch(fetchMetaData);
            doFetch(fetchMetaData);
            return true;
        } catch (Exception e) {
            log.error("fetch url [" + fetchMetaData.getUrl() + "] fail!",e);
            return false;
        }
    }

    protected abstract void doFetch(FetchMetaData fetchMetaData);

    protected void preFetch(FetchMetaData fetchMetaData) {

    }

}
