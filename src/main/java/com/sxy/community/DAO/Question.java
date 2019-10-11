package com.sxy.community.DAO;

import lombok.Data;

@Data
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
