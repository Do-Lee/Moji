package com.moji.lee.moji.Fragment;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.moji.lee.moji.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class ItemFragment extends SherlockFragment {
    TextView title;
    TextView content;
    View v;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.item, container, false);

        Bundle bundle = this.getArguments();
        title = (TextView) v.findViewById(R.id.ItemTitle);
        content = (TextView) v.findViewById(R.id.ItemContent);
        title.setText(bundle.getString("title"));
        content.setText(bundle.getString("content"));
        String imgUrl = bundle.getString("image");
        if(!imgUrl.equals("")) {

            Bitmap bitmap = getImageFromURL(imgUrl);
            ImageView imageView = (ImageView) v.findViewById(R.id.ItemImage);
            imageView.setImageBitmap(bitmap);
        }

        content.setText(bundle.getString("content"));
        content.setMovementMethod(new ScrollingMovementMethod());
        return v;
    }

    public static Bitmap getImageFromURL(String imageURL){
        Bitmap imgBitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;

        try
        {
            URL url = new URL(imageURL);
            conn = (HttpURLConnection)url.openConnection();
            conn.connect();

            int nSize = conn.getContentLength();
            bis = new BufferedInputStream(conn.getInputStream(), nSize);
            imgBitmap = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e){
            e.printStackTrace();
        } finally{
            if(bis != null) {
                try {bis.close();} catch (IOException e) {}
            }
            if(conn != null ) {
                conn.disconnect();
            }
        }

        return imgBitmap;
    }
}