package com.ganxin.codebase.widgets.gridview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *
 * Description : 重写的GridView,使其自身不带滚动  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class DisabledScrollGridView extends GridView {     
    public DisabledScrollGridView(Context context, AttributeSet attrs) {     
        super(context, attrs);     
    }     
    
    public DisabledScrollGridView(Context context) {     
        super(context);     
    }     
    
    public DisabledScrollGridView(Context context, AttributeSet attrs, int defStyle) {     
        super(context, attrs, defStyle);     
    }     
    
    @Override     
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {     
    
        int expandSpec = MeasureSpec.makeMeasureSpec(     
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);     
        super.onMeasure(widthMeasureSpec, expandSpec);     
    }   
} 