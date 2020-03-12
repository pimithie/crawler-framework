package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;

/**
 * strategy interface
 * fetch content from internet
 */
public interface Fetcher {

    /**
     * can fetch?
     * @param fetchMetaData the specific url's fetch meta data
     * @return true, if the current fetcher can fetch the content of this url;otherwise,false
     */
    boolean canFatch(FetchMetaData fetchMetaData);

    /**
     * do fetch
     */
    boolean fetch(FetchMetaData fetchMetaData);
}
