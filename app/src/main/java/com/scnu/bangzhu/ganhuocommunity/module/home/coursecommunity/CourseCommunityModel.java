package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public interface CourseCommunityModel {

    void loadHotArticleList();

    void loadArticleList(final int page, final int limit, final int actionType);

}
