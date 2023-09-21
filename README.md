# yolo-tool

### 集成swagger

> swagger默认开启，配置信息如下，
>
> [详细的配置请看gitee地址](https://gitee.com/huanglei1111/developer-document/blob/master/SpringBoot%20%E7%B3%BB%E5%88%97/springboot-%E6%95%B4%E5%90%88knife4j.md)
>
> swagger访问地址：ip:端口/doc.html

```yml
# Knife4j接口文档
knife4j:
  enable: true
  # 开启生产环境屏蔽
  production: false
  basic:
    enable: false
    username: admin
    password: 123456

springdoc:
  info:
    # 标题
    title: '标题：FastAdmin后台管理系统_接口文档'
    # 描述
    description: '描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...'
    termsOfServiceUrl: "127.0.0.1:8888"
    # 版本
    version: '版本号: 1.1.0'
    # 作者信息
    contact:
      name: yolo
      email: 2936412130@qq.com
      url: https://gitee.com/huanglei1111
```

### 请求日志打印

> 只有当处于生成环境，或者测试环境，请求日志才会打印
>
> ```
> /**
>  * 开发环境
>  */
> String DEV_CODE = "dev";
> /**
>  * 生产环境
>  */
> String PROD_CODE = "prod";
> /**
>  * 测试环境
>  */
> String TEST_CODE = "test";
> ```

```yml
spring:
  profiles:
    active: dev    
```

