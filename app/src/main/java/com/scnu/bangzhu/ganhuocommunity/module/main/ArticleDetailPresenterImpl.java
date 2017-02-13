package com.scnu.bangzhu.ganhuocommunity.module.main;

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
    public void loadRelevantArticle(String type) {
        mModel.loadRelevantArticle(type);
    }

    @Override
    public void loadRelevantArticleSuccess() {
        mModel.loadRelevantArticleSuccess();
    }

    @Override
    public void loadRelevantArticleFailture() {
        mModel.loadRelevantArticleFailture();
    }

    @Override
    public void loadRelevantComment() {
        mModel.loadRelevantComment();
    }

    @Override
    public void loadRelevantCommentSuccess() {
        mModel.loadRelevantCommentSuccess();
    }

    @Override
    public void loadRelevantCommentFailture() {
        mModel.loadRelevantCommentFailture();
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
