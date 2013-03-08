CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password_md5` char(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ILOGIN` (`login`),
  UNIQUE KEY `IEMAIL` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `account` (
  `user_id` bigint(20) NOT NULL,
  `total_balance` bigint(20) NOT NULL DEFAULT '0',
  `available_balance` bigint(20) NOT NULL DEFAULT '0',
  `version` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `description` text,
  `price` bigint(20) NOT NULL,
  `available_number` int(11) NOT NULL,
  `selled_number` int(11) NOT NULL DEFAULT '0',
  `state` enum('CREATED','ONLINE','OFFLINE') NOT NULL DEFAULT 'CREATED',
  `online_time` datetime DEFAULT NULL,
  `offline_time` datetime DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `pic_url` varchar(100) NOT NULL,
  `rating_number` int(11) NOT NULL DEFAULT '0',
  `rating_times` int(11) NOT NULL DEFAULT '0',
  `average_rating` float(2,1) NOT NULL DEFAULT '0.0',
  `rating_stars1` int(11) NOT NULL DEFAULT '0',
  `rating_stars2` int(11) NOT NULL DEFAULT '0',
  `rating_stars3` int(11) NOT NULL DEFAULT '0',
  `rating_stars4` int(11) NOT NULL DEFAULT '0',
  `rating_stars5` int(11) NOT NULL DEFAULT '0',
  `version` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO category (id, name) values (1, '服装'), (2, '化妆品'), (3, '家用电器'), (4, '手机数码'), (5, '图书音像'), (6, '日用百货');

CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `receiver_name` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `address_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buyer_id` bigint(20) NOT NULL,
  `seller_id` bigint(20) NOT NULL,
  `money` bigint(20) NOT NULL,
  `goods_id` bigint(20) NOT NULL,
  `num` int(11) NOT NULL,
  `receiver_name` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `state` enum('CREATED','PAYED','DELIVERED','RECEIVED','COMPLETED','CANCELED','REFUNDED') NOT NULL DEFAULT 'CREATED',
  `last_update_time` datetime NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `buyer_id` (`buyer_id`),
  KEY `seller_id` (`seller_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `type` enum('CREATE','PAY','DELIVER','RECEIVE','COMPLETE','CANCEL','REFUND') NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_history_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_history_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `rating` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `rating_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `number` tinyint(4) NOT NULL,
  `comment` varchar(1024) DEFAULT NULL,
  `buy_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `buy_price` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `goods_id` (`goods_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `rating_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;