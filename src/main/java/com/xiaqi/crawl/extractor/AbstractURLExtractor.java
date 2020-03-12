package com.xiaqi.crawl.extractor;

import com.xiaqi.crawl.filter.URLExtract.URLExtractFilterComposite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;

@Slf4j
public abstract class AbstractURLExtractor implements URLExtractor {

    @Autowired
    private URLExtractFilterComposite urlExtractFilterComposite;

    @Override
    public List<URL> extract(URL url) {
        beforeExtract(url);
        List<URL> result = this.doExtract(url);
        afterExtract(result,url);
        return doFilter(result);
    }

    protected List<URL> doFilter(List<URL> result) {
        return urlExtractFilterComposite.doFilter(result);
    }

    protected void beforeExtract(URL url) {

    }

    protected void afterExtract(List<URL> result,URL url) {

    }

    public abstract List<URL> doExtract(URL url);

    public void setUrlExtractFilterComposite(URLExtractFilterComposite urlExtractFilterComposite) {
        this.urlExtractFilterComposite = urlExtractFilterComposite;
    }
}
