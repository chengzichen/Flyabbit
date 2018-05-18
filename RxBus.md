

# 目录
- [前言](#前言)
- [特点](#特点)
- [使用依赖](#使用依赖)
- [使用](#使用)



# 前言

<div >   
 <img src="http://i.imgur.com/4498nb3.jpg" width = "500" height = "250" alt="图片名称" align=center />
</div>


- 技术选型
- 组件化设计
- 本地Maven (nexus)简单使用
- 自动打包(Jenkins持续集成)
- 单元测试-
- 线上Bug快速修复(热修复)
- ReactNative-Android 的简单实践
- 阿里Atlas(插件化)与该项目的简单实践


**集android技术于一体,你们想要的都在这里*


# 特点: 

提供普通的事件通知,以及粘性事件通知,线程调度,使用了Rxjava必备神器


# 使用

**Step 1. 在根目录的gradle文件中配置**


	allprojects {
			repositories {
				...
				 jcenter()
			}
		}


**step2 添加依赖(By 3.0):**


	dependencies {

	       api 'com.dhc.rxbus:rxbus2:1.0.0'
	}
