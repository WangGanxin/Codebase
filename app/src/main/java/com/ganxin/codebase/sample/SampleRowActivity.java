package com.ganxin.codebase.sample;

import android.content.Intent;
import android.os.Bundle;

import com.ganxin.codebase.R;
import com.ganxin.codebase.framework.BaseActivity;
import com.ganxin.codebase.widgets.rowview.ContainerView;
import com.ganxin.codebase.widgets.rowview.base.OnRowChangedListener;
import com.ganxin.codebase.widgets.rowview.group.GroupDescriptor;
import com.ganxin.codebase.widgets.rowview.normal.NormalRowDescriptor;
import com.ganxin.codebase.widgets.rowview.profile.ProfileRowDescriptor;

import java.util.ArrayList;

/**
 * Description : 主界面  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class SampleRowActivity extends BaseActivity implements OnRowChangedListener {

    private ContainerView containerView;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_sample_row);
    }

    @Override
    protected void setUpView() {
        containerView= (ContainerView)findViewById(R.id.containerView);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {
        ArrayList<GroupDescriptor> groupDescriptors = new ArrayList<>();

        GroupDescriptor group1 = new GroupDescriptor();
        group1.addDescriptor(new ProfileRowDescriptor(R.string.row_profile)
                .avatarUrl("")
                .label("Stay")
                .detailLabel("WeChat ID: stay4it")
                .hasAction(true));
        groupDescriptors.add(group1);

        GroupDescriptor group2 = new GroupDescriptor();
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_mypost)
                .iconResId(R.drawable.more_my_album)
                .label(getString(R.string.row_mypost))
                .hasAction(true));
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_favorites)
                .iconResId(R.drawable.more_my_favorite)
                .label(getString(R.string.row_favorites))
                .hasAction(true));
        group2.addDescriptor(new NormalRowDescriptor(R.string.row_wallet)
                .iconResId(R.drawable.more_my_bank_card)
                .label(getString(R.string.row_wallet))
                .hasAction(true));
        groupDescriptors.add(group2);

        GroupDescriptor group3 = new GroupDescriptor();
        group3.addDescriptor(new NormalRowDescriptor(R.string.row_sticker)
                .iconResId(R.drawable.more_emoji_store)
                .label(getString(R.string.row_sticker))
                .hasAction(true));
        groupDescriptors.add(group3);

        GroupDescriptor group4 = new GroupDescriptor();
        group4.headerLabel("Settings")
                .addDescriptor(new NormalRowDescriptor(R.string.row_settings)
                        .iconResId(R.drawable.more_setting)
                        .label(getString(R.string.row_settings))
                        .hasAction(true));
        groupDescriptors.add(group4);

        containerView.initializeData(groupDescriptors, this);
        containerView.hasPaddingTop(true);
        containerView.notifyDataChanged();
    }

    @Override
    public void onRowChanged(int rowId) {
        switch (rowId) {
            case R.string.row_settings:
                startActivity(new Intent(this, SampleLoadingActivity.class));
                break;
            case R.string.row_sticker:
                startActivity(new Intent(this, SampleTabActivity.class));
                break;
        }
    }

    @Override
    public void onRowChanged(int rowId, String content) {

    }
}
