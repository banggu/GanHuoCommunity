package com.scnu.bangzhu.ganhuocommunity.module.main.articledetail;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import cn.bmob.v3.BmobUser;

/**
 * Created by bangzhu on 2017/2/12.
 */
public interface ArticleDetailModel {
    void chechLoved(Article article);

    void setRead(Article article, String bindType);

    void setLike(Article article, String bindType);

    void deleteLike(Article article);

    void loadRelevantArticle(MyUser user);

    void loadRelevantComment(String articleId);

    void postComment(String articleId, String content);
}
