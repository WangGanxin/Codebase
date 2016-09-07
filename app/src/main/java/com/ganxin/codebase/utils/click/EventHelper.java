package com.ganxin.codebase.utils.click;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Description : 事件处理类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class EventHelper {
    private static Map<String, Long> pluginLastLaunchTime = new HashMap<>();

    private EventHelper() {}

    public static boolean isRubbish(Context context, String mask) {
        boolean result = false;

        try {
            long lastLaunchTime = pluginLastLaunchTime.containsKey(mask) ? pluginLastLaunchTime.get(mask) : 0;
            if (System.currentTimeMillis() - lastLaunchTime < 1000) {
                result = true;
            }

            pluginLastLaunchTime.put(mask, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static boolean isRubbish(Context context, String mask, long ignoretime) {
        boolean result = false;

        try {
            long lastLaunchTime = pluginLastLaunchTime.containsKey(mask) ? pluginLastLaunchTime.get(mask) : 0;
            if (System.currentTimeMillis() - lastLaunchTime < ignoretime) {
                result = true;
            }

            pluginLastLaunchTime.put(mask, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
