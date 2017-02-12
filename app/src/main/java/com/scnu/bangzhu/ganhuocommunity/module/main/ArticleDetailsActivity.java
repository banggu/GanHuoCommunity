package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class ArticleDetailsActivity extends BaseActivity implements View.OnClickListener, ArticleDetailsView {
    private WebView mWebVeiw;
    private Article mArticle;
    private RelativeLayout mShardToolBar;
    private ImageView mLove, mComment, mShare;
    private TextView mCollected;
    private LinearLayout mCommentContainer;
    private EditText mContentEdit;
    private TextView mSendContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        init();
        initView();
        setListener();
    }

    private void init() {
        Intent intent = getIntent();
        mArticle = (Article) intent.getSerializableExtra("article");
    }

    private void initView() {
        mWebVeiw = (WebView) findViewById(R.id.article_details_webView);
        mWebVeiw.loadDataWithBaseURL(null, mArticle.getContent(), "text/html", "utf-8", null);
        mShardToolBar = (RelativeLayout) findViewById(R.id.share_toolbar_rl);
        mLove = (ImageView) findViewById(R.id.love_imageView);
        mComment = (ImageView) findViewById(R.id.comment_imageView);
        mShare = (ImageView) findViewById(R.id.share_imageView);
        mCollected = (TextView) findViewById(R.id.collect_textView);
        mCommentContainer = (LinearLayout) findViewById(R.id.content_container_ll);
        mContentEdit = (EditText) findViewById(R.id.content_edit_editview);
        mSendContent = (TextView) findViewById(R.id.send_content_textview);
    }

    private void setListener() {
        mComment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_imageView:
                showCommentPanel();
                break;
        }
    }

    @Override
    public void setArticleContent(String content) {

    }

    @Override
    public void setLove() {

    }

    @Override
    public void setNotLove() {

    }

    @Override
    public void setCollectedNum(String num) {

    }

    @Override
    public void showCommentPanel() {
        mShardToolBar.setVisibility(View.GONE);
        mCommentContainer.setVisibility(View.VISIBLE);
        mContentEdit.clearFocus();
        mContentEdit.requestFocus();
    }

    @Override
    public void hideCommentPanel() {

    }
}
