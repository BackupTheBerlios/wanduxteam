-- phpMyAdmin SQL Dump
-- version 2.6.0-pl2
-- http://www.phpmyadmin.net
-- 
-- Serveur: localhost
-- Généré le : Vendredi 14 Octobre 2005 à 13:38
-- Version du serveur: 4.1.11
-- Version de PHP: 4.3.10-8
-- 
-- Base de données: `wandux`
-- 

-- --------------------------------------------------------

-- 
-- Structure de la table `GLOBAL_CONF`
-- 

CREATE TABLE `GLOBAL_CONF` (
  `GLOBAL_ID` int(11) NOT NULL default '0',
  `GLOBAL_KEY` int(11) NOT NULL default '0',
  `GLOBAL_HOSTNAME` text,
  `GLOBAL_DOMAIN_NAME` text,
  PRIMARY KEY  (`GLOBAL_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Structure de la table `LANG_INFO`
-- 

CREATE TABLE `LANG_INFO` (
  `LANG_ID` int(11) NOT NULL auto_increment,
  `LANG_LANGUAGE` varchar(50) NOT NULL default '',
  `LANG_CODE` int(11) default NULL,
  `LANG_MS_LOCAL_ID` varchar(10) default NULL,
  `LANG_MS_LOCAL_ID_HEXA` varchar(10) default NULL,
  `LANG_ISO_CODE` varchar(10) default NULL,
  `LANG_SYMBOLIC` varchar(30) default NULL,
  `LANG_ENV_VARIABLE` varchar(30) default NULL,
  PRIMARY KEY  (`LANG_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=77 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `LINUXCOMPONENTS`
-- 

CREATE TABLE `LINUXCOMPONENTS` (
  `ID` bigint(20) NOT NULL default '0',
  `NAME` varchar(128) NOT NULL default '',
  `TYPE` smallint(6) NOT NULL default '0',
  `WINEXISTS` tinyint(1) NOT NULL default '0',
  `PROPRIETARY` tinyint(1) NOT NULL default '0',
  `MULTIPLATFORM` tinyint(1) NOT NULL default '0',
  `STDPROTOCOL` tinyint(2) NOT NULL default '0',
  `RATING` tinyint(128) NOT NULL default '0',
  `URL` varchar(128) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Structure de la table `NETWORK_CONFIG`
-- 

CREATE TABLE `NETWORK_CONFIG` (
  `NETWORK_ID` int(11) NOT NULL default '0',
  `NETWORK_KEY` int(11) default NULL,
  `NETWORK_MAC_ADRESS` text,
  `NETWORK_STATUS` tinyint(1) default NULL,
  `NETWORK_DHCP_ENABLED` tinyint(1) default NULL,
  `NETWORK_GATEWAY` text,
  `NETWORK_INTERFACE` text,
  `NETWORK_DNS_SERVER` text,
  `NETWORK_DNS_SERVER2` text,
  `NETWORK_IP_ADDRESS` text,
  `NETWORK_SUBNETMASK` text,
  PRIMARY KEY  (`NETWORK_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Structure de la table `PARAM_IE`
-- 

CREATE TABLE `PARAM_IE` (
  `IE_PARAM_ID` int(11) NOT NULL default '0',
  `IE_PARAM_USER_ID` int(11) default NULL,
  `IE_PARAM_SAVE_DIRECTORY` text,
  `IE_PROXY_SERVER` text,
  `IE_PROXY_OVERRIDE` text,
  `IE_PROXY_AUTO_CONFIG_URL` text,
  PRIMARY KEY  (`IE_PARAM_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Structure de la table `SOFTCATEGORY`
-- 

CREATE TABLE `SOFTCATEGORY` (
  `ID` tinyint(4) NOT NULL auto_increment,
  `NAME` varchar(128) NOT NULL default '',
  `DESCRIPTION` text NOT NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `SOFTSUBCATEGORY`
-- 

CREATE TABLE `SOFTSUBCATEGORY` (
  `ID` tinyint(4) NOT NULL auto_increment,
  `NAME` varchar(128) NOT NULL default '',
  `DESCRIPTION` text NOT NULL,
  `CATEGORY` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

-- --------------------------------------------------------

-- 
-- Structure de la table `SOFTTYPES`
-- 

CREATE TABLE `SOFTTYPES` (
  `ID` tinyint(4) NOT NULL default '0',
  `NAME` varchar(56) NOT NULL default '',
  `DESCRIPTION` text NOT NULL,
  `TEXTMODE` tinyint(1) NOT NULL default '0',
  `GUIMODE` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Structure de la table `USERS_DATA`
-- 

CREATE TABLE `USERS_DATA` (
  `USER_ID` int(11) NOT NULL default '0',
  `USER_LOGIN` varchar(42) default NULL,
  `USER_KEY` int(11) default NULL,
  `USER_TYPE` text,
  `USER_PASS` text,
  `USER_HOME` text,
  `USER_PROXY_SERV` text,
  `USER_PROXY_OVERRIDE` text,
  `USER_BGIMG` text,
  `USER_KB_LAYOUT` text,
  `USER_TIMEZONE` text,
  PRIMARY KEY  (`USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

-- 
-- Structure de la table `WINDOWSCOMPONENTS`
-- 

CREATE TABLE `WINDOWSCOMPONENTS` (
  `ID` bigint(20) unsigned NOT NULL auto_increment,
  `NAME` varchar(128) NOT NULL default '',
  `TYPE` mediumint(8) unsigned NOT NULL default '0',
  `LXEXISTS` tinyint(1) unsigned zerofill NOT NULL default '0',
  `PROPRIETARY` tinyint(1) unsigned zerofill NOT NULL default '0',
  `MANAGED` tinyint(1) unsigned zerofill NOT NULL default '0',
  `MULTIPLATFORM` tinyint(1) unsigned zerofill NOT NULL default '0',
  `STDPROTOCOL` tinyint(2) unsigned zerofill NOT NULL default '00',
  `RATING` tinyint(128) unsigned NOT NULL default '0',
  `URL` varchar(128) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='full windows program list' AUTO_INCREMENT=1 ;
