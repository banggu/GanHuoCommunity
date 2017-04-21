package com.scnu.bangzhu.ganhuocommunity.module.home.schoolnewscommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public interface SchoolNewsCommunityPresenter {
    void queryTotalPageNum(PageModel pageModel);

    void queryPageNumSuccess(int pageNum);

    void queryPageNumFailtrue();

    void loadHotArticleList();

    void loadHotArticleListSuccess(List<Article> list);

    void loadHotArticleListFailtrue();

    void loadArticleList(PageModel pageModel);

    void loadArticleListSuccess(List<Article> list);

    void loadArticleListFailtrue();
}
