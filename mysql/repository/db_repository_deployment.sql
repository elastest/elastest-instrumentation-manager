create database if not exists `eim`;

USE `eim`;


/*Table structure for table `agent` */

DROP TABLE IF EXISTS `agent`;

CREATE TABLE `agent` (
	  `agent_id` varchar(255) NOT NULL,
	  `host` varchar(255) NOT NULL,
	  `monitored` tinyint(1) NOT NULL,
	  `logstash_ip` varchar(255) NOT NULL,
	  `logstash_port` varchar(255) NOT NULL,
	  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Table structure for table `agentconfiguration` */

DROP TABLE IF EXISTS `agent_configuration`;

CREATE TABLE `agent_configuration` (
	  `agent_id` varchar(255) NOT NULL,
	  `exec` varchar(255) NOT NULL,
	  `component` varchar(255) NOT NULL,
	  `packetbeat_stream` varchar(255) NOT NULL,
	  `topbeat_stream` varchar(255) NOT NULL,
	  `filebeat_stream` varchar(255) NOT NULL,
	  `filebeat_paths` text NOT NULL,
	  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
