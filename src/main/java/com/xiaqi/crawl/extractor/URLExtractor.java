package com.xiaqi.crawl.extractor;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/***
 * extract all URLs from html page
 */
public interface URLExtractor {

    List<URL> extract(URL url);
}
