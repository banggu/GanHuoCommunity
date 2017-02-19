package com.scnu.bangzhu.ganhuocommunity.module.main;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by bangzhu on 2017/2/12.
 */
public class ArticleDetailPresenterImpl implements ArticleDetailPresenter {
    private ArticleDetailsView mView;
    private ArticleDetailModel mModel;

    public ArticleDetailPresenterImpl(ArticleDetailsView view) {
        mView = view;
        mModel = new ArticleDetailModelImpl(this);
    }

    @Override
    public void loadRelevantArticle(BmobUser user) {
        mModel.loadRelevantArticle(user);
    }

    @Override
    public void loadRelevantArticleSuccess(List<Article> articleList) {
        mView.refreshRelevantArticle(articleList);
    }

    @Override
    public void loadRelevantArticleFailture() {

    }

    @Override
    public void loadRelevantComment(String articleId) {
        mModel.loadRelevantComment(articleId);
    }

    @Override
    public void loadRelevantCommentSuccess(List<Comment> commentList) {
        mView.refreshRelevantComment(commentList);
    }

    @Override
    public void loadRelevantCommentFailture() {

    }

    @Override
    public void postComment(String articleId, String content) {
        mModel.postComment(articleId, content);
    }

    @Override
    public void postCommentSuccess() {
        mView.postCommentSuccess();
    }
}
