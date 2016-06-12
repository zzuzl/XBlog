/* 创建xblog数据库 */
CREATE DATABASE xblog;

/* 使用数据库 */
USE xblog;

/* 创建用户表 */
CREATE TABLE t_user(
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `email` VARCHAR(45) NOT NULL COMMENT 'email',
  `hash` VARCHAR(45) NOT NULL  COMMENT 'hash密码',
  `salt` VARCHAR(10) NOT NULL COMMENT 'salt',
  `nickname` VARCHAR(20) NOT NULL COMMENT '昵称',
  `reg_time` DATETIME NOT NULL DEFAULT now() COMMENT '注册时间',
  `fans_count` INT NOT NULL DEFAULT 0 COMMENT '粉丝数',
  `attention_count` INT NOT NULL DEFAULT 0 COMMENT '关注数',
  `photo_src` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '头像路径',
  `motto` VARCHAR(255) COMMENT '个性签名',
  `interest` VARCHAR(255) COMMENT '兴趣',
  `sex` VARCHAR(5) NOT NULL DEFAULT '男' COMMENT '性别',
  `url` VARCHAR(50) NOT NULL COMMENT '博客地址',
  PRIMARY KEY (user_id),
  key reg_time_index(reg_time)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '用户表';

/* 插入测试数据 */
INSERT INTO t_user(email,hash,salt,nickname,motto,url) VALUES
  ('672399171@qq.com','a8uoze10adc3949ba59abbe56e057f20f883e','a8uoz','who am i','我是谁','rcg6u4891a9a9a0628b7cb82d62328ecdf5fb'),
  ('675046577@qq.com','a8uoze10adc3949ba59abbe56e057f20f883e','a8uoz','who are you','你是谁','3hpxk4891a9a9a0628b7cb82d62328ecdf5fb');

/* 创建关注表 */
CREATE TABLE t_attention(
  `from` INT NOT NULL COMMENT '关注者id',
  `to` INT NOT NULL COMMENT '被关注者id',
  `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (`from`,`to`),
  KEY create_time_index(create_time),
  FOREIGN KEY(`from`) REFERENCES t_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`to`) REFERENCES t_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '关注表';

/* 插入测试数据 */
INSERT INTO t_attention(`from`,`to`) VALUES
  (1,2);

/* 创建分类表 */
CREATE TABLE t_category(
  `cate_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` VARCHAR(50) NOT NULL COMMENT '标题',
  `p_id` INT NOT NULL DEFAULT 0 COMMENT '父亲id',
  PRIMARY KEY (cate_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '分类表';

/* 插入测试数据 */
INSERT INTO t_category(title, p_id) VALUES
  ('编程语言',0),
  ('JAVA',1),
  ('PHP',1),
  ('C/C++',1),
  ('移动开发',0),
  ('Android',5),
  ('IOS',5),
  ('WindowPhone',5),
  ('数据库技术',0),
  ('MySQL',9),
  ('SQLServer',9),
  ('Mongodb',9);

/* 创建文章表 */
CREATE TABLE t_article(
  `article_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cate_id` INT NOT NULL COMMENT '分类id',
  `title` VARCHAR(50) NOT NULL COMMENT '标题',
  `description` TEXT COMMENT '描述',
  `content` TEXT COMMENT '内容',
  `post_time` DATETIME NOT NULL DEFAULT now() COMMENT '发表时间',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `comment_count` INT DEFAULT 0 COMMENT '评论次数',
  `like_count` INT DEFAULT 0 COMMENT '赞次数',
  `user_id` INT NOT NULL COMMENT '用户id',
  `tag` VARCHAR(200) DEFAULT '' COMMENT '标签',
  PRIMARY KEY (article_id),
  KEY post_time_index(post_time),
  FOREIGN KEY (cate_id) REFERENCES t_category(cate_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES t_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '文章表';

/* 插入测试数据 */
INSERT INTO t_article (cate_id, title, description, content, user_id, tag)
VALUES (2,'JAVA基础教程一','JAVA基础教程,快来学习啊!','JAVA基础教程,快来学习啊!','1','JAVA');

/* 创建评论表 */
CREATE TABLE t_comment (
  `comment_id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` TEXT NOT NULL COMMENT '内容',
  `post_time` DATETIME NOT NULL DEFAULT now() COMMENT '发表时间',
  `article_id` INT NOT NULL COMMENT '文章id',
  `user_id` INT NOT NULL COMMENT '用户id',
  `p_id` INT NOT NULL COMMENT '父亲id',
  PRIMARY KEY (comment_id),
  KEY post_time_index(post_time),
  FOREIGN KEY (article_id) REFERENCES t_article(article_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES t_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '评论表';

/* 插入测试数据 */
INSERT INTO t_comment(content, article_id, p_id,user_id) VALUES
  ('文章写得很不错',1,0,1),
  ('岂止不错，简直逆天!',1,1,1);

/* 创建点赞表 */
CREATE TABLE t_like(
  `user_id` INT NOT NULL COMMENT '用户id',
  `article_id` INT NOT NULL COMMENT '文章id',
  `create_time` DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  PRIMARY KEY (user_id,article_id),
  KEY create_time_index(create_time),
  FOREIGN KEY (user_id) REFERENCES t_user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (article_id) REFERENCES t_article(article_id) ON UPDATE CASCADE ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '点赞表';


