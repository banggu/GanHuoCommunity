package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public interface CourseCommunityPresenter {
    void queryTotalPageNum(int limit);

    void queryPageNumSuccess(int pageNum);

    void queryPageNumFailtrue();

    void loadHotArticleList();

    void loadHotArticleListSuccess(List<Article> list);

    void loadHotArticleListFailtrue();

    void loadArticleList(final int page, final int limit, final int actionType);

    void loadArticleListSuccess(int curPage, int actionType, List<Article> list);

    void loadArticleListFailtrue();

}
