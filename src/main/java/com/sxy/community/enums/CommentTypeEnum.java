package com.sxy.community.enums;




import com.sxy.community.DAO.Comment;

/**
 * 1代表回复问题，2代表回复评论
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    /**
     * 判断是否存在某个枚举量
     * @param comment
     * @return
     */
    public static boolean isExist(Comment comment) {
        CommentTypeEnum[] values = CommentTypeEnum.values();
        for (CommentTypeEnum value : values) {
            if (comment.getType()==value.getType()){
                return true;
            }
        }
        return false;
    }
}
