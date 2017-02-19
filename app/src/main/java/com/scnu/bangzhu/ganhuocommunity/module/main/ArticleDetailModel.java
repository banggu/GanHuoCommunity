package com.scnu.bangzhu.ganhuocommunity.module.main;

import cn.bmob.v3.BmobUser;

/**
 * Created by bangzhu on 2017/2/12.
 */
public interface ArticleDetailModel {
    void loadRelevantArticle(BmobUser user);

    void loadRelevantComment(String articleId);

    void postComment(String articleId, String content);
}
