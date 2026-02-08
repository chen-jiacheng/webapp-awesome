-- 文件：V1.0.0__create_user_tables.sql
-- 完整的用户模块表结构

/*==============================================================*/
/* Table: tb_user 用户主表                                      */
/*==============================================================*/
CREATE TABLE IF NOT EXISTS `tb_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密存储）',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用，2-锁定',
    `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
    `login_count` INT(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除标记：0-正常，1-删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`) COMMENT '用户名唯一索引',
    UNIQUE KEY `uk_email` (`email`) COMMENT '邮箱唯一索引',
    UNIQUE KEY `uk_phone` (`phone`) COMMENT '手机号唯一索引',
    KEY `idx_status` (`status`) COMMENT '状态索引',
    KEY `idx_create_time` (`create_time`) COMMENT '创建时间索引',
    KEY `idx_nickname` (`nickname`) COMMENT '昵称索引'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';