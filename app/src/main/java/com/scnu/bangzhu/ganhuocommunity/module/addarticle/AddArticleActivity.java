package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;

import jp.wasabeef.richeditor.RichEditor;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class AddArticleActivity extends BaseActivity implements AddArticleView, View.OnClickListener{
    private EditText mArticleTitle, mArticleType;
    private RichEditor mRichEditor;
    private ImageView mInsertImage, mPostArticle;
    private AddArticlePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        initView();
        setListeners();
    }

    private void initView() {
        mArticleTitle = (EditText) findViewById(R.id.article_title_editText);
        mArticleType = (EditText) findViewById(R.id.article_type_editText);
        mRichEditor = (RichEditor) findViewById(R.id.rich_editor);
        mInsertImage = (ImageView) findViewById(R.id.insert_image_imageView);
        mPostArticle = (ImageView) findViewById(R.id.post_article_imageView);

        mPresenter = new AddArticlePresenterImpl(this);
        mRichEditor.loadCSS("file:///android_asset/rich_editor.css");
    }

    private void setListeners() {
        mInsertImage.setOnClickListener(this);
        mPostArticle.setOnClickListener(this);
    }

    @Override
    public void setArticleTitle(String title) {
        mArticleTitle.setText(title);
    }

    @Override
    public void setArticleType(String type) {
        mArticleType.setText(type);
    }

    @Override
    public void insertImage(String imageUrl, String alt) {
        mRichEditor.insertImage(imageUrl, alt);
    }

    @Override
    public void setArticlContent(String content) {
        mRichEditor.setHtml(content);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.insert_image_imageView:
                chooseImageFromAlbum();
                break;
            case R.id.post_article_imageView:
                break;
        }
    }

    private void chooseImageFromAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 12581);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            Uri imageUri = null;
            String imagePath = null;
            if(requestCode == 12581) {
                if(data == null)
                {
                    Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                    return;
                }
                imageUri = data.getData();
                if(imageUri == null )
                {
                    Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            String[] pojo = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(imageUri, pojo, null, null,null);
            if(cursor != null )
            {
                int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                cursor.moveToFirst();
                imagePath = cursor.getString(columnIndex);
                cursor.close();
            }
            if(imagePath != null && ( imagePath.endsWith(".png") || imagePath.endsWith(".PNG") ||imagePath.endsWith(".jpg") ||imagePath.endsWith(".JPG")  ))
            {
                mPresenter.uploadImage(imagePath);
            }else{
                Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
