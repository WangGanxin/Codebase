package com.ganxin.codebase.utils.sharedpreferences;

/**
 * Description : 设置改变监听器  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface OnSettingsChangedListenr {
	/**
	 * 
	 * @param type 类型
	 * @param newVal 新的value
	 */
	void settingsChanged(String type, Object newVal);
}
