package utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;

import java.lang.reflect.Field;

import activity.MainActivity;

public abstract class BaseFragment extends Fragment implements OnNewIntent {

    /**
     * 根元素
     */
    protected View rootView;
    /**
     * 布局渲染工具
     */
    protected LayoutInflater mInflater;
    protected Context mContext;
    private boolean mainTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContext = inflater.getContext();
        rootView = initRootView();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainTab = mainTabHide();
        if (mainTab) {
            getActivity().findViewById(R.id.mainTab).setVisibility(View.GONE);
        }
        setViews();
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mainTab) {
            getActivity().findViewById(R.id.mainTab).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化View,加载布局
     *
     * @return 布局
     */
    public abstract View initRootView();

    /**
     * 解决fragment嵌套问题:No activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化数据
     */
    public abstract void setViews();

    /**
     * 加载网络数据
     */
    public abstract void initData();

    public abstract boolean mainTabHide();

    /**
     * 默认的就是SINGLE_TASK模式（不带参数）
     */
    public void open(@NonNull Fragment fragment) {
        getRoot().manager.addFragment(this, fragment, null);
    }

    /**
     *  默认的就是SINGLE_TASK模式（带参数）
     */
    public void open(@NonNull Fragment fragment, Bundle bundle) {
        getRoot().manager.addFragment(this, fragment, bundle);
    }

    /*
     *(建议不用其他启动模式)
     * 默认的就是SINGLE_TASK模式
     * 整个app建议只用一个启动模式
     */
    public void open(@NonNull Fragment fragment, Bundle bundle, int stackMode) {
        getRoot().manager.addFragment(this, fragment, bundle, stackMode);
    }

    /**
     * Jump to the specified fragment and do not hide the current page.
     * @param to To jump to the page
     */
    public void dialogFragment(Fragment to) {
        getRoot().manager.dialogFragment(to);
    }

    /**
     * Set the animation to add fragment in dialog mode
     *
     * @param dialog_in  The next page to enter the animation
     * @param dialog_out The next page out of the animation
     */
    public void setDialogAnim(@AnimRes int dialog_in, @AnimRes int dialog_out) {
        getRoot().manager.setDialogAnim(dialog_in, dialog_out);
    }

    /**
     * close this current Fragment
     */
    public void close() {
        getRoot().manager.close(this);
    }

    /**
     * Closes the specified fragment
     */
    public void close(Fragment fragment) {
        getRoot().manager.close(fragment);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            onNowHidden();
        } else {
            onNextShow();
        }
    }

    /**
     * Override this method to facilitate access to the current page page Pause callback
     */
    private void onNowHidden() {

    }

    /**
     * Override this method to facilitate access to the current page page Resume callback
     */
    private void onNextShow() {

    }

    /**
     * Get fragment dependent Activity, many times this is very useful
     *
     * @return RootActivity dependent Activity
     */
    public MainActivity getRoot() {
        FragmentActivity activity = getActivity();
        if (activity instanceof MainActivity) {
            return (MainActivity) activity;
        } else {
            throw new ClassCastException("this activity mast be extends MainActivity");
        }
    }

    /**
     * Override this method in order to facilitate the singleTop mode to be called in
     */
    @Override
    public void onNewIntent() {
    }

}
