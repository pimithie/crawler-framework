package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;


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

    protected void doFetch(FetchMetaData fetchMetaData) {
        try {
            FileUtils.copyToFile(new URL(fetchMetaData.getUrl()).openConnection().getInputStream(),new File(this.getSaveLocation(),fetchMetaData.getFilename()));
        } catch (IOException e) {
            log.error("save resource " + fetchMetaData + " fail",e);
        }
    }

    protected abstract String getSaveLocation();

    protected void preFetch(FetchMetaData fetchMetaData) {

    }

}
