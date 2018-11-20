# PersonalMaven
个人Maven库 用来存放开发的通用模块

 <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/资源管理-v1.0-blue.svg?style=flat-square" alt="资源管理" />
  </a>
   <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/QrCodeSDK-v1.0-blue.svg?style=flat-square" alt="二维码" />
  </a>
   <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/JDSDK-v1.1-blue.svg?style=flat-square" alt="京东SDK" />
  </a>
   <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/CSSDK-v1.1-blue.svg?style=flat-square" alt="客服IMSDK" />
  </a>
   <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/CommonSDK-v1.0.0-blue.svg?style=flat-square" alt="公共SDK" />
  </a>
     <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/PoplayerSDK-v1.0.0-blue.svg?style=flat-square" alt="PoplayerSDK" />
  </a>


使用前在根项目的build.gradle加入

```groovy
allprojects {
    repositories {
        maven {
            url "https://raw.githubusercontent.com/chenhongs/personal_maven/master"
        }
    }
}
```



## 使用资源管理模块

```groovy
implementation 'com.chenhong:resource:1.0'
```

## 使用二维码模块

```groovy
implementation 'com.chenhong:qrCode:1.0'
```

## 使用京东sdk 

京东sdk的功能汇总:

1.授权登录 

2.H5打开页面 详情页  任意url 购物车 订单 搜索关键词

3.呼起京东网页或者app


-ps: 因为安全图片问题 合并module无法初始化

```groovy
implementation 'com.chenhong:jdsdk:1.1'
```

## 使用环信客服模块

```groovy
implementation 'com.chenhong:cssdk:1.0.0'
```

## 使用弹窗管理模块Poplayer


```groovy
implementation 'com.chenhong:poplayer:1.0.0'
```

## 使用推送模块(个推集合多通道-华为魅族小米oppo)

```groovy
implementation 'com.chenhong:push:1.0.1'
```

## 使用Common基础组件

common提供了许多功能

包括

1.基础MVP架构定义

2.提供base基类

3.提供application,activity,fragment代理

4.提供全局Activity管理栈

5.提供工具类和自定义view

6.提供全局路由功能

7.提供用户信息管理 

8.提供基础广播

9.提供第三方sdk wx,高德地图，

10.提供全局消息收发

11.提供全局注入信息类 (包含错误处理 gson解析 缓存配置 网络处理等)

12.

提供数据层 1.缓存 2.数据库 3.网络 4.资源包 5.SP

其中提供统一管理接口 RepositoryManager 可由context获取注入对象获取

```groovy
implementation 'com.chenhong:common:1.0.0'
```
