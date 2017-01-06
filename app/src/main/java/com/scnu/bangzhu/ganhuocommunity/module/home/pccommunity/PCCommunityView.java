package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface PCCommunityView {
    void showDataLoading();

    void showNetworkError();

    void refreshArticleList(int curPage, List<Article> list);

    void refreshHotArticleList(List<Article> list);
}
