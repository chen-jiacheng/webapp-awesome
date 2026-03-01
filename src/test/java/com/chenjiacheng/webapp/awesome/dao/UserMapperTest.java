package com.chenjiacheng.webapp.awesome.dao;

import com.chenjiacheng.webapp.awesome.WebappAwesomeApplicationTests;
import com.chenjiacheng.webapp.awesome.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * UserMapperTest
 *
 * @author chenjiacheng
 * @since 2026/3/1 20:30
 */
@Slf4j
public class UserMapperTest extends WebappAwesomeApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        log.info("{}", userMapper);
    }

}
