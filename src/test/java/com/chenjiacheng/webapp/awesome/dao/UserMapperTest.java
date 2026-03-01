package com.chenjiacheng.webapp.awesome.dao;

import com.chenjiacheng.webapp.awesome.WebappAwesomeApplicationTests;
import com.chenjiacheng.webapp.awesome.dao.mapper.UserMapper;
import com.chenjiacheng.webapp.awesome.dao.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

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
    public void insertSelectiveTest() {
        UserDO user = new UserDO();
        user.setAvatar("xxx");
        user.setId(null);
        user.setCreateBy("SYS");
        user.setUpdateBy("SYS");
        user.setCreateTime(null);
        user.setUpdateTime(null);
        user.setDeleted(null);

        user.setUid(UUID.randomUUID().toString().replace("-",""));
        user.setUsername("jack");
        user.setPassword("xxxxxxxxxxxxxxxxxxxxxxxxxx");

        user.setNickname("jack");
        user.setEmail("jack@email.com");
        user.setPhone("18601734691");
        user.setStatus(1);
        user.setRemark(null);

        user.setLastLoginIp(null);
        user.setLoginCount(null);
        user.setLastLoginTime(null);


        log.info("user:{}",user);
        int inserted = userMapper.insertSelective(user);
        log.info("inserted: {}", inserted);
    }

}
