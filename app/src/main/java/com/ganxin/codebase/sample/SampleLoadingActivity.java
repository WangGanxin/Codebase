package com.ganxin.codebase.sample;

import android.os.Bundle;

import com.ganxin.codebase.application.AppStatusTracker;
import com.ganxin.codebase.application.ConstantValues;
import com.ganxin.codebase.framework.BaseActivity;

/**
 * Description : 主界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class SampleLoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {

    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }
}
