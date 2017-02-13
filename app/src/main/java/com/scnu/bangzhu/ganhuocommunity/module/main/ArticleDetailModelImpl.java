package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by bangzhu on 2017/2/12.
 */
public class ArticleDetailModelImpl implements ArticleDetailModel {
    private ArticleDetailPresenter mPresenter;

    public ArticleDetailModelImpl(ArticleDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadRelevantArticle(String type) {

    }

    @Override
    public void loadRelevantArticleSuccess() {

    }

    @Override
    public void loadRelevantArticleFailture() {

    }

    @Override
    public void loadRelevantComment() {

    }

    @Override
    public void loadRelevantCommentSuccess() {

    }

    @Override
    public void loadRelevantCommentFailture() {

    }

    @Override
    public void postComment(String articleId, String content) {
        BmobUser user = BmobUser.getCurrentUser();
        Article article = new Article();
        article.setObjectId(articleId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setArticle(article);
        comment.setUser(user);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.i("success", "post comment success " + s);
                    mPresenter.postCommentSuccess();
                } else {

                }
            }
        });
    }
}
