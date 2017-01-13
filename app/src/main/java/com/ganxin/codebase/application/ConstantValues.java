package com.ganxin.codebase.application;

/**
 * Description : 全局配置常量类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ConstantValues {

    public static final int STATUS_FORCE_KILLED = -1;
    public static final int STATUS_LOGOUT = 0;
    public static final int STATUS_OFFLINE = 1;
    public static final int STATUS_ONLINE = 2;
    public static final int STATUS_KICK_OUT = 3;

    public static final String KEY_HOME_ACTION = "key_home_action";
    public static final int ACTION_BACK_TO_HOME = 0;
    public static final int ACTION_RESTART_APP = 1;
    public static final int ACTION_LOGOUT = 2;
    public static final int ACTION_KICK_OUT = 3;

	public static final String SHARPREFER_FILENAME = "codebase"; //SharedPreference的文件名
	public static final String SETTING_TYPE_ONE="setting_type_one"; //设置的类型
}
