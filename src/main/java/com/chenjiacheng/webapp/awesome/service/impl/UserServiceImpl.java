package com.chenjiacheng.webapp.awesome.service.impl;

import com.chenjiacheng.webapp.awesome.dao.mapper.UserMapper;
import com.chenjiacheng.webapp.awesome.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author chenjiacheng
 * @since 2026/3/1 19:25
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

}
