# IOT

emqtt：[下载说明](https://www.emqx.cn/downloads#broker)

macos简单命令：

~~~bash
# 安装
$ brew tap emqx/emqx
$ brew install emqx

# 启动
$ emqx start
emqx 4.0.0 is started successfully!
$ emqx_ctl status
Node 'emqx@127.0.0.1' is started
emqx v4.0.0 is running

# 停止
$ emqx stop
ok

# 卸载
$ brew uninstall emqx
~~~

SpringBoot：

Filter：

-   [定义](https://segmentfault.com/a/1190000025152370)
-   [使用](https://www.cnblogs.com/jeffwongishandsome/p/spring-boot-use-filter-and-interceptor-to-implement-an-easy-auth-system.html)

#### SpringBoot入门

>   [案例](https://blog.csdn.net/Neuf_Soleil/article/details/88925013)

##### 使用 CLI 搭建 Vue.js 项目

###### 几个基本概念

**前后端分离**：核心思想是前端页面通过 ajax 调用后端的 restuful api 进行数据交互

**单页面应用**(single page web application，SPA)：就是只有一张页面，并在用户与应用程序交互时动态更新该页面的 Web 应用程序

**restful api**：核心思想是**把**网页**请求当成资源**（名词而不是动宾短语）来看待

-   看url就知道要什么
-   看http method就知道干什么
-   看http status code就知道结果如何

比如：

~~~
Server提供的RESTful API中，URL中只使用名词来指定资源，原则上不使用动词：
- http://api.qc.com/v1/newsfeed: 获取某人的新鲜;
- http://api.qc.com/v1/friends: 获取某人的好友列表;
- http://api.qc.com/v1/profile: 获取某人的详细信息;

用HTTP协议里的动词来实现资源的添加，修改，删除等操作：
- GET 用来获取资源
- POST 用来新建资源（也可以用于更新资源）
- PUT 用来更新资源
- DELETE 用来删除资源
~~~

**entity、bo、vo、po、dto、pojo的区别**：参考[文章](https://www.jianshu.com/p/b934b0d72602)

pom.xml一直下载不下来，需要修改为镜像

big sur的java环境有问题，需要手动设置并删除文件

mysql连接一直出问题，后来卸载了brew装的mysql，采用dmg下载，然后重新设置用户名和密码

#### 依赖和注解介绍

>   Spring的注解形式：@Repository、@Service、@Controller，它们分别对应存储层Bean，业务层Bean，和展示层Bean

##### mybatis

**mybatis配置**：参考[🔗](https://www.jianshu.com/p/c97536dd8c18)

-   pom.xml添加maven依赖：mybatis-spring-boot-starter和mysql-connector-java，具体版本到[maven仓库](https://mvnrepository.com/)里查

-   在application.properties添加数据库和mybatis配置：

    -   `mybatis.type-aliases-package`用来指定DO实体对象所在包，这样在写xml时resultType就可以省去包名，当然这个属性也可以不设置，使resultType直接指定返回对象类所在的整条路径名即可

    -   `mybatis.mapper-locations`用来指定xml文件所在目录，这里默认根目录是resources

    ~~~
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/iot?characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false
    spring.datasource.username=root
    spring.datasource.password=fuck1712824638
    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    
    mybatis.type-aliases-package=com.gehao.iotserver.dal.dataobject
    mybatis.mapper-locations=classpath:mybatis.mapper/*.xml
    mybatis.configuration.map-underscore-to-camel-case=true
    ~~~

-   在dal层创建dataobject包和mapper包，分别用于存放do实体对象和mybatis的查询函数

-   在resources/mybatis/mapper目录下创建dal/mapper函数对应的xml文件，并写好查询语句

-   使用者(一般是service)只需要诸如dal.mapper对象，然后调用函数即可使用sql语句

**@Reposity**：参考[🔗](https://blog.csdn.net/aliushui/article/details/46042455)

为什么 @Repository 只能标注在 DAO 类上呢？这是因为该注解的作用不只是将类识别为Bean，同时它还能将所标注的类中抛出的数据访问异常封装为 Spring 的数据访问异常类型。 Spring本身提供了一个丰富的并且是与具体的数据访问技术无关的数据访问异常结构，用于封装不同的持久层框架抛出的异常，使得异常独立于底层的框架

**运行出错**：参考[🔗](https://blog.csdn.net/sundacheng1989/article/details/81630370)

一个很坑爹的错误：实际操作的时候mybatis没有成功查询到数据，报的error是：Invalid bound statement (not found)，查了很多[原因](https://blog.csdn.net/sundacheng1989/article/details/81630370)，结果发现我的错误最特殊，路径不能用点号，如下所示：

```bash
# 错误写法
mybatis.mapperLocations=classpath:mybatis.mapper/*.xml
# 正确写法
mybatis.mapperLocations=classpath:mybatis/mapper/*.xml
```

##### lombok

lombok可以自动生成getters, setters, equals, hashCode and toString等代码，你只需要写好类成员变量即可

项目中经常使用bean，entity等类，绝大部分数据类类中都需要get、set、toString、equals和hashCode方法，而使用了lombok的注解@Setter、@Getter、@ToString、@@RequiredArgsConstructor、@EqualsAndHashCode或@Data(等价于前面的注解并集)之后，就不需要编写或生成get/set等方法，很大程度上减少了代码量，而且减少了代码维护的负担

此外，增加@Accessors(chain = true)注解可以让set方法返回对象本身

~~~java
package com.gehao.iotserver.dal.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author gehao
 */
@Data
@Accessors(chain = true)
public class UserDO {
}
~~~

#### 过滤

如下：

<img src="https://user-gold-cdn.xitu.io/2019/12/14/16f04e00a9e12728?imageView2/0/w/1280/h/960/format/webp/ignore-error/1" style="zoom:67%;" >

前端代码加到后端里，一个服务器即可运行



#### mqtt集成

原理介绍：[🔗](https://zhuanlan.zhihu.com/p/353577360)、[🔗](https://www.ctolib.com/gaoyf95-springboot-mqtt.html)、[🔗](https://xie.infoq.cn/article/5663942f0b68e0d42f7eab880)、

和Springboot集成：[连接](https://segmentfault.com/a/1190000017811919)

