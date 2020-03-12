package com.xiaqi.crawl.filter.fetch;

import com.xiaqi.crawl.filter.Filter;

public interface FetchFilter extends Filter {

    boolean filter(FetchMetaData fetchMetaData);

}
