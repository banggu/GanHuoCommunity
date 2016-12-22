package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public interface AddArticleModel {
    void uploadImage(String imageUrl);

    void postArticle(String user, String title, String type, String imageUrl, String content);
}
