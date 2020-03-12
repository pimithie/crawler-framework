package com.xiaqi.crawl.filter.URLExtract;

import com.xiaqi.crawl.filter.AbstractFilterComposite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;

@Component
public class URLExtractFilterComposite extends AbstractFilterComposite<URLExtractFilter> {

    @Autowired
    public URLExtractFilterComposite(List<URLExtractFilter> urlExtractFilters) {
        super(urlExtractFilters);
    }

    public List<URL> doFilter(List<URL> filterURLs) {
        Node<URLExtractFilter> current =  this.getFilterChain();
        List<URL> result = filterURLs;
        while (null != current) {
            result = current.getFilter().filterURLs(result);
            current = current.getNext();
        }
        return result;
    }

}
