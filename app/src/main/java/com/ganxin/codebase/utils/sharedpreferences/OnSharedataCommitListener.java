package com.ganxin.codebase.utils.sharedpreferences;

/**
 * Description : sharedata的设置监听器  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public interface OnSharedataCommitListener {
	/***
	 * 提交保存成功后触发
	 * @param configKey    设置键
	 * @param configValue  设置键值
	 */
	void onSharedataCommit(String configKey, Object configValue);
}
