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
```

### yolo-tool-launch

监听项目名称，ip，端口，等服务信息

### yolo-tool-common

#### 集成swagger

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

#### 线程池配置

```yml
yolo:
  async:
    enabled: true
    keep-alive-seconds: 300
    queue-capacity: 10000
```

### yolo-tool-mybatis-plus

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

### yolo-tool-excel

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

### yolo-tool-log

> 介绍：自定义注解对日志进行入库

首先需要执行[yolo-tool-log.sql文件](https://gitee.com/huanglei1111/yolo-tool/blob/master/doc/yolo-tool-log.sql)

使用

```xml
        <dependency>
            <groupId>com.yolo</groupId>
            <artifactId>yolo-tool-log</artifactId>
            <version>0.0.1</version>
        </dependency>
```

### yolo-tool-xss

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

