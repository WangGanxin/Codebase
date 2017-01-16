package com.ganxin.codebase.sample;

import android.content.Intent;
import android.os.Bundle;

import com.ganxin.codebase.R;
import com.ganxin.codebase.application.ConstantValues;
import com.ganxin.codebase.framework.BaseActivity;
import com.ganxin.codebase.framework.ITabFragment;
import com.ganxin.codebase.widgets.tab.TabLayout;

import java.util.ArrayList;

/**
 * Description : 主界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class SampleTabActivity extends BaseActivity implements TabLayout.OnTabClickListener{

    private ITabFragment currentFragment;
    private TabLayout mTabLayout;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_tab);
    }

    @Override
    protected void setUpView() {
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);

        //添加tab（因为tab里的Fragment的添加方式不是预先加入Layout容器内，无需开启Fragment的懒加载）
        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_news, R.string.tab_news, SampleNewsFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_bottomtabbar_wechat, R.string.tab_wechat, SampleWechatFragment.class));
        mTabLayout.setUpData(tabs, this);
        mTabLayout.setCurrentTab(0);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_BACK_TO_HOME);
        switch (action) {
            case ConstantValues.ACTION_KICK_OUT:
                break;
            case ConstantValues.ACTION_LOGOUT:
                break;
            case ConstantValues.ACTION_RESTART_APP:
                protectApp();
                break;
            case ConstantValues.ACTION_BACK_TO_HOME:
                break;
        }
    }

    @Override
    protected void protectApp() {
        startActivity(new Intent(this, SampleLoadingActivity.class));
        finish();
    }

    @Override
    public void onTabClick(TabLayout.Tab tab) {
        try {
            setTitle(tab.labelResId);

            ITabFragment tmpFragment = (ITabFragment) getSupportFragmentManager().findFragmentByTag(tab.targetFragmentClz.getSimpleName());
            if (tmpFragment == null) {
                tmpFragment = tab.targetFragmentClz.newInstance();
                if (currentFragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment.getFragment())
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commit();
                }
            } else {
                if (currentFragment == null) {
                    getSupportFragmentManager().beginTransaction()
                            .show(tmpFragment.getFragment())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment.getFragment())
                            .show(tmpFragment.getFragment())
                            .commit();
                }
            }
            currentFragment = tmpFragment;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
