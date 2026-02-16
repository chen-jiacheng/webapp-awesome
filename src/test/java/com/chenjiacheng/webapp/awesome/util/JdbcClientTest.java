package com.chenjiacheng.webapp.awesome.util;

import lombok.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * JdbcClientTest
 * Created by ChenJiaCheng at 2026/2/16 21:21
 *
 * @author ChenJiaCheng
 * @since 1.0.0
 **/
public class JdbcClientTest {

    public static void main(String[] args) throws SQLException {
        // 初始化（一次）
        JdbcClient.init(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/webapp_db?useSSL=false",
                "root",
                "PX%wr!%9!q)Sq3a");

        JdbcClient db = JdbcClient.getInstance();

        // 查询（防注入）
        User user = db.queryOne("SELECT * FROM tb_user WHERE username = ? AND status = ?", User.class, "user2024005", 1);
        System.out.println("user = " + user);

        // 更新（防注入）
        int rows = db.execute("UPDATE tb_user SET login_count = ? WHERE id = ?", 10, 5);
        System.out.println("rows = " + rows);
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private Long id;
        private String username;
        private String password;
        private Integer status;
        private Integer loginCount;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }

}
