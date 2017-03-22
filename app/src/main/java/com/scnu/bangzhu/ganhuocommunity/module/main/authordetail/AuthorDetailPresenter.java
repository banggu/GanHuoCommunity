package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

/**
 * Created by bangzhu on 2017/3/19.
 */

public interface AuthorDetailPresenter {
    void followAuthor(MyUser author);

    void unfollowAuthor(MyUser author);

    void loadFollowCount(String authorId);

    void loadFollowCountSuccess(int count);

    void loadFollowCountFailtrue(int count);

    void loadFollowerCount(String authorId);

    void loadFollowerCountSuccess(int count);

    void loadFollowerCountFailtrue(int count);

    void loadShareArticleCount();

    void loadCollectArticleCount();
}
