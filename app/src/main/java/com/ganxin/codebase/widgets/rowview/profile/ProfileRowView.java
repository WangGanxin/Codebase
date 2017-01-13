package com.ganxin.codebase.widgets.rowview.profile;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.ganxin.codebase.R;
import com.ganxin.codebase.widgets.rowview.base.BaseRowView;


/**
 *
 * Description : ProfileRowView  <br/>
 * author : WangGanxin <br/>
 * date : 2017/1/13 <br/>
 * email : mail@wangganxin.me <br/>
 */
public class ProfileRowView extends BaseRowView<ProfileRowDescriptor> implements OnClickListener {

    private ImageView mWidgetRowActionImg;
    private ProfileRowDescriptor descriptor;
    private ImageView mWidgetRowIconImg;
    private TextView mWidgetRowLabel;
    private TextView mWidgetRowDetailLabel;

    public ProfileRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    public ProfileRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public ProfileRowView(Context context) {
        super(context);
        initializeView();
    }

    @Override
    public void notifyDataChanged(String content) {
        super.notifyDataChanged(content);
    }

    @Override
    public void notifyDataChanged(ProfileRowDescriptor descriptor) {
        if (descriptor != null) {
            this.descriptor=descriptor;
            mWidgetRowIconImg.setBackgroundResource(R.drawable.mini_avatar);
            mWidgetRowLabel.setText(descriptor.label);
            mWidgetRowDetailLabel.setText(descriptor.detailLabel);
            if (descriptor.hasAction) {
                setOnClickListener(this);
                setBackgroundResource(R.drawable.widget_general_row_selector);
            } else {
                setBackgroundColor(Color.WHITE);
            }
            setVisibility(View.VISIBLE);
        } else {
            setVisibility(View.GONE);
        }
    }

    private void initializeView() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_profile_row, this);
        mWidgetRowIconImg = (ImageView) findViewById(R.id.mWidgetRowIconImg);
        mWidgetRowLabel = (TextView) findViewById(R.id.mWidgetRowLabel);
        mWidgetRowDetailLabel = (TextView) findViewById(R.id.mWidgetRowDetailLabel);
        mWidgetRowActionImg = (ImageView) findViewById(R.id.mWidgetRowActionImg);
        mWidgetRowActionImg.setBackgroundResource(R.drawable.ic_row_forward);
    }


    @Override
    public void onClick(View v) {
        if (listener != null&&descriptor!=null) {
            listener.onRowChanged(descriptor.rowId);
        }
    }


}
