package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface PCCommunityPresenter {
    void queryTotalPageNum(int limit);

    void queryPageNumSuccess(int pageNum);

    void queryPageNumFailtrue();

    void loadHotArticleList();

    void loadHotArticleListSuccess(List<Article> list);

    void loadHotArticleListFailtrue();

    void loadArticleList(final int page, final int limit, final int actionType, PCCommunityFragment.StaticHandler handler);

    void loadArticleListSuccess(int curPage, int actionType, List<Article> list);

    void loadArticleListFailtrue();
}
