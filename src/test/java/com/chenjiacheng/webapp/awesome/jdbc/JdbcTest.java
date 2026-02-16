package com.chenjiacheng.webapp.awesome.jdbc;

import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * JdbcTest
 * Created by ChenJiaCheng at 2026/2/16 14:12
 *
 * @author ChenJiaCheng
 * @since 1.0.0
 **/
public class JdbcTest {

    @Test
    public void driverManagerTest() throws SQLException, ClassNotFoundException {
        //1. 加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        //2. 获取连接
        String url = "jdbc:mysql://localhost:3306/webapp_db?useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "PX%wr!%9!q)Sq3a";
        Connection connection = DriverManager.getConnection(url, user, password);

        //3. 创建Statement
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM tb_user";

        //4. 执行SQL
        ResultSet resultSet = statement.executeQuery(sql);

        ResultSetMetaData metaData = resultSet.getMetaData();

        //5. 解析结果集
        while (resultSet.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                System.out.printf("%s = %s \r\n", columnName, value);
            }
            System.out.println("---------------------------------------");
        }

        //6. 释放资源
        resultSet.close();
        statement.close();
        connection.close();

    }


}
