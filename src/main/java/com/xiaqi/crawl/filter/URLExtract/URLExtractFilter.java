package com.xiaqi.crawl.filter.URLExtract;

import com.xiaqi.crawl.filter.Filter;

import java.net.URL;
import java.util.List;

public interface URLExtractFilter extends Filter {

    List<URL> filterURLs(List<URL> filteredURLs);
}
