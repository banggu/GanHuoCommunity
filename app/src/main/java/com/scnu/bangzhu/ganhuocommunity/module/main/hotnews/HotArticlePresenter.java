package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/4.
 */

public interface HotArticlePresenter {

    void getPageNum(HotArticleFragment.PageModel pageModel);

    void loadHotArticle();

    void loadHotArticleSuccess(List<Article> list);

    void loadHotArticleFailture();

    void loadArticle(HotArticleFragment.PageModel pageModel);

    void loadArticleSuccess(List<Article> list);

    void loadArticleFailture();
}
