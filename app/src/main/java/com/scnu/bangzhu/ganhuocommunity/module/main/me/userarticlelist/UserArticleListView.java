package com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/26.
 */

public interface UserArticleListView {
    void refreshArticle(List<Article> list);
}
