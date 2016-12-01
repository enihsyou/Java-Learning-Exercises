package com.enihsyou.shane.photogallery;

public class GalleryItem {
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String urlPattern) {
        mUrl = "http://img.hb.aicdn.com/" + urlPattern;
    }

    private String mId;
    private String mCaption;
    private String mUrl;

    @Override
    public String toString() {
        return mCaption;
    }
}
