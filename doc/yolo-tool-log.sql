CREATE TABLE `log_api` (
                           `id` bigint(20) NOT NULL COMMENT '编号',
                           `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
                           `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
                           `server_host` varchar(255) DEFAULT NULL COMMENT '服务器名',
                           `server_ip` varchar(255) DEFAULT NULL COMMENT '服务器IP地址',
                           `env` varchar(255) DEFAULT NULL COMMENT '服务器环境',
                           `type` char(1) DEFAULT '1' COMMENT '日志类型',
                           `title` varchar(255) DEFAULT '' COMMENT '日志标题',
                           `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
                           `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
                           `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
                           `remote_ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
                           `method_class` varchar(255) DEFAULT NULL COMMENT '方法类',
                           `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
                           `params` text COMMENT '操作提交的数据',
                           `time` varchar(64) DEFAULT NULL COMMENT '执行时间',
                           `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接口日志表';



CREATE TABLE `log_error` (
                             `id` bigint(20) NOT NULL COMMENT '编号',
                             `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
                             `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
                             `server_host` varchar(255) DEFAULT NULL COMMENT '服务器名',
                             `server_ip` varchar(255) DEFAULT NULL COMMENT '服务器IP地址',
                             `env` varchar(255) DEFAULT NULL COMMENT '系统环境',
                             `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
                             `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
                             `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
                             `stack_trace` text COMMENT '堆栈',
                             `exception_name` varchar(255) DEFAULT NULL COMMENT '异常名',
                             `message` text COMMENT '异常信息',
                             `line_number` int(11) DEFAULT NULL COMMENT '错误行数',
                             `remote_ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
                             `method_class` varchar(255) DEFAULT NULL COMMENT '方法类',
                             `file_name` varchar(1000) DEFAULT NULL COMMENT '文件名',
                             `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
                             `params` text COMMENT '操作提交的数据',
                             `time` varchar(64) DEFAULT NULL COMMENT '执行时间',
                             `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错误日志表';


CREATE TABLE `log_usual` (
                             `id` bigint(20) NOT NULL COMMENT '编号',
                             `tenant_id` varchar(12) DEFAULT '000000' COMMENT '租户ID',
                             `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
                             `server_host` varchar(255) DEFAULT NULL COMMENT '服务器名',
                             `server_ip` varchar(255) DEFAULT NULL COMMENT '服务器IP地址',
                             `env` varchar(255) DEFAULT NULL COMMENT '系统环境',
                             `log_level` varchar(10) DEFAULT NULL COMMENT '日志级别',
                             `log_id` varchar(100) DEFAULT NULL COMMENT '日志业务id',
                             `log_data` text COMMENT '日志数据',
                             `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
                             `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
                             `remote_ip` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
                             `method_class` varchar(255) DEFAULT NULL COMMENT '方法类',
                             `method_name` varchar(255) DEFAULT NULL COMMENT '方法名',
                             `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
                             `params` text COMMENT '操作提交的数据',
                             `time` datetime DEFAULT NULL COMMENT '执行时间',
                             `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通用日志表';