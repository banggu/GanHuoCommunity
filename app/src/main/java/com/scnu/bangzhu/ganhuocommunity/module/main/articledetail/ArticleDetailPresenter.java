package com.scnu.bangzhu.ganhuocommunity.module.main.articledetail;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by bangzhu on 2017/2/12.
 */
public interface ArticleDetailPresenter {
    void chechLoved(Article article);

    void hadLoved(boolean love);

    void setRead(Article article, String bindType);

    void setLike(Article article, String bindType);

    void deleteLike(Article article);

    void loadRelevantArticle(MyUser user);

    void loadRelevantArticleSuccess(List<Article> articleList);

    void loadRelevantArticleFailture();

    void loadRelevantComment(String articleId);

    void loadRelevantCommentSuccess(List<Comment> commentList);

    void loadRelevantCommentFailture();

    void postComment(String articleId, String content);

    void postCommentSuccess();

}
