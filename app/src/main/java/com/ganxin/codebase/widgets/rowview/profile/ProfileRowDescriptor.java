package com.ganxin.codebase.widgets.rowview.profile;

import com.ganxin.codebase.widgets.rowview.base.BaseRowDescriptor;

/**
 *
 * Description : ProfileRowDescriptor  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ProfileRowDescriptor extends BaseRowDescriptor {
    public String label;
    public String detailLabel;
    public boolean hasAction;
    private String avatarUrl;


    public ProfileRowDescriptor(int rowId) {
        super(rowId);
    }

    public ProfileRowDescriptor avatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public ProfileRowDescriptor label(String label) {
        this.label = label;
        return this;
    }

    public ProfileRowDescriptor detailLabel(String detailLabel) {
        this.detailLabel = detailLabel;
        return this;
    }

    public ProfileRowDescriptor hasAction(boolean hasAction) {
        this.hasAction = hasAction;
        return this;
    }

}
