SET FOREIGN_KEY_CHECKS=0;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `cg_master_resource_category`;
CREATE TABLE `cg_master_resource_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭id',
  `org_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '鏁版嵁闅旂缁勭粐id',
  `org_tag` varchar(100) DEFAULT NULL COMMENT '缁勭粐鏍囪瘑',
  `pid` varchar(100) NOT NULL COMMENT '鐖惰妭鐐�',
  `name` varchar(100) NOT NULL COMMENT '鍚嶅瓧',
  `field` varchar(100) DEFAULT NULL COMMENT '鍒嗙粍鏍囪瘑瀛楁',
  `description` varchar(200) DEFAULT NULL COMMENT '璇存槑',
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cg_master_resource`;
CREATE TABLE `cg_master_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭id',
  `org_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '鏁版嵁闅旂缁勭粐id',
  `org_tag` varchar(100) DEFAULT NULL COMMENT '缁勭粐鏍囪瘑',
  `name` varchar(100) NOT NULL COMMENT '璧勬簮鍚嶅瓧',
  `status` varchar(26) NOT NULL COMMENT '鐘舵�乕OPEN,SUBMITTED,APPROVED,REJECTED,CLOSED]',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `category_id` bigint(20) DEFAULT NULL COMMENT '璧勬簮鍒嗙被鍏宠仈ID',
  `user_id` bigint(20) DEFAULT 0 COMMENT '璧勬簮褰掑睘鐢ㄦ埛',
  `owner_id` bigint(20) DEFAULT 0 COMMENT '璧勬簮褰掑睘鐢ㄦ埛鎴栫敤[owner_id]',
  `code` varchar(100) DEFAULT NULL COMMENT '璧勬簮鍞竴缂栧彿',
  `title` varchar(100) DEFAULT NULL COMMENT '璧勬簮鏍囬',
  `registered_time` datetime DEFAULT NULL COMMENT '娉ㄥ唽鏃堕棿',
  `start_time` datetime DEFAULT NULL COMMENT '寮�濮嬫椂闂�',
  `end_time` datetime DEFAULT NULL COMMENT '缁撴潫鏃堕棿',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '鏇存柊鏃堕棿',
  `closed_time` datetime DEFAULT NULL COMMENT '鍏抽棴鏃堕棿',
  `expired_time` datetime DEFAULT NULL COMMENT '杩囨湡鏃堕棿',
  `appointed_time` datetime DEFAULT NULL COMMENT '棰勭害鏃堕棿',
  `invalid` smallint DEFAULT 0 COMMENT '鏄惁鏃犳晥',
  `description` varchar(200) DEFAULT NULL COMMENT '璇存槑',
  `note` text DEFAULT NULL COMMENT '澶囨敞',
  PRIMARY KEY (`id`),
  UNIQUE (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cg_master_resource_item`;
CREATE TABLE `cg_master_resource_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭id',
  `name` varchar(100) NOT NULL COMMENT '鍚嶅瓧',
  `master_id` bigint(20) NOT NULL COMMENT '涓讳綋ID',
  `master_field` varchar(100) DEFAULT NULL COMMENT '鍏宠仈涓讳綋瀛楁',
  `title` varchar(100) DEFAULT NULL COMMENT '鏍囬',
  `amount` int DEFAULT NULL COMMENT '鏁伴噺',
  `price` DECIMAL(10,2) DEFAULT NULL COMMENT '浠锋牸',
  `color` varchar(26) DEFAULT NULL COMMENT '棰滆壊',
  `size` varchar(26) DEFAULT NULL COMMENT '灏哄',
  `description` varchar(200) DEFAULT NULL COMMENT '璇存槑',
  `invalid` smallint DEFAULT 0 COMMENT '鏄惁鏃犳晥',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cg_master_resource_record`;
CREATE TABLE `cg_master_resource_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭id',
  `name` varchar(100) NOT NULL COMMENT '鍚嶅瓧',
  `other_id` bigint(20) NOT NULL COMMENT '涓讳綋ID',
  `master_field` varchar(100) DEFAULT NULL COMMENT '鍏宠仈涓讳綋瀛楁',
  `title` varchar(100) DEFAULT NULL COMMENT '鏍囬',
  `description` varchar(200) DEFAULT NULL COMMENT '璇存槑',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cg_master_resource_peer`;
CREATE TABLE `cg_master_resource_peer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭id',
  `org_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '鏁版嵁闅旂缁勭粐id',
  `org_tag` varchar(100) DEFAULT NULL COMMENT '缁勭粐鏍囪瘑',
  `name` varchar(100) NOT NULL COMMENT '璧勬簮鍚嶅瓧',
  `status` varchar(26) NOT NULL COMMENT '鐘舵�乕OPEN,SUBMITTED,APPROVED,REJECTED,CLOSED]',
  `user_id` bigint(20) DEFAULT 0 COMMENT '璧勬簮褰掑睘鐢ㄦ埛',
  `title` varchar(100) DEFAULT NULL COMMENT '璧勬簮鏍囬',
  `category_group` varchar(50) DEFAULT NULL COMMENT '璧勬簮鍒嗙被褰掑睘鍒嗙粍',
  `category_id` bigint(20) DEFAULT NULL COMMENT '璧勬簮鍒嗙被ID',
  `description` varchar(200) DEFAULT NULL COMMENT '璧勬簮璇存槑',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `cg_master_resource_relation`;
CREATE TABLE `cg_master_resource_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭id',
  `master_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '涓昏祫婧怚D',
  `master_peer_id` bigint(20) DEFAULT 0 COMMENT '澶氬澶氫富璧勬簮ID',
  `flag` int DEFAULT NULL COMMENT '鍏崇郴鏍囪',
  `description` varchar(200) DEFAULT NULL COMMENT '鍏崇郴璇存槑',
  PRIMARY KEY (id),
  UNIQUE (`master_id`,`master_peer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

