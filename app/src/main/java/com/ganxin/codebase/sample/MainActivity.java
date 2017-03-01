package com.ganxin.codebase.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

import com.ganxin.codebase.R;
import com.ganxin.codebase.utils.html.HtmlTemplate;
import com.ganxin.codebase.widgets.textview.SpannableBuilder;

/**
 * Description : MainActivity  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //HtmlTemplate
        String html=HtmlTemplate.wrap("test","hello world");
        WebView wv= (WebView) findViewById(R.id.articles_webview);
        wv.loadDataWithBaseURL("", html,"text/html", "utf8", "404");

        //SpannableBuilder
        TextView tvTitle= (TextView) findViewById(R.id.main_tv);
        tvTitle.setText(SpannableBuilder.create(this)
                .append("关联店铺", R.dimen.sp_16, R.color.colorPrimary)
                .append("(请添加您的所有店铺)", R.dimen.sp_12, R.color.colorAccent)
                .build());
    }
}
