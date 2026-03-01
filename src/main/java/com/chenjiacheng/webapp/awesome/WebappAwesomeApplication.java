package com.chenjiacheng.webapp.awesome;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.chenjiacheng.webapp.awesome.dao.mapper")
public class WebappAwesomeApplication {

    public static void main(String[] args) {
        log.info("启动中...");
        SpringApplication.run(WebappAwesomeApplication.class, args);
        log.info("启动成功");
    }

}
