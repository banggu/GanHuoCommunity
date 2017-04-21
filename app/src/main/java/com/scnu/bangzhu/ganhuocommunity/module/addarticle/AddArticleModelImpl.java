package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.CollectRead;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class AddArticleModelImpl implements AddArticleModel{
    private AddArticlePresenter mPresenter;
    private Article mArticle;

    public AddArticleModelImpl(AddArticlePresenter presenter) {
        mPresenter = presenter;
    }

    //上传图片
    @Override
    public void uploadImage(String imageUrl) {
        final BmobFile bmobFile = new BmobFile(new File(imageUrl));
        bmobFile.uploadblock(new UploadFileListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                    Log.i("HZWING", bmobFile.getFileUrl());
                    mPresenter.onUploadImageSuccess(bmobFile.getFileUrl());
                }else{
                    Log.i("HZWING", e.getMessage());
                    mPresenter.onUploadImageFailtrue(e.getMessage());
                }

            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
            }
        });
    }

    @Override
    public void postArticle(String title, String type, String imageUrl, String content) {
        MyUser author = BmobUser.getCurrentUser(MyUser.class);
        Article article = new Article();
        article.setTitle(title);
        article.setType(type);
        article.setImageUrl(imageUrl);
        article.setContent(content);
        article.setLikesCount(0);
        article.setReadCount(0);
        article.setAuthor(author);
        mArticle = article;
        article.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    mPresenter.onPostArticleSuccess();
                    bindCreatedArticle();
                }else{
                    mPresenter.onPostArticleFailtrue();
                }
            }
        });
    }

    //绑定用户的创建文章
    private void bindCreatedArticle() {
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
                        addBindCreation(list.get(0).getObjectId(), mArticle);
                    } else {//还没订阅，则创建订阅
                        initCreation(mArticle);
                    }
                } else {

                }
            }
        });
    }

    //添加收藏
    private void addBindCreation(String objId, Article article) {
        CollectRead collectRead = new CollectRead();
        collectRead.setObjectId(objId);

        BmobRelation relation = new BmobRelation();
        relation.add(article);

        collectRead.setCreated(relation);
        collectRead.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){

                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    //创建收藏关系
    private void initCreation(Article article) {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        final CollectRead collectRead = new CollectRead();

        BmobRelation relation = new BmobRelation();
        relation.add(article);

        collectRead.setUserId(user.getObjectId());
        collectRead.setCreated(relation);
        collectRead.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null) {

                }
            }
        });
    }

}
