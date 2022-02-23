package com.manning.readinglist;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class ReadingListApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(ReadingListApplication.class, args);
    }
    
    @Override  //实现无业务逻辑跳转，从而减少控制器代码的编写
    public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/login").setViewName("login");
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
      //argumentResolvers.add(new ReaderHandlerMethodArgumentResolver());
    }
    
}
