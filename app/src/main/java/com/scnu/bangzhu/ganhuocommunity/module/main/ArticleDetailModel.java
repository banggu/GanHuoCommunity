package com.scnu.bangzhu.ganhuocommunity.module.main;

/**
 * Created by bangzhu on 2017/2/12.
 */
public interface ArticleDetailModel {
    void loadRelevantArticle(String type);

    void loadRelevantArticleSuccess();

    void loadRelevantArticleFailture();

    void loadRelevantComment();

    void loadRelevantCommentSuccess();

    void loadRelevantCommentFailture();

    void postComment(String articleId, String content);
}
