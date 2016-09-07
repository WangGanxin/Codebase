package com.ganxin.codebase.widgets.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 *
 * Description : 重写的ListView,使其自身不带滚动  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DisabledScrollListView extends ListView{
    public DisabledScrollListView(Context context, AttributeSet attrs) {     
        super(context, attrs);     
    }     
    
    public DisabledScrollListView(Context context) {     
        super(context);     
    }     
    
    public DisabledScrollListView(Context context, AttributeSet attrs, int defStyle) {     
        super(context, attrs, defStyle);     
    }     
    
    @Override     
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {     
    
        int expandSpec = MeasureSpec.makeMeasureSpec(     
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);     
        super.onMeasure(widthMeasureSpec, expandSpec);     
    } 
}
