package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface PCCommunityModel {
    void queryTotalPageNum(PageModel pageModel);

    void loadHotArticleList();

    void loadArticleList(PageModel pageModel);
}
