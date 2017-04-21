package com.scnu.bangzhu.ganhuocommunity.module.main.messagenotification;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MessageItem;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by bangzhu on 2017/3/24.
 */

public class NotifyMessageService extends IntentService {
    private static final int LOAD_ARTICLE_LIST = 543210;
    private static final int FINISH_REFRESH_ARTICLE = 543211;

    private ArrayList<MessageItem> mMessageItemList;
    private int mIndex = 0;
    private int mSize;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_ARTICLE_LIST:
                    MessageItem messageItem = (MessageItem) msg.obj;
                    mMessageItemList.add(messageItem);
                    mIndex = mIndex + 1;
                    if(mIndex == mSize) {
                        sendMessageNotification();
                    }
                    break;
            }
        }
    };

    public NotifyMessageService() {
        super("NotifyMessageService");
        mMessageItemList = new ArrayList<>();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
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
                    loadMessageItem(list);
                }
            }
        });
    }

    private void loadMessageItem(List<MyUser> userList) {
//        String sql1 = "select * from GameScore where createdAt >= date('"+new BmobDate(date).getDate()+"')";
        //" and createdAt >= date('" + new BmobDate(date).getDate() + "')"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date  = new Date(System.currentTimeMillis());
        mSize = userList.size();
        for(int i=0;i<mSize;i++) {
            final MyUser u = userList.get(i);
            BmobQuery<Article> query = new BmobQuery<Article>();
            query.addWhereEqualTo("author", u);    // 查询当前用户的所有帖子
            query.order("-updatedAt");
            query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
            query.findObjects(new FindListener<Article>() {

                @Override
                public void done(List<Article> object,BmobException e) {
                    if(e==null){
                        int s = object.size();
                        if(s > 0) {
                            ArrayList list = new ArrayList(object);
                            MessageItem messageItem = new MessageItem(u, s, list);
                            Message msg = new Message();
                            msg.what = LOAD_ARTICLE_LIST;
                            msg.obj = messageItem;
                            mHandler.sendMessage(msg);
                        }
                    }else{

                    }
                }

            });
//            StringBuffer sql = new StringBuffer();
//            sql.append("select include author,* from Article where author = pointer(" +  "'" + "_User" + "'" + "," + "'" + mUser.getObjectId() + "'"  + ")");
//            query.doSQLQuery(sql.toString(), new SQLQueryListener<Article>() {
//                @Override
//                public void done(BmobQueryResult<Article> result, BmobException e) {
//                    if(e == null) {
//                        List<Article> list = result.getResults();
//                        int s = list.size();
//                        if(s > 0) {
//                            MessageItem messageItem = new MessageItem(mUser, s, list);
//                            Message msg = new Message();
//                            if(mIndex == s-1) {
//                                msg.what = FINISH_REFRESH_ARTICLE;
//                            } else {
//                                msg.what = LOAD_ARTICLE_LIST;
//                            }
//                            msg.obj = messageItem;
//                            mHandler.sendMessage(msg);
//                        }
//                    }
//                }
//            });
        }
    }

    private void sendMessageNotification() {
        Intent intent = new Intent("com.bangzhu.receiver.MESSAGE_NOTIFICATION");
        intent.putExtra("messagenotification", mMessageItemList);
        sendBroadcast(intent);
    }
}
