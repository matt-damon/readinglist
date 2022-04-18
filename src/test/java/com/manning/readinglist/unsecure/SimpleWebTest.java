package com.manning.readinglist.unsecure;

import static org.junit.Assert.*;

import com.manning.readinglist.ReadingListApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


//启动嵌入式的servlet容器运行应用程序，之后可以发起真实的HTTP请求
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ReadingListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebTest {

    @Value("${local.server.port}")
    private int port;

    @Test(expected = HttpClientErrorException.class)
    public void pageNotFound() {
        try {
            RestTemplate rest = new RestTemplate();
            String forObject = rest.getForObject("http://localhost:{port}/?username=liyang", String.class, port);
            System.out.println(forObject);
//      fail("Should result in HTTP 404");
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            throw e;
        }
    }

    //  @Test
    public void testRestTemplate() throws Exception {
        TestRestTemplate rest = new TestRestTemplate();
        String s = rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
        System.out.println(s);

    }

}
