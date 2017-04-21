package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.CollectRead;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.Subscribe;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityFragment;

import java.util.List;
import java.util.logging.LogRecord;

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
 * Created by bangzhu on 2017/3/19.
 */

public class AuthorDetailModelImpl implements AuthorDetailModel {
    public static final int FINISH_CREATE_SUBSCRIBTION = 10081;
    public static final int FINISH_MINUS_SUBSCRIBTION = 10082;

    private AuthorDetailPresenter mPresenter;
    private boolean mCacheBool;
    private String mCacheStr;
    private MyUser mAuthor;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FINISH_CREATE_SUBSCRIBTION:
                    doMySuscribtion(mAuthor);
                    break;
                case FINISH_MINUS_SUBSCRIBTION:
                    doMyUnfollow(mAuthor);
                    break;
            }
        }
    };

    public AuthorDetailModelImpl(AuthorDetailPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void followAuthor(final MyUser author) {//作者被订阅
        mAuthor = author;
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Subscribe where authorId = " + "'" + author.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        addSubscription(list.get(0).getObjectId());
                    } else {//还没订阅，则创建订阅
                        createSubscription(author);
                    }
                } else {

                }
            }
        });
    }

    //增加订阅
    private void addSubscription(final String subscribId) {
        mCacheStr = subscribId;
        Subscribe subscribe = new Subscribe();
        subscribe.setObjectId(subscribId);

        BmobRelation relation = new BmobRelation();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        relation.add(user);

        subscribe.setFollower(relation);
        subscribe.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryFollowerNum(mCacheStr, "follower", true);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    //创建订阅
    private void createSubscription(MyUser author) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final Subscribe subscribe = new Subscribe();
        BmobRelation relation = new BmobRelation();
        relation.add(user);
        subscribe.setAuthorId(author.getObjectId());
        subscribe.setFollower(relation);
        subscribe.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    queryFollowerNum(s, "follower", true);

                }
            }
        });
    }

    //用户订阅,即保存用户关注了哪个作者的记录
    private void doMySuscribtion(final MyUser author) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Subscribe where authorId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        addMySubscription(list.get(0).getObjectId(), author);
                    } else {//还没订阅，则创建订阅
                        createMySubscription(author);
                    }
                } else {

                }
            }
        });
    }

    private void createMySubscription(MyUser author) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final Subscribe subscribe = new Subscribe();

        BmobRelation relation = new BmobRelation();
        relation.add(author);

        subscribe.setAuthorId(user.getObjectId());
        subscribe.setFollow(relation);
        subscribe.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {
                    queryFollowerNum(s, "follow", true);
                }
            }
        });
    }

    private void addMySubscription(String subscribId, MyUser author) {
        mCacheStr = subscribId;
        Subscribe subscribe = new Subscribe();
        subscribe.setObjectId(subscribId);

        BmobRelation relation = new BmobRelation();
        relation.add(author);

        subscribe.setFollow(relation);
        subscribe.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryFollowerNum(mCacheStr, "follow", true);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void queryFollowerNum(final String objId, final String actionType, final boolean followType) {
        mCacheStr = objId;
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from _User where related "+ actionType + " to pointer(" + "'" + "Subscribe" + "'" + "," + "'" + objId + "'"  + ")");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<MyUser>() {
            @Override
            public void done(BmobQueryResult<MyUser> result, BmobException e) {
                if(e == null) {
                    List<MyUser> list = result.getResults();
                    updateFollowerCount(mCacheStr, list.size(), actionType, followType);
                }
            }
        });
    }

    private void updateFollowerCount(String objId, int num, final String bindType, boolean followType) {
        mCacheStr = bindType;
        mCacheBool = followType;
        Subscribe subscribe = new Subscribe();
        if (bindType == "follower") {
            subscribe.setFollowerCount(num);
        } else if(bindType == "follow") {
            subscribe.setFollowCount(num);
        }

        subscribe.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(mCacheStr == "follower") {
                    Message msg = new Message();
                    if(mCacheBool) {
                        msg.what = FINISH_CREATE_SUBSCRIBTION;
                    } else {
                        msg.what = FINISH_MINUS_SUBSCRIBTION;
                    }
                    mHandler.sendMessage(msg);
                }

            }
        });
    }

    @Override
    public void unfollowAuthor(final MyUser author) {
        mAuthor = author;
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Subscribe where authorId = " + "'" + author.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        minusSubscription(list.get(0).getObjectId());
                    } else {//还没订阅，则创建订阅
                        return;
                    }
                } else {

                }
            }
        });
    }

    private void minusSubscription(String subscribeId) {
        mCacheStr = subscribeId;
        Subscribe subscribe = new Subscribe();
        subscribe.setObjectId(subscribeId);
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobRelation relation = new BmobRelation();
        relation.remove(user);
        subscribe.setFollower(relation);
        subscribe.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryFollowerNum(mCacheStr, "follower", false);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void doMyUnfollow(final MyUser author) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Subscribe where authorId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        minusMySubscription(list.get(0).getObjectId(), author);
                    } else {//还没订阅，则创建订阅
                        return;
                    }
                } else {

                }
            }
        });
    }

    private void minusMySubscription(String subscribId, MyUser author) {
        mCacheStr = subscribId;
        Subscribe subscribe = new Subscribe();
        subscribe.setObjectId(subscribId);

        BmobRelation relation = new BmobRelation();
        relation.remove(author);
        subscribe.setFollow(relation);
        subscribe.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    queryFollowerNum(mCacheStr, "follow", false);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    @Override
    public void loadFollowCount(String authorId) {
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Subscribe where authorId = " + "'" + authorId + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        mPresenter.loadFollowCountSuccess(list.get(0).getFollowCount());
                    } else {//还没订阅，则创建订阅
                        mPresenter.loadFollowCountFailtrue(0);
                    }
                } else {
                    mPresenter.loadFollowCountFailtrue(0);
                }
            }
        });
    }

    @Override
    public void loadFollowerCount(String authorId) {
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Subscribe where authorId = " + "'" + authorId + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        mPresenter.loadFollowerCountSuccess(list.get(0).getFollowerCount());
                    } else {//还没订阅，则创建订阅
                        mPresenter.loadFollowerCountFailtrue(0);
                    }
                } else {
                    mPresenter.loadFollowerCountFailtrue(0);
                }
            }
        });
    }

    //加载作者发表文章数据
    @Override
    public void loadShareArticleCount(String authorId) {
        //select include author,* from Article where author = pointer('_User', '6720c14c28');
        BmobQuery<Article> query = new BmobQuery<Article>();
        StringBuffer sql = new StringBuffer();
        sql.append("select include author,* from Article where author = pointer(" +  "'" + "_User" + "'" + "," + "'" + authorId + "'"  + ")");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e == null) {
                    List<Article> list = result.getResults();
                    if(list.size() > 0) {
                        mPresenter.loadShareArticleCountSuccess(list);
                    } else {
                        mPresenter.loadShareArticleCountFailtrue(0);
                    }
                } else {
                    mPresenter.loadShareArticleCountFailtrue(0);
                }
            }
        });
    }

    @Override
    public void loadCollectArticleCount(String authorId) {
        BmobQuery<CollectRead> query = new BmobQuery<CollectRead>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + authorId + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<CollectRead>() {
            @Override
            public void done(BmobQueryResult<CollectRead> result, BmobException e) {
                if(e == null) {
                    List<CollectRead> list = (List<CollectRead>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        BmobQuery<Article> query = new BmobQuery<Article>();
                        StringBuffer sql = new StringBuffer();
                        sql.append("select * from Article where related "+ "collected" + " to pointer(" + "'" + "CollectRead" + "'" + "," + "'" + list.get(0).getObjectId() + "'"  + ")");
                        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
                            @Override
                            public void done(BmobQueryResult<Article> result, BmobException e) {
                                if(e == null) {
                                    List<Article> list = result.getResults();
                                    mPresenter.loadCollectArticleCountSuccess(list);
                                }
                            }
                        });
                    } else {//还没订阅，则创建订阅
                        mPresenter.loadCollectArticleCountFailtrue(0);
                    }
                } else {
                    mPresenter.loadCollectArticleCountFailtrue(0);
                }
            }
        });
    }

}
