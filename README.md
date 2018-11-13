# PersonalMaven
个人Maven库 用来存放开发的通用模块

 <a href="http://www.apache.org/licenses/LICENSE-2.0">
    <img src="http://img.shields.io/badge/资源管理-v1.0%202.0-blue.svg?style=flat-square" alt="资源管理" />
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
