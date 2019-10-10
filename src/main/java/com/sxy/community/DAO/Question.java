package com.sxy.community.DAO;

public class Question {
    private Integer id;
    private String title;
    private String describe;
    private String tag;
    private Long gmtcreat;
    private Long gmtmodified;
    private Long creater;
    private Integer viewcount;
    private Integer commentcount;
    private Integer likecount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getGmtcreat() {
        return gmtcreat;
    }

    public void setGmtcreat(Long gmtcreat) {
        this.gmtcreat = gmtcreat;
    }

    public Long getGmtmodified() {
        return gmtmodified;
    }

    public void setGmtmodified(Long gmtmodified) {
        this.gmtmodified = gmtmodified;
    }

    public Long getCreater() {
        return creater;
    }

    public void setCreater(Long creater) {
        this.creater = creater;
    }

    public Integer getViewcount() {
        return viewcount;
    }

    public void setViewcount(Integer viewcount) {
        this.viewcount = viewcount;
    }

    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", describe='" + describe + '\'' +
                ", tag='" + tag + '\'' +
                ", gmtcreat=" + gmtcreat +
                ", gmtmodified=" + gmtmodified +
                ", creater=" + creater +
                ", viewcount=" + viewcount +
                ", commentcount=" + commentcount +
                ", likecount=" + likecount +
                '}';
    }
}
