# 目录
- [前言](#前言)
- [特点](#特点)
- [使用依赖](#使用依赖)
- [代码结构](#代码结构)
- [具体使用](#具体使用)


# 前言

<div >   
 <img src="http://i.imgur.com/4498nb3.jpg" width = "500" height = "250" alt="图片名称" align=center />
</div>


- 技术选型
- 组件化设计
- 本地Maven (nexus)简单使用
- 自动打包(Jenkins持续集成)
- 单元测试
- 线上Bug快速修复(热修复)
- ReactNative-Android 的简单实践
- 阿里Atlas(插件化)与该项目的简单实践


**集android技术于一体,你们想要的都在这里**

# 特点:   

-  `degger2` ,`util`....每个Modle中提供了`XXDiHelper` 快速使用AOP依赖注入,一键注入你想要的
-  `rxjava`,`retrofit`让网络请求线程切换赢在起跑线上
-  使用`ARouter`路由解耦跳转更加灵活
- ` MVP`更加简单实用,网络,缓存,数据库开箱即用
-  `base`中封装了懒加载的`BaseFragment`,`BaseActivity`,满足你日常开发的各种动作和姿势
- `room`,`MemoryCache`,`SPHelper`和` RxCache `提供了强大的网络请求和缓存功能
- 使用`AccountManager`提供了登录用户资料的简单管理


# 使用依赖:
	   
-  [`fragmentation`](https://github.com/YoKeyword/Fragmentation) :**强大的Framgment支持库,如同Activity一样使用**
-  [`rxjava`](https://github.com/ReactiveX/RxJava),[`rxandroid`](https://github.com/ReactiveX/RxAndroid)	       :**这个不用说了,强大的链式编程线程调度库**	       
-  [`rxlifecycle` ](https://github.com/trello/RxLifecycle) :   **Lifecycle handling APIs for Android apps using RxJava**
- `gson`,`retrofit`,`okhttp3`,`okhttp3-logging-interceptor`...	  **网络请求框架整合**     
-  [`rxpermissions `](https://github.com/tbruyelle/RxPermissions)    **Android runtime permissions powered by RxJava2**
-  [`RxCache`](https://github.com/VictorAlbertos/RxCache)      **Reactive caching library for Android and Java**
-  [`PersistentCookieJar`](https://github.com/franmontiel/PersistentCookieJar) **A persistent CookieJar implementation for OkHttp 3 based on SharedPreferences.**
-  [`dagger2`](https://github.com/google/dagger) **A fast dependency injector for Android and Java.**
-  [`arouter`](https://github.com/alibaba/ARouter)   **Android平台中对页面、服务提供路由功能的中间件**
-  [`room`](https://developer.android.com/topic/libraries/architecture/room.html) **Room是谷歌官方的数据库ORM框架**

# 代码结构:

    src
    └─com
       └─dhc
      	 └─library
			└─base
      	 		│  BaseApplication.java 		定义Application基类
       			│  BaseActivity.java  			无MVP的activity基类
       			│  BaseBean.java 				数据类的基类
       			│  BaseChildApplication.java  	该Application只能放在子moudle中使用,用于moudle隔离
       			│  BaseFragment.java 			无MVP的Fragment基类
       			│  BaseSubscriber.java     		调用者自己对请求数据进行处理
       			│  IBaseModle.java   			Modle请求数据的基类
       			│  IBasePresenter.java     		Presenter基类
				│  IBaseView.java     			View的基类
				│  XDaggerActivity.java   		MVP activity基类
				│  XDaggerFragment.java 		MVP Fragment基类
				│  XDataBindingActivity.java    Databinding和Dagger2使用的Activity
				│  XDataBindingFragment.java   	Databinding和Dagger2使用的Fragment
				│  XPresenter.java       		用于绑定view和解绑view
				│	other....
       		 └─data 
       			├─account
       			│  AccountManager.java   		登陆账号管理类
       			├─Cache 			
       			│  MemoryCache.java				内存缓存
       			├─net
       			│  ApiResponse.java				Api响应结果的封装类
				│  TokenInterceptor.java		Token管理
				│  SubscriberListener.java 		业务异常干货统一处理
				│  CallInterceptor.java			做请求前和请求后的操作
       			├─DBHelper.java	  				room数据库帮助类
       			├─HttpHelper.java  				网络请求的辅助类
				├─SPHelper.java 				SharedPreferences统一管理类								
       		└─di 
			  ├─component
			  │	AppComponent.java 				App的注入使用
			  ├─module	....					注入对象提供者
			  │  other...
       		└─util
       			├─file 							文件处理工具
       			├─media							图文视频处理工具
				├─rx							rxjava处理工具
       			├─storage						储存文件夹处理工具
       			├─string						String处理工具
       			├─sys							系统类处理工具
       			├─AppContext					保存全局的Application
       			├─ApplicationLike				作为接口，方便主工程调度子模块的声明周期
       			├─AppManager					Activity进栈出栈统一管理
				.....




# 具体使用


#### **Step 1. 在根目录的gradle文件中配置**


	allprojects {
			repositories {
				...
				 jcenter()
			}
		}


#### **step2 添加依赖(By 3.0):**


	dependencies {

	       api 'com.dhc.lib:mvp:1.0.4'
	}

#### **step3 项目中使用**

##### 1,Application中初始化 

可以让自己的主Application直接继承BaseApplication,如果有特殊的要求也可以把相对的代码复制到自己主Application中

	  public class   App extends BaseApplication{
		  public void onCreate() {
				//todo
			}
			...
		}


配置网络请求BaseUrl,也可以配置多个BaseUrl,以及网络相关的配置,具体配置可参考[NetConfig ](https://github.com/chengzichen/Flyabbit/blob/28e53032bd051748c59daa85e1e8b64dd78107b4/baselib/library/src/main/java/com/dhc/library/data/IDataHelper.java#L29)

	  /**
	     * 必须重新设置BaseUrl
	     * @return
	     */
	    @Override
	    public  IDataHelper.NetConfig getNetConfig() {
	        return new IDataHelper.NetConfig().configBaseURL(Constants.GANK_URL);
	    }




##### 2,根据实际要求实数据类ApiResponse, 请求异常统一处理BaseSubscriberListener,业务异常干货统一处理BaseSubscriber

参考自己具体业务进行处理
示例 :
ApiResponse

	public class GankApiResponse<T> implements ApiResponse<T> {
    private boolean error;
    public T results;
    @Override
    public T getData() {...}
    public void setData(T data) {...}
    @Override
    public boolean isSuccess() {...}
    @Override
    public boolean checkReLogin() {00}
	}
示例 :
BaseSubscriber

	public class GankSubscriber<T extends ApiResponse> extends BaseSubscriber<T> {
    private static final String TAG =GankSubscriber.class.getSimpleName() ;
    public GankSubscriber(SubscriberListener mSubscriberOnNextListener, Context aContext) {...}

    @Override
    public void onNext(T response) {
        if (mSubscriberOnNextListener != null) {
            if (response != null && response.isSuccess()) {
                mSubscriberOnNextListener.onSuccess(response.getData());
            } else {
                if (response.checkReLogin())
                    mSubscriberOnNextListener.checkReLogin("请先登陆", "请先登陆");
                mSubscriberOnNextListener.onFail(new NetError("请先登陆", NetError.BusinessError));
            }
        }
    }
}		

示例 :
BaseSubscriber

	public abstract class GankSubscriberListener<T>  extends BaseSubscriberListener<T> {
	    //对应HTTP的状态码
	    private static final int UNAUTHORIZED = 401;//没有权限
	    @Override
	    public void checkReLogin(String errorCode, String errorMsg) {
	        super.checkReLogin(errorCode, errorMsg);
	        RxBus.getDefault().post(new Events<String>(GO_LOGIN, AppContext.get().getString(R.string.GO_LOGIN)));
	    }
	
	    @Override
	    public boolean isCheckReLogin(HttpException httpException) {
	        return httpException.code() == UNAUTHORIZED;//or  todo
	    }
	}



**简单实用就到此就搞定了,还有些具体的功能和使用可以就看代码**