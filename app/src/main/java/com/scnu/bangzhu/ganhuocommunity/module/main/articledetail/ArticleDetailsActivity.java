package com.scnu.bangzhu.ganhuocommunity.module.main.articledetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;
import com.scnu.bangzhu.ganhuocommunity.module.home.HotArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.module.main.authordetail.AuthorDetailActivity;
import com.scnu.bangzhu.ganhuocommunity.util.SoftKeyBoardUtil;
import com.scnu.bangzhu.ganhuocommunity.util.StringUtil;
import com.scnu.bangzhu.ganhuocommunity.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class ArticleDetailsActivity extends BaseActivity implements View.OnClickListener, ArticleDetailsView {
    //标题栏相关控件
    private ImageView mBack;
    private LinearLayout mUserArea;
    private CircleImageView mUserAvatar;
    private TextView mAuthorName;
    private ImageView mMore;

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
    private boolean mLoveable = false;

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
        mBack = (ImageView) findViewById(R.id.article_details_back);
        mUserArea = (LinearLayout) findViewById(R.id.article_details_user_area);
        mUserAvatar = (CircleImageView) findViewById(R.id.article_details_avatar);
        mAuthorName = (TextView) findViewById(R.id.article_details_author);
        mMore = (ImageView) findViewById(R.id.article_details_more);
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
        mBack.setOnClickListener(this);
        mUserArea.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mRelevantArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article article = mRelevantArticleList.get(i);
                startMe(ArticleDetailsActivity.this, article);
            }
        });
        mComment.setOnClickListener(this);
        mLove.setOnClickListener(this);
        mSendContent.setOnClickListener(this);
        mCommentPanel.setOnClickListener(this);
    }

    private void setContent() {
        setAvatarAndUsername();
        mPresenter.chechLoved(mArticle);
        mPresenter.loadRelevantArticle(mArticle.getAuthor());
        mPresenter.loadRelevantComment(mArticle.getObjectId());

        setCollectedNum(String.valueOf(mArticle.getLikesCount()));
        setArticleTip(String.valueOf(mArticle.getReadCount()), String.valueOf(mArticle.getLikesCount()));
        mPresenter.setRead(mArticle, "read");
    }

    //设置用户头像和用户名
    private void setAvatarAndUsername() {
        Glide.with(this)
                .load(StringUtil.getOriginUrl(mArticle.getAuthor().getUserAvatar()))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(mUserAvatar);
        mAuthorName.setText(mArticle.getAuthor().getUsername());
    }

    //计算ListView 的高度
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

    private void bindLoveRelation() {
        int collectNum = getCollectNum();
        if(mLoveable) {
            setNotLove();
            setCollectedNum(String.valueOf(collectNum-1));
        } else {
            setLove();
            setCollectedNum(String.valueOf(collectNum+1));
        }
    }

    private int getCollectNum() {
        String collectStr = mCollected.getText().toString();
        int index = collectStr.indexOf(getResources().getString(R.string.collect));
        if(index == -1) {
            return -1;
        }
        String collectNumStr = collectStr.substring(index+2);
        return Integer.valueOf(collectNumStr);
    }

    private void gotoAuthorDetail() {
        AuthorDetailActivity.startMe(this, mArticle.getAuthor());
    }

    public static void startMe(Context context, Article article) {
        Intent intent = new Intent(context, ArticleDetailsActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        if(mLoveable) {
            mPresenter.setLike(mArticle, "like");
        } else {
            mPresenter.deleteLike(mArticle);
        }
        super.onStop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.article_details_back:
                finish();
            case R.id.article_details_user_area:
                gotoAuthorDetail();
                break;
            case R.id.article_details_more:
                break;
            case R.id.comment_panel_ll:
                showCommentPanel();
                break;
            case R.id.love_imageView:
                bindLoveRelation();
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
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setArticleContent(String content) {

    }

    @Override
    public void setArticleTip(String readNum, String likeNum) {
        mArticleTag.setText(getResources().getString(R.string.read)+" "+readNum+ " · " +getResources().getString(R.string.collect)+likeNum);
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
        mLove.setImageResource(R.drawable.ic_love_click1);
        mLoveable = true;
    }

    @Override
    public void setNotLove() {
        mLove.setImageResource(R.drawable.ic_love_unfollowl);
        mLoveable = false;
    }

    @Override
    public void setCollectedNum(String num) {
        mCollected.setText(getResources().getString(R.string.collect) + num);
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
