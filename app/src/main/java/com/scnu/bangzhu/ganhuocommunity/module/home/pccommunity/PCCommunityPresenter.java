package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface PCCommunityPresenter {
    void loadHotArticleList();

    void loadHotArticleListSuccess(List<Article> list);

    void loadHotArticleListFailtrue();

    void loadArticleList(final int page, final int limit, final int actionType);

    void loadArticleListSuccess(int curPage, List<Article> list);

    void loadArticleListFailtrue();
}
