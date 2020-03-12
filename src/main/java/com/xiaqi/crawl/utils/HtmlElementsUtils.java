package com.xiaqi.crawl.utils;

import com.google.common.collect.Lists;
import com.xiaqi.crawl.constant.HtmlConstant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
public class HtmlElementsUtils {

    public static List<? extends URL> extractURLsFromHtmlElements(Elements elements) {
        String tagName = elements.get(0).tagName();
        switch (tagName) {
            case HtmlConstant.ANCHOR_TAG_NAME:
                return getAllUrlsFromAnchorTag(elements);
            case HtmlConstant.IMG_TAG_NAME:
                return getAllUrlsFromImgTag(elements);
            case HtmlConstant.AUDIO_TAG_NAME:
                return getAllUrlsFromAudioTag(elements);
            case HtmlConstant.VIDEO_TAG_NAME:
                return getAllUrlsFromVideoTag(elements);
        }
        return Lists.newArrayList();
    }

    private static List<? extends URL> getAllUrlsFromVideoTag(Elements elements) {
        List<URL> result = Lists.newArrayList();
        elements.forEach(e -> {
            Elements source = e.getElementsByTag(HtmlConstant.SOURCE_TAG_NAME);
            source.forEach(src -> {
                if (src.hasAttr(HtmlConstant.SRC_ATTRIBUTE_KEY_NAME)) {
                    String audioUrl = src.attr(HtmlConstant.ABSOLUTE_SRC_ATTRIBUTE_KEY_NAME);
                    try {
                        result.add(new URL(audioUrl));
                    } catch (MalformedURLException e1) {
                        log.error("this URL [" +audioUrl+ "] maybe format is error",e);
                    }
                }
            });
        });
        return result;
    }

    private static List<? extends URL> getAllUrlsFromAudioTag(Elements elements) {
        List<URL> result = Lists.newArrayList();
        elements.forEach(e -> {
            Elements source = e.getElementsByTag(HtmlConstant.SOURCE_TAG_NAME);
            source.forEach(src -> {
                if (src.hasAttr(HtmlConstant.SRC_ATTRIBUTE_KEY_NAME)) {
                    String audioUrl = src.attr(HtmlConstant.ABSOLUTE_SRC_ATTRIBUTE_KEY_NAME);
                    try {
                        result.add(new URL(audioUrl));
                    } catch (MalformedURLException e1) {
                        log.error("this URL [" +audioUrl+ "] maybe format is error",e);
                    }
                }
            });
        });
        return result;
    }

    private static List<? extends URL> getAllUrlsFromImgTag(Elements elements) {
        List<URL> result = Lists.newArrayList();
        elements.forEach(e -> {
            if (e.hasAttr(HtmlConstant.SRC_ATTRIBUTE_KEY_NAME)) {
                String url = e.attr(HtmlConstant.ABSOLUTE_SRC_ATTRIBUTE_KEY_NAME);
                try {
                    result.add(new URL(url));
                } catch (MalformedURLException e1) {
                    log.error("this URL [" +url+ "] maybe format is error",e);
                }
            }
        });
        return result;
    }

    private static List<? extends URL> getAllUrlsFromAnchorTag(Elements elements) {
        List<URL> result = Lists.newArrayList();
        elements.forEach(e -> {
            if (e.hasAttr(HtmlConstant.HREF_ATTRIBUTE_KEY_NAME)) {
                String url = e.attr(HtmlConstant.ABSOLUTE_HREF_ATTRIBUTE_KEY_NAME);
                try {
                    result.add(new URL(url));
                } catch (MalformedURLException e1) {
                    log.error("this URL [" +url+ "] maybe format is error",e);
                }
            }
        });
        return result;
    }

}
