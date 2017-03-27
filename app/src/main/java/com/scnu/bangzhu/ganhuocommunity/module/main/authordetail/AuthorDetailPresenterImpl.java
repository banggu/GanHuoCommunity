package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangzhu on 2017/3/19.
 */

public class AuthorDetailPresenterImpl implements AuthorDetailPresenter {
    private AuthorDetailView mView;
    private AuthorDetailModel mModel;

    public AuthorDetailPresenterImpl(AuthorDetailView view) {
        mView = view;
        mModel = new AuthorDetailModelImpl(this);
    }

    @Override
    public void followAuthor(MyUser author) {
        mModel.followAuthor(author);
    }

    @Override
    public void unfollowAuthor(MyUser author) {
        mModel.unfollowAuthor(author);
    }

    @Override
    public void loadFollowCount(String authorId) {
        mModel.loadFollowCount(authorId);
    }

    @Override
    public void loadFollowCountSuccess(int count) {
        mView.setFollowCount(count);
    }

    @Override
    public void loadFollowCountFailtrue(int count) {
        mView.setFollowCount(count);
    }

    @Override
    public void loadFollowerCountSuccess(int count) {
        mView.setFollowerCount(count);
    }

    @Override
    public void loadFollowerCountFailtrue(int count) {
        mView.setFollowerCount(count);
    }

    @Override
    public void loadFollowerCount(String authorId) {
        mModel.loadFollowerCount(authorId);
    }

    @Override
    public void loadShareArticleCount(String authorId) {
        mModel.loadShareArticleCount(authorId);
    }

    @Override
    public void loadShareArticleCountSuccess(List<Article> articleList) {
        mView.setShareArticleCount(articleList);
    }

    @Override
    public void loadShareArticleCountFailtrue(int count) {
        mView.setShareArticleCount(new ArrayList<Article>());
    }

    @Override
    public void loadCollectArticleCount(String authorId) {
        mModel.loadCollectArticleCount(authorId);
    }

    @Override
    public void loadCollectArticleCountSuccess(List<Article> articleList) {
        mView.setCollectArticleCount(articleList);
    }

    @Override
    public void loadCollectArticleCountFailtrue(int count) {
        mView.setCollectArticleCount(new ArrayList<Article>());
    }
}
