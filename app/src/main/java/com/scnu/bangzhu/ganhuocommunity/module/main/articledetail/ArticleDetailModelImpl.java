package com.scnu.bangzhu.ganhuocommunity.module.main.articledetail;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.CollectRead;
import com.scnu.bangzhu.ganhuocommunity.model.Comment;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.Subscribe;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by bangzhu on 2017/2/12.
 */
public class ArticleDetailModelImpl implements ArticleDetailModel {
    private static final int FINISH_LIKE = 100101;
    private static final int FINISH_UNLIKE = 100102;
    private static final int FINISH_COLLECTED = 100103;

    private ArticleDetailPresenter mPresenter;
    private Article mArticle;
    private String mCacheStr;
    private boolean mCacheBool;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FINISH_COLLECTED :
                    userReadArticle(mArticle);
                    break;
            }
        }
    };

    public ArticleDetailModelImpl(ArticleDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void chechLoved(Article article) {
        // 查询喜欢这篇文章的所有用户，因此查询的是用户表
        BmobQuery<MyUser> query = new BmobQuery<>();
        Article a = new Article();
        a.setObjectId(article.getObjectId());
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("likes", new BmobPointer(a));
        query.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> object,BmobException e) {
                if(e==null){
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    for(MyUser u : object) {
                        if(u.getObjectId().equals(user.getObjectId())) {
                            mPresenter.hadLoved(true);
                            return;
                        }
                    }
                    mPresenter.hadLoved(false);
                }else{
                    mPresenter.hadLoved(false);
                }
            }

        });
    }

    @Override
    public void setRead(Article article, String bindType) {
        bindRelation(article, bindType);
    }

    @Override
    public void setLike(Article article, String bindType) {
        mArticle = article;
        bindRelation(article, bindType);
        userCollectArticle(article);
    }

    @Override
    public void deleteLike(final Article a) {
        mArticle = a;
        Article article = new Article();
        article.setObjectId(a.getObjectId());
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.remove(user);
        article.setLikes(relation);
        article.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    getRelationCount(a, "like");
                }else{

                }
            }

        });
        userCancleCollectArticle(a);
    }

    @Override
    public void loadRelevantArticle(MyUser user) {
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
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
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

    //绑定关注关系
    private void bindRelation(Article a, final String bindType) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final Article article = new Article();
        article.setObjectId(a.getObjectId());
        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(user);
        //多对多关联指向`post`的`likes`字段
        if(bindType == "like") {
            article.setLikes(relation);
        } else if(bindType == "read") {
            article.setRead(relation);
        }

        article.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("HZWING","多对多关联添加成功");
                    getRelationCount(article, bindType);
                }else{
                    Log.i("HZWING","失败："+e.getMessage());
                }
            }

        });
    }

    private void getRelationCount(Article a, final String bindType) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        final Article article = new Article();
        article.setObjectId(a.getObjectId());
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        if(bindType == "like") {
            query.addWhereRelatedTo("likes", new BmobPointer(article));
        } else if(bindType == "read") {
            query.addWhereRelatedTo("read", new BmobPointer(article));
        }
        query.findObjects(new FindListener<MyUser>() {

            @Override
            public void done(List<MyUser> object,BmobException e) {
                if(e==null){
                    Log.i("bmob","查询个数："+object.size());
                    updateArticle(article, object.size(), bindType);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void updateArticle(Article a, int num, String bindType) {
        Article article = new Article();
        if (bindType == "like") {
            article.setLikesCount(num);
        } else if(bindType == "read") {
            article.setReadCount(num);
        }

        article.update(a.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.i("smile", "update success");
            }
        });
    }

    private void userCollectArticle(final Article article) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<CollectRead> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<CollectRead>() {
            @Override
            public void done(BmobQueryResult<CollectRead> result, BmobException e) {
                if(e == null) {
                    List<CollectRead> list = (List<CollectRead>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        addCollection(list.get(0).getObjectId(), article);
                    } else {//还没订阅，则创建订阅
                        createCollection(article);
                    }
                } else {

                }
            }
        });
    }

    private void userCancleCollectArticle(final Article article) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<CollectRead> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<CollectRead>() {
            @Override
            public void done(BmobQueryResult<CollectRead> result, BmobException e) {
                if(e == null) {
                    List<CollectRead> list = (List<CollectRead>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        minusCollection(list.get(0).getObjectId(), article);
                    } else {//还没订阅，则创建订阅
                        return;
                    }
                } else {

                }
            }
        });
    }

    //创建收藏关系
    private void createCollection(Article article) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final CollectRead collectRead = new CollectRead();

        BmobRelation relation = new BmobRelation();
        relation.add(article);

        collectRead.setUserId(user.getObjectId());
        collectRead.setCollected(relation);
        collectRead.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    queryCollectNum(s, "collected", true);
                }
            }
        });
    }

    //添加收藏
    private void addCollection(String objId, Article article) {
        mCacheStr = objId;
        CollectRead collectRead = new CollectRead();
        collectRead.setObjectId(objId);

        BmobRelation relation = new BmobRelation();
        relation.add(article);

        collectRead.setCollected(relation);
        collectRead.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryCollectNum(mCacheStr, "collected", true);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void minusCollection(String objId, Article article) {
        mCacheStr = objId;
        CollectRead collectRead = new CollectRead();
        collectRead.setObjectId(objId);

        BmobRelation relation = new BmobRelation();
        relation.remove(article);
        collectRead.setCollected(relation);
        collectRead.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryCollectNum(mCacheStr, "collected", false);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void queryCollectNum(String objId, final String actionType, final boolean collectType) {
        mCacheStr = objId;
        BmobQuery<Article> query = new BmobQuery<Article>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Article where related "+ actionType + " to pointer(" + "'" + "CollectRead" + "'" + "," + "'" + objId + "'"  + ")");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e == null) {
                    List<Article> list = result.getResults();
                    updateCollectedCount(mCacheStr, list.size(), actionType, collectType);
                }
            }
        });
    }

    private void updateCollectedCount(String objId, int num, String actionType, boolean collectType) {
        mCacheStr = actionType;
        mCacheBool = collectType;
        CollectRead collectRead = new CollectRead();
        if (actionType == "collected") {
            collectRead.setCollectedCount(num);
        } else if(actionType == "read") {
            collectRead.setReadCount(num);
        }

        collectRead.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(mCacheStr == "collected") {
                    Message msg = new Message();
                    msg.what = FINISH_COLLECTED;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    private void userReadArticle(final Article article) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<CollectRead> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<CollectRead>() {
            @Override
            public void done(BmobQueryResult<CollectRead> result, BmobException e) {
                if(e == null) {
                    List<CollectRead> list = (List<CollectRead>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        addRead(list.get(0).getObjectId(), article);
                    } else {//还没订阅，则创建订阅
                        createRead(article);
                    }
                } else {

                }
            }
        });
    }

    private void createRead(Article article) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final CollectRead collectRead = new CollectRead();

        BmobRelation relation = new BmobRelation();
        relation.add(article);

        collectRead.setUserId(user.getObjectId());
        collectRead.setRead(relation);
        collectRead.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    queryCollectNum(s, "read", true);
                }
            }
        });
    }

    private void addRead(String objId, Article article) {
        mCacheStr = objId;
        CollectRead collectRead = new CollectRead();
        collectRead.setObjectId(objId);

        BmobRelation relation = new BmobRelation();
        relation.add(article);

        collectRead.setRead(relation);
        collectRead.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryCollectNum(mCacheStr, "read", true);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }
        });
    }
}
