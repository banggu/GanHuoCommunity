package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public interface CourseCommunityModel {
    void queryTotalPageNum(PageModel pageModel);

    void loadHotArticleList();

    void loadArticleList(PageModel pageModel);

}
