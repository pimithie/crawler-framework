package com.xiaqi.crawl.extractor;

import com.google.common.collect.Lists;
import com.xiaqi.crawl.constant.HtmlConstant;
import com.xiaqi.crawl.constant.HttpConstant;
import com.xiaqi.crawl.utils.HtmlElementsUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
@Component
public class DefaultURLExtractor extends AbstractURLExtractor {

    @Override
    public List<URL> doExtract(URL url) {
        Document document = null;
        List<URL> result = Lists.newArrayList();
        try {
            document = Jsoup.parse(url, HttpConstant.HTTP_TIMEOUT_MILLIS);
        } catch (IOException e) {
            log.error("parse the html document failed,the url is [" + url.toString() + "]",e);
            return result;
        }
        result.addAll(HtmlElementsUtils.extractURLsFromHtmlElements(document.getElementsByTag(HtmlConstant.ANCHOR_TAG_NAME)));
        result.addAll(HtmlElementsUtils.extractURLsFromHtmlElements(document.getElementsByTag(HtmlConstant.IMG_TAG_NAME)));
        result.addAll(HtmlElementsUtils.extractURLsFromHtmlElements(document.getElementsByTag(HtmlConstant.AUDIO_TAG_NAME)));
        result.addAll(HtmlElementsUtils.extractURLsFromHtmlElements(document.getElementsByTag(HtmlConstant.VIDEO_TAG_NAME)));
        return result;
    }

    @Override
    protected void afterExtract(List<URL> result,URL url) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        log.info("extract URLs [" + result + "] from [" + url.toString() + "]");
    }
}
