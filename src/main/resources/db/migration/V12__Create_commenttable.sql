CREATE TABLE comment
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parent_id BIGINT NOT NULL,
    type INT NOT NULL COMMENT '父类的类型',
    commentator BIGINT NOT NULL COMMENT '评论人id',
    gmt_create BIGINT NOT NULL COMMENT '创建时间',
    gmt_modify BIGINT NOT NULL COMMENT '更新时间',
    like_count BIGINT DEFAULT 0 NOT NULL
);