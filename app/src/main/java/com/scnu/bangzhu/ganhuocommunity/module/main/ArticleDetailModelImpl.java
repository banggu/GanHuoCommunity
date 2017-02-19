package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
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
    public void loadRelevantArticle(BmobUser user) {
        BmobQuery<Article> query = new BmobQuery<Article>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.order("likesCount");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(3);
        query.findObjects(new FindListener<Article>() {

            @Override
            public void done(List<Article> object,BmobException e) {
                if(e==null){
                    mPresenter.loadRelevantArticleSuccess(object);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    @Override
    public void loadRelevantComment(String articleId) {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        Article article = new Article();
        article.setObjectId(articleId);
        query.addWhereEqualTo("article",new BmobPointer(article));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,article.author");
        query.findObjects(new FindListener<Comment>() {

            @Override
            public void done(List<Comment> objects,BmobException e) {
                mPresenter.loadRelevantCommentSuccess(objects);
            }
        });
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
