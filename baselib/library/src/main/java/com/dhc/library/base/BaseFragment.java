package com.dhc.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.dhc.library.framework.ISupportBaseFragment;
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
 * @creator:denghc(desoce)
 * @updateTime:2018/7/30 12:02
 * @description: BaseFragment by no mvp
 */
public abstract class BaseFragment extends SupportFragment implements LifecycleProvider<FragmentEvent> ,ISupportBaseFragment {

    private static final Handler handler = new Handler();

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected View mRootView;

    protected Context mContext;


    /**
     * The activity is onAttach to the Fragment
     */
    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }


    /**
     * get Handler for thread scheduling
     *
     * @return
     */
    public final Handler getHandler() {
        return handler;
    }

    /**
     * replace  findViewById
     *
     * @param resId   layout resId
     * @param <T>   View
     * @return    View
     */
    @Override
    public  <T extends View> T $(int resId) {
        return (T) mRootView.findViewById(resId);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        int layoutId = getLayoutId();
        if (layoutId > 0)
            mRootView = inflater.inflate(layoutId, null);
        return mRootView;
    }

    /**
     * The default is landscape animation
     *
     * @return
     */
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!useLazy()) {
            initEventAndData(savedInstanceState);
        }
        Log.i(TAG,this.getClass().getName()+"onViewCreated");
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (useLazy()) {
            initEventAndData(savedInstanceState);
            Log.i(TAG,this.getClass().getName()+"onLazyInitView");
        }
    }

    /**
     * useLazy
     * @return
     */
    public boolean useLazy() {
        return true;
    }

    /**
     * Delayed popup keyboard
     *
     * @param focus Keyboard focus view
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

    /**
     * Pop up or close the keyboard
      * @param isShow  Pop up or close
     */
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
     * Check if the soft keyboard pops up
     */
    public boolean isShowKeyboard() {
        if (_mActivity == null || getView() == null)
            return false;
        InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(mContext.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(getView().getWindowToken(), 0)) {
            imm.showSoftInput(getView(), 0);
            return true;
        } else {
            return false;
        }
    }

    /**
     * When the Fragment state changes
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }


    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // todo,When the Fragment is visible to the use
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // todo,When the Fragment is invisible to the use
    }

    /**------------------------             Rxlife   start            ------------------------*/

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
        mRootView = null;
        mContext = null;
        Log.i(TAG,this.getClass().getName()+"onDestroyView");

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
    /**------------------------             Rxlife  end               ------------------------*/

}
