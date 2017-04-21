package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

/**
 * Created by bangzhu on 2017/3/19.
 */

public interface AuthorDetailModel {
    void followAuthor(MyUser author);

    void unfollowAuthor(MyUser author);

    void loadFollowCount(String authorId);

    void loadFollowerCount(String authorId);

    void loadShareArticleCount(String authorId);

    void loadCollectArticleCount(String authorId);
}
