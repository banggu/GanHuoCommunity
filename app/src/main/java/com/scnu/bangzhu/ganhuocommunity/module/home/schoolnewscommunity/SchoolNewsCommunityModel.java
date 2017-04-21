package com.scnu.bangzhu.ganhuocommunity.module.home.schoolnewscommunity;

import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface SchoolNewsCommunityModel {
    void queryTotalPageNum(PageModel pageModel);

    void loadHotArticleList();

    void loadArticleList(PageModel pageModel);
}
