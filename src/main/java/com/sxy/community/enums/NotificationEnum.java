package com.sxy.community.enums;

/**
 * 通知回复
 */
public enum  NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private int type;
    private String name;

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    /**
     * 拿到相应的枚举对应的字符串
     * @param type
     * @return
     */
    public static String getTypeName(Integer type){
        for (NotificationEnum value : NotificationEnum.values()) {  //枚举用values遍历
            if (value.getType()==type){
                return value.getName();
            }
        }
        return "";
    }
}
