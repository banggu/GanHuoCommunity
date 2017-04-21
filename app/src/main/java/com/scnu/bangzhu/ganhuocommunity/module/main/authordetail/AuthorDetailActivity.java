package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.module.main.articledetail.ArticleDetailPresenterImpl;
import com.scnu.bangzhu.ganhuocommunity.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by bangzhu on 2017/3/16.
 */

public class AuthorDetailActivity extends BaseActivity implements AuthorDetailView, View.OnClickListener {
    private ImageView mBack;
    private TextView mTopTitle;
    private TextView mArticleDetailAuthor, mFollow;
    private CircleImageView mAvatar;
    private TextView mUserName;
    private TextView mUserFollow, mUserFollower;
    private LinearLayout mShareArticlePanel, mCollectedArticlePanel;
    private TextView mUserShareArticleNum, mUserCollectedArticleNum;
    private AuthorDetailPresenter mPresenter;
    //是否关注标识
    private boolean isFollowed = false;
    private ArrayList<Article> mCollectedArticleList;
    private ArrayList<Article> mShareArticleList;
    private MyUser mAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_details);
        init();
        initView();
        setContent();
        setListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isFollowed) {
            mPresenter.followAuthor(mAuthor);
        } else {
            mPresenter.unfollowAuthor(mAuthor);
        }
    }

    private void init() {
        Intent intent = getIntent();
        mAuthor = (MyUser) intent.getSerializableExtra("author");
        mPresenter = new AuthorDetailPresenterImpl(this);

        mShareArticleList = new ArrayList<>();
        mCollectedArticleList = new ArrayList<>();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.article_details_back);
        mTopTitle = (TextView) findViewById(R.id.article_details_author);
        mArticleDetailAuthor = (TextView) findViewById(R.id.article_details_author);
        mFollow = (TextView) findViewById(R.id.author_details_follow);
        mAvatar = (CircleImageView) findViewById(R.id.author_details_avatar);
        mUserName = (TextView) findViewById(R.id.author_details_name);
        mUserFollow = (TextView) findViewById(R.id.author_details_user_follow);
        mUserFollower = (TextView) findViewById(R.id.author_details_user_follower);
        mShareArticlePanel = (LinearLayout) findViewById(R.id.author_details_share_article_panel);
        mCollectedArticlePanel = (LinearLayout) findViewById(R.id.author_details_collect_article_panel);
        mUserShareArticleNum = (TextView) findViewById(R.id.author_details_share_article);
        mUserCollectedArticleNum = (TextView) findViewById(R.id.author_details_collect_article);
    }

    private void setContent() {
        mTopTitle.setText(getResources().getString(R.string.author_detail));
        mArticleDetailAuthor.setText(getResources().getString(R.string.author_detail));
        //加载用户头像
        Glide.with(this)
                .load(StringUtil.getOriginUrl(mAuthor.getUserAvatar()))
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(mAvatar);
        mUserName.setText(mAuthor.getUsername());
        mPresenter.loadFollowCount(mAuthor.getObjectId());
        mPresenter.loadFollowerCount(mAuthor.getObjectId());
        mPresenter.loadCollectArticleCount(mAuthor.getObjectId());
        mPresenter.loadShareArticleCount(mAuthor.getObjectId());
    }

    private void setListener() {
        mBack.setOnClickListener(this);
        mFollow.setOnClickListener(this);
        mShareArticlePanel.setOnClickListener(this);
        mCollectedArticlePanel.setOnClickListener(this);
    }

    private void clickFollow() {
        if(!isFollowed) {
            setFollow();
        } else {
            setUnfollow();
        }
    }

    private void gotoAuthorArticleListActivity(List<Article> list, String title) {
        if (list.size() <= 0 || list == null) {
            return;
        }
        AuthorArticleListActivity.startMe(this, (ArrayList<Article>) list, title);
    }

    public static void startMe(Context context, MyUser author) {
        Intent intent =  new Intent(context, AuthorDetailActivity.class);
        intent.putExtra("author", author);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.article_details_back:
                finish();
                break;
            case R.id.author_details_follow:
                clickFollow();
                break;
            case R.id.author_details_share_article_panel:
                gotoAuthorArticleListActivity(mShareArticleList, getResources().getString(R.string.share_article));
                break;
            case R.id.author_details_collect_article_panel:
                gotoAuthorArticleListActivity(mCollectedArticleList, getResources().getString(R.string.collect_article));
                break;
        }
    }

    @Override
    public void setAvatar(String imageUrl) {

    }

    @Override
    public void setAuthorName(String userName) {

    }

    @Override
    public void setFollow() {
        isFollowed = true;
        mFollow.setBackgroundResource(R.drawable.border_checked_bg);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_checked);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        mFollow.setCompoundDrawables(drawable, null, null, null);
        mFollow.setText(getResources().getString(R.string.followed));
        mFollow.setTextColor(getResources().getColor(R.color.colorPrimaryWhite));
    }

    @Override
    public void setUnfollow() {
        isFollowed = false;
        mFollow.setBackgroundResource(R.drawable.border_bg);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_s_add);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        mFollow.setCompoundDrawables(drawable, null, null, null);
        mFollow.setText(getResources().getString(R.string.follow));
        mFollow.setTextColor(getResources().getColor(R.color.primary_green));
    }

    @Override
    public void setFollowCount(int count) {
        String text = count + "\n" + getResources().getString(R.string.follow);
        mUserFollow.setText(text);
    }

    @Override
    public void setFollowerCount(int count) {
        String text = count + "\n" + getResources().getString(R.string.follower);
        mUserFollower.setText(text);
    }

    @Override
    public void setShareArticleCount(List<Article> articleList) {
        if(articleList.size() <= 0) {
            mUserShareArticleNum.setText(String.valueOf(0));
            return;
        }
        int size = articleList.size();
        mUserShareArticleNum.setText(String.valueOf(size));
        mUserShareArticleNum.refreshDrawableState();
//        mShareArticleList = articleList;
        mShareArticleList.clear();
        for (Article article : articleList) {
            mShareArticleList.add(article);
        }
    }

    @Override
    public void setCollectArticleCount(List<Article> articleList) {
        if(articleList.size() <= 0) {
            mUserCollectedArticleNum.setText(String.valueOf(0));
            return;
        }
        int size = articleList.size();
        mUserCollectedArticleNum.setText(String.valueOf(size));
        mUserCollectedArticleNum.refreshDrawableState();
//        mCollectedArticleList = articleList;
        mCollectedArticleList.clear();
        for (Article article : articleList) {
            mCollectedArticleList.add(article);
        }
    }
}
