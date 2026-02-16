package com.chenjiacheng.webapp.awesome.util;


import lombok.*;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JdbcClient
 * Created by ChenJiaCheng at 2026/2/16 16:54
 *
 * @author ChenJiaCheng
 * @since 1.0.0
 **/
public class JdbcClient {

    private String url;

    private String username;

    private String password;

    // 反射缓存：避免重复解析类结构
    private static final Map<Class<?>, Map<String, Field>> FIELD_CACHE = new ConcurrentHashMap<>();

    // 单例（静态内部类，线程安全）
    private static class Holder {
        private static JdbcClient INSTANCE;
    }


    /**
     * 初始化
     *
     * @param driver   驱动
     * @param url      数据库连接地址
     * @param username 用户名
     * @param password 密码
     */
    public static synchronized void init(String driver, String url, String username, String password) {
        if (Holder.INSTANCE != null) {
            throw new IllegalStateException("Already initialized");
        }
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver not found: " + driver, e);
        }
        Holder.INSTANCE = new JdbcClient(url, username, password);
    }

    /**
     * 获取单例
     *
     * @return 单例
     */
    public static JdbcClient getInstance() {
        if (Holder.INSTANCE == null) {
            throw new IllegalStateException("Not initialized");
        }
        return Holder.INSTANCE;
    }

    /**
     * 构造函数
     *
     * @param url      数据库连接地址
     * @param username 用户名
     * @param password 密码
     */
    private JdbcClient(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    /**
     * 查询单个结果
     *
     * @param sql    sql
     * @param clazz  返回对象类型
     * @param params 参数
     * @param <T>    返回对象类型
     * @return 结果
     * @throws SQLException SQL异常
     */
    public <T> T queryOne(String sql, Class<T> clazz, Object... params) throws SQLException {
        List<T> list = queryList(sql, clazz, params);
        if (list.size() > 1) {
            throw new SQLException("Expect one but found: " + list.size());
        }
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 查询列表
     *
     * @param sql    sql
     * @param clazz  列表元素类型
     * @param params 参数
     * @param <T>    列表元素类型
     * @return 列表
     * @throws SQLException SQL异常
     */
    public <T> List<T> queryList(String sql, Class<T> clazz, Object... params) throws SQLException {
        List<T> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                Map<String, Field> fieldMap = getFieldMap(clazz);

                while (rs.next()) {
                    T obj = newInstance(clazz);
                    for (int i = 1; i <= columnCount; i++) {
                        String colName = meta.getColumnLabel(i);
                        Field field = fieldMap.get(colName.toLowerCase());
                        if (field != null) {
                            setField(field, obj, rs.getObject(i));
                        }
                    }
                    result.add(obj);
                }
            }
        }
        return result;
    }

    /**
     * 执行SQL
     *
     * @param sql    sql
     * @param params 参数
     * @return 影响行数
     * @throws SQLException SQL异常
     */
    public int execute(String sql, Object... params) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, username, password); PreparedStatement ps = conn.prepareStatement(sql)) {
            setParams(ps, params);
            return ps.executeUpdate();
        }
    }

    // ==================== 私有工具 ====================

    private void setParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    private <T> T newInstance(Class<T> clazz) throws SQLException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new SQLException("Cannot create instance: " + clazz.getName(), e);
        }
    }

    private Map<String, Field> getFieldMap(Class<?> clazz) {
        return FIELD_CACHE.computeIfAbsent(clazz, c -> {
            Map<String, Field> map = new ConcurrentHashMap<>();
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                map.put(f.getName().toLowerCase(), f);
            }
            return map;
        });
    }

    private void setField(Field field, Object obj, Object value) {
        if (value == null) return;
        try {
            // 简单类型转换
            Class<?> target = field.getType();
            if (target == Long.class || target == long.class) {
                value = ((Number) value).longValue();
            } else if (target == Integer.class || target == int.class) {
                value = ((Number) value).intValue();
            }
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Set field failed: " + field.getName(), e);
        }
    }


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
        private Date createTime;
        private Date updateTime;
    }


}
