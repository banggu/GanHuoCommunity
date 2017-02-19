package com.scnu.bangzhu.ganhuocommunity.module.main;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;

import java.util.List;

/**
 * Created by bangzhu on 2017/2/11.
 */
public interface ArticleDetailsView {
    void setArticleContent(String content);

    void refreshRelevantArticle(List<Article> articleList);

    void refreshRelevantComment(List<Comment> commentList);

    void setLove();

    void setNotLove();

    void setCollectedNum(String num);

    void showCommentPanel();

    void hideCommentPanel();

    void postCommentSuccess();
}
