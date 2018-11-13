# personal_maven
个人Maven库 用来存放开发的通用模块

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
