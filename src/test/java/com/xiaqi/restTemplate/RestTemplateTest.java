package com.xiaqi.restTemplate;

import com.xiaqi.crawl.utils.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestTemplateTest {

    private final RestTemplate restTemplate = new RestTemplate();

    private final Properties properties = new Properties();

    @Before
    public void before() {
        InputStream resource = this.getClass().getClassLoader().getResourceAsStream("BiliBiliVideoDownLoadHeader.properties");
        try {
            properties.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    public void videoDownloadTest() {
        String url = "http://upos-sz-mirrorks3.bilivideo.com/upgcxcode/21/01/155560121/155560121-1-64.flv?e=ig8euxZM2rNcNbTg7WdVhoMjhbUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNo8g2ENvNo8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&uipk=5&nbs=1&deadline=1582801772&gen=playurl&os=ks3bv&oi=3060750193&trid=d4fe9c34553b4285be0e28c40ca5e12bu&platform=pc&upsig=a897e4ada709f18b78e6940646628b67&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=308772934&logo=80000000";
        //        <T> T execute(String url, HttpMethod method, RequestCallback requestCallback,
//                ResponseExtractor<T> responseExtractor, Object... uriVariables) throws RestClientException;
        restTemplate.execute(url, HttpMethod.GET, request -> {
            HttpHeaders headers = request.getHeaders();
            properties.forEach((k,v) -> {
                headers.add(k.toString(),v.toString());
            });
//            headers.add("Host","upos-sz-mirrorks3.bilivideo.com");
            System.out.println(request.getHeaders());
        }, response -> {
            try {
                InputStream body = response.getBody();
                byte[] bytes = new byte[(int) response.getHeaders().getContentLength()];
                int totalRead = 0;
                while (totalRead < bytes.length) {
                    int read = body.read(bytes,totalRead,bytes.length - totalRead);
                    if (read < 0) {
                        throw new RuntimeException("socket read error");
                    }
                    totalRead += read;
                }
                FileUtils.saveToFile(bytes,"./1.flv");
                return "ok";
            } catch (Exception e) {
                e.printStackTrace();
                return "not ok!";
            }
        });
    }

}
