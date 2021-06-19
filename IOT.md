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

和Springboot集成：[🔗](https://blog.csdn.net/qq_41873771/article/details/114965238)、[🔗](https://blog.csdn.net/User_jing/article/details/111678676)、

#### Spring Boot

>   对Spring Boot来说，所有在启动类的下级包里的注解，都会被自动扫描到
>
>   当配置完Spring扫描指定包及其子包中的类时，会识别所有标记了@Component、@Controller、@Service、@Repository注解的类，由于@Configuration注解本身也用@Component标注了，Spring将能够识别出@Configuration标注类，并顺便扫描该类下所有@Bean方法

##### 注解

按照对象的来源，注解分为两类：

-   一类是**使用Bean**，就是创建**第三方库的类实例**，通常是@Bean修饰的方法所返回的类对象，其中@Bean修饰的方法一般会在@Configuration修饰的类中

    注：@Bean会要求被修饰的方法必须返回一个类对象，并将该类对象转化为Bean，交给Spring容器管理；值得注意的是，返回的对象可以是自定义的类也可以是第三方库的类，由于我们通常会用注册Bean的方法(见后面)来创建自定义类实例的Bean，因此@Bean也就基本上只用于创建第三方库类实例的Bean了

-   一类是**注册Bean**，就是创建**自定义的类实例**，通常是@Controller , @Service , @Repository , @Component , @Configration这些注解来修饰自定义的类，并会自动生成被修饰的自定义类的实例

简单来说，一种是用别人定义好的类来创建对象，一种是用自定义的类来创建对象，最终实例化的对象都会转化成Bean(默认是单例)，放在IoC容器中，交给Spring管理，这样的对象最终会和@Autowired , @Resource配合注入，供各种其他对象使用

##### @Configuration和@Bean

先看个例子：

~~~java
// DataSource类型的配置类，其中XxxDataSource一般是第三方库的类
@Configuration
public class DataSourceConfig {
	@Bean(name = "mysqlDataSource") // name如果不写，默认是方法名，因此这一行可以去掉
	public MysqlDataSource mysqlDataSource() {
		return new MysqlDataSource();
	}
    @Bean
    public XxxDataSource xxxDataSource() {
        return new XxxDataSource();
    }
    ...
}
~~~

形象地说，@Configuration就相当于是父级的`<beans>`目录，而@Bean就相当于是该目录下的一个个`<bean>`标签

~~~xml
<beans>
	<bean>1</bean>
    <bean>2</bean>
    <bean>3</bean>
    ...
</beans>
~~~

@Configuration注解本质上就是@Component(只不过基本用不到它的实例化对象)，它一般用于修饰`xxxConfig`配置类，这个类的作用主要是声明多个**同一类型**的@Bean方法，并在运行时刻根据这些方法返回的类对象来生成Bean

~~~java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {
    String value() default "";
}

~~~

@Bean的作用前面已经说过了，就是用来修饰返回类实例的方法，并将返回的类实例转化为Bean，默认是**单例模式**，交给Spring容器管理，其他地方可以通过@AutoWired或@Resource取得用@Bean注解的bean

-   name：Bean的名字，默认是方法名
-   initMethod：容器在初始化Bean后，会调用该属性指定的方法
-   destroyMethod：容器在销毁Bean前，会调用该属性指定的方法
-   autowire：指定Bean的自动装配策略，有三种类型：Autowire.BY_NAME, Autowire.BY_TYPE, Autowire.NO

~~~java
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
    @AliasFor("name")
    String[] value() default {};
    @AliasFor("value")
    String[] name() default {};
    Autowire autowire() default Autowire.NO;
    String initMethod() default "";
    String destroyMethod() default "(inferred)";
}
~~~

##### @Component,@Controller,@Service,@Repository

###### 区别简介

@Component、@Repository、@Service、@Controller这四个本质上属于同一类注解，用法相同，功能相同，只是名字不同，用于标识不同类型的类

-   @Component ：标注一个普通的Spring Bean类
-   @Controller：标注一个controller类，通常和@RequestMapping注解配合使用
-   @Service：标注一个service类
-   @Repository：标注一个dao类 

被注解的java类当做Bean实例，Bean实例的名称默认是Bean类的首字母小写，其他部分不变

###### @Controller

@Controller标注一个controller类，主要用于处理前端请求并返回结果，通常和@RequestMapping注解配合使用

@RequestMapping：将url映射到特定的类或方法，可以使用**value**属性标识url，使用**method**属性标识其所接受的方法类型，主要是HTTP GET/POST，一旦指定就只能接收该类型，否则所有类型都接受，通常我们会用@GetMapping和@PostMapping注解来表示GET和POST的request请求

-   @GetMapping：等价于@RequestMapping(method = RequestMethod.GET)，**直接从url获得数据**
-   @PostMapping：等价于@RequestMapping(method = RequestMethod.POST)，**从body中拿数据**

注意，在url上带参数的形式只能是GET请求，如果将带参数的url发给@PostMapping修饰的方法就会报错，@PostMapping修饰的方法一般是用于接收前端发送的请求，前端会把数据塞进body里

前端和后端进行数据传输是用的HTTP协议，[参数提交](https://www.jianshu.com/p/065fc0555056)有两种方式：url和请求体，通常GET请求会把参数放在url中，POST请求会把参数放在请求体中

**从前端传数据到后端**，不论是GET请求还是POST请求，传的都是**键值对数据**，@GetMapping指定了从**url**中拿k-v键值对数据（url数据类型: application/x-www-form-urlencoded），用@RequestParam接收；@PostMapping指定了从**请求体**中拿json键值对数据（请求体数据类型: application/json），用@RequestBody接收

对后端来说，[接收键值对数据](https://cloud.tencent.com/developer/article/1414464)有两种形式：@RequestParam和@RequestBody

-   @RequestParam一般用于接收请求头里的参数(类型为x-www-form-urlencoded)，通常用于接收GET请求，可以将k-v数据绑定到函数参数上，value表示参数名，required表示该参数是否必须存在(默认为true)，defaultValue用于设置value的默认值

    注：像POST请求也可以使用@RequestParam，但必须要将请求体的类型设置为x-www-form-urlencoded

    ~~~java
    // GET请求url为：http://ip/menuTree.json?appName="demo"&keyword="rpc"
    // 访问该url相当于直接调用该函数
    // 等价于@RequestMapping(value="/menuTree.json", method="RequestMethod.GET")
    @GetMapping("/menuTree.json")
    public CustomResult menuTree(
        @RequestParam(value = "appName") String appName,
        @RequestParam(value = "keyword", required = false) String keyword) {
        String menu = componentService.getMenuTree(appName, keyword, isOrigin);
        return new CustomResult(menu);
    }
    ~~~

-   @RequestBody用于接收请求体中的数据(类型为json)，通常用于接收POST请求，可以将json数据转为java对象

    ~~~java
    @PostMapping("/login")
    public Result login(@RequestBody UserDO requestUser) {
        return loginService.login(requestUser);
    }
    ~~~

**从后端传数据到前端**，一般返回的是html/jsp数据和json数据：

-   返回html/jsp数据：如果只是对类使用@Controller注解，那么在该类@RequestMapping修饰的方法上，要求必须返回html或jsp页面，并且前端会跳转到该页面，如果只是返回普通的数据(比如字符串或其他对象)，就会报错

    在函数中返回html/jsp文件名称即可，这些文件需要放在`/resources/templates`文件下，且html头需要有`<html  xmlns:th="http://www.thymeleaf.org">`声明

    ~~~java
    @Controller
    public class xxxController {
        @GetMapping("/404")
        public String test() {
            return "404"; // 这里返回的“404”是文件名，网页会显示404.html
        }
    }
    ~~~

-   返回json数据：@Controller修饰的类，其中的方法再用@ResponseBody修饰，就可以使该方法的返回值以json数据的形式返回，如果是对象，json数据就是{属性名：属性值}的集合

    而@RestController就相当于是@Controller+@ResponseBody，因此被@RestController修饰的类，其中所有方法的返回值都会被解析为json数据

    ~~~java
    // 返回json数据
    @Controller
    public class xxxController {
        @ResponseBody
        @GetMapping("/test")
        public Result test(...) {
            return new Result();
        }
    }
    等价于...
    @RestController
    public class xxxController {
        @GetMapping("/test")
        public Result test(...) {
            return new Result();
        }   
    }
    ~~~





后端404转发前端vue：[🔗](http://www.cxyzjd.com/article/Mr_EvanChen/83625082)



☆mqtt类的设计



vue入门：https://www.cnblogs.com/keepfool/p/5619070.html

前端可视化--大屏

https://blog.csdn.net/Mrkaizi/article/details/112233166

黑马：https://juejin.cn/post/6960125126008389669#heading-38、https://www.bilibili.com/video/BV1VZ4y1M7ZC?p=2

vue修改proxyTable解决**跨域**请求：[1](https://www.geek-share.com/detail/2789999270.html)

到config/index.js文件中，找到proxyTable，加上：

~~~js
proxyTable: {
    '/api': {
        target: 'http://localhost:8443', // 后端地址
            changeOrigin: true, // 允许跨域
                pathRewrite: {
                    '^/api': '' // 重写路径
                }
    }
},
~~~

前端请求url中的所有`/api`都会被替换成`/`，由于我们在main.js里把axios的baseURL设置成了`/api`，因此调用this.$http.get(“datainfo”)/post("datainfo")发起http请求时，前端的请求url其实是`/api/datainfo`，然后根据我们的proxyTable，会把这个`/api`替换成空，即url变成了`/datainfo`，然后加上target地址，变成了`http://localhost:8443/datainfo`，注意，此时我们在后端springboot就要配置为`@GetMapping("datainfo")`，而不是`@GetMapping("api/datainfo")`

地图轨迹：https://blog.csdn.net/yc_1993/article/details/51439365

data：id、位置(经纬度)、时间(info和timestamp)、值(value)->报警信息(alert)

~~~bash
Publishing message: {
	"alert":1,
	"clientId":"device0005",
	"info":"Device Data 2021/06/15 00:29:44",
	"lat":30.4978018283844,
	"lng":120.02572371959687,
	"timestamp":1623688184668,
	"value":85}
~~~

图表规划：vue每秒刷新

-   设备在线数量：单折线图
-   设备值：多折线图+柱形图
-   位置：地图,https://www.pianshen.com/article/5319354514/

如何使用百度地图api的bmap：其实echarts包中已经有现成的bmap，而我们早就已经install过echarts了，因此无需下载任何文件，如果想要显示地图，只需两步：

-   [生成百度地图密钥](https://lbsyun.baidu.com/apiconsole/key#/home)并在`index.html`中引入

    ~~~html
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=HS96e7pqaFkaXo6yS1h6iNIh01T5N42g"></script>
    ~~~

-   在想要使用map地图的vue组件的script模块中，导入bmap即可

    ~~~html
    <script>
    import 'echarts/dist/extension/bmap.min.js'
    export default {
        ...
    }
    </script>
    ~~~

-   测试代码：如果能够显示地图表示成功导入

    ~~~js
    methods: {
        initChart() {
            this.chartInstance = this.$echarts.init(this.$refs.track_map_ref)
            const initOption = {
                bmap: {
                    center: [113.844038, 22.907044],//默认中心点;
                    zoom: 12,//缩放级别；
                    roam: false,//是否缩放
                    mapStyle:{style:'hardedge'},
                    mapOptions: {
                        enableMapClick: false//能都是地图可以点击
                    },     
                },
                geo: {                
                    map: 'bmap', // 使用百度地图的bamp                 
                    type: 'map'
                },
                series: []    
            }
            this.chartInstance.setOption(initOption)
        },
    }
    ~~~

-   百度地图bmap api使用文档：https://malagis.com/baidu-map-api-custom-style-map.html

-   画轨迹：https://blog.csdn.net/weixin_42429220/article/details/109495713

-   echarts的option是merge方式，不会删除原先的旧点：https://github.com/apache/echarts/issues/3976

从单设备轨迹切换回多设备位置时，单设备对应的点并没有被移除，因为setOption本身默认是做merge，会保留原先的点，为了解决这个问题，有两种思路：

-   清空原chart，重新绘制：重载资源太浪费了

    ~~~js
    // 重置echarts，性能低，但目前官方并没有提供更好的方案
    resetEcharts() {
        // console.log(this.chartInstance.getOption())
        this.chartInstance.clear()
        this.chartInstance.setOption({bmap:{}})
    
        this.bmap = this.chartInstance.getModel().getComponent('bmap').getBMap()     
        this.bmap.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT})) // 地图左下角显示比例尺
        this.bmap.setMapStyleV2({styleId: '8394c7f10d2cb459727e8c39ba0a3650'})
    }
    ~~~

-   setOption时删除旧的部分组件，做属性的替换而不是合并：https://echarts.apache.org/zh/api.html#echartsInstance.setOption，参考replaceMerge参数，但存在一些bug，参考我提的issue：https://github.com/apache/echarts/issues/15186

    ~~~js
    this.chartInstance.setOption(dataOption, {replaceMerge: 'series'})
    ~~~

    发现可能是原先我的echarts4.9.0版本不支持replaceMerge特性，需要升级到echarts5