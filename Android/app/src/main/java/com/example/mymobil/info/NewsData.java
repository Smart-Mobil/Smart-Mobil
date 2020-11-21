package com.example.mymobil.info;

import java.io.Serializable;

//Serializable: 복잡한 데이터구조를 단순하게 바꿔줌
//private: 캡슐화
/**
 * Create by Jinyeob on 2020. 04. 28.
 */
public class NewsData implements Serializable {
    private String title;
    private String urlToImage;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
