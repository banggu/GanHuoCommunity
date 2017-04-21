package com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.ArticleFlag;
import com.scnu.bangzhu.ganhuocommunity.model.CollectRead;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;
import com.scnu.bangzhu.ganhuocommunity.model.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by bangzhu on 2017/4/1.
 */

public class UserCreatedArticleModelImpl implements UserCreatedArticleModel {
    private UserCreatedArticlePresenter mPresenter;
    private Article mArticle;

    public UserCreatedArticleModelImpl(UserCreatedArticlePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadArticleList() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Subscribe> query = new BmobQuery<>();
        query.order("-createdAt");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from CollectRead where userId = " + "'" + user.getObjectId() + "'");
        //加载更多
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Subscribe>() {
            @Override
            public void done(BmobQueryResult<Subscribe> result, BmobException e) {
                if(e == null) {
                    List<Subscribe> list = (List<Subscribe>) result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        loadCollectedArticle(list.get(0).getObjectId());
                    } else {//还没订阅，则创建订阅
                        return;
                    }
                } else {

                }
            }
        });
    }

    private void loadCollectedArticle(String objId) {
        BmobQuery<Article> query = new BmobQuery<Article>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from Article where related "+ "created" + " to pointer(" + "'" + "CollectRead" + "'" + "," + "'" + objId + "'"  + ")");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
            @Override
            public void done(BmobQueryResult<Article> result, BmobException e) {
                if(e == null) {
                    List<Article> list = result.getResults();
                    List<ArticleFlag> articleFlagList = new ArrayList<ArticleFlag>(list.size());
                    for(Article article : list) {
                        ArticleFlag af = new ArticleFlag(article, true);
                        articleFlagList.add(af);
                    }
                    mPresenter.loadArticleListSuccess(articleFlagList);
                }
            }
        });
    }

    @Override
    public void updateSubscribe(ArticleListEditableAdapter adapter) {
        int size = adapter.getCount();
        ArticleFlag articleFlag;
        for(int i=0;i<size;i++) {
            articleFlag = (ArticleFlag) adapter.getItem(i);
            if(articleFlag.flag) {
                updateArticleSubscribe(articleFlag.article);
            }
        }
    }

    private void updateArticleSubscribe(final Article article) {
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
                    List<CollectRead> list =  result.getResults();
                    //找得到，说明订阅了
                    if(list.size() > 0) {
                        minusMySubscription(list.get(0).getObjectId(), article);

                    } else {//还没订阅，则创建订阅
                        return;
                    }
                } else {

                }
            }
        });
    }

    private void minusMySubscription(String subscribId, Article article) {
        mArticle = article;
        CollectRead collectRead = new CollectRead();
        collectRead.setObjectId(subscribId);

        BmobRelation relation = new BmobRelation();
        relation.remove(article);
        collectRead.setCreated(relation);
        collectRead.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    deleteArticle(mArticle);
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void deleteArticle(Article a) {
        Article article = new Article();
        article.setObjectId(a.getObjectId());
        article.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("bmob","成功");
                    mPresenter.updateSubscribeSuccess();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
}
