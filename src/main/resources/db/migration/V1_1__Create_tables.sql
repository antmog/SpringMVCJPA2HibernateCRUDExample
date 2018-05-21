-- users
CREATE TABLE IF NOT EXISTS `users` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADRESS` varchar(255) NOT NULL,
  `BALANCE` double NOT NULL,
  `BIRTH_DATE` datetime NOT NULL,
  `FIRST_NAME` varchar(255) NOT NULL,
  `LAST_NAME` varchar(255) NOT NULL,
  `LOGIN` varchar(255) NOT NULL,
  `MAIL` varchar(255) NOT NULL,
  `PASSPORT_ID` int(11) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ROLE` varchar(255) NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_csdx7w03xd46e93i1joonkgjq` (`PASSPORT_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `users`;

-- contracts
CREATE TABLE IF NOT EXISTS `contracts` (
  `CONTRACT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHONE_NUMBER` varchar(255) NOT NULL,
  `CONTRACT_PRICE` double NOT NULL,
  `CONTRACT_STATUS` varchar(255) NOT NULL,
  `CONTRACT_TARIFF_ID` int(11) NOT NULL,
  `CONTRACT_USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`CONTRACT_ID`),
  KEY `FKlq9vwqp9phntpbnojufn9dg9b` (`CONTRACT_TARIFF_ID`),
  KEY `FK5lxif8h3iv2x325n3gdolyrnq` (`CONTRACT_USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `contracts`;

-- active options for contract
CREATE TABLE IF NOT EXISTS `active_options` (
  `CONTRACT_ID` int(11) NOT NULL,
  `OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`CONTRACT_ID`,`OPTION_ID`),
  KEY `FK1er0wdb0mqnu0gqf9ptvln3u1` (`OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `active_options`;

-- tariffs
CREATE TABLE IF NOT EXISTS `tariffs` (
  `TARIFF_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `PRICE` double NOT NULL,
  `TARIFF_STATUS` varchar(255) NOT NULL,
  PRIMARY KEY (`TARIFF_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `tariffs`;

-- available options (all options for tariff)
CREATE TABLE IF NOT EXISTS `available_options` (
  `TARIFF_ID` int(11) NOT NULL,
  `OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`TARIFF_ID`,`OPTION_ID`),
  KEY `FKlris6v3fsn8haf8s4ry6741xt` (`OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `available_options`;

-- options (all options)
CREATE TABLE IF NOT EXISTS `tariff_options` (
  `OPTION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COSTOFADD` double NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `PRICE` double NOT NULL,
  PRIMARY KEY (`OPTION_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `tariff_options`;

-- excluding options rules
CREATE TABLE IF NOT EXISTS `excluding_options` (
  `OPTION_ID` int(11) NOT NULL,
  `EXCLUDING_OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`EXCLUDING_OPTION_ID`),
  KEY `FKj4q134nrqprtilqei8x4lc061` (`EXCLUDING_OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `excluding_options`;

-- related options rules
CREATE TABLE IF NOT EXISTS `related_options` (
  `OPTION_ID` int(11) NOT NULL,
  `RELATED_OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`RELATED_OPTION_ID`),
  KEY `FK3hn1qildtpof6an41wityig2o` (`RELATED_OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `related_options`;

-- list of options, for wich selected option is related
CREATE TABLE IF NOT EXISTS `is_related_for` (
  `OPTION_ID` int(11) NOT NULL,
  `RELATED_FOR` int(11) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`RELATED_FOR`),
  UNIQUE KEY `UK_84hkg122dnqktoq07p0bxc99n` (`RELATED_FOR`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `is_related_for`;

-- data for login tokens (remember me function)
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `persistent_logins`;

