# yolo-tool

## 工程结构

```java
yolo-tool
├── yolo-tool-launch -- 基础启动模块
├── yolo-tool-common -- 通用工具模块
├── yolo-tool-mybatis-plus -- mybatis-plus增强模块  
├── yolo-tool-excel -- excel模块  
├── yolo-tool-log -- 日志模块   
├── yolo-tool-xss -- xss模块  
├── yolo-tool-oss -- 对象存储模块  
├── yolo-tool-redis -- redis模块  
```

## 基础启动模块

监听项目名称，ip，端口，等服务信息

## 通用工具模块

### 集成swagger

```xml
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-common</artifactId>
            <version>0.0.1</version>
        </dependency>
```

swagger访问地址：ip:端口/doc.html

```yml
# yolo-tool 自定义配置 swagger默认开启
yolo:
  swagger:
    enabled: true
    title: "swagger测试"
    description: "描述"
    version: V1.0
    contact-user: yolo
    contact-email: huanglei421023@163.com
    contact-url: https://gitee.com/huanglei1111
# Knife4j接口文档
knife4j:
  enable: true
  # 开启生产环境屏蔽
  production: false
  basic:
    enable: true
    username: admin
    password: 123456
```

[knife4j详细配置信息](https://gitee.com/huanglei1111/developer-document/blob/master/SpringBoot%20%E7%B3%BB%E5%88%97/springboot-%E6%95%B4%E5%90%88knife4j.md)

> 注意：只有当yolo.swagger.enabled开启的时候才可以使用knife4j的配置信息，不然会报错

### 线程池配置

```yml
yolo:
  async:
    enabled: true
    keep-alive-seconds: 300
    queue-capacity: 10000
```

### 全局请求拦截

```yml
# 排除一些接口避免进行xss校验
yolo:
  request:
    enabled: true # 开启自定义请求
    skip-url: # 需要放行的接口
      # 下边的是knife4j使用的
      - /*.html
      - /swagger-resources
      - /webjars/**
      - /**/api-docs
      # swagger 文档配置
      - /*/api-docs
      - /*/api-docs/**
      # 静态资源
      - /*.html
      - /**/*.html
      - /**/*.css
      - /**/*.js
      # 公共路径
      - /favicon.ico
      - /error
```

### 本地文件上传

```yml
yolo:
  upload:
    enable: true   # 是否启用本地文件上传
    default-max-size: 50000000 # 文档大小默认50M   50*1024*1024
    default-file-name-length: 88 # 文档图片名称
```

## mybatis-plus增强模块

> 集成mybatis-plus

```xml
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-mybatis-plus</artifactId>
            <version>0.0.1</version>
        </dependency>
```

> 介绍

1. 自定义增强 BaseMapperPlus 简化操作
2. 自定义日志打印输出 ` yolo.mybatis.sql-log = true`启用
3. 自定义条件查询语句和分页查询条件

[Mybatis-plus配置](https://gitee.com/huanglei1111/yolo-springboot-demo/blob/master/demo-orm-mybatis-plus/src/main/resources/application.yml)

## excel模块

> 集成easyexcel

```xml
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-excel</artifactId>
            <version>0.0.1</version>
        </dependency>
```

> 介绍
>
> 1. 对easyexcel进行封装增强，自定义导入结果和导入默认导入监听
> 2. 自定义注解，枚举类型转换，合并策略，级联下拉选

## 日志模块

### api日志

> 自定义注解对日志进行入库

首先需要执行[yolo-tool-log.sql文件](https://gitee.com/huanglei1111/yolo-tool/blob/master/doc/yolo-tool-log.sql)

使用

```xml
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-log</artifactId>
            <version>0.0.1</version>
        </dependency>
```

### 控制器 请求日志

> 当处于开发环境或者测试环境会打印请求响应结果

配置如下

```yml
# dev 或者 test
spring:
  profiles:
    active: dev
```

## xss模块

> 介绍
>
> 1. 对表单绑定的字符串类型进行 xss 处理
> 2. 对 json 字符串数据进行 xss 处理
> 3. 提供路由和控制器方法级别的放行规则
> 4. 对表单和 json 字符串 trim 处理

使用

```xml
    <dependency>
        <groupId>com.yolo</groupId>
        <artifactId>yolo-tool-xss</artifactId>
        <version>0.0.1</version>
    </dependency>
```

配置

```yml
yolo:
  xss:
    enabled: true #开启xss
    trim-text: true # 全局是否去除文本首尾空格
    mode: clear # 模式：clear 清理（默认），escape 转义
    pretty-print: false # clear 专用 prettyPrint，默认关闭： 保留换行
    enable-escape: false # clear 专用 转义，默认关闭
    path-patterns: # 拦截的路径默认为 /**
    path-exclude-patterns: # 放行的路径，默认为空
```

## oss模块

集成了阿里云，七牛云，minio

使用

```xml 
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-oss</artifactId>
            <version>0.0.1</version>
        </dependency>
```

配置

```yml
yolo:
  oss:
    enabled: true # 是否启用
    name: alioss  # 对象存储名称 阿里云:alioss  minio:minio 七牛:qiniu
    endpoint: oss-cn-beijing.aliyuncs.com # 对象存储服务的URL
    access-key: LTAI5tGXXXXXXXXXXp9bhB    # Access key就像用户ID，可以唯一标识你的账户
    secret-key: DY1A7KGWrxXXXXXXXGFWmjeXE # Secret key是你账户的密码
    bucket-name: test               # 默认的存储桶名称
```

## redis模块

使用

```xml
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-redis</artifactId>
            <version>0.0.1</version>
        </dependency>
```

### redis cache 增强

支持 # 号分隔 cachename 和 超时，支持 ms（毫秒），s（秒），m（分），h（小时，默认一小时），d（天）等单位

```java
@Cacheable(value = "user#5m", key = "#id")
public String selectById(Serializable id) {
    log.info("selectById");
    return "selectById:" + id;
}
```

RedisCache 为简化 redis 使用的 bean

```java
@Autowired
private RedisCache redisCache;

@Override
public String findById(Serializable id) {
    return redisCache.get("user:" + id, () -> userMapper.selectById(id));
}
```

### 分布式限流

```yml
yolo:
  redis:
    rate-limiter:
      enable: true
```

使用注解

```java
/**
 * 分布式 限流注解，默认速率为 600/ms
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RateLimiter {

	/**
	 * 限流的 key 支持，必须：请保持唯一性
	 *
	 * @return key
	 */
	String value();

	/**
	 * 限流的参数，可选，支持 spring el # 读取方法参数和 @ 读取 spring bean
	 *
	 * @return param
	 */
	String param() default "";

	/**
	 * 支持的最大请求，默认: 100
	 *
	 * @return 请求数
	 */
	long max() default 100L;

	/**
	 * 持续时间，默认: 3600
	 *
	 * @return 持续时间
	 */
	long ttl() default 1L;

	/**
	 * 时间单位，默认为分
	 *
	 * @return TimeUnit
	 */
	TimeUnit timeUnit() default TimeUnit.MINUTES;
}
```

