package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public interface AddArticlePresenter {
    void postArticle(String title, String type, String imageUrl, String content);

    void onPostArticleSuccess();

    void onPostArticleFailtrue();
}
