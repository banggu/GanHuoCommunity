package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/4.
 */

public interface HotArticleView {
    void hideHeader();

    void refreshHotArticle(List<Article> list);

    void refreshArticle(List<Article> list);
}
