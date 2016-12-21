package com.scnu.bangzhu.ganhuocommunity.module.addarticle;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
        ViewPager viewPager;
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
//                String[] pojo = {MediaStore.Images.Media.DATA};
//                //Cursor cursor = managedQuery(imageUri, pojo, null, null,null);
//                Cursor cursor = getContentResolver().query(imageUri, pojo, null, null, null);
//                if(cursor != null )
//                {
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
//                    imagePath = cursor.getString(columnIndex);
//                    Log.i("HZWING", "image path" + imagePath);
//                    mPresenter.uploadImage(imagePath);
//                    cursor.close();
//                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    imagePath = getImagePathKitKat(imageUri);
                } else {
                    imagePath = getImagePathBeforeKitKat(imageUri);
                }
                Log.i("HZWING", "image path" + imagePath);
                mPresenter.uploadImage(imagePath);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getImagePathKitKat(Uri uri) {
        String imagePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this,uri)){
                //如果是document类型的uri 则通过id进行解析处理
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                    //解析出数字格式id
                    String id = docId.split(":")[1];
                    String selection = MediaStore.Images.Media._ID + "=" +id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("" +
                            "content://downloads/public_downloads"),Long.valueOf(docId));
                    imagePath = getImagePath(contentUri,null);
                }
            }else if ("content".equals(uri.getScheme())){
                //如果不是document类型的uri，则使用普通的方式处理
                imagePath = getImagePath(uri,null);
            }
            return imagePath;
        }
        return null;
    }

    private String getImagePathBeforeKitKat(Uri uri){
        String imagePath = getImagePath(uri,null);
        return imagePath;
    }

    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,seletion,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
