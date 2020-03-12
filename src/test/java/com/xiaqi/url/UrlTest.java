package com.xiaqi.url;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlTest {

    @Test
    public void testGetFile() throws MalformedURLException {
        URL url = new URL("http://www.baidu.com/a/b/c/d/a.pdf");
        System.out.println(url.getFile());
    }

}
