package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

/**
 * Created by bangzhu on 2017/3/19.
 */

public interface AuthorDetailView {
    void setAvatar(String imageUrl);

    void setAuthorName(String userName);

    void setFollow();

    void setUnfollow();

    void setFollowCount(int count);

    void setFollowerCount(int count);

    void setShareArticleCount(int count);

    void setCollectArticleCount(int count);
}
