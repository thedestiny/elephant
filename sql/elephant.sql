## build database
DROP DATABASE If Exists `elephant`;
CREATE DATABASE If Not Exists  `elephant` DEFAULT ;
use elephant;

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS `blog_article`;
CREATE TABLE `blog_article`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255)  NOT NULL COMMENT '标题',
  `sub_title` varchar(255)  NULL DEFAULT NULL COMMENT '副标题',
  `marks` varchar(255)  NULL DEFAULT NULL COMMENT '摘要',
  `show_pic` varchar(255)  NULL DEFAULT NULL COMMENT '显示图片',
  `category` varchar(255)  NULL DEFAULT NULL COMMENT '文章类型',
  `out_link_url` varchar(255)  NULL DEFAULT NULL COMMENT '外链地址',
  `resources` varchar(255)  NULL DEFAULT NULL COMMENT '来源',
  `publist_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `content` text  NOT NULL COMMENT '内容',
  `text` text  NULL COMMENT '纯文字文章内容',
  `click` int(11) NULL DEFAULT NULL COMMENT '浏览量',
  `channel_id` bigint(20) NULL DEFAULT NULL COMMENT '栏目ID',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `is_top` bit(1) NULL DEFAULT NULL COMMENT '是否置顶',
  `is_recommend` bit(1) NULL DEFAULT NULL COMMENT '是否推荐',
  `status` int(11) NULL DEFAULT NULL COMMENT '文章状态',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '博客内容' ;

-- ----------------------------
-- Records of blog_article
-- ----------------------------


-- ----------------------------
-- Table structure for blog_article_tags
-- ----------------------------
DROP TABLE IF EXISTS `blog_article_tags`;
CREATE TABLE `blog_article_tags`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `tags_id` bigint(20) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  COMMENT = '标签-文章关联表' ;

-- ----------------------------
-- Table structure for blog_channel
-- ----------------------------
DROP TABLE IF EXISTS `blog_channel`;
CREATE TABLE `blog_channel`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255)  NOT NULL COMMENT '名称',
  `site_id` bigint(20) NULL DEFAULT NULL COMMENT '站点ID',
  `href` varchar(500)  NULL DEFAULT NULL COMMENT '链接地址',
  `logo` varchar(255)  NULL DEFAULT NULL COMMENT '栏目图标',
  `is_base_channel` bit(1) NULL DEFAULT NULL COMMENT '是否为主栏目',
  `can_comment` bit(1) NULL DEFAULT NULL COMMENT '是否能够评论',
  `is_no_name` bit(1) NULL DEFAULT NULL COMMENT '是否匿名',
  `is_can_aduit` bit(1) NULL DEFAULT NULL COMMENT '是否开启审核',
  `seo_title` varchar(255)  NULL DEFAULT NULL COMMENT '网页title(seo)',
  `seo_keywords` varchar(255)  NULL DEFAULT NULL COMMENT '网页关键字(seo) ',
  `seo_description` varchar(255)  NULL DEFAULT NULL COMMENT '网页描述(seo)',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父节点ID',
  `parent_ids` varchar(2000)  NULL DEFAULT NULL COMMENT '父节点联集',
  `level` bigint(2) NULL DEFAULT NULL COMMENT '层级深度',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '博客栏目' ;

-- ----------------------------
-- Records of blog_channel
-- ----------------------------
INSERT INTO `blog_channel` VALUES (1, '网站首页', 1, '/index', '', b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '1,', 1, 60, '2018-01-22 11:55:24', 1, '2018-01-23 00:37:57', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (2, '文章专栏', 1, '/wzzl', '', b'1', b'1', b'0', b'0', NULL, NULL, NULL, NULL, '2,', 1, 50, '2018-01-22 11:55:56', 1, '2018-02-03 21:41:52', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (3, '资源分享', 1, '/share', '', b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '3,', 1, 10, '2018-01-22 11:56:50', 1, '2018-02-06 11:20:48', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (4, '点点滴滴', 1, '/dddd', '', b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '4,', 1, 30, '2018-01-22 11:57:16', 1, '2018-02-08 01:37:32', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (5, '关于本站', 1, '/about', '', b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '5,', 1, 20, '2018-01-22 11:57:40', 1, '2018-02-06 00:14:18', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (6, '通知', 1, '/tz', NULL, b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '6,', 1, 0, '2018-01-22 11:59:01', 1, '2018-01-22 23:25:40', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (7, '更新日志', 1, '/gxrz', NULL, b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '7,', 1, -1, '2018-01-22 23:37:25', 1, '2018-01-22 23:37:38', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (9, 'H5', 1, '/wzzl/h5', NULL, b'1', b'0', b'0', b'0', NULL, NULL, NULL, 2, '2,9,', 2, 0, '2018-01-27 01:34:24', 1, '2018-01-27 01:46:23', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (10, 'mysql', 1, '/wzzl/mysql', NULL, b'1', b'0', b'0', b'0', NULL, NULL, NULL, 2, '2,10,', 2, 10, '2018-01-27 01:34:42', 1, '2018-01-27 01:46:17', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (11, 'springboot', 1, '/wzzl/springboot', NULL, b'1', b'0', b'0', b'0', NULL, NULL, NULL, 2, '2,11,', 2, 20, '2018-01-27 01:35:05', 1, '2018-01-27 01:46:09', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (12, 'mybatis', 1, '/wzzl/mybatis', NULL, b'1', b'0', b'0', b'0', NULL, NULL, NULL, 2, '2,12,', 2, 30, '2018-01-27 01:35:18', 1, '2018-01-27 01:46:02', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (13, 'IDE', 1, '/wzzl/ide', NULL, b'1', b'1', b'0', b'0', NULL, NULL, NULL, 2, '2,13,', 2, 40, '2018-01-27 01:35:44', 1, '2018-02-04 04:00:11', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (14, 'java', 1, '/wzzl/java', NULL, b'1', b'1', b'0', b'0', NULL, NULL, NULL, 2, '2,14,', 2, 50, '2018-01-27 01:35:56', 1, '2018-02-04 02:04:20', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (15, '留言墙', 1, '/about/messages', NULL, b'0', b'1', b'0', b'0', NULL, NULL, NULL, 5, '5,15,', 2, 0, '2018-02-05 21:27:13', 1, '2018-02-06 00:07:04', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (16, '友情链接', 1, '/about/friendlink', NULL, b'0', b'0', b'0', b'0', NULL, NULL, NULL, 5, '5,16,', 2, 10, '2018-02-05 21:27:33', 1, '2018-02-06 00:06:30', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (17, '关于作者', 1, '/about/author', NULL, b'0', b'0', b'0', b'0', NULL, NULL, NULL, 5, '5,17,', 2, 20, '2018-02-05 21:27:51', 1, '2018-02-06 00:06:18', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (18, '关于博客', 1, '/about/blog', NULL, b'0', b'0', b'0', b'0', NULL, NULL, NULL, 5, '5,18,', 2, 30, '2018-02-05 21:28:09', 1, '2018-02-05 23:14:56', 1, NULL, 0);
INSERT INTO `blog_channel` VALUES (19, '首页banner图', 1, '/sybannert', NULL, b'0', b'0', b'0', b'0', NULL, NULL, NULL, NULL, '19,', 1, 1, '2018-02-07 10:31:12', 1, '2018-02-07 10:31:21', 1, NULL, 0);

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` text  NOT NULL COMMENT '评论内容',
  `type` int(11) NULL DEFAULT NULL COMMENT 'ip,input,YES',
  `ip` varchar(255)  NULL DEFAULT NULL COMMENT 'ip',
  `system` varchar(255)  NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255)  NULL DEFAULT NULL COMMENT '浏览器',
  `floor` int(11) NULL DEFAULT NULL COMMENT '楼层',
  `channel_id` bigint(20) NULL DEFAULT NULL COMMENT '栏目ID',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章ID',
  `reply_id` bigint(20) NULL DEFAULT NULL COMMENT '回复评论ID',
  `is_admin_reply` bit(1) NULL DEFAULT NULL COMMENT '管理员是否回复',
  `reply_content` text  NULL COMMENT '管理员回复内容',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  COMMENT = '博客评论' ;

-- ----------------------------
-- Table structure for blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `blog_tags`;
CREATE TABLE `blog_tags`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255)  NULL DEFAULT NULL COMMENT '标签名字',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1  COMMENT = '博客标签,1' ;

-- ----------------------------
-- Table structure for hat_area
-- ----------------------------
DROP TABLE IF EXISTS `hat_area`;
CREATE TABLE `hat_area`  (
  `id` int(11) NOT NULL,
  `areaID` varchar(255)  NULL DEFAULT NULL,
  `area` varchar(255)  NULL DEFAULT NULL,
  `father` varchar(255)  NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ;

-- ----------------------------
-- Records of hat_area
-- ----------------------------
INSERT INTO `hat_area` VALUES (1, '110101', '东城区', '110100');
INSERT INTO `hat_area` VALUES (2, '110102', '西城区', '110100');
INSERT INTO `hat_area` VALUES (3, '110103', '崇文区', '110100');
INSERT INTO `hat_area` VALUES (4, '110104', '宣武区', '110100');
INSERT INTO `hat_area` VALUES (5, '110105', '朝阳区', '110100');
INSERT INTO `hat_area` VALUES (6, '110106', '丰台区', '110100');
INSERT INTO `hat_area` VALUES (7, '110107', '石景山区', '110100');
INSERT INTO `hat_area` VALUES (8, '110108', '海淀区', '110100');
INSERT INTO `hat_area` VALUES (9, '110109', '门头沟区', '110100');
INSERT INTO `hat_area` VALUES (10, '110111', '房山区', '110100');
INSERT INTO `hat_area` VALUES (11, '110112', '通州区', '110100');
INSERT INTO `hat_area` VALUES (12, '110113', '顺义区', '110100');
INSERT INTO `hat_area` VALUES (13, '110114', '昌平区', '110100');
INSERT INTO `hat_area` VALUES (14, '110115', '大兴区', '110100');
INSERT INTO `hat_area` VALUES (15, '110116', '怀柔区', '110100');
INSERT INTO `hat_area` VALUES (16, '110117', '平谷区', '110100');
INSERT INTO `hat_area` VALUES (17, '110228', '密云县', '110200');
INSERT INTO `hat_area` VALUES (18, '110229', '延庆县', '110200');
INSERT INTO `hat_area` VALUES (19, '120101', '和平区', '120100');
INSERT INTO `hat_area` VALUES (20, '120102', '河东区', '120100');
INSERT INTO `hat_area` VALUES (21, '120103', '河西区', '120100');
INSERT INTO `hat_area` VALUES (22, '120104', '南开区', '120100');
INSERT INTO `hat_area` VALUES (23, '120105', '河北区', '120100');
INSERT INTO `hat_area` VALUES (24, '120106', '红桥区', '120100');
INSERT INTO `hat_area` VALUES (25, '120107', '塘沽区', '120100');
INSERT INTO `hat_area` VALUES (26, '120108', '汉沽区', '120100');
INSERT INTO `hat_area` VALUES (27, '120109', '大港区', '120100');
INSERT INTO `hat_area` VALUES (28, '120110', '东丽区', '120100');
INSERT INTO `hat_area` VALUES (29, '120111', '西青区', '120100');
INSERT INTO `hat_area` VALUES (30, '120112', '津南区', '120100');
INSERT INTO `hat_area` VALUES (31, '120113', '北辰区', '120100');
INSERT INTO `hat_area` VALUES (32, '120114', '武清区', '120100');
INSERT INTO `hat_area` VALUES (33, '120115', '宝坻区', '120100');
INSERT INTO `hat_area` VALUES (34, '120221', '宁河县', '120200');
INSERT INTO `hat_area` VALUES (35, '120223', '静海县', '120200');
INSERT INTO `hat_area` VALUES (36, '120225', '蓟　县', '120200');
INSERT INTO `hat_area` VALUES (37, '130101', '市辖区', '130100');
INSERT INTO `hat_area` VALUES (38, '130102', '长安区', '130100');
INSERT INTO `hat_area` VALUES (39, '130103', '桥东区', '130100');
INSERT INTO `hat_area` VALUES (40, '130104', '桥西区', '130100');
INSERT INTO `hat_area` VALUES (41, '130105', '新华区', '130100');
INSERT INTO `hat_area` VALUES (42, '130107', '井陉矿区', '130100');

-- ----------------------------
-- Table structure for quartz_task
-- ----------------------------
DROP TABLE IF EXISTS `quartz_task`;
CREATE TABLE `quartz_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255)  NULL DEFAULT NULL COMMENT '任务名称,input,YES',
  `cron` varchar(255)  NULL DEFAULT NULL COMMENT '任务表达式',
  `target_bean` varchar(255)  NULL DEFAULT NULL COMMENT '执行的类',
  `trget_method` varchar(255)  NULL DEFAULT NULL COMMENT '执行方法',
  `params` varchar(255)  NULL DEFAULT NULL COMMENT '执行参数,textarea,YES,false,false,false',
  `status` int(11) NULL DEFAULT NULL COMMENT '任务状态,radio,YES',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = '定时任务,1' ;

-- ----------------------------
-- Records of quartz_task
-- ----------------------------
INSERT INTO `quartz_task` VALUES (1, '同步文章浏览量数据', '0 0 1 * * ?', 'systemTask', 'synchronizationArticleView', '1', 0, '2018-01-26 22:46:23', 1, '2018-01-27 00:59:39', 1, '每天凌晨1点钟执行该任务', 0);
INSERT INTO `quartz_task` VALUES (2, '生成文章搜索索引', '0 0 1 * * ?', 'systemTask', 'createArticleIndex', '1', 0, '2018-01-29 09:50:39', 1, '2018-01-29 09:50:39', 1, '生成文章搜索索引，每天晚上1点执行', 0);

-- ----------------------------
-- Table structure for quartz_task_log
-- ----------------------------
DROP TABLE IF EXISTS `quartz_task_log`;
CREATE TABLE `quartz_task_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_id` bigint(20) NULL DEFAULT NULL COMMENT '任务ID',
  `name` varchar(255)  NULL DEFAULT NULL COMMENT '定时任务名称,input,YES',
  `target_bean` varchar(255)  NULL DEFAULT NULL COMMENT '定制任务执行类',
  `trget_method` varchar(255)  NULL DEFAULT NULL COMMENT '定时任务执行方法',
  `params` varchar(255)  NULL DEFAULT NULL COMMENT '执行参数',
  `status` int(11) NULL DEFAULT NULL COMMENT '任务状态',
  `error` text  NULL COMMENT '异常消息,textarea,YES,false,false,false',
  `times` int(11) NULL DEFAULT NULL COMMENT '执行时间',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = '任务执行日志,1' ;

-- ----------------------------
-- Records of quartz_task_log
-- ----------------------------
INSERT INTO `quartz_task_log` VALUES (1, 2, '执行定时任务【生成文章搜索索引】', 'systemTask', 'createArticleIndex', '1', 0, NULL, 44, '2018-10-29 19:35:52', 1, '2018-10-31 20:30:54', 1, NULL, 1);
INSERT INTO `quartz_task_log` VALUES (2, 2, '执行定时任务【生成文章搜索索引】', 'systemTask', 'createArticleIndex', '1', 0, NULL, 5, '2018-10-29 19:36:30', 1, '2018-10-31 20:32:08', 1, NULL, 1);
INSERT INTO `quartz_task_log` VALUES (3, 2, '执行定时任务【生成文章搜索索引】', 'systemTask', 'createArticleIndex', '1', 0, NULL, 146, '2018-11-02 01:00:00', 1, '2018-11-02 01:00:00', 1, NULL, 0);
INSERT INTO `quartz_task_log` VALUES (4, 1, '执行定时任务【同步文章浏览量数据】', 'systemTask', 'synchronizationArticleView', '1', 0, NULL, 247, '2018-11-02 01:00:00', 1, '2018-11-02 01:00:00', 1, NULL, 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100)  NULL DEFAULT NULL COMMENT '数据值',
  `label` varchar(100)  NULL DEFAULT NULL COMMENT '标签名',
  `type` varchar(100)  NULL DEFAULT NULL COMMENT '类型',
  `description` varchar(100)  NULL DEFAULT NULL COMMENT '描述',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64)  NULL DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64)  NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64)  NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1)  NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`label`) USING BTREE,
  INDEX `sys_dict_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典表,测试表' ;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '0', '否', 'is_or_not', '系统字段', 0, '0', '1', '2018-01-05 20:38:12', '1', '2018-01-05 20:38:15', NULL, '0');
INSERT INTO `sys_dict` VALUES (2, '1', '是', 'is_or_not', '系统字段', 1, '0', '1', '2018-01-05 20:38:40', '1', '2018-01-05 20:38:44', NULL, '0');
INSERT INTO `sys_dict` VALUES (3, 'qiniu', '七牛云存储', 'sys_rescource_source', '系统字段', 0, '0', '1', '2018-01-14 06:39:57', '1', '2018-01-14 06:39:57', NULL, '0');
INSERT INTO `sys_dict` VALUES (4, 'oss', '阿里云存储', 'sys_rescource_source', '系统字段', 1, '0', '1', '2018-01-14 06:39:57', '1', '2018-01-14 06:39:57', NULL, '0');
INSERT INTO `sys_dict` VALUES (108, '2333', '我的标签2', 'you_sign', NULL, 2, '0', '1', '2018-01-16 16:03:12', '1', '2018-01-16 17:52:15', NULL, '0');
INSERT INTO `sys_dict` VALUES (110, '322', '他的标签', 'you_sign', '我', 3, '0', '1', '2018-01-16 17:52:49', '1', '2018-01-16 18:02:37', NULL, '0');
INSERT INTO `sys_dict` VALUES (121, '1', '本站文章', 'blog_article_category', '博客内容-文章类型(此数据为系统自动创建:数据表【blog_article】中的字段【category】对应的值)', 0, '0', '1', '2018-01-17 16:05:45', '1', '2018-01-17 16:05:45', NULL, '0');
INSERT INTO `sys_dict` VALUES (122, '2', '外链', 'blog_article_category', '博客内容-文章类型(此数据为系统自动创建:数据表【blog_article】中的字段【category】对应的值)', 1, '0', '1', '2018-01-17 16:05:45', '1', '2018-01-17 16:05:45', NULL, '0');
INSERT INTO `sys_dict` VALUES (123, '0', '正常', 'quartz_task_status', '定时任务-任务状态(此数据为系统自动创建:数据表【quartz_task】中的字段【status】对应的值)', 0, '0', '1', '2018-01-24 23:41:56', '1', '2018-01-24 23:41:56', NULL, '0');
INSERT INTO `sys_dict` VALUES (124, '1', '暂停', 'quartz_task_status', '定时任务-任务状态(此数据为系统自动创建:数据表【quartz_task】中的字段【status】对应的值)', 1, '0', '1', '2018-01-24 23:41:56', '1', '2018-01-24 23:41:56', NULL, '0');

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40)  NULL DEFAULT NULL COMMENT '分组名称',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父分组ID',
  `level` bigint(2) NULL DEFAULT NULL COMMENT '级别',
  `parent_ids` varchar(2000)  NULL DEFAULT NULL COMMENT '分组序列号',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '分组排序值',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '标记',
  `del_flag` tinyint(2) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   ;

-- ----------------------------
-- Table structure for sys_group_ur
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_ur`;
CREATE TABLE `sys_group_ur`  (
  `group_id` bigint(20) NOT NULL COMMENT '分组ID',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB  ;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(50) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(20)  NULL DEFAULT NULL COMMENT '请求类型',
  `title` varchar(255)  NULL DEFAULT '' COMMENT '日志标题',
  `remote_addr` varchar(255)  NULL DEFAULT NULL COMMENT '操作IP地址',
  `username` varchar(255)  NULL DEFAULT NULL COMMENT '操作用户昵称',
  `request_uri` varchar(255)  NULL DEFAULT NULL COMMENT '请求URI',
  `http_method` varchar(10)  NULL DEFAULT NULL COMMENT '操作方式',
  `class_method` varchar(255)  NULL DEFAULT NULL COMMENT '请求类型.方法',
  `params` text  NULL COMMENT '操作提交的数据',
  `session_id` varchar(255)  NULL DEFAULT NULL COMMENT 'sessionId',
  `response` longtext  NULL COMMENT '返回内容',
  `use_time` bigint(11) NULL DEFAULT NULL COMMENT '方法执行时间',
  `browser` varchar(255)  NULL DEFAULT NULL COMMENT '浏览器信息',
  `area` varchar(255)  NULL DEFAULT NULL COMMENT '地区',
  `province` varchar(255)  NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255)  NULL DEFAULT NULL COMMENT '市',
  `isp` varchar(255)  NULL DEFAULT NULL COMMENT '网络服务提供商',
  `exception` text  NULL COMMENT '异常信息',
  `create_by` varchar(64)  NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(64) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `remarks` varchar(255)  NULL DEFAULT NULL,
  `del_flag` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_log_create_by`(`create_by`) USING BTREE,
  INDEX `sys_log_request_uri`(`request_uri`) USING BTREE,
  INDEX `sys_log_type`(`type`) USING BTREE,
  INDEX `sys_log_create_date`(`create_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统日志' ;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, 'Ajax请求', '用户登录', '127.0.0.1', '我是管理员', 'http://localhost:8080/login/main', 'POST', 'com.mysiteforme.admin.controller.LoginController.loginMain', '[\"org.apache.shiro.web.servlet.ShiroHttpServletRequest@ef1ad26\"]', '8310d5c4-f6ef-4937-8d20-e1c6c6cdb915', '{\"success\":false,\"message\":\"验证码错误\"}', 2, 'Windows-Chrome-63.0.3239.132', NULL, NULL, NULL, NULL, NULL, '1', '2018-02-08 13:52:23', 1, '2018-02-08 13:52:23', NULL, b'0');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40)  NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT '0' COMMENT '父菜单',
  `level` bigint(2) NULL DEFAULT NULL COMMENT '菜单层级',
  `parent_ids` varchar(2000)  NULL DEFAULT NULL COMMENT '父菜单联集',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `href` varchar(2000)  NULL DEFAULT NULL COMMENT '链接地址',
  `target` varchar(20)  NULL DEFAULT NULL COMMENT '打开方式',
  `icon` varchar(100)  NULL DEFAULT NULL COMMENT '菜单图标',
  `bg_color` varchar(255)  NULL DEFAULT NULL COMMENT '显示背景色',
  `is_show` tinyint(2) NULL DEFAULT NULL COMMENT '是否显示',
  `permission` varchar(200)  NULL DEFAULT NULL COMMENT '权限标识',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `remarks` varchar(255)  NULL DEFAULT NULL,
  `del_flag` tinyint(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, '1,', 20, '', NULL, '', NULL, 1, '', 1, '2018-01-16 11:29:46', 1, '2018-01-20 03:09:26', NULL, 0);
INSERT INTO `sys_menu` VALUES (2, '系统用户管理', 1, 2, '1,2,', 9, '/admin/system/user/list', NULL, '', '#47e69c', 1, 'sys:user:list', 1, '2018-01-16 11:31:18', 1, '2018-01-20 05:59:20', NULL, 0);
INSERT INTO `sys_menu` VALUES (3, '系统角色管理', 1, 2, '1,3,', 10, '/admin/system/role/list', NULL, '', '#c23ab9', 1, 'sys:role:list', 1, '2018-01-16 11:32:33', 1, '2018-01-20 05:58:58', NULL, 0);
INSERT INTO `sys_menu` VALUES (4, '系统权限管理', 1, 2, '1,4,', 20, '/admin/system/menu/list', NULL, '', '#d4573b', 1, 'sys:menu:list', 1, '2018-01-16 11:33:19', 1, '2018-02-08 09:49:28', NULL, 0);
INSERT INTO `sys_menu` VALUES (5, '系统资源管理', 1, 2, '1,5,', 30, '/admin/system/rescource/list', NULL, '', '#f5e42a', 1, 'sys:rescource:list', 1, '2018-01-16 11:34:48', 1, '2018-01-20 05:56:35', NULL, 0);
INSERT INTO `sys_menu` VALUES (6, '系统日志管理', 1, 2, '1,6,', 40, '/admin/system/log/list', NULL, '', '#b56c18', 1, 'sys:log:list', 1, '2018-01-16 11:35:31', 1, '2018-01-20 05:12:17', NULL, 0);
INSERT INTO `sys_menu` VALUES (7, '网站基本信息', 1, 2, '1,7,', 50, '/admin/system/site/show', NULL, '', '#95deb9', 1, 'sys:site:list', 1, '2018-01-16 11:36:50', 1, '2018-01-20 05:55:44', NULL, 0);
INSERT INTO `sys_menu` VALUES (8, '数据库管理', 1, 2, '1,8,', 60, '/admin/system/table/list', NULL, '', '#369e16', 1, 'sys:table:list', 1, '2018-01-16 11:38:29', 1, '2018-01-20 03:14:23', NULL, 0);
INSERT INTO `sys_menu` VALUES (9, '系统字典管理', 1, 2, '1,9,', 70, '/admin/system/dict/list', NULL, '', '#1bbcc2', 1, 'sys:dict:list', 1, '2018-01-16 14:51:52', 1, '2018-01-20 03:12:27', NULL, 0);
INSERT INTO `sys_menu` VALUES (10, '博客管理', 0, 1, '10,', 10, '', NULL, '', NULL, 1, '', 1, '2018-01-17 13:21:53', 1, '2018-01-22 00:28:28', NULL, 0);
INSERT INTO `sys_menu` VALUES (11, '栏目管理', 10, 2, '10,11,', 6, '/admin/blogChannel/list', NULL, '', NULL, 1, 'blog:channel:list', 1, '2018-01-17 13:22:57', 1, '2018-02-08 10:20:54', NULL, 0);
INSERT INTO `sys_menu` VALUES (12, '博客评论', 10, 2, '10,12,', 7, '/admin/blogComment/list', NULL, '', '#c8e332', 1, 'blog:comment:list', 1, '2018-01-17 13:23:52', 1, '2018-02-08 10:25:26', NULL, 0);
INSERT INTO `sys_menu` VALUES (13, '博客文章', 10, 2, '10,13,', 8, '/admin/blogArticle/list', NULL, '', '#1962b5', 1, 'blog:article:list', 1, '2018-01-17 16:02:07', 1, '2018-02-08 10:26:13', NULL, 0);
INSERT INTO `sys_menu` VALUES (14, '定时任务', 0, 1, '14,', 5, '', NULL, '', NULL, 1, '', 1, '2018-01-26 22:39:35', 1, '2018-02-08 10:31:05', NULL, 0);
INSERT INTO `sys_menu` VALUES (15, '任务列表', 14, 2, '14,15,', 15, '/admin/quartzTask/list', NULL, '', '#d6d178', 1, 'quartz:task:list', 1, '2018-01-26 22:41:25', 1, '2018-02-08 10:31:11', NULL, 0);
INSERT INTO `sys_menu` VALUES (16, '任务执行日志', 14, 2, '14,16,', 10, '/admin/quartzTaskLog/list', NULL, '', '#5158d6', 1, 'quartz:log:list', 1, '2018-01-27 01:07:11', 1, '2018-02-08 10:37:51', NULL, 0);
INSERT INTO `sys_menu` VALUES (17, '新增字典', 9, 3, '1,9,17,', 0, '', NULL, NULL, NULL, 0, 'sys:dict:add', 1, '2018-02-08 09:39:09', 1, '2018-02-08 09:39:19', NULL, 0);
INSERT INTO `sys_menu` VALUES (18, '编辑字典', 9, 3, '1,9,18,', 10, '', NULL, NULL, NULL, 0, 'sys:dict:edit', 1, '2018-02-08 09:40:37', 1, '2018-02-08 09:40:46', NULL, 0);
INSERT INTO `sys_menu` VALUES (19, '编辑字典类型', 9, 3, '1,9,19,', 20, '', NULL, NULL, NULL, 0, 'sys:dict:editType', 1, '2018-02-08 09:42:46', 1, '2018-02-08 09:54:07', NULL, 0);
INSERT INTO `sys_menu` VALUES (20, '新增系统权限', 4, 3, '1,4,20,', 0, '', NULL, NULL, NULL, 0, 'sys:menu:add', 1, '2018-02-08 09:49:15', 1, '2018-02-08 09:49:38', NULL, 0);
INSERT INTO `sys_menu` VALUES (21, '编辑系统权限', 4, 3, '1,4,21,', 10, '', NULL, NULL, NULL, 0, 'sys:menu:edit', 1, '2018-02-08 09:50:16', 1, '2018-02-08 09:50:25', NULL, 0);
INSERT INTO `sys_menu` VALUES (22, '删除系统权限', 4, 3, '1,4,22,', 20, '', NULL, NULL, NULL, 0, 'sys:menu:delete', 1, '2018-02-08 09:51:53', 1, '2018-02-08 09:53:42', NULL, 0);
INSERT INTO `sys_menu` VALUES (23, '删除系统资源', 5, 3, '1,5,23,', 0, '', NULL, NULL, NULL, 0, 'sys:rescource:delete', 1, '2018-02-08 09:56:44', 1, '2018-02-08 09:56:53', NULL, 0);
INSERT INTO `sys_menu` VALUES (24, '新增系统角色', 3, 3, '1,3,24,', 0, '', NULL, NULL, NULL, 0, 'sys:role:add', 1, '2018-02-08 09:58:11', 1, '2018-02-08 09:58:11', NULL, 0);
INSERT INTO `sys_menu` VALUES (25, '编辑菜单权限', 3, 3, '1,3,25,', 10, '', NULL, NULL, NULL, 0, 'sys:role:edit', 1, '2018-02-08 09:59:01', 1, '2018-02-08 09:59:01', NULL, 0);
INSERT INTO `sys_menu` VALUES (26, '删除角色', 3, 3, '1,3,26,', 20, '', NULL, NULL, NULL, 0, 'sys:role:delete', 1, '2018-02-08 09:59:56', 1, '2018-02-08 09:59:56', NULL, 0);
INSERT INTO `sys_menu` VALUES (27, '编辑系统信息', 7, 3, '1,7,27,', 0, '', NULL, NULL, NULL, 0, 'sys:site:edit', 1, '2018-02-08 10:01:40', 1, '2018-02-08 10:01:40', NULL, 0);
INSERT INTO `sys_menu` VALUES (28, '数据库新增', 8, 3, '1,8,28,', 0, '', NULL, NULL, NULL, 0, 'sys:table:add', 1, '2018-02-08 10:02:51', 1, '2018-02-08 10:02:51', NULL, 0);
INSERT INTO `sys_menu` VALUES (29, '编辑数据库', 8, 3, '1,8,29,', 10, '', NULL, NULL, NULL, 0, 'sys:table:edit', 1, '2018-02-08 10:03:58', 1, '2018-02-08 10:03:58', NULL, 0);
INSERT INTO `sys_menu` VALUES (30, '新增数据库字段', 8, 3, '1,8,30,', 20, '', NULL, NULL, NULL, 0, 'sys:table:addField', 1, '2018-02-08 10:05:11', 1, '2018-02-08 10:05:11', NULL, 0);
INSERT INTO `sys_menu` VALUES (31, '编辑数据库字段', 8, 3, '1,8,31,', 30, '', NULL, NULL, NULL, 0, 'sys:table:editField', 1, '2018-02-08 10:05:47', 1, '2018-02-08 10:05:47', NULL, 0);
INSERT INTO `sys_menu` VALUES (32, '删除数据库字段', 8, 3, '1,8,32,', 40, '', NULL, NULL, NULL, 0, 'sys:table:deleteField', 1, '2018-02-08 10:07:29', 1, '2018-02-08 10:15:39', NULL, 0);
INSERT INTO `sys_menu` VALUES (33, '删除数据库', 8, 3, '1,8,33,', 50, '', NULL, NULL, NULL, 0, 'sys:table:deleteTable', 1, '2018-02-08 10:08:16', 1, '2018-02-08 10:08:16', NULL, 0);
INSERT INTO `sys_menu` VALUES (34, '下载源码', 8, 3, '1,8,34,', 60, '', NULL, NULL, NULL, 0, 'sys:table:download', 1, '2018-02-08 10:09:28', 1, '2018-02-08 10:09:28', NULL, 0);
INSERT INTO `sys_menu` VALUES (35, '新增系统用户', 2, 3, '1,2,35,', 0, '', NULL, NULL, NULL, 0, 'sys:user:add', 1, '2018-02-08 10:10:32', 1, '2018-02-08 10:10:32', NULL, 0);
INSERT INTO `sys_menu` VALUES (36, '编辑系统用户', 2, 3, '1,2,36,', 10, '', NULL, NULL, NULL, 0, 'sys:user:edit', 1, '2018-02-08 10:11:49', 1, '2018-02-08 10:11:49', NULL, 0);
INSERT INTO `sys_menu` VALUES (37, '删除系统用户', 2, 3, '1,2,37,', 20, '', NULL, NULL, NULL, 0, 'sys:user:delete', 1, '2018-02-08 10:12:43', 1, '2018-02-08 10:12:43', NULL, 0);
INSERT INTO `sys_menu` VALUES (38, '新增博客文章', 13, 3, '10,13,38,', 0, '', NULL, NULL, NULL, 0, 'blog:article:add', 1, '2018-02-08 10:16:59', 1, '2018-02-08 10:16:59', NULL, 0);
INSERT INTO `sys_menu` VALUES (39, '博客文章编辑', 13, 3, '10,13,39,', 10, '', NULL, NULL, NULL, 0, 'blog:article:edit', 1, '2018-02-08 10:17:16', 1, '2018-02-08 10:17:16', NULL, 0);
INSERT INTO `sys_menu` VALUES (40, '博客文章删除', 13, 3, '10,13,40,', 20, '', NULL, NULL, NULL, 0, 'blog:article:delete', 1, '2018-02-08 10:17:34', 1, '2018-02-08 10:44:02', NULL, 0);
INSERT INTO `sys_menu` VALUES (41, '博客评论回复', 12, 3, '10,12,41,', 0, '', NULL, NULL, NULL, 0, 'blog:comment:reply', 1, '2018-02-08 10:19:29', 1, '2018-02-08 10:20:33', NULL, 0);
INSERT INTO `sys_menu` VALUES (42, '新增博客栏目', 11, 3, '10,11,42,', 0, '', NULL, NULL, NULL, 0, 'blog:channel:add', 1, '2018-02-08 10:22:06', 1, '2018-02-08 10:22:06', NULL, 0);
INSERT INTO `sys_menu` VALUES (43, '编辑博客栏目', 11, 3, '10,11,43,', 10, '', NULL, NULL, NULL, 0, 'blog:channel:edit', 1, '2018-02-08 10:23:31', 1, '2018-02-08 10:23:31', NULL, 0);
INSERT INTO `sys_menu` VALUES (44, '删除博客栏目', 11, 3, '10,11,44,', 20, '', NULL, NULL, NULL, 0, 'blog:channel:delete', 1, '2018-02-08 10:23:57', 1, '2018-02-08 10:23:57', NULL, 0);
INSERT INTO `sys_menu` VALUES (45, '删除博客评论', 12, 3, '10,12,45,', 10, '', NULL, NULL, NULL, 0, 'blog:comment:delete', 1, '2018-02-08 10:28:48', 1, '2018-02-08 10:28:48', NULL, 0);
INSERT INTO `sys_menu` VALUES (46, '新增定时任务', 15, 3, '14,15,46,', 0, '', NULL, NULL, NULL, 0, 'quartz:task:add', 1, '2018-02-08 10:32:46', 1, '2018-02-08 10:32:46', NULL, 0);
INSERT INTO `sys_menu` VALUES (47, '编辑定时任务', 15, 3, '14,15,47,', 10, '', NULL, NULL, NULL, 0, 'quartz:task:edit', 1, '2018-02-08 10:34:18', 1, '2018-02-08 10:34:18', NULL, 0);
INSERT INTO `sys_menu` VALUES (48, '删除定时任务', 15, 3, '14,15,48,', 20, '', NULL, NULL, NULL, 0, 'quartz:task:delete', 1, '2018-02-08 10:35:07', 1, '2018-02-08 10:35:07', NULL, 0);
INSERT INTO `sys_menu` VALUES (49, '暂停定时任务', 15, 3, '14,15,49,', 30, '', NULL, NULL, NULL, 0, 'quartz:task:paush', 1, '2018-02-08 10:35:43', 1, '2018-02-08 10:35:43', NULL, 0);
INSERT INTO `sys_menu` VALUES (50, '恢复运行任务', 15, 3, '14,15,50,', 40, '', NULL, NULL, NULL, 0, 'quartz:task:resume', 1, '2018-02-08 10:36:26', 1, '2018-02-08 10:36:26', NULL, 0);
INSERT INTO `sys_menu` VALUES (51, '立即执行运行任务', 15, 3, '14,15,51,', 50, '', NULL, NULL, NULL, 0, 'quartz:task:run', 1, '2018-02-08 10:36:55', 1, '2018-02-08 10:36:55', NULL, 0);
INSERT INTO `sys_menu` VALUES (52, '删除定时任务日志', 16, 3, '14,16,52,', 0, '', NULL, NULL, NULL, 0, 'quartz:log:delete', 1, '2018-02-08 10:39:04', 1, '2018-02-08 10:39:04', NULL, 0);
INSERT INTO `sys_menu` VALUES (53, '修改密码', 2, 3, '1,2,53,', 30, '', NULL, '', NULL, 0, 'sys:user:changePassword', 1, '2018-03-15 10:14:06', 1, '2018-03-15 10:14:06', NULL, 0);
INSERT INTO `sys_menu` VALUES (54, '删除字典', 9, 3, '1,9,54,', 30, '', NULL, NULL, NULL, 0, 'sys:dict:delete', 1, '2018-03-15 10:16:55', 1, '2018-03-15 10:16:55', NULL, 0);
INSERT INTO `sys_menu` VALUES (55, '系统日志删除', 6, 3, '1,6,55,', 0, '', NULL, NULL, NULL, 0, 'system:logs:delete', 1, '2018-06-16 04:30:32', 1, '2018-06-16 04:30:32', NULL, 0);
INSERT INTO `sys_menu` VALUES (56, '博客标签', 10, 2, '10,56,', 3, '/admin/blogTags/list', NULL, '', '#c3e8ce', 1, 'blog:tags:list', 1, '2018-06-16 04:42:15', 1, '2018-06-16 04:42:30', NULL, 0);
INSERT INTO `sys_menu` VALUES (57, '博客标签新增', 56, 3, '10,56,57,', 0, '', NULL, NULL, NULL, 0, 'blog:tags:add', 1, '2018-06-16 04:43:02', 1, '2018-06-16 04:43:02', NULL, 0);
INSERT INTO `sys_menu` VALUES (58, '博客标签编辑', 56, 3, '10,56,58,', 10, '', NULL, NULL, NULL, 0, 'blog:tags:edit', 1, '2018-06-16 04:43:26', 1, '2018-06-16 04:43:26', NULL, 0);
INSERT INTO `sys_menu` VALUES (59, '博客标签删除', 56, 3, '10,56,59,', 20, '', NULL, NULL, NULL, 0, 'blog:tags:delete', 1, '2018-06-16 04:43:56', 1, '2018-06-16 04:43:56', NULL, 0);
INSERT INTO `sys_menu` VALUES (60, 'Druid数据监控', 1, 2, '1,60,', 25, '/admin/druid/list', NULL, '', '#7e8755', 1, 'sys:druid:list', 1, '2018-06-16 05:06:17', 1, '2018-06-16 05:06:26', NULL, 0);
INSERT INTO `sys_menu` VALUES (61, '七牛云存储信息', 7, 3, '1,7,61,', 10, '', NULL, NULL, NULL, 0, 'sys:site:editQiniu', 1, '2018-07-12 18:46:39', 1, '2018-07-12 18:46:39', NULL, 0);
INSERT INTO `sys_menu` VALUES (62, '阿里云信息存储', 7, 3, '1,7,62,', 20, '', NULL, NULL, NULL, 0, 'sys:site:editOss', 1, '2018-07-12 18:47:05', 1, '2018-07-12 18:47:05', NULL, 0);
INSERT INTO `sys_menu` VALUES (63, '业务管理', 0, 1, '63,', 30, '/bussiness/data', NULL, '', '#a87439', 1, '', 1, '2018-10-31 17:08:59', 1, '2018-10-31 17:08:59', NULL, 0);
INSERT INTO `sys_menu` VALUES (64, '一级业务', 63, 2, '63,64,', 0, '/bussiness/data/level', NULL, NULL, NULL, 1, '', 1, '2018-10-31 17:09:46', 1, '2018-10-31 17:09:46', NULL, 0);

-- ----------------------------
-- Table structure for sys_rescource
-- ----------------------------
DROP TABLE IF EXISTS `sys_rescource`;
CREATE TABLE `sys_rescource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(255)  NULL DEFAULT NULL COMMENT '文件名称',
  `source` varchar(255)  NULL DEFAULT NULL COMMENT '来源',
  `web_url` varchar(500)  NULL DEFAULT NULL COMMENT '资源网络地址',
  `hash` varchar(255)  NULL DEFAULT NULL COMMENT '文件标识',
  `file_size` varchar(50)  NULL DEFAULT NULL COMMENT '文件大小',
  `file_type` varchar(255)  NULL DEFAULT NULL COMMENT '文件类型',
  `original_net_url` text  NULL  COMMENT '原始网址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  COMMENT = '系统资源' ;

-- ----------------------------
-- Records of sys_rescource
-- ----------------------------
INSERT INTO `sys_rescource` VALUES (2, '77861034-25ef-4856-87ec-24da18398ecf.jpg', 'qiniu', 'https://static.mysiteforme.com/77861034-25ef-4856-87ec-24da18398ecf.jpg', 'FvRpJ_-DlF-eFJ4l28xVGVUsHRCX', '105kb', '.jpg', NULL, '2018-02-08 13:57:44', 1, '2018-02-08 13:57:44', 1, NULL, 0);
INSERT INTO `sys_rescource` VALUES (3, '04ea0ce0-e53b-4eb3-b1ea-dc316063d29f.jpg', 'qiniu', 'https://static.mysiteforme.com/04ea0ce0-e53b-4eb3-b1ea-dc316063d29f.jpg', 'FrJ5IUYX0zMw5HpDW4fUfNvMv4Q3', '329kb', '.jpg', NULL, '2018-02-08 13:59:06', 1, '2018-02-08 13:59:06', 1, NULL, 0);
INSERT INTO `sys_rescource` VALUES (4, '3e2baf40-f2e5-4b3c-93d1-3f97965017ec.jpg', 'qiniu', 'https://static.mysiteforme.com/3e2baf40-f2e5-4b3c-93d1-3f97965017ec.jpg', 'FpgHWPFgcyMX1lqNEDk8Pdnl3lsu', '228kb', '.jpg', NULL, '2018-03-13 12:54:36', 1, '2018-03-13 12:54:36', 1, NULL, 0);

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(255)  NULL DEFAULT NULL COMMENT '文件名称',
  `source` varchar(255)  NULL DEFAULT NULL COMMENT '来源',
  `web_url` varchar(500)  NULL DEFAULT NULL COMMENT '资源网络地址',
  `hash` varchar(255)  NULL DEFAULT NULL COMMENT '文件标识',
  `file_size` varchar(50)  NULL DEFAULT NULL COMMENT '文件大小',
  `file_type` varchar(255)  NULL DEFAULT NULL COMMENT '文件类型',
  `original_net_url` text  NULL,
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   COMMENT = '系统资源' ;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (2, '77861034-25ef-4856-87ec-24da18398ecf.jpg', 'qiniu', 'https://static.mysiteforme.com/77861034-25ef-4856-87ec-24da18398ecf.jpg', 'FvRpJ_-DlF-eFJ4l28xVGVUsHRCX', '105kb', '.jpg', NULL, '2018-02-08 13:57:44', 1, '2018-02-08 13:57:44', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (3, '04ea0ce0-e53b-4eb3-b1ea-dc316063d29f.jpg', 'qiniu', 'https://static.mysiteforme.com/04ea0ce0-e53b-4eb3-b1ea-dc316063d29f.jpg', 'FrJ5IUYX0zMw5HpDW4fUfNvMv4Q3', '329kb', '.jpg', NULL, '2018-02-08 13:59:06', 1, '2018-02-08 13:59:06', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (4, '3e2baf40-f2e5-4b3c-93d1-3f97965017ec.jpg', 'qiniu', 'https://static.mysiteforme.com/3e2baf40-f2e5-4b3c-93d1-3f97965017ec.jpg', 'FpgHWPFgcyMX1lqNEDk8Pdnl3lsu', '228kb', '.jpg', NULL, '2018-03-13 12:54:36', 1, '2018-03-13 12:54:36', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (5, '7ca5f312-e488-4d02-8a78-4e104aa082ee.jpg', 'local', '/static/upload/7ca5f312-e488-4d02-8a78-4e104aa082ee.jpg', 'FvyT6D5Rez6ITqzbwKU4GsADLeHR', '64kb', 'image/jpeg', NULL, '2018-10-31 15:45:37', 1, '2018-10-31 15:45:37', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (6, '56d40648-b81f-4e8a-9ab0-c286cd17f3dc.jpg', 'local', '/static/upload/56d40648-b81f-4e8a-9ab0-c286cd17f3dc.jpg', 'FpNRiUMuVHGQ2lUUyize0QL0lj2N', '58kb', 'image/jpeg', NULL, '2018-10-31 15:45:49', 1, '2018-10-31 15:45:49', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (7, 'c15a2153-8f55-4aef-aa66-13b49c55273f.jpg', 'local', '/static/upload/c15a2153-8f55-4aef-aa66-13b49c55273f.jpg', 'FvWwo33-8GUH8WnRGOsSPm0u5Z3N', '57kb', 'image/jpeg', NULL, '2018-10-31 15:48:25', 1, '2018-10-31 15:48:25', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (8, '8b148605-dbc9-4de8-90dc-268832c0132b.jpg', 'local', '/static/upload/8b148605-dbc9-4de8-90dc-268832c0132b.jpg', 'FqERN-nqNpHcxd7NivQMAAeVRZoZ', '128kb', 'image/jpeg', NULL, '2018-10-31 17:12:39', 1, '2018-10-31 17:12:39', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (9, 'cd225983-ef14-4520-a161-32f74b01e8c0.jpg', 'local', '/static/upload/cd225983-ef14-4520-a161-32f74b01e8c0.jpg', 'FlAqWzkDlDKfMvwafEvyv6TcDFcM', '124kb', 'image/jpeg', NULL, '2018-10-31 17:13:12', 1, '2018-10-31 17:13:12', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (10, '4caa85a7-8f7f-44de-bfc4-54e875e49155.jpg', 'oss', '/upload/4caa85a7-8f7f-44de-bfc4-54e875e49155.jpg', 'FqERN-nqNpHcxd7NivQMAAeVRZoZ', '128kb', '.jpg', NULL, '2018-10-31 17:28:18', 1, '2018-10-31 17:28:18', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (11, '85afa3e3-5b0d-4e7e-99e1-8f11f954d49d.jpg', 'oss', 'http://qingtengjia.oss-cn-beijing.aliyuncs.com/upload/85afa3e3-5b0d-4e7e-99e1-8f11f954d49d.jpg', 'FlAqWzkDlDKfMvwafEvyv6TcDFcM', '124kb', '.jpg', NULL, '2018-10-31 17:41:21', 1, '2018-10-31 17:41:21', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (12, '06423fd7-786b-4252-a079-f710addda284.jpg', 'oss', 'http://qingtengjia.oss-cn-beijing.aliyuncs.com/upload/06423fd7-786b-4252-a079-f710addda284.jpg', 'Fhkdm3doQo_nuXt2nXonB4zmnHF5', '45kb', '.jpg', NULL, '2018-10-31 17:44:02', 1, '2018-10-31 17:44:02', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (13, '33249123-019e-4aab-abd8-ac682a4449be.jpg', 'oss', 'http://qingtengjia.oss-cn-beijing.aliyuncs.comupload/33249123-019e-4aab-abd8-ac682a4449be.jpg', 'FpgID_jADM0ga2nu0vMLxsOHjG_n', '95kb', '.jpg', NULL, '2018-10-31 18:22:43', 1, '2018-10-31 18:22:43', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (14, '68c7305e-52a4-4937-9835-3d2bffcdafdb.jpg', 'oss', 'http://qingtengjia.oss-cn-beijing.aliyuncs.com/upload/68c7305e-52a4-4937-9835-3d2bffcdafdb.jpg', 'FhzGdR6E1JDcnbUaUaW_8UD6EVxt', '50kb', '.jpg', NULL, '2018-10-31 18:40:37', 1, '2018-10-31 18:40:37', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (15, 'd1a5410d-d7c4-4674-a9c2-a016979985d7.jpg', 'oss', 'http://qingtengjia.oss-cn-beijing.aliyuncs.com/upload/d1a5410d-d7c4-4674-a9c2-a016979985d7.jpg', 'FvWwo33-8GUH8WnRGOsSPm0u5Z3N', '57kb', '.jpg', NULL, '2018-10-31 18:41:51', 1, '2018-10-31 18:41:51', 1, NULL, 0);
INSERT INTO `sys_resource` VALUES (16, '44bc6b3a-8ac7-4944-8ccb-4e0e99bfbc0f.jpg', 'oss', 'http://qingtengjia.oss-cn-beijing.aliyuncs.com/upload/44bc6b3a-8ac7-4944-8ccb-4e0e99bfbc0f.jpg', 'FhRCBMHEFPdVeqeojcFphDyxjYLB', '110kb', '.jpg', NULL, '2018-10-31 23:30:37', 1, '2018-10-31 23:30:37', 1, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40)  NULL DEFAULT NULL COMMENT '角色名称',
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_by` bigint(20) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `remarks` varchar(255)  NULL DEFAULT NULL,
  `del_flag` tinyint(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   ;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '老司机', '2017-11-02 14:19:07', 1, '2018-02-08 13:24:35', 1, '', 0);
INSERT INTO `sys_role` VALUES (2, '系统管理员', '2017-11-29 19:36:37', 1, '2018-07-12 18:47:20', 1, '', 0);
INSERT INTO `sys_role` VALUES (3, '测试角色', '2018-10-31 15:40:32', 1, '2018-10-31 15:40:32', 1, '这是一个测试', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB  ;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (2, 8);
INSERT INTO `sys_role_menu` VALUES (2, 9);
INSERT INTO `sys_role_menu` VALUES (2, 10);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 25);
INSERT INTO `sys_role_menu` VALUES (2, 26);
INSERT INTO `sys_role_menu` VALUES (2, 27);
INSERT INTO `sys_role_menu` VALUES (2, 28);
INSERT INTO `sys_role_menu` VALUES (2, 29);
INSERT INTO `sys_role_menu` VALUES (2, 30);
INSERT INTO `sys_role_menu` VALUES (2, 31);
INSERT INTO `sys_role_menu` VALUES (2, 32);
INSERT INTO `sys_role_menu` VALUES (2, 33);
INSERT INTO `sys_role_menu` VALUES (2, 34);
INSERT INTO `sys_role_menu` VALUES (2, 35);
INSERT INTO `sys_role_menu` VALUES (2, 36);
INSERT INTO `sys_role_menu` VALUES (2, 37);
INSERT INTO `sys_role_menu` VALUES (2, 38);
INSERT INTO `sys_role_menu` VALUES (2, 39);
INSERT INTO `sys_role_menu` VALUES (2, 40);
INSERT INTO `sys_role_menu` VALUES (2, 41);
INSERT INTO `sys_role_menu` VALUES (2, 42);
INSERT INTO `sys_role_menu` VALUES (2, 43);
INSERT INTO `sys_role_menu` VALUES (2, 44);
INSERT INTO `sys_role_menu` VALUES (2, 45);
INSERT INTO `sys_role_menu` VALUES (2, 46);
INSERT INTO `sys_role_menu` VALUES (2, 47);
INSERT INTO `sys_role_menu` VALUES (2, 48);
INSERT INTO `sys_role_menu` VALUES (2, 49);
INSERT INTO `sys_role_menu` VALUES (2, 50);
INSERT INTO `sys_role_menu` VALUES (2, 51);
INSERT INTO `sys_role_menu` VALUES (2, 52);
INSERT INTO `sys_role_menu` VALUES (2, 53);
INSERT INTO `sys_role_menu` VALUES (2, 54);
INSERT INTO `sys_role_menu` VALUES (2, 55);
INSERT INTO `sys_role_menu` VALUES (2, 56);
INSERT INTO `sys_role_menu` VALUES (2, 57);
INSERT INTO `sys_role_menu` VALUES (2, 58);
INSERT INTO `sys_role_menu` VALUES (2, 59);
INSERT INTO `sys_role_menu` VALUES (2, 60);
INSERT INTO `sys_role_menu` VALUES (2, 61);
INSERT INTO `sys_role_menu` VALUES (2, 62);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 8);
INSERT INTO `sys_role_menu` VALUES (3, 23);
INSERT INTO `sys_role_menu` VALUES (3, 31);
INSERT INTO `sys_role_menu` VALUES (3, 32);
INSERT INTO `sys_role_menu` VALUES (3, 33);
INSERT INTO `sys_role_menu` VALUES (3, 34);

-- ----------------------------
-- Table structure for sys_site
-- ----------------------------
DROP TABLE IF EXISTS `sys_site`;
CREATE TABLE `sys_site`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255)  NULL DEFAULT NULL,
  `url` varchar(500)  NULL DEFAULT NULL COMMENT '系统网址',
  `open_message` bit(1) NULL DEFAULT NULL COMMENT '是否开放评论',
  `is_no_name` bit(1) NULL DEFAULT NULL COMMENT '是否匿名评论',
  `version` varchar(255)  NULL DEFAULT NULL,
  `author` varchar(255)  NULL DEFAULT NULL,
  `author_icon` varchar(255)  NULL DEFAULT NULL,
  `file_upload_type` varchar(255)  NULL DEFAULT NULL,
  `weibo` varchar(255)  NULL DEFAULT NULL,
  `qq` varchar(255)  NULL DEFAULT NULL,
  `git` varchar(255)  NULL DEFAULT NULL,
  `github` varchar(255)  NULL DEFAULT NULL,
  `phone` varchar(255)  NULL DEFAULT NULL,
  `email` varchar(255)  NULL DEFAULT NULL,
  `address` varchar(255)  NULL DEFAULT NULL,
  `logo` varchar(255)  NULL DEFAULT NULL,
  `server` varchar(255)  NULL DEFAULT NULL,
  `data_base` varchar(255)  NULL DEFAULT NULL,
  `max_upload` int(11) NULL DEFAULT NULL,
  `keywords` varchar(255)  NULL DEFAULT NULL,
  `description` varchar(255)  NULL DEFAULT NULL,
  `powerby` varchar(255)  NULL DEFAULT NULL,
  `record` varchar(255)  NULL DEFAULT NULL,
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_date` datetime(0) NULL DEFAULT NULL,
  `remarks` text  NULL,
  `del_flag` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ;

-- ----------------------------
-- Records of sys_site
-- ----------------------------
INSERT INTO `sys_site` VALUES (1, 'elephant', 'https://mysiteforme.com/', b'1', b'0', '1.0', 'wangl', 'https://static.mysiteforme.com/3c5b69f4-2e39-4f88-b302-a6eb48e4c43a.jpg', 'oss', 'https://weibo.com/u/2173866382', '1115784675', 'https://gitee.com/wanglingxiao/', 'https://github.com/wangl1989', '13776055179', '1115784675@qq.com', '江苏-苏州', 'http://ow9lg82yy.bkt.clouddn.com/24f5c360-485d-4a6d-9468-2a61c353cf37.ico', 'windows', 'mysql', 2, NULL, '网站描述', NULL, '苏ICP备17063650号', 1, '2017-12-30 22:46:15', 1, '2018-07-12 18:49:01', '<p>89年小码农一只,从事java后台开发</p><p><br></p>', b'0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(36)  NULL DEFAULT NULL COMMENT '登录名',
  `nick_name` varchar(40)  NULL DEFAULT NULL COMMENT '昵称',
  `icon` varchar(2000)  NULL DEFAULT NULL COMMENT 'icon',
  `password` varchar(40)  NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(40)  NULL DEFAULT NULL COMMENT 'shiro加密盐',
  `tel` varchar(11)  NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(200)  NULL DEFAULT NULL COMMENT '邮箱地址',
  `locked` tinyint(2) NULL DEFAULT NULL COMMENT '是否锁定',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '标记',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3  COMMENT = '用户表' ;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'test', '管理员', 'http://qingtengjia.oss-cn-beijing.aliyuncs.com/upload/d1a5410d-d7c4-4674-a9c2-a016979985d7.jpg', '810339f5426fe2dcaf92c5cac718acc6471a034b', '3fb62b5aeede1bbf', '13776055179', 'b@qq.com', 0, '2017-11-27 22:19:39', 1, '2018-10-31 19:19:19', 1, '这是测试', 0);
INSERT INTO `sys_user` VALUES (2, 'destiny', 'destiny', NULL, 'e8628edfe1c9c4c5cc3d889edbc54ccb51f5d4e6', 'ba85c3e886c13252', '13849869912', 'xieyue86@163.com', 0, '2018-10-31 15:41:20', 1, '2018-10-31 15:41:20', 1, NULL, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2  COMMENT = '用户角色表' ;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (1, 3);
INSERT INTO `sys_user_role` VALUES (2, 3);

-- ----------------------------
-- Table structure for upload_info
-- ----------------------------
DROP TABLE IF EXISTS `upload_info`;
CREATE TABLE `upload_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `local_window_url` varchar(255)  NULL DEFAULT NULL COMMENT '本地window系统上传路径',
  `local_linux_url` varchar(255)  NULL DEFAULT NULL COMMENT '本地LINUX系统上传路径',
  `qiniu_base_path` varchar(255)  NULL DEFAULT NULL COMMENT '七牛前缀路径',
  `qiniu_bucket_name` varchar(255)  NULL DEFAULT NULL COMMENT '七牛bucket的目录名称',
  `qiniu_dir` varchar(255)  NULL DEFAULT NULL COMMENT '七牛文件存储目录',
  `qiniu_access_key` varchar(255)  NULL DEFAULT NULL COMMENT '七牛qiniuAccess值',
  `qiniu_secret_key` varchar(255)  NULL DEFAULT NULL COMMENT '七牛qiniuKey的值',
  `qiniu_test_access` bit(1) NULL DEFAULT NULL COMMENT '七牛上传测试',
  `oss_base_path` varchar(255)  NULL DEFAULT NULL COMMENT '阿里云前缀路径',
  `oss_bucket_name` varchar(255)  NULL DEFAULT NULL COMMENT '阿里云bucket的目录名称',
  `oss_dir` varchar(255)  NULL DEFAULT NULL COMMENT '阿里云文件上传目录',
  `oss_key_id` varchar(255)  NULL DEFAULT NULL COMMENT '阿里云ACCESS_KEY_ID值',
  `oss_key_secret` varchar(255)  NULL DEFAULT NULL COMMENT '阿里云ACCESS_KEY_SECRET',
  `oss_endpoint` varchar(255)  NULL DEFAULT NULL COMMENT '阿里云ENDPOINT值',
  `oss_test_access` bit(1) NULL DEFAULT NULL COMMENT '阿里云上传测试',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `remarks` varchar(255)  NULL DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2  COMMENT = '文件上传配置';

-- ----------------------------
-- Records of upload_info
-- ----------------------------

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

CREATE TABLE QRTZ_JOB_DETAILS(
SCHED_NAME VARCHAR(120) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
JOB_CLASS_NAME VARCHAR(250) NOT NULL,
IS_DURABLE VARCHAR(1) NOT NULL,
IS_NONCONCURRENT VARCHAR(1) NOT NULL,
IS_UPDATE_DATA VARCHAR(1) NOT NULL,
REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
NEXT_FIRE_TIME BIGINT(13) NULL,
PREV_FIRE_TIME BIGINT(13) NULL,
PRIORITY INTEGER NULL,
TRIGGER_STATE VARCHAR(16) NOT NULL,
TRIGGER_TYPE VARCHAR(8) NOT NULL,
START_TIME BIGINT(13) NOT NULL,
END_TIME BIGINT(13) NULL,
CALENDAR_NAME VARCHAR(200) NULL,
MISFIRE_INSTR SMALLINT(2) NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
REPEAT_COUNT BIGINT(7) NOT NULL,
REPEAT_INTERVAL BIGINT(12) NOT NULL,
TIMES_TRIGGERED BIGINT(10) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_CRON_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
CRON_EXPRESSION VARCHAR(120) NOT NULL,
TIME_ZONE_ID VARCHAR(80),
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
BLOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_CALENDARS (
SCHED_NAME VARCHAR(120) NOT NULL,
CALENDAR_NAME VARCHAR(200) NOT NULL,
CALENDAR BLOB NOT NULL,
PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
ENGINE=InnoDB;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
ENGINE=InnoDB;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
ENTRY_ID VARCHAR(95) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
FIRED_TIME BIGINT(13) NOT NULL,
SCHED_TIME BIGINT(13) NOT NULL,
PRIORITY INTEGER NOT NULL,
STATE VARCHAR(16) NOT NULL,
JOB_NAME VARCHAR(200) NULL,
JOB_GROUP VARCHAR(200) NULL,
IS_NONCONCURRENT VARCHAR(1) NULL,
REQUESTS_RECOVERY VARCHAR(1) NULL,
PRIMARY KEY (SCHED_NAME,ENTRY_ID))
ENGINE=InnoDB;

CREATE TABLE QRTZ_SCHEDULER_STATE (
SCHED_NAME VARCHAR(120) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
CHECKIN_INTERVAL BIGINT(13) NOT NULL,
PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
ENGINE=InnoDB;

CREATE TABLE QRTZ_LOCKS (
SCHED_NAME VARCHAR(120) NOT NULL,
LOCK_NAME VARCHAR(40) NOT NULL,
PRIMARY KEY (SCHED_NAME,LOCK_NAME))
ENGINE=InnoDB;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);


