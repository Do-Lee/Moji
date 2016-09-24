package com.moji.lee.moji.CardView;

/**
 * Created by Lee on 16. 5. 28..
 */
public class MyData{
    public String title;
    public String content;
    public String image;
    public MyData(String title, String content){
        this.title = title;
        this.content = content;
    }
    public MyData(String title, String content, String image){
        this.title = title;
        this.content = content;
        this.image = image;
    }
}