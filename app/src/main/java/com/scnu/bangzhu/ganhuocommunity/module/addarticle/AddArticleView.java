package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public interface AddArticleView {
    void setArticleTitle(String title);

    void setArticleType(String type);

    void insertImage(String imageUrl, String alt);

    void setArticlContent(String content);

    void navigateToMain();
}
