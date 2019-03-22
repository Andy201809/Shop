
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `admin` VALUES ('1', 'admin', 'tuShOfiBrA8+br7ENrMS8A==');

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `category` VALUES ('1', '手机通讯');
INSERT INTO `category` VALUES ('2', '摄影摄像');
INSERT INTO `category` VALUES ('3', '数码影音');
INSERT INTO `category` VALUES ('4', '智能家居');

DROP TABLE IF EXISTS `indent`;
CREATE TABLE `indent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` float DEFAULT NULL COMMENT '总价',
  `amount` int(11) DEFAULT NULL COMMENT '商品总数',
  `status` tinyint(4) DEFAULT '1' COMMENT '订单状态(1未付款/2已付款/3已发货/4已完成)',
  `paytype` tinyint(4) DEFAULT '0' COMMENT '支付方式 (1微信/2支付宝/3货到付款)',
  `systime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `user_id` int(11) DEFAULT NULL COMMENT '下单用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `items`;
CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float DEFAULT NULL COMMENT '购买时价格',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `indent_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `cover` varchar(255) DEFAULT NULL COMMENT '封面',
  `price` float DEFAULT NULL COMMENT '价格',
  `intro` varchar(255) DEFAULT NULL COMMENT '介绍',
  `stock` int DEFAULT NULL COMMENT '库存',
  `category_id` int(11) DEFAULT NULL COMMENT '分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

INSERT INTO `product` VALUES ('1', '相机1', '/picture/pic1.jpg', '6899.99', '商品简介', '10', '2');
INSERT INTO `product` VALUES ('2', '相机2', '/picture/pic2.jpg', '8871.31', '商品简介', '10', '2');
INSERT INTO `product` VALUES ('3', '相机3', '/picture/pic3.jpg', '1299.85', '商品简介', '10', '2');
INSERT INTO `product` VALUES ('4', '电视1', '/picture/pic4.jpg', '350.88', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('5', '电视2', '/picture/pic5.jpg', '589.88', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('6', '电视3', '/picture/pic6.jpg', '456.28', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('7', '笔记本', '/picture/pic7.jpg', '2552.65', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('8', '智能摄像头1', '/picture/pic8.jpg', '123.45', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('9', '智能摄像头2', '/picture/pic9.jpg', '255.62', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('10', '音响组合', '/picture/pic10.jpg', '514.05', '商品简介', '10', '3');
INSERT INTO `product` VALUES ('11', '智能电器', '/picture/pic11.jpg', '99.99', '商品简介', '10', '4');
INSERT INTO `product` VALUES ('12', '智能风扇', '/picture/pic12.jpg', '79.99', '商品简介', '10', '4');
INSERT INTO `product` VALUES ('13', '智能冰箱1', '/picture/pic13.jpg', '126.66', '商品简介', '10', '4');
INSERT INTO `product` VALUES ('14', '智能冰箱2', '/picture/pic14.jpg', '523.87', '商品简介', '10', '4');
INSERT INTO `product` VALUES ('15', '智能水果机', '/picture/pic15.jpg', '98.54', '商品简介', '10', '4');
INSERT INTO `product` VALUES ('16', '智能蔬菜机', '/picture/pic16.jpg', '122.17', '商品简介', '10', '4');
INSERT INTO `product` VALUES ('17', '苹果手机', '/picture/pic17.jpg', '566.98', '商品简介', '10', '1');

DROP TABLE IF EXISTS `product_new`;
CREATE TABLE `product_new` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `product_new` VALUES ('1', '11');
INSERT INTO `product_new` VALUES ('2', '5');
INSERT INTO `product_new` VALUES ('3', '8');
INSERT INTO `product_new` VALUES ('4', '13');

DROP TABLE IF EXISTS `product_sale`;
CREATE TABLE `product_sale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `discount` tinyint(4) DEFAULT '100' COMMENT '折扣(x%)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `product_sale` VALUES ('1', '2', '92');
INSERT INTO `product_sale` VALUES ('2', '7', '82');
INSERT INTO `product_sale` VALUES ('3', '10', '89');
INSERT INTO `product_sale` VALUES ('4', '16', '79');

DROP TABLE IF EXISTS `product_show`;
CREATE TABLE `product_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `showtext` varchar(255) DEFAULT NULL COMMENT '展示图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

INSERT INTO `product_show` VALUES ('1', '17', '推荐理由');
INSERT INTO `product_show` VALUES ('2', '4', '推荐理由');
INSERT INTO `product_show` VALUES ('3', '1', '推荐理由');
INSERT INTO `product_show` VALUES ('4', '6', '推荐理由');
INSERT INTO `product_show` VALUES ('5', '7', '推荐理由');
INSERT INTO `product_show` VALUES ('6', '9', '推荐理由');
INSERT INTO `product_show` VALUES ('7', '12', '推荐理由');
INSERT INTO `product_show` VALUES ('8', '16', '推荐理由');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '登录密码',
  `payword` varchar(255) DEFAULT NULL COMMENT '支付密码',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `users` VALUES ('1', '1', 'iUOoOdMAl7FsB1Kig37hmg==', 'iUOoOdMAl7FsB1Kig37hmg==', '12312341234', '北京北京北京');


DROP TABLE IF EXISTS `shopcart`;
CREATE TABLE `shopcart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `product_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;