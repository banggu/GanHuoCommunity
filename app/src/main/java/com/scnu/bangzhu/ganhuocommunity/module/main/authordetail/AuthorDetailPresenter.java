package com.scnu.bangzhu.ganhuocommunity.module.main.authordetail;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import java.util.List;

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

    void loadShareArticleCount(String authorId);

    void loadShareArticleCountSuccess(List<Article> articleList);

    void loadShareArticleCountFailtrue(int count);

    void loadCollectArticleCount(String authorId);

    void loadCollectArticleCountSuccess(List<Article> articleList);

    void loadCollectArticleCountFailtrue(int count);
}
