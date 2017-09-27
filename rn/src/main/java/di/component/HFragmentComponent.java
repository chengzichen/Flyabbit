package di.component;

import com.dhc.flyabbit.rn.ReactNativeFragment;
import com.dhc.library.di.FragmentScope;
import com.dhc.library.di.component.AppComponent;
import com.dhc.library.di.module.FragmentModule;

import dagger.Component;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:54
 * 描述 ：Fragment里的注解,限定Activity的范围,以及依赖注入的范围为Fragment
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface HFragmentComponent {


    void inject(ReactNativeFragment reactNativeFragment);
}
