## 前言
- 快速实现MVP组件化
- ReactNative-Android 的简单实践
- 阿里Atlas(插件化)与该项目的简单实践

**集android技术于一体,你们想要的都在这里**

## 分支说明

   - master : 主分支以MVP和组件化更新为主
   - feature-rn : rn特色分支 :主要以更新rn与Android结合实践优化为主
   - feature-atlas : 以阿里Atlas插件化结合为主

## 最新版本


| 项目名 | 文档说明 |源码 | Demo |最新版本|
| :------| ------: | :------: | :------: | :------: |
| AndroidStudio插件 | [IDE 插件文档](https://juejin.im/post/5b163118f265dahttps://juejin.im/post/5b163118f265da6e1349072a6e1349072a) | [源码](https://github.com/chengzichen/component) | 无 |![](https://img.shields.io/github/release/chengzichen/component.svg)|
| 组件化脚本 | [**组件化脚本文档**](https://github.com/chengzichen/Flyabbit/blob/master/%E7%BB%84%E4%BB%B6%E5%8C%96%E7%9A%84%E4%BD%BF%E7%94%A8.md) | [源码](https://github.com/chengzichen/Flyabbit/tree/master/build-gradle) |无 | [ ![Download](https://api.bintray.com/packages/chengzichen/maven/component-plugin/images/download.svg) ](https://bintray.com/chengzichen/maven/component-plugin/_latestVersion)|
| FMVP | [**FMVP文档**](https://github.com/chengzichen/Flyabbit/blob/master/Fmvp%E4%BB%8B%E7%BB%8D.md) | [源码](https://github.com/chengzichen/FMVP) |[demo](https://github.com/chengzichen/FMVP) |[![Download](https://api.bintray.com/packages/chengzichen/maven/mvp/images/download.svg) ](https://bintray.com/chengzichen/maven/mvp/_latestVersion)|
| FSelector | [**FSelector文档**](https://github.com/chengzichen/FSelector) | [源码](https://github.com/chengzichen/FSelector) |[demo](https://github.com/chengzichen/FSelector) |[![](https://www.jitpack.io/v/chengzichen/FSelector.svg)](https://www.jitpack.io/#chengzichen/FSelector)|
| RxBus |[总线(**RxBus**)文档](https://github.com/chengzichen/Flyabbit/blob/master/RxBus.md)| [源码](https://github.com/chengzichen/Flyabbit/tree/master/baselib/rxbus2) |暂无 |[ ![Download](https://api.bintray.com/packages/chengzichen/maven/rxbus2/images/download.svg) ](https://bintray.com/chengzichen/maven/rxbus2/_latestVersion)|
| Timberhelper |[好用的日志管理文档](https://github.com/chengzichen/Flyabbit/blob/master/Timber.md)| [源码](https://github.com/chengzichen/Flyabbit/tree/master/baselib/timberhelper) |暂无 |[ ![Download](https://api.bintray.com/packages/chengzichen/maven/timberhelper/images/download.svg) ](https://bintray.com/chengzichen/maven/timberhelper/_latestVersion)|


## 效果


[Gif动态效果](https://github.com/chengzichen/Photo/raw/master/gif/show.gif)   [*DemoAPK下载*](https://github.com/chengzichen/Photo/blob/master/gif/app-release.apk)   [架构图](https://i.imgur.com/sEuZMdp.png)

## 简介
-  [AndroidStudio插件](https://juejin.im/post/5b163118f265dahttps://juejin.im/post/5b163118f265da6e1349072a6e1349072a)

	- 使用简单,能够灵活配置任意的组件使用组件化
	- 一键使用MVP,提供了选择最新或者适合自己的mvpsdk依赖版本(Dagger2 以及ARouter)

-----
- [**组件化脚本插件**](https://github.com/chengzichen/Flyabbit/blob/master/%E7%BB%84%E4%BB%B6%E5%8C%96%E7%9A%84%E4%BD%BF%E7%94%A8.md) 

 	- 优化组件化gradle脚本,让配置脚本更轻盈

-----

- [**FMVP**](https://github.com/chengzichen/Flyabbit/blob/master/Fmvp%E4%BB%8B%E7%BB%8D.md)

	-  `degger2` ,`util`....每个Modle中提供了`XXDiHelper` 快速使用AOP依赖注入,一键注入你想要的
	-  `rxjava`,`retrofit`让网络请求线程切换赢在起跑线上
	-  使用`ARouter`路由解耦跳转更加灵活
	- ` MVP`更加简单实用,网络,缓存,数据库开箱即用
	-  `base`中封装了懒加载的`BaseFragment`,`BaseActivity`,满足你日常开发的各种动作和姿势
	- `room`,`MemoryCache`,`SPHelper`和` RxCache `提供了强大的网络请求和缓存功能



-----

-  [日志管理(**Timber**)](https://github.com/chengzichen/Flyabbit/blob/master/Timber.md)

	- 整合了timber和logger日志框架,一行代码初始化,debug打印日志,release异步保存错误日志到指定的位置. 

-----

-  [事件总线(**RxBus**)](https://github.com/chengzichen/Flyabbit/blob/master/RxBus.md) 
	- 提供普通的消息通知,以及粘性消息通知,使用了Rxjava必备神器
 
-----
## 混淆

- android混淆说明
	
	混淆文件都在了`app/proguard-pro`目录下,第三方库的混淆都提供了,满足日常开发,除了第三方开源库之外,在`app/proguard-pro/proguard-self.pro`中还有项目特有的混淆方式.需要添加就在改文件中添加混淆


## 相关文章

第一篇-网络篇:

 - [[从零开始系列]AndroidApp研发之路(一) 网络请求的封装(一)](http://blog.csdn.net/chengzichen_/article/details/77659318)

第二篇-Retrofit源码解析

  - [[从零开始系列]AndroidApp研发之路(二)Retrofit原理篇](http://blog.csdn.net/chengzichen_/article/details/77840996)

第三篇-Android组件化和快速实现MVP

  - [ComponentPlugin专注于:Android组件化和快速实现MVP(干货)](https://juejin.im/post/5b163118f265da6e1349072a)

  更新中....
  
## 关于个人
 
  
  Github:[https://github.com/chengzichen](https://github.com/chengzichen)
  
  CSDN : [http://blog.csdn.net/chengzichen_](http://blog.csdn.net/chengzichen_)
  
  个人博客 : [https://chengzichen.github.io/](https://chengzichen.github.io/)
<div  align="center"> 
本人一直都致力于组件化和插件化的研究如果大家有更好的想法可以联系我一起成长
</div>
<div  align="center">   
 <img src="https://i.imgur.com/J1LpBum.jpg" width = "200" height = "300" alt="图片名称" align=center />
</div>
