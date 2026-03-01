package com.chenjiacheng.webapp.awesome.controller;

import com.chenjiacheng.webapp.awesome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author chenjiacheng
 * @since 2026/3/1 19:26
 */
@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list() {
        return "";
    }

}
