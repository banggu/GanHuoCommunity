package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface PCCommunityPresenter {
    void loadHotArticleList();

    void loadHotArticleListSuccess();

    void loadHotArticleListFailtrue();

    void loadArticleList();

    void loadArticleListSuccess(List<Article> list);

    void loadArticleListFailtrue();
}
