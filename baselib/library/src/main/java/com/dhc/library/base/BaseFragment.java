package com.dhc.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.dhc.library.data.bean.ToolBarOptions;
import com.dhc.library.framework.IDaggerListener;
import com.dhc.library.utils.ToolbarUtil;
import com.dhc.library.utils.logger.KLog;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;


/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:08
 * 描述 ：无MVP的Fragment基类
 */
public abstract class BaseFragment extends SupportFragment implements LifecycleProvider<FragmentEvent>, IDaggerListener {
    private static final Handler handler = new Handler();
    private Toolbar toolbar;
    protected View mView;
    private boolean isInited = false;
    //_mActivity在SupportFragment中已经在onAttach中绑定了activity可以直接使用
    protected Context mContext;
    private ToolbarUtil mToolbarUtil;


    /**
     * activity与frament绑定时调用
     */
    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public ToolbarUtil getToolbarUtil() {
        return mToolbarUtil;
    }


    public final Handler getHandler() {
        return handler;
    }

    /**
     * 替代findviewbyid
     *
     * @param resId
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int resId) {
        return (T) mView.findViewById(resId);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        int layoutId = getLayoutId();
        if (layoutId > 0)
            mView = inflater.inflate(layoutId, null);
        initInject(savedInstanceState);
        return mView;
    }

    /**
     * 默认为横向切换动画
     *
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * viem 创建的回调
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!useLazy()) {
            isInited = true;
            initEventAndData(view);
        }
        KLog.t("ui").i("Fragment: " + getClass().getSimpleName() + " onViewCreated()");
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (useLazy()) {
            isInited = true;
            initEventAndData(mView);
            KLog.t("ui").i("Fragment: " + getClass().getSimpleName() + " onLazyInitView()");
        }
    }


    /**
     * 设置标题栏
     *
     * @param toolBarId
     * @param options
     */
    public void setToolBar(int toolBarId, ToolBarOptions options) {
        setToolBar(toolBarId, options, false);
    }

    /**
     * 设置标题栏
     *
     * @param toolBarId
     * @param options
     */
    public void setToolBar(int toolBarId, ToolBarOptions options, boolean haveLine) {
        toolbar = (Toolbar) mView.findViewById(toolBarId);
        if (options == null) {
            throw new RuntimeException(
                    " options is null ");
        }
        mToolbarUtil = new ToolbarUtil(_mActivity, toolbar, haveLine);
        if (!TextUtils.isEmpty(options.titleString)) {
            mToolbarUtil.setTitle(options.titleString);

        } else if (options.titleId != 0) {
            mToolbarUtil.setTitle(getContext().getText(options.titleId));
        }
        if (options.mOptionsButtons != null && options.mOptionsButtons.size() > 0) {
            mToolbarUtil.addOptionButton(options.mOptionsButtons);
        }

        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        if (options.isNeedNavigate) {
            toolbar.setNavigationIcon(options.navigateId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doYouWantTodo();
                    pop();
                }
            });
        }
    }

    protected void doYouWantTodo() {

    }

    public void setTitle(String title) {
        if (mToolbarUtil != null)
            mToolbarUtil.setTitle(title);
    }

    public boolean useLazy() {
        return true;
    }

    /**
     * 延时弹出键盘
     *
     * @param focus 键盘的焦点项
     */
    protected void showKeyboardDelayed(View focus) {
        final View viewToFocus = focus;
        if (focus != null) {
            focus.requestFocus();
        }

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewToFocus == null || viewToFocus.isFocused()) {
                    showKeyboard(true);
                }
            }
        }, 300);
    }

    protected void showKeyboard(boolean isShow) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        if (isShow) {
            if (activity.getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(activity.getCurrentFocus(), 0);
            }
        } else {
            if (activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

        }
    }

    /**
     * 判断软键盘是否弹出
     */
    public boolean isSHowKeyboard() {
        if (_mActivity == null || getView() == null)
            return false;
        InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(mContext.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(getView().getWindowToken(), 0)) {
            imm.showSoftInput(getView(), 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    /**
     * 获取titile对象
     *
     * @return
     */
    public Toolbar getToolBar() {
        return toolbar;
    }

    public int getToolBarHeight() {
        if (toolbar != null) {
            return toolbar.getHeight();
        }

        return 0;
    }

    /**
     * 当Fragment状态改变时调用
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //        if (!isInited && !hidden) {
        //            isInited = true;
        //            if (mView != null)
        //                initEventAndData(mView);
        //        }
    }


    protected abstract int getLayoutId();

    protected abstract void initEventAndData(View view);

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // todo,当该Fragment对用户可见时
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // todo,当该Fragment对用户不可见时
    }

    /**------------------------             Rxlife用于管理Rxjava的生命周期的                ------------------------*/
    /**
     * ------------------------                            end              ------------------------
     */

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }


    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        toolbar = null;
        mView = null;
        mContext = null;
        KLog.t("ui ").i("Fragment: " + getClass().getSimpleName() + " onDestroyView()");
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }
    /**------------------------             Rxlife用于管理Rxjava的生命周期的                ------------------------*/
    /**------------------------                            end              ------------------------*/


}
