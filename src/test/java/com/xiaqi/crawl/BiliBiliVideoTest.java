package com.xiaqi.crawl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaqi.entity.BiliBiliVideoDownLoadURLInfo;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

/**
 * 查询video cid接口: https://api.bilibili.com/x/player/pagelist?aid=79430036&jsonp=jsonp
 *      request header:
 *          Host: api.bilibili.com
 *          Origin: https://www.bilibili.com
 *          Referer: https://www.bilibili.com
 *      args:
 *          aid: avid
 * 具体哪个视频的那个p的detail info： https://api.bilibili.com/x/web-interface/view
 * 查询下载URL: https://api.bilibili.com/x/player/playurl?aid=79430036&cid=135883220&qn=32
 */
public class BiliBiliVideoTest {

    private final Properties requestHeaders = new Properties();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println(classLoader);
        InputStream resource = classLoader.getResourceAsStream("http-request-header.properties");
        if (null == resource) {
            System.out.println("resource not found!");
            return;
        }
        try {
            requestHeaders.load(resource);
            System.out.println("load properties successfully! " + requestHeaders);
        } catch (IOException e) {
            System.out.println("load properties configuration file failed!");
            requestHeaders.clear();
        }
    }

    @Test
    public void testVideoDownload() {
        if (requestHeaders.isEmpty()) {
            System.out.println("request header is null!");
            return;
        }
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) new URL("https://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/20/32/135883220/135883220-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1582735466&gen=playurl&os=kodobv&oi=3060750193&trid=19bfcd95a7024496a97d0464c4760cb3u&platform=pc&upsig=35e9cdab92f84b915b819e3afdec78ac&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=308772934").openConnection();
            setRequestHeader(urlConnection);
            System.out.println("content-type:" +  urlConnection.getContentType());
            System.out.println("content-length:" +  urlConnection.getContentLengthLong());
            FileUtils.copyToFile(urlConnection.getInputStream(),new File("./123.m4s"));
        } catch (IOException e) {
            System.out.println("crawl fail!");
            e.printStackTrace();
        } finally {
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
    }

    private void setRequestHeader(HttpsURLConnection urlConnection) {
        requestHeaders.forEach((k,v) -> {
            urlConnection.setRequestProperty(k.toString(),v.toString());
        });
    }

    @Test
    public void tesVideoDownLoad() {
        HttpsURLConnection httpsURLConnection = null;
        try {
            httpsURLConnection = (HttpsURLConnection) new URL("https://api.bilibili.com/x/player/playurl?avid=66333861&cid=%20115048060&qn=32").openConnection();
            setRequestHeader(httpsURLConnection);
            int contentLength = httpsURLConnection.getContentLength();
            System.out.println(contentLength);
            System.out.println("content-type:" + httpsURLConnection.getContentType());
            if (contentLength <= 0) {
//                throw new RuntimeException("content length is smaller than 0");
                System.out.println("content length is smaller than 0");
                processTransferEncoding(httpsURLConnection);
                return;
            }
            processHasContentLengthHeader(httpsURLConnection, contentLength);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != httpsURLConnection) {
                httpsURLConnection.disconnect();
            }
        }
    }

    private void processTransferEncoding(HttpsURLConnection httpsURLConnection) throws IOException {
        String transferEncoding = httpsURLConnection.getHeaderField("Transfer-Encoding");
        if (null == transferEncoding || "".equals(transferEncoding)) {
            throw new RuntimeException("the response don't have transfer encoding or content length");
        }
        InputStream inputStream = httpsURLConnection.getInputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        byte[] bytes = new byte[2048];
        int read = gzipInputStream.read(bytes);
        if (read <= 0) {
            throw new RuntimeException("socket read error");
        }
        int nread = 0;
        if (read < bytes.length) {
            nread = gzipInputStream.read(bytes, read, bytes.length - read);
            if (nread <= 0) {
                throw new RuntimeException("socket read error");
            }
        }
        String responseString = new String(bytes, 0, read + nread);
        BiliBiliVideoDownLoadURLInfo biliBiliVideoDownLoadURLInfo = objectMapper.readValue(responseString, BiliBiliVideoDownLoadURLInfo.class);
        System.out.println(biliBiliVideoDownLoadURLInfo);
        downloadVideo(biliBiliVideoDownLoadURLInfo);
    }

    private void downloadVideo(BiliBiliVideoDownLoadURLInfo biliBiliVideoDownLoadURLInfo) throws IOException {
        HttpURLConnection httpsURLConnection = (HttpURLConnection) new URL(biliBiliVideoDownLoadURLInfo.getData().getDurl().get(0).getUrl()).openConnection();
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream("BiliBiliVideoDownLoadHeader.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        properties.forEach((k,v) -> {
            httpsURLConnection.setRequestProperty(k.toString(),v.toString());
        });
        httpsURLConnection.getHeaderFields().forEach((k,v) -> {
            System.out.println(k + "--->" + v);
        });
        long videoSize = httpsURLConnection.getContentLengthLong();
        if (videoSize <= 0) {
            throw new RuntimeException("video size smaller than 0");
        }
        byte[] bytes = new byte[(int) videoSize];
        int read = httpsURLConnection.getInputStream().read(bytes);
        if (read <= 0) {
            throw new RuntimeException("read size smaller than 0");
        }
        String contentDisposition = httpsURLConnection.getHeaderField("Content-Disposition");
        int start = contentDisposition.indexOf("filename=\"");
        int end = contentDisposition.indexOf("\"",start + 10);
        String fileName = contentDisposition.substring(start + 10,end);
        // TODO: resource release
        new FileOutputStream(new File("./"+ fileName)).write(bytes);
        return;
    }

    private void processHasContentLengthHeader(HttpsURLConnection httpsURLConnection, int contentLength) throws IOException {
        byte[] bytes = new byte[contentLength];
        int read = httpsURLConnection.getInputStream().read(bytes);
        if (read == -1) {
            throw new RuntimeException("eof");
        }
        System.out.println(new String(bytes));
    }



}
