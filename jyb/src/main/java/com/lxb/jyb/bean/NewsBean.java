package com.lxb.jyb.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.JSONObject;
import com.lxb.jyb.util.HttpConstant;

public class NewsBean implements Parcelable, Comparable<NewsBean> {
    private String newsTitle;
    private String newsAbstract;
    private String updatetime;
    private String newsThumbnail;
    private String editorName;
    private String newsView;
    private String newsId;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(newsTitle);
        dest.writeString(newsAbstract);
        dest.writeString(updatetime);
        dest.writeString(newsThumbnail);
        dest.writeString(editorName);
        dest.writeString(newsView);
        dest.writeString(newsId);

    }

    public static final Parcelable.Creator<NewsBean> CREATOR = new Creator<NewsBean>() {

        @Override
        public NewsBean[] newArray(int size) {
            // TODO Auto-generated method stub
            return new NewsBean[size];
        }

        @Override
        public NewsBean createFromParcel(Parcel source) {
            // TODO Auto-generated method stub
            NewsBean bean = new NewsBean();
            bean.setNewsTitle(source.readString());
            bean.setNewsAbstract(source.readString());
            bean.setUpdatetime(source.readString());
            bean.setNewsThumbnail(source.readString());
            bean.setEditorName(source.readString());
            bean.setNewsView(source.readString());
            bean.setNewsId(source.readString());
            return bean;
        }
    };

    public NewsBean(JSONObject obj) {
        // TODO Auto-generated constructor stub
        if (null != obj) {

            newsTitle = obj.getString("newsTitle");
            newsAbstract = obj.getString("newsAbstract");
            updatetime = obj.getString("updatetime");
            newsThumbnail = obj.getString("newsThumbnail");
            editorName = obj.getString("editorName");
            newsView = obj.getString("newsView");
            newsId = obj.getString("newsId");

        }
    }
public NewsBean(JSONObject obj,String type){
    if(null!=obj){
        newsTitle = obj.getString("title");
        newsAbstract = obj.getString("newsLink");
        updatetime = obj.getString("publishDate");
        newsThumbnail = HttpConstant.NSH+obj.getString("thumbnailLink");
        editorName = obj.getString("editorName");
        newsView = obj.getString("newsLink");
        newsId = obj.getString("newsId");

    }
}
    public NewsBean(String newsTitle, String newsAbstract, String updatetime,
                    String newsThumbnail, String editorName, String newsView,
                    String newsId) {
        super();
        this.newsTitle = newsTitle;
        this.newsAbstract = newsAbstract;
        this.updatetime = updatetime;
        this.newsThumbnail = newsThumbnail;
        this.editorName = editorName;
        this.newsView = newsView;
        this.newsId = newsId;
    }

    public NewsBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "NewsBean [newsTitle=" + newsTitle + ", newsAbstract="
                + newsAbstract + ", updatetime=" + updatetime
                + ", newsThumbnail=" + newsThumbnail + ", editorName="
                + editorName + ", newsView=" + newsView + ", newsId=" + newsId
                + "]";
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsAbstract() {
        return newsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        this.newsAbstract = newsAbstract;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getNewsThumbnail() {
        return newsThumbnail;
    }

    public void setNewsThumbnail(String newsThumbnail) {
        this.newsThumbnail = newsThumbnail;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public String getNewsView() {
        return newsView;
    }

    public void setNewsView(String newsView) {
        this.newsView = newsView;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public int compareTo(NewsBean another) {
        // TODO Auto-generated method stub
        return another.getUpdatetime().compareTo(this.getUpdatetime());
    }

}
