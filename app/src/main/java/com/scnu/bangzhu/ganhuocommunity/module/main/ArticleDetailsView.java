package com.scnu.bangzhu.ganhuocommunity.module.main;

/**
 * Created by bangzhu on 2017/2/11.
 */
public interface ArticleDetailsView {
    void setArticleContent(String content);

    void setLove();

    void setNotLove();

    void setCollectedNum(String num);

    void showCommentPanel();

    void hideCommentPanel();
}
