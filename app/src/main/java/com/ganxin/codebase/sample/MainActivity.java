package com.ganxin.codebase.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ganxin.codebase.R;
import com.ganxin.codebase.utils.html.HtmlTemplate;

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

        String html=HtmlTemplate.wrap("test","hello world");
        WebView wv= (WebView) findViewById(R.id.articles_webview);
        wv.loadDataWithBaseURL("", html,"text/html", "utf8", "404");
    }
}
