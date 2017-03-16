package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

import android.util.Log;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class AddArticleModelImpl implements AddArticleModel{
    private AddArticlePresenter mPresenter;

    public AddArticleModelImpl(AddArticlePresenter presenter) {
        mPresenter = presenter;
    }

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
        article.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    mPresenter.onPostArticleSuccess();
                }else{
                    mPresenter.onPostArticleFailtrue();
                }
            }
        });
    }
}
