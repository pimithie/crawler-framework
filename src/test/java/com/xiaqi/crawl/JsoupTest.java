package com.xiaqi.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class JsoupTest {

    private Document document;

    @Before
    public void initializeDocument() throws IOException {
        document = Jsoup.parse(new URL("http://www.baidu.com/"), 5 * 1000);
    }

    @Test
    public void jsoupHelloWorld() {
        System.out.println(document);
    }

    @Test
    public void testElementSelection() {
        Elements aTags = document.getElementsByTag("a");
        List<String> urls = new ArrayList<>(aTags.size());
        for (Element aTag : aTags) {
            String href = aTag.attr("href");
            System.out.println(href);
        }
    }

    @Test
    public void getAllPTagUrl() {
        Elements p = document.getElementsByTag("p");
        for (Element element : p) {
            System.out.println("==============");
            String text = element.text();
            System.out.println("==============");
        }
    }

    @Test
    public void testBiliBiliSearch() throws IOException {
        URL url = new URL("https://search.bilibili.com/all?keyword=Java%E6%9E%B6%E6%9E%84%E5%B8%88&from_source=banner_search");
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        setHttpRequestHeader(urlConnection);
        try {
            System.out.println("Content-Type:" + urlConnection.getContentType());
            System.out.println("Content-Length:" + urlConnection.getContentLength());
            Elements elements = this.getElementsByTag(urlConnection,url.toString(),"a");
            for (Element element : elements) {
                System.out.println("============");
                System.out.println(element.attr("title"));
                System.out.println(element.attr("abs:href"));
                System.out.println("============");
            }
        } finally {
            if (null !=  urlConnection) {
                urlConnection.disconnect();
            }
        }
    }

    private Elements getElementsByTag(HttpsURLConnection urlConnection,String baseURL,String tagName) throws IOException {
        return Jsoup.parse(urlConnection.getInputStream(),"UTF-8",baseURL).getElementsByTag(tagName);
    }

    private void readResponseBody(HttpsURLConnection urlConnection) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    private void setHttpRequestHeader(HttpsURLConnection urlConnection) throws ProtocolException {
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
    }


}
