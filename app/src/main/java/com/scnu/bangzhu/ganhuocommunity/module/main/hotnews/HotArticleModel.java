package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

/**
 * Created by bangzhu on 2017/3/4.
 */

public interface HotArticleModel {

    void getPageNum(PageModel pageModel);

    void loadHotArticle();

    void loadArticle(PageModel pageModel);
}
