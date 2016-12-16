package com.ganxin.codebase.utils.sharedpreferences;

import android.content.Context;

import com.ganxin.codebase.app.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description : 设置管理类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/12/16 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class SettingManager implements OnSharedataCommitListener {

    private SettingManager() {

    }

    private static class SettingManagerHolder{
        private static final SettingManager INSTANCE=new SettingManager();
    }

    public static SettingManager getInstance() {
        return SettingManagerHolder.INSTANCE;
    }

    private HashMap<String, List<OnSettingsChangedListenr>> listeners = new HashMap<>();

    /**
     * 添加设置监听
     * @param listener 监听对象
     * @param type     类型
     *                 <li>{@link Constants#SETTING_TYPE_ONE} 设置的类型
     */
    public void addSettingsChangedListener(OnSettingsChangedListenr listener,
                                           String... type) {
        if (listener == null || type == null)
            return;
        for (int i = 0; i < type.length; i++) {
            List<OnSettingsChangedListenr> list = listeners.get(type[i]);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(listener);
            listeners.put(type[i], list);
        }
    }

    /**
     * 解除设置监听
     * @param type 类型
     *             <li>{@link Constants#SETTING_TYPE_ONE} 设置的类型
     */
    public void removeSettingsChangedListener(String... type) {
        if(listeners!=null&&listeners.size()>0){
            for (int i = 0; i < type.length; i++) {
                listeners.remove(type[i]);
            }
        }
    }

    private void notifySettingsChanged(String type, Object newVal) {
        List<OnSettingsChangedListenr> list = listeners.get(type);
        if (list != null) {
            for (OnSettingsChangedListenr scl : list) {
                scl.settingsChanged(type, newVal);
            }
        }
    }

    @Override
    public void onSharedataCommit(String configKey, Object configValue) {
        notifySettingsChanged(configKey, configValue);
    }

    /**
     * @param context
     * @param value
     */
    public void setStatus(Context context, boolean value) {
        SharedPreferencesAccessor.setConfig(context, Constants.SHARPREFER_FILENAME,
                Constants.SETTING_TYPE_ONE, value, this);
    }

    /**
     * @param context
     * @return
     */
    public boolean getStatus(Context context) {
        return SharedPreferencesAccessor.getBooleanConfig(context, Constants.SHARPREFER_FILENAME,
                Constants.SETTING_TYPE_ONE, false);
    }
}
