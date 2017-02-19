package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;
import com.scnu.bangzhu.ganhuocommunity.module.home.HotArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.util.SoftKeyBoardUtil;
import com.scnu.bangzhu.ganhuocommunity.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class ArticleDetailsActivity extends BaseActivity implements View.OnClickListener, ArticleDetailsView {
    private WebView mWebVeiw;
    private TextView mArticleTag, mRelevantArticleTip;
    private ListView mRelevantArticle, mRelevantComment;
    private LinearLayout mCommentPanel;

    private RelativeLayout mShardToolBar;
    private ImageView mLove, mComment, mShare;
    private TextView mCollected;

    private LinearLayout mCommentContainer;
    private EditText mContentEdit;
    private TextView mSendContent;

    private Article mArticle;
    private List<Article> mRelevantArticleList;
    private HotArticleListAdapter mRelevantArticleAdapter;
    private List<Comment> mRelevantCommentList;
    private CommentArticleListAdapter mRelevantCommentAdapter;
    private ArticleDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        init();
        initView();
        bindView();
        setListener();
        setContent();
    }

    private void init() {
        Intent intent = getIntent();
        mArticle = (Article) intent.getSerializableExtra("article");
        mPresenter = new ArticleDetailPresenterImpl(this);
    }

    private void initView() {
        mWebVeiw = (WebView) findViewById(R.id.article_details_webView);
        mWebVeiw.loadDataWithBaseURL(null, mArticle.getContent(), "text/html", "utf-8", null);
        mArticleTag = (TextView) findViewById(R.id.article_tag_textView);
        mRelevantArticleTip = (TextView) findViewById(R.id.relevant_article_textView);
        mRelevantArticle = (ListView) findViewById(R.id.relevant_article_listView);
        mCommentPanel = (LinearLayout) findViewById(R.id.comment_panel_ll);
        mRelevantComment = (ListView) findViewById(R.id.relevant_comment_listView);

        mShardToolBar = (RelativeLayout) findViewById(R.id.share_toolbar_rl);
        mLove = (ImageView) findViewById(R.id.love_imageView);
        mComment = (ImageView) findViewById(R.id.comment_imageView);
        mShare = (ImageView) findViewById(R.id.share_imageView);
        mCollected = (TextView) findViewById(R.id.collect_textView);

        mCommentContainer = (LinearLayout) findViewById(R.id.content_container_ll);
        mContentEdit = (EditText) findViewById(R.id.content_edit_editview);
        mSendContent = (TextView) findViewById(R.id.send_content_textview);
    }

    private void bindView() {
        mRelevantArticleList = new ArrayList<>();
        mRelevantArticleAdapter = new HotArticleListAdapter(this, mRelevantArticleList);
        mRelevantArticle.setAdapter(mRelevantArticleAdapter);

        mRelevantCommentList = new ArrayList<>();
        mRelevantCommentAdapter = new CommentArticleListAdapter(this, mRelevantCommentList);
        mRelevantComment.setAdapter(mRelevantCommentAdapter);
    }

    private void setListener() {
        mComment.setOnClickListener(this);
        mSendContent.setOnClickListener(this);
        mCommentPanel.setOnClickListener(this);
    }

    private void setContent() {
        mPresenter.loadRelevantArticle(mArticle.getAuthor());
        mPresenter.loadRelevantComment(mArticle.getObjectId());
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        BaseAdapter listAdapter = (BaseAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_panel_ll:
                showCommentPanel();
                break;
            case R.id.comment_imageView:
                showCommentPanel();
                break;
            case R.id.send_content_textview:
                mPresenter.postComment(mArticle.getObjectId(), mContentEdit.getText().toString());
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isInputMethodOpen = SoftKeyBoardUtil.isSoftKeyBoardShow(this, mContentEdit);
        if(isInputMethodOpen && (mCommentContainer.getVisibility() == View.VISIBLE)) {
            showCommentPanel();
            return true;
        }
        return false;
    }

    @Override
    public void setArticleContent(String content) {

    }

    @Override
    public void refreshRelevantArticle(List<Article> articleList) {
        mRelevantArticleList.clear();
        mRelevantArticleList.addAll(articleList);
        mRelevantArticleAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mRelevantArticle);
    }

    @Override
    public void refreshRelevantComment(List<Comment> commentList) {
        mRelevantCommentList.clear();
        mRelevantCommentList.addAll(commentList);
        mRelevantCommentAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mRelevantComment);
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
        if(mCommentContainer.getVisibility() == View.GONE) {
            mShardToolBar.setVisibility(View.GONE);
            mCommentContainer.setVisibility(View.VISIBLE);
            mContentEdit.setFocusable(true);
            mContentEdit.setFocusableInTouchMode(true);
            mContentEdit.requestFocus();
            SoftKeyBoardUtil.openSoftKeyBoard(this, mContentEdit);
            Log.i("Hzwing", "isopen = "+SoftKeyBoardUtil.isSoftKeyBoardShow(this, mContentEdit));
        } else {
            mShardToolBar.setVisibility(View.VISIBLE);
            mCommentContainer.setVisibility(View.GONE);
            SoftKeyBoardUtil.closeSoftKeyBoard(this, mContentEdit);
            Log.i("Hzwing", "isopen = "+SoftKeyBoardUtil.isSoftKeyBoardShow(this, mContentEdit));
        }
    }

    @Override
    public void hideCommentPanel() {

    }

    @Override
    public void postCommentSuccess() {
        mContentEdit.setText("");
        mCommentContainer.setVisibility(View.GONE);
        mShardToolBar.setVisibility(View.VISIBLE);
        ToastUtil.showToast(this, getResources().getString(R.string.comment_success));
        int count = mRelevantCommentAdapter.getCount();
        mRelevantComment.setSelection(count-1);
    }
}
