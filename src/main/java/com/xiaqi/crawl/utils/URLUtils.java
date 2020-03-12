package com.xiaqi.crawl.utils;

import com.xiaqi.crawl.constant.HttpConstant;
import com.xiaqi.crawl.filter.fetch.FetchMetaData;
import lombok.extern.java.Log;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Log
public class URLUtils {

    public static FetchMetaData getFetchMetaData(String url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(url).openConnection();
        httpsURLConnection.setConnectTimeout(HttpConstant.HTTP_TIMEOUT_MILLIS);

        long contentLength = httpsURLConnection.getContentLengthLong();
        // transfer-encoding may be set chunked
        if (-1 == contentLength) {
           return processChunkedTransfer(httpsURLConnection);
        }
        return FetchMetaData.builder().filename(getFilename(url)).size(contentLength).build();
    }

    private static FetchMetaData processChunkedTransfer(HttpsURLConnection httpsURLConnection) {
        return null;
    }

    private static String getFilename(String url) throws MalformedURLException {
        String uri = new URL(url).getFile();
        int lastIndex = uri.lastIndexOf('/');
        if (-1 == lastIndex) {
            log.warning("extract filename from url [" + url + "] fail");
            return null;
        }
        return uri.substring(lastIndex + 1);
    }
}
