package com.scnu.bangzhu.ganhuocommunity.module.home.schoolnewscommunity;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface SchoolNewsCommunityModel {
    void queryTotalPageNum(int limit);

    void loadHotArticleList();

    void loadArticleList(final int page, final int limit, final int actionType);
}
