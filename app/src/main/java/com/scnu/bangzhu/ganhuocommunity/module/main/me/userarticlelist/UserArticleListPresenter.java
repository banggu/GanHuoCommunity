package com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist;


import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/26.
 */

public interface UserArticleListPresenter {
    void getPageNum(String actionType, PageModel pageModel);

    void loadArticleList(String actionType, PageModel pageModel);

    void loadArticleListSuccess(List<Article> articleList);

    void loadArticleFailtrue();
}
