# yolo-tool

### 工程结构

```java
yolo-tool
├── yolo-tool-launch -- 基础启动模块
├── yolo-tool-common -- 通用工具模块
├── yolo-tool-excel -- excel模块  
└── yolo-tool-all -- 包含上面所有模块模块
```

### 集成swagger

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

