package com.chenjiacheng.webapp.awesome;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chenjiacheng.webapp.awesome.dao.mapper")
public class WebappAwesomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappAwesomeApplication.class, args);
    }

}
