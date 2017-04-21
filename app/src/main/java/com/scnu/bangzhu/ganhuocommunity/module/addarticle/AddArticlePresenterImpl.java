package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class AddArticlePresenterImpl implements AddArticlePresenter{
    private AddArticleView mView;
    private AddArticleModel mModel;

    public AddArticlePresenterImpl(AddArticleView view) {
        mView = view;
        mModel = new AddArticleModelImpl(this);
    }

    @Override
    public void uploadImage(String imageUrl) {
        mModel.uploadImage(imageUrl);
    }

    @Override
    public void onUploadImageSuccess(String imageUrl) {
        mView.insertImage(imageUrl, "本地图片");
    }

    @Override
    public void onUploadImageFailtrue(String errMsg) {

    }

    @Override
    public void postArticle(String title, String type, String imageUrl, String content) {
        mModel.postArticle(title, type, imageUrl, content);
    }

    @Override
    public void onPostArticleSuccess() {
        mView.navigateToMain();
    }

    @Override
    public void onPostArticleFailtrue() {

    }
}
