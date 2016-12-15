package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class AddArticleModelImpl implements AddArticleModel{
    private AddArticlePresenter mPresenter;

    public AddArticleModelImpl(AddArticlePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void postArticle(String title, String type, String imageUrl, String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setType(type);
        article.setImageUrl(imageUrl);
        article.setContent(content);
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
