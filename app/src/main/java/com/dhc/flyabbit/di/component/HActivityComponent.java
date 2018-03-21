package com.dhc.flyabbit.di.component;

import com.dhc.flyabbit.app.App;
import com.dhc.library.di.ActivityScope;
import com.dhc.library.di.component.AppComponent;
import com.dhc.library.di.module.ActivityModule;

import dagger.Component;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:42
 * 描述 ：activity的注解使用,限定Context的范围,以及依赖注入的范围
 */
@ActivityScope
@Component(dependencies =AppComponent.class, modules = ActivityModule.class)
public interface HActivityComponent {


    void inject(App app);
//

//    void inject(HomeActivity homeActivity);

}
