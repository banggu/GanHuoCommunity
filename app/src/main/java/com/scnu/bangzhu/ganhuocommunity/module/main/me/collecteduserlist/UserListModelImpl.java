package com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist;

import android.os.Message;
import android.util.Log;

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
 * Created by bangzhu on 2017/3/27.
 */

public class UserListModelImpl implements UserListModel {
    private UserListPresenter mPresenter;
    private String mCacheStr;

    public UserListModelImpl(UserListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadUserList() {
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
                        loadCollectedUser(list.get(0).getObjectId());
                    } else {//还没订阅，则创建订阅
                        return;
                    }
                } else {

                }
            }
        });
    }

    private void loadCollectedUser(String objId) {
        BmobQuery<MyUser> query = new BmobQuery<MyUser>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from _User where related "+ "follow" + " to pointer(" + "'" + "Subscribe" + "'" + "," + "'" + objId + "'"  + ")");
        query.doSQLQuery(sql.toString(), new SQLQueryListener<MyUser>() {
            @Override
            public void done(BmobQueryResult<MyUser> result, BmobException e) {
                if(e == null) {
                    List<MyUser> list = result.getResults();
                    List<MyUserFlag> myUserFlagList = new ArrayList<MyUserFlag>(list.size());
                    for(MyUser user : list) {
                        MyUserFlag uf = new MyUserFlag(user, true);
                        myUserFlagList.add(uf);
                    }
                    mPresenter.loadUserListSuccess(myUserFlagList);
                }
            }
        });
    }

    @Override
    public void updateSubscribe(UserListAdapter adapter) {
        int size = adapter.getCount();
        MyUserFlag myUserFlag;
        for(int i=0;i<size;i++) {
            myUserFlag = (MyUserFlag) adapter.getItem(i);
            if(!myUserFlag.flag) {
                updateUserSubscribe(myUserFlag.user);
            }
        }
    }

    private void updateUserSubscribe(final MyUser author) {
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
        Subscribe subscribe = new Subscribe();
        if (bindType == "follower") {
            subscribe.setFollowerCount(num);
        } else if(bindType == "follow") {
            subscribe.setFollowCount(num);
        }

        subscribe.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
            mPresenter.updateSubscribeSuccess();
            }
        });
    }
}
