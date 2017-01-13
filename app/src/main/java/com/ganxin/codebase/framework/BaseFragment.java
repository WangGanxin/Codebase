package com.ganxin.codebase.framework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ganxin.codebase.utils.log.LogUtil;

/**
 *
 * Description : Fragment基类  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public abstract class BaseFragment extends Fragment {
    private boolean isVisibleToUser;
    private boolean isViewInitialized;
    private boolean isDataInitialized;
    private boolean isLazyLoadEnabled;

    public abstract void setUpView(View view);
    public abstract void setUpData();

    public void enableLazyLoad(){
        isLazyLoadEnabled = true;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.e(toString() + ":onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e(toString() + ":onCreate");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        LogUtil.e(toString() + ":setUserVisibleHint:" + isVisibleToUser);
        checkIfLoadData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.e(toString() + ":onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.e(toString() + ":onViewCreated");
        if (!isLazyLoadEnabled){
            setUpView(view);
            setUpData();
        }else {
            setUpView(view);
            isViewInitialized = true;
            if (savedInstanceState != null){
                onRestoreInstanceState(savedInstanceState);
            }
            if (isDataInitialized){
                setUpData();
            }else {
                checkIfLoadData();
            }
        }
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isDataInitialized = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.e(toString() + ":onActivityCreated");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.e(toString() + ":onViewStateRestored");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitialized = false;
        LogUtil.e(toString() + ":onDestroyView");
    }

    private void checkIfLoadData() {
        Log.i("mylog","mpresenter---isVisibleToUser--"+isVisibleToUser+"--isViewInitialized--"+isViewInitialized+
                "--isDataInitialized--"+isDataInitialized);
        if (isVisibleToUser && isViewInitialized && !isDataInitialized) {
            isDataInitialized = true;
//            TODO load data
            setUpData();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e(toString() + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.e(toString() + ":onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.e(toString() + ":onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.e(toString() + ":onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.e(toString() + ":onStop");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e(toString() + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.e(toString() + ":onDetach");
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        LogUtil.e(toString() + ":onInflate");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.e(toString() + ":onHiddenChanged:" + hidden);
    }

}
