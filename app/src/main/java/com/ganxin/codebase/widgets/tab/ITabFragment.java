package com.ganxin.codebase.widgets.tab;

import com.ganxin.codebase.framework.BaseFragment;

/**
 *
 * Description : 底部TabFragment接口  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */

public interface ITabFragment {

    void onMenuItemClick();

    BaseFragment getFragment();
}
