package com.ganxin.codebase.widgets.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 *
 * Description : 正方形的Layout  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/4 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class SquareLaylout extends RelativeLayout{
	@SuppressLint("NewApi")
	public SquareLaylout(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
   
    public SquareLaylout(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
   
    public SquareLaylout(Context context) {  
        super(context);  
    }  
   
    @SuppressWarnings("unused")  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        // For simple implementation, or internal size is always 0.  
        // We depend on the container to specify the layout size of  
        // our view. We can't really know what it is since we will be  
        // adding and removing different arbitrary views and do not  
        // want the layout to change as this happens.  
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));  
   
        // Children are just made to fill our space.  
        int childWidthSize = getMeasuredWidth();  
        int childHeightSize = getMeasuredHeight();  
        //高度和宽度一样  
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
    }  
}
