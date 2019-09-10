/*
Navicat MySQL Data Transfer

Source Server         : huwei
Source Server Version : 50645
Source Host           : localhost:3306
Source Database       : huwei

Target Server Type    : MYSQL
Target Server Version : 50645
File Encoding         : 65001

Date: 2019-09-10 16:22:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `class`
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '新闻分类ID',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_id` int(32) DEFAULT '0' COMMENT '父类Id',
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标识,0未删除，1删除',
  `order` int(32) DEFAULT '1' COMMENT '排序字段',
  `open_flag` int(1) DEFAULT '0' COMMENT '是否开放,0未开放，1开放',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(32) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_user_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('1', '江西省', '0', '0', '21', '1', '2019-09-04 15:43:33', null, '2019-09-04 14:53:10', '1');
INSERT INTO `class` VALUES ('2', '南昌市', '1', '0', '22', '1', '2019-09-04 15:43:37', null, '2019-09-04 15:22:10', '1');
INSERT INTO `class` VALUES ('3', '一级表单', '0', '1', null, null, '2019-09-04 15:43:40', null, '2019-09-04 11:26:11', '2');
INSERT INTO `class` VALUES ('32', '九江市', '1', '0', '1', '0', '2019-09-04 16:40:09', null, null, null);
INSERT INTO `class` VALUES ('33', '湖南省', '0', '0', '1', '0', '2019-09-04 16:44:23', null, null, null);
INSERT INTO `class` VALUES ('34', '山东省', '0', '0', '30', '0', '2019-09-05 09:17:52', null, null, null);
INSERT INTO `class` VALUES ('35', '陕西省', '0', '0', '1', '0', '2019-09-05 09:19:57', null, null, null);
INSERT INTO `class` VALUES ('36', '山西省', '0', '0', '20', '1', '2019-09-05 09:22:07', null, null, null);
INSERT INTO `class` VALUES ('37', '新建区', '2', '0', '1', '1', '2019-09-06 09:12:11', null, null, null);

-- ----------------------------
-- Table structure for `dept`
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `dept_id` int(32) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sort` int(32) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(32) DEFAULT '0',
  `parent_id` int(32) DEFAULT '0',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', '研发中心', '30', '2019-09-10 09:51:33', null, '0', '0');
INSERT INTO `dept` VALUES ('2', '产品中心', '0', '2019-09-10 09:56:08', '2019-09-10 10:08:51', '0', '0');
INSERT INTO `dept` VALUES ('5', 'Java研发部', '0', '2019-09-10 10:02:07', null, '0', '1');
INSERT INTO `dept` VALUES ('6', 'Android研发部', '10', '2019-09-10 10:02:26', '2019-09-10 10:07:02', '0', '1');
INSERT INTO `dept` VALUES ('7', '测试部', '0', '2019-09-10 10:02:38', '2019-09-10 10:07:13', '0', '2');
INSERT INTO `dept` VALUES ('8', '非学历教育事业部', '0', '2019-09-10 10:03:07', '2019-09-10 10:09:39', '1', '0');
INSERT INTO `dept` VALUES ('9', '云阳文化', '0', '2019-09-10 10:09:30', '2019-09-10 10:09:39', '1', '8');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '前端url',
  `parentId` int(32) DEFAULT NULL,
  `icon` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `component` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'vue页面',
  `sort` int(32) DEFAULT '0' COMMENT '排序值',
  `type` int(32) DEFAULT NULL COMMENT '菜单类型，0为菜单，1为按钮',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(32) DEFAULT '0' COMMENT '0--正常，1--删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '权限管理', null, '0', 'glyphicon glyphicon-lock', null, '0', null, '2019-09-10 15:35:56', null, '0');
INSERT INTO `menu` VALUES ('2', '角色管理', 'roleTable.html', '1', 'glyphicon glyphicon-pawn', null, '20', null, null, '2019-09-09 17:28:48', '0');
INSERT INTO `menu` VALUES ('3', '用户管理', 'user-tables.html', '1', 'glyphicon glyphicon-user', null, '0', null, null, null, '0');
INSERT INTO `menu` VALUES ('4', '新闻管理', null, '0', 'glyphicon glyphicon-list-alt', null, '20', null, null, null, '0');
INSERT INTO `menu` VALUES ('5', '新闻管理', 'news-tables.html', '4', 'glyphicon glyphicon-file', null, '0', null, null, null, '0');
INSERT INTO `menu` VALUES ('6', '新闻分类管理', 'newsClass.html', '4', 'glyphicon glyphicon-th-list', null, '0', null, null, null, '0');
INSERT INTO `menu` VALUES ('7', '菜单管理', 'menu.html', '1', 'glyphicon glyphicon-list', null, '0', null, null, null, '0');
INSERT INTO `menu` VALUES ('9', '测试顶级', '当前为顶级菜单', '0', null, null, '1', null, '2019-09-07 09:56:37', '2019-09-07 10:12:18', '1');
INSERT INTO `menu` VALUES ('10', '测试子级', '', '9', null, null, '10', null, '2019-09-07 09:59:51', '2019-09-07 10:12:18', '1');
INSERT INTO `menu` VALUES ('11', '子级2', '', '9', null, null, '10', null, '2019-09-07 10:10:44', '2019-09-07 10:12:18', '1');
INSERT INTO `menu` VALUES ('12', '部门管理', '', '0', null, null, '5', null, '2019-09-09 17:12:53', '2019-09-09 17:29:08', '0');
INSERT INTO `menu` VALUES ('13', '部门管理', 'dept-table.html', '1', 'glyphicon glyphicon-menu-hamburger', null, '0', null, '2019-09-09 17:14:42', '2019-09-09 17:31:28', '0');

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` int(11) NOT NULL AUTO_INCREMENT,
  `news_title` varchar(255) DEFAULT NULL,
  `news_tag` varchar(32) DEFAULT NULL,
  `news_content` mediumtext,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('3', '泰王室罕见公布新王妃私照 流量太高王室网', '娱乐', '<p>泰国王室昨天（26日）罕见公布34岁的泰王贵妃诗妮娜的一组照片和个人介绍，包括练枪打靶、驾驶飞机、跳伞和夫妇合影，其英姿飒爽的形象瞬间爆红网络，导致王室网站一度崩溃。</p>', '2019-08-28 17:31:09', '胡伟');
INSERT INTO `news` VALUES ('12', 'formTitle', 'formTag', '测试修改和删除功能', '2019-08-29 11:07:28', 'huwei');
INSERT INTO `news` VALUES ('17', '', null, '<p><br></p>', '2019-08-30 16:13:17', null);
INSERT INTO `news` VALUES ('20', '富文本标题', '富文本标签', '<p>富文本内容</p>\n', '2019-08-30 16:18:52', 'huwei');
INSERT INTO `news` VALUES ('22', '上传图片开始', '标签', '<p><img alt=\"\" src=\"/file/uploadImage/cun.jpg\" style=\"height:376px; width:371px\" /></p>\n', '2019-08-30 17:55:02', 'huwei');
INSERT INTO `news` VALUES ('23', '这是测试数据库的时间是否正确', '', '<p>用来测试数据插入数据库时，时间是否正确</p>\n', '2019-09-02 17:40:57', 'huwei');
INSERT INTO `news` VALUES ('24', '更换格式之后的新闻', '新闻', '<p>人在院内走，狗从背后来，两只狗打架，大爷被&ldquo;撞翻&rdquo;。</p>\n\n<p>8月25日晚上7时许，68岁的陈大爷吃完晚饭，就在自家小区内散步，突然两只小狗从他的身后跑来，将陈大爷撞倒在地致大爷左肩、胯骨等处骨折。</p>\n\n<p><img src=\"https://pics4.baidu.com/feed/b3119313b07eca801454c7ad5845f5d8a0448397.png?token=2e4c3c4ec7ad81e545b5b358b70ba847&amp;s=7699728943D130696C019C0F03007041\" /></p>\n\n<p>8月25日晚上7时许，68岁的陈大爷吃完晚饭，就在自家小区内散步。 一只小狗从他的身后跑来，同一时间，左侧的绿化带里又窜出另外一只小狗，将陈大爷撞倒在地后，两只小狗打成一团。</p>\n\n<p>随后，陈大爷被送医治疗，&nbsp;经过诊断，左肩、胯骨等处骨折，前期手术治疗已预付了3万余元，后续还需进行牵引等治疗。原本和睦的小区邻里，也因为治疗费等问题闹得很不愉快。</p>\n\n<p>一次意外</p>\n\n<p>68岁老人院内散步被邻居家的狗撞倒</p>\n\n<p>&ldquo;快下来，你老伴被狗撞倒了。&rdquo;</p>\n\n<p>8月25日晚上7点过，家住郫都区成都后花园小区的谢女士吃了晚饭，正在家里陪小孙女写作业，突然就接到了老伴打来的电话。但电话那头的人，却不是老伴。&ldquo;应该是好心邻居帮忙打的，喊我快点下去看看。&rdquo;</p>\n\n<p>谢女士立即跑到楼下，就看到&nbsp;老伴昏倒在小区绿化带之间的人行道上，周围已经围了不少人。旁边还有两个邻居，牵着自家的狗。而这两只狗，就是导致谢女士老伴摔倒的&ldquo;罪魁祸首&rdquo;。小区内的监控录像，还原了事发经过。</p>\n\n<p>原来，晚上6点多，谢女士的老伴陈大爷像往常一样，到楼下小区内散步。刚在院子里转了2、3圈，就有一只未拴狗绳的小狗从他的背后跑来，小狗的跑动引起一旁绿化带里另一只小狗的注意，它也迅速从左侧窜了出来，&nbsp;尽管主人用狗绳牵着，但是由于绳子较长，小狗距离陈大爷很近，一瞬间就将陈大爷撞倒在地。随后，两只小狗打成一团，又被主人拉开。</p>\n\n<p>但倒地的陈大爷，却怎么也站不起来了。了解了事发经过，谢女士又生气又心疼，好在周围热心邻居和狗主人一起，拨打了120急救电话。不一会儿，陈大爷就被赶来的医护人员送往附近的郫都区中西医结合医院。&nbsp;经检查，陈大爷左肩和胯部骨折，其中左肩情况较为严重，需手术治疗，医院建议陈大爷转院</p>\n', '2019-09-03 15:33:24', 'huwei');
INSERT INTO `news` VALUES ('25', '', '', '<p>人在院内走，狗从背后来，两只狗打架，大爷被&ldquo;撞翻&rdquo;。</p>\n\n<p>8月25日晚上7时许，68岁的陈大爷吃完晚饭，就在自家小区内散步，突然两只小狗从他的身后跑来，将陈大爷撞倒在地致大爷左肩、胯骨等处骨折。</p>\n\n<p><img src=\"https://pics4.baidu.com/feed/b3119313b07eca801454c7ad5845f5d8a0448397.png?token=2e4c3c4ec7ad81e545b5b358b70ba847&amp;s=7699728943D130696C019C0F03007041\" /></p>\n\n<p>8月25日晚上7时许，68岁的陈大爷吃完晚饭，就在自家小区内散步。 一只小狗从他的身后跑来，同一时间，左侧的绿化带里又窜出另外一只小狗，将陈大爷撞倒在地后，两只小狗打成一团。</p>\n\n<p>随后，陈大爷被送医治疗，&nbsp;经过诊断，左肩、胯骨等处骨折，前期手术治疗已预付了3万余元，后续还需进行牵引等治疗。原本和睦的小区邻里，也因为治疗费等问题闹得很不愉快。</p>\n\n<p>一次意外</p>\n\n<p>68岁老人院内散步被邻居家的狗撞倒</p>\n\n<p>&ldquo;快下来，你老伴被狗撞倒了。&rdquo;</p>\n\n<p>8月25日晚上7点过，家住郫都区成都后花园小区的谢女士吃了晚饭，正在家里陪小孙女写作业，突然就接到了老伴打来的电话。但电话那头的人，却不是老伴。&ldquo;应该是好心邻居帮忙打的，喊我快点下去看看。&rdquo;</p>\n\n<p>谢女士立即跑到楼下，就看到&nbsp;老伴昏倒在小区绿化带之间的人行道上，周围已经围了不少人。旁边还有两个邻居，牵着自家的狗。而这两只狗，就是导致谢女士老伴摔倒的&ldquo;罪魁祸首&rdquo;。小区内的监控录像，还原了事发经过。</p>\n\n<p>原来，晚上6点多，谢女士的老伴陈大爷像往常一样，到楼下小区内散步。刚在院子里转了2、3圈，就有一只未拴狗绳的小狗从他的背后跑来，小狗的跑动引起一旁绿化带里另一只小狗的注意，它也迅速从左侧窜了出来，&nbsp;尽管主人用狗绳牵着，但是由于绳子较长，小狗距离陈大爷很近，一瞬间就将陈大爷撞倒在地。随后，两只小狗打成一团，又被主人拉开。</p>\n\n<p>但倒地的陈大爷，却怎么也站不起来了。了解了事发经过，谢女士又生气又心疼，好在周围热心邻居和狗主人一起，拨打了120急救电话。不一会儿，陈大爷就被赶来的医护人员送往附近的郫都区中西医结合医院。&nbsp;经检查，陈大爷左肩和胯部骨折，其中左肩情况较为严重，需手术治疗，医院建议陈大爷转院</p>\n', '2019-09-03 15:35:12', 'huwei');
INSERT INTO `news` VALUES ('26', '', '', '<p><img alt=\"\" src=\"/file/uploadImage/cun.jpg\" style=\"height:376px; width:371px\" /></p>\n', null, null);
INSERT INTO `news` VALUES ('27', '', '', '', null, null);
INSERT INTO `news` VALUES ('28', '', '', '', null, null);
INSERT INTO `news` VALUES ('29', '', '', '', null, null);
INSERT INTO `news` VALUES ('30', '', '', '<p><br></p>', null, null);
INSERT INTO `news` VALUES ('31', 'hua的新闻', '', '<p>这是花的新闻</p>', null, 'hua');
INSERT INTO `news` VALUES ('32', '', '', '', null, null);
INSERT INTO `news` VALUES ('33', '测试路径', '', '', null, null);
INSERT INTO `news` VALUES ('34', '测试路径', '', '', null, null);
INSERT INTO `news` VALUES ('35', '测试路径', '', '', null, null);
INSERT INTO `news` VALUES ('36', '', '', '', null, null);
INSERT INTO `news` VALUES ('37', '', '', '', null, null);
INSERT INTO `news` VALUES ('38', '', '', '', null, null);
INSERT INTO `news` VALUES ('39', '', '', '', null, null);
INSERT INTO `news` VALUES ('40', '', '', '', null, null);
INSERT INTO `news` VALUES ('41', '', '', '', null, null);
INSERT INTO `news` VALUES ('42', '', '', '', null, null);
INSERT INTO `news` VALUES ('43', '', '', '', null, null);
INSERT INTO `news` VALUES ('44', '', '', '', null, null);
INSERT INTO `news` VALUES ('45', '', '', '', null, null);
INSERT INTO `news` VALUES ('46', '', '', '', null, null);
INSERT INTO `news` VALUES ('47', '', '', '', null, null);
INSERT INTO `news` VALUES ('48', '', '', '', null, null);
INSERT INTO `news` VALUES ('49', '', '', '', null, null);
INSERT INTO `news` VALUES ('50', '', '', '', null, null);
INSERT INTO `news` VALUES ('51', '', '', '', null, null);
INSERT INTO `news` VALUES ('53', '测试按2', '', '<p>222</p>', null, null);
INSERT INTO `news` VALUES ('55', '测试按钮', '', '', null, null);
INSERT INTO `news` VALUES ('56', '测试按钮', '', '', null, null);
INSERT INTO `news` VALUES ('57', '测试', '测试路径', '', null, null);

-- ----------------------------
-- Table structure for `news_class`
-- ----------------------------
DROP TABLE IF EXISTS `news_class`;
CREATE TABLE `news_class` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `news_id` int(32) DEFAULT NULL,
  `news_class_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of news_class
-- ----------------------------
INSERT INTO `news_class` VALUES ('1', '29', '2');
INSERT INTO `news_class` VALUES ('23', '30', '36');
INSERT INTO `news_class` VALUES ('24', '30', '33');
INSERT INTO `news_class` VALUES ('33', '32', '1');
INSERT INTO `news_class` VALUES ('34', '32', '36');
INSERT INTO `news_class` VALUES ('35', '37', '2');
INSERT INTO `news_class` VALUES ('36', '36', '2');
INSERT INTO `news_class` VALUES ('37', '42', '2');
INSERT INTO `news_class` VALUES ('38', '42', '1');
INSERT INTO `news_class` VALUES ('39', '43', '2');
INSERT INTO `news_class` VALUES ('40', '43', '1');
INSERT INTO `news_class` VALUES ('41', '43', '36');
INSERT INTO `news_class` VALUES ('42', '44', '2');
INSERT INTO `news_class` VALUES ('43', '44', '32');
INSERT INTO `news_class` VALUES ('44', '44', '37');
INSERT INTO `news_class` VALUES ('45', '45', '37');
INSERT INTO `news_class` VALUES ('46', '46', '34');
INSERT INTO `news_class` VALUES ('47', '47', '2');
INSERT INTO `news_class` VALUES ('48', '47', '1');
INSERT INTO `news_class` VALUES ('49', '47', '36');
INSERT INTO `news_class` VALUES ('50', '47', '37');
INSERT INTO `news_class` VALUES ('51', '48', '33');
INSERT INTO `news_class` VALUES ('52', '48', '37');
INSERT INTO `news_class` VALUES ('53', '49', '34');
INSERT INTO `news_class` VALUES ('54', '49', '37');
INSERT INTO `news_class` VALUES ('55', '50', '1');
INSERT INTO `news_class` VALUES ('56', '50', '37');
INSERT INTO `news_class` VALUES ('74', '53', '1');
INSERT INTO `news_class` VALUES ('86', '55', '32');
INSERT INTO `news_class` VALUES ('87', '56', '1');
INSERT INTO `news_class` VALUES ('88', '57', '1');
INSERT INTO `news_class` VALUES ('89', '57', '33');
INSERT INTO `news_class` VALUES ('90', '3', '1');
INSERT INTO `news_class` VALUES ('91', '31', '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(32) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_code` int(32) DEFAULT NULL,
  `role_desc` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int(32) DEFAULT '0' COMMENT '0--正常，1--删除',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '123', '12321', '12312', '2019-09-07 14:19:13', '2019-09-09 10:25:06', '1');
INSERT INTO `role` VALUES ('2', '管理员', '101', '最高权限者，拥有管理平台所有的权限', '2019-09-07 14:20:43', '2019-09-10 11:50:12', '0');
INSERT INTO `role` VALUES ('3', '12322', '123', '123', '2019-09-07 14:22:33', '2019-09-09 10:24:51', '1');
INSERT INTO `role` VALUES ('4', '233', '23', '23', '2019-09-07 14:23:01', null, '0');
INSERT INTO `role` VALUES ('5', '123123', '123123', '123213', '2019-09-07 14:23:53', null, '0');
INSERT INTO `role` VALUES ('6', '123123', '123123', '123123', '2019-09-07 14:26:46', null, '0');
INSERT INTO `role` VALUES ('7', '123', '123', '123', '2019-09-07 14:27:25', null, '0');
INSERT INTO `role` VALUES ('8', '123213', '123123', '123213', '2019-09-07 14:27:56', null, '0');
INSERT INTO `role` VALUES ('9', '312312312', '123', '123', '2019-09-07 15:05:53', null, '0');
INSERT INTO `role` VALUES ('10', '新增角色测试', '213123', '123123', '2019-09-09 10:30:15', '2019-09-10 11:50:33', '0');
INSERT INTO `role` VALUES ('11', '新增角色22', '123', '用于测试新增角色功能点', '2019-09-09 10:31:16', '2019-09-09 10:37:16', '1');
INSERT INTO `role` VALUES ('13', 'Android研发人员', null, '', '2019-09-10 11:05:40', '2019-09-10 11:23:29', '1');
INSERT INTO `role` VALUES ('14', 'java开发人员', null, '', '2019-09-10 11:25:15', '2019-09-10 11:40:19', '1');

-- ----------------------------
-- Table structure for `role_dept`
-- ----------------------------
DROP TABLE IF EXISTS `role_dept`;
CREATE TABLE `role_dept` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `role_id` int(32) DEFAULT NULL,
  `dept_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of role_dept
-- ----------------------------
INSERT INTO `role_dept` VALUES ('5', '2', '1');
INSERT INTO `role_dept` VALUES ('6', '10', '2');

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `role_id` int(32) DEFAULT NULL,
  `menu_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('22', '10', '1');
INSERT INTO `role_menu` VALUES ('23', '10', '6');
INSERT INTO `role_menu` VALUES ('27', '2', '1');
INSERT INTO `role_menu` VALUES ('28', '2', '6');
INSERT INTO `role_menu` VALUES ('29', '7', '1');
INSERT INTO `role_menu` VALUES ('30', '7', '2');
INSERT INTO `role_menu` VALUES ('31', '2', '2');
INSERT INTO `role_menu` VALUES ('32', '2', '3');
INSERT INTO `role_menu` VALUES ('33', '2', '4');
INSERT INTO `role_menu` VALUES ('34', '2', '5');
INSERT INTO `role_menu` VALUES ('36', '2', '7');
INSERT INTO `role_menu` VALUES ('41', '2', '13');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `note` varchar(255) DEFAULT '''''' COMMENT '备注',
  `del_flag` int(32) DEFAULT '0' COMMENT '删除的标识，1表示删除，0表示未删除',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'hw', '1234', '2019-08-28 16:09:20', '2019-09-10 11:51:42', '\'\'', '0');
INSERT INTO `user` VALUES ('3', 'huwei2', '1234', '2019-08-29 14:30:01', '2019-09-09 15:44:29', '\'\'', '1');
INSERT INTO `user` VALUES ('4', 'huwei', '123', '2019-08-30 10:01:11', '2019-09-09 15:44:33', '\'\'', '0');
INSERT INTO `user` VALUES ('33', 'hwei', '123', '2019-09-03 16:27:22', '2019-09-09 15:44:36', '\'\'', '0');
INSERT INTO `user` VALUES ('35', 'root', '123', '2019-09-09 14:57:25', '2019-09-09 16:56:08', '\'\'', '0');
INSERT INTO `user` VALUES ('39', '用户五', '321', '2019-09-09 16:54:55', '2019-09-09 16:54:57', '第五个用户', '1');
INSERT INTO `user` VALUES ('40', 'hua', '123', '2019-09-10 11:25:52', '2019-09-10 11:33:54', 'java开发人员', '0');
INSERT INTO `user` VALUES ('41', 'king', '123', '2019-09-10 11:37:13', '2019-09-10 11:37:20', '', '1');
INSERT INTO `user` VALUES ('42', 'king', '123', '2019-09-10 11:37:34', '2019-09-10 11:39:29', '', '1');
INSERT INTO `user` VALUES ('43', 'king', '123', '2019-09-10 11:39:53', null, '', '0');
INSERT INTO `user` VALUES ('44', 'tom', '123', '2019-09-10 16:00:11', null, '', '0');
INSERT INTO `user` VALUES ('45', 'jerry', '', '2019-09-10 16:00:24', null, '', '0');
INSERT INTO `user` VALUES ('46', '小强', '123', '2019-09-10 16:00:49', null, '', '0');
INSERT INTO `user` VALUES ('47', '大王', '123', '2019-09-10 16:01:00', null, '', '0');
INSERT INTO `user` VALUES ('48', '小王', '123', '2019-09-10 16:01:10', null, '', '0');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_id` int(32) DEFAULT NULL,
  `role_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('6', '35', '2');
INSERT INTO `user_role` VALUES ('10', '2', '4');
INSERT INTO `user_role` VALUES ('11', '44', '6');
INSERT INTO `user_role` VALUES ('12', '44', '10');
INSERT INTO `user_role` VALUES ('13', '45', '4');
INSERT INTO `user_role` VALUES ('14', '46', '4');
INSERT INTO `user_role` VALUES ('15', '47', '4');
INSERT INTO `user_role` VALUES ('16', '48', '5');
