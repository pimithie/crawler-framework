package com.xiaqi.crawl.fetcher;

import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import com.xiaqi.crawl.utils.URLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class FetcherComposite {

    @Autowired
    private List<Fetcher> fetchers;

    public boolean fetch(String url) {
        try {
            if (CollectionUtils.isEmpty(fetchers)) {
                log.warn("fetchers is empty,fetch can't proceed!");
                return false;
            }
            for (Fetcher fetcher : fetchers) {
                FetchMetaData fetchMetaData = URLUtils.getFetchMetaData(url);
                if (fetcher.canFatch(fetchMetaData)) {
                    fetcher.fetch(fetchMetaData);
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("fetch url [" + url + "]fail!",e);
            return false;
        }
    }

}
