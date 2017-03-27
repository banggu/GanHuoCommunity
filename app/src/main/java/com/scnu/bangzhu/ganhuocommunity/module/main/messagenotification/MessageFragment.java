package com.scnu.bangzhu.ganhuocommunity.module.main.messagenotification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.MessageItem;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.module.main.MainActivity;
import com.scnu.bangzhu.ganhuocommunity.module.main.authordetail.AuthorArticleListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class MessageFragment extends Fragment {
    private View mView;
    private ListView mMessageList;
    private MessageListAdapter mMessageListAdapter;
    private List<MessageItem> mMessageItemList;
    private MessageNotificationReceiver mReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message, container,false);
        initView();
        bindView();
        bindReceiver();
        setContent();
        setListener();
        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mReceiver);
    }

    private void initView() {
        mMessageList = (ListView) mView.findViewById(R.id.message_list_listView);

    }

    private void bindView() {
        mMessageItemList = new ArrayList<>();
        mMessageListAdapter = new MessageListAdapter(getActivity(), mMessageItemList);
        mMessageList.setAdapter(mMessageListAdapter);
    }

    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.bangzhu.receiver.MESSAGE_NOTIFICATION");
        mReceiver = new MessageNotificationReceiver();
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    private void setContent() {
        bindToService();
    }

    private void setListener() {
        mMessageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AuthorArticleListActivity.startMe(getActivity(), mMessageItemList.get(i).getArticleList(), mMessageItemList.get(i).getUser().getUsername()+"的消息");
            }
        });
    }

    private void bindToService() {
        Intent intent = new Intent(getActivity(), NotifyMessageService.class);
        getActivity().startService(intent);
    }

    class MessageNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<MessageItem> list = (ArrayList<MessageItem>) intent.getSerializableExtra("messagenotification");
            mMessageItemList.clear();
            mMessageItemList.addAll(list);
            mMessageListAdapter.notifyDataSetInvalidated();
            startAlarm();
        }
    }

    private void startAlarm() {
        Intent intent = new Intent(getActivity(),
                RepeatingAlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                getActivity(), 0, intent, 0);

        // We want the alarm to go off 10 seconds from now.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 10 * 1000, sender);
    }
}
