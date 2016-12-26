package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class ArticleDetailsActivity extends BaseActivity{
    private WebView mWebVeiw;
    private String mArticleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        init();
        initView();
    }

    private void init() {
        Intent intent = getIntent();
        mArticleContent = intent.getStringExtra("articleContent");
    }

    private void initView() {
        mWebVeiw = (WebView) findViewById(R.id.article_details_webView);
        mWebVeiw.loadDataWithBaseURL(null, mArticleContent, "text/html", "utf-8", null);
    }

}
