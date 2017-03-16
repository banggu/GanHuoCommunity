package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

/**
 * Created by bangzhu on 2017/3/4.
 */

public interface HotArticleModel {

    void getPageNum(HotArticleFragment.PageModel pageModel);

    void loadHotArticle();

    void loadArticle(HotArticleFragment.PageModel pageModel);
}
