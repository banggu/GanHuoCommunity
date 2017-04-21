package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public interface CourseCommunityView {

    void showNetworkError();

    void setPageNum(int pageNum);

    void refreshArticleList(List<Article> list);

    void refreshHotArticleList(List<Article> list);

}
