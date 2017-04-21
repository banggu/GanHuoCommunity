package com.scnu.bangzhu.ganhuocommunity.module.main.messagenotification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.MessageItem;
import com.scnu.bangzhu.ganhuocommunity.util.StringUtil;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/24.
 */

public class MessageListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MessageItem> mMessageList;

    public MessageListAdapter(Context context, List<MessageItem> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_message_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.msgImage = (ImageView) convertView.findViewById(R.id.article_item_image_imageView);
            viewHolder.msgTitle = (TextView) convertView.findViewById(R.id.article_item_title_textView);
            viewHolder.msgNum = (TextView) convertView.findViewById(R.id.message_notification_msgNum);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MessageItem messageItem = mMessageList.get(position);
        String imageUrl = StringUtil.getOriginUrl(messageItem.getUser().getUserAvatar());
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.msgImage);
        viewHolder.msgTitle.setText(messageItem.getUser().getUsername());
        viewHolder.msgNum.setText(String.valueOf(messageItem.getMsgNum()));
        return convertView;
    }

    final class ViewHolder {
        public ImageView msgImage;
        public TextView msgTitle;
        public TextView msgNum;
    }
}
