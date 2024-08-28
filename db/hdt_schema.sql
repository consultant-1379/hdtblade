-- hdt_schema.sql
-- This creates the main HDT database schema.
--
-- USED FOR DEVELOPMENT
--
-- FIXME: The Version should come from CVS.
-- Author: Ralph Schlosser
-- Date: 22/10/2010
-- Last Change: 12/08/2011

DROP DATABASE IF EXISTS `hdt`;

CREATE DATABASE `hdt`;
USE `hdt`;

-- Technically the table below doesn't need to be dropped
-- as the DB schma was dropped already but just
-- to be prepared in case this gets copied somehwere else.
--
-- The HDT configuration table.
CREATE TABLE `hdtconfiguration` (    
    `dbversion` CHAR(10) NOT NULL DEFAULT '',
    PRIMARY KEY(`dbversion`)
 )
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The HDT systems table.
CREATE TABLE `systems` (
    `system_name` CHAR(50) NOT NULL DEFAULT '',
    `system_description` CHAR(100) NOT NULL DEFAULT '',
    PRIMARY KEY(`system_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Sytem-to-Role table.
CREATE TABLE `system_role` (
    `system_name` CHAR(50) NOT NULL DEFAULT '',
    `role_name` CHAR(50) NOT NULL DEFAULT ''
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Sytem-to-Network table.
CREATE TABLE `system_network` (
    `system_name` CHAR(50) NOT NULL DEFAULT '',
    `network_name` CHAR(50) NOT NULL DEFAULT ''
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The HDT roles table.
CREATE TABLE `roles` (
    `role_name` CHAR(50) NOT NULL DEFAULT '',
    `role_description` CHAR(100) NOT NULL DEFAULT '',
    `hw_config_table_name` CHAR(50) NOT NULL DEFAULT '',
    PRIMARY KEY(`role_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The HDT networks table.
CREATE TABLE `networks` (
    `network_name` CHAR(50) NOT NULL DEFAULT '',
    `network_description` CHAR(100) NOT NULL DEFAULT '',
    PRIMARY KEY(`network_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Quicklinks table.
CREATE TABLE `quicklinks` (
    `quicklink_description` CHAR(50) NOT NULL DEFAULT '',
    `quicklink_url` CHAR(100) NOT NULL DEFAULT '',
    PRIMARY KEY(`quicklink_description`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Quicklinks table.
CREATE TABLE `bundles` (
    `bundle_name` CHAR(50) NOT NULL DEFAULT '',
    `bundle_description` CHAR(100) NOT NULL DEFAULT '',
    PRIMARY KEY(`bundle_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Parameters table.
CREATE TABLE `parameters` (
    `parameter_name` CHAR(50) NOT NULL DEFAULT '',
    `parameter_description` CHAR(100) NOT NULL DEFAULT '',
    `parameter_type` CHAR(50) NOT NULL DEFAULT 'Integer',
    PRIMARY KEY(`parameter_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Parameter Values table.
CREATE TABLE `parameter_values` (
    `parameter_name` CHAR(50) NOT NULL DEFAULT '',    
    `parameter_default_val` CHAR(50) NOT NULL DEFAULT '0',
    `parameter_min_val` CHAR(50) NOT NULL DEFAULT '0',
    `parameter_max_val` CHAR(50) NOT NULL DEFAULT '0',
    `parameter_step_val` CHAR(50) NOT NULL DEFAULT '0',
    `bundle_name` CHAR(50) NOT NULL,
    `role_name` CHAR(50) NOT NULL DEFAULT '',
    `network_name` CHAR(50) NOT NULL DEFAULT '',
    `system_name` CHAR(50) NOT NULL DEFAULT '',    
    `visibility` TINYINT NOT NULL DEFAULT 1
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Variables table.
CREATE TABLE `variables` (
    `variable_name` CHAR(50) NOT NULL DEFAULT '',
    `variable_description` CHAR(100) NOT NULL DEFAULT '',
    `variable_result_type` CHAR(50) NOT NULL DEFAULT 'Double',
    PRIMARY KEY(`variable_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Variable-to-Formula table.
CREATE TABLE `variable_formula` (    
    `variable_name` CHAR(50) NOT NULL DEFAULT '',
    `formula_name` CHAR(50) NOT NULL DEFAULT '',    
    `role_name` CHAR(50) NOT NULL DEFAULT '',
    `network_name` CHAR(50) NOT NULL DEFAULT '',
    `system_name` CHAR(50) NOT NULL DEFAULT ''
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Formulas table.
CREATE TABLE `formulas` (
    `formula_name` CHAR(50) NOT NULL DEFAULT '',
    `formula_description` CHAR(100) NOT NULL DEFAULT '',
    `formula_js_code` VARCHAR(20000) NOT NULL DEFAULT '',
    PRIMARY KEY(`formula_name`)
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Hardware Config-to-APP Number table.
CREATE TABLE `hw_config` (
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_description` TEXT(500) NOT NULL
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Hardware Config-to-APP Number table.
CREATE TABLE `app_numbers` (
    `app_number` CHAR(50) NOT NULL DEFAULT '',
    `app_num_description` CHAR(100) NOT NULL DEFAULT '',
    `app_num_lod` CHAR(100) NOT NULL DEFAULT '',
    `app_num_eol` CHAR(100) NOT NULL DEFAULT '',
    `app_exposed` BOOLEAN DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

-- The Hardware Config-to-APP Number table.
CREATE TABLE `hw_config_app` (
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `app_number` CHAR(50) NOT NULL DEFAULT '',
    `quantity` INT UNSIGNED NOT NULL
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;


--
-- Hardware Config Tables
-- FIXME: Use a database template.
--
CREATE TABLE `admin_hw_config` (
    `ADMIN_CPU` DOUBLE DEFAULT 0.0,
    `ADMIN_MEM` DOUBLE DEFAULT 0.0,
    `ADMIN_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `bis_hw_config` (
    `BIS_CPU` DOUBLE DEFAULT 0.0,
    `BIS_MEM` DOUBLE DEFAULT 0.0,
    `BIS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `eba_hw_config` (
    `EBA_CPU` DOUBLE DEFAULT 0.0,
    `EBA_MEM` DOUBLE DEFAULT 0.0,
    `EBA_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0

)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `eniqs_hw_config` (
    `ENIQS_CPU` DOUBLE DEFAULT 0.0,
    `ENIQS_MEM` DOUBLE DEFAULT 0.0,
    `ENIQS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `inf_hw_config` (
    `INF_CPU` DOUBLE DEFAULT 0.0,
    `INF_MEM` DOUBLE DEFAULT 0.0,
    `INF_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `mws_hw_config` (
    `MWS_CPU` DOUBLE DEFAULT 0.0,
    `MWS_MEM` DOUBLE DEFAULT 0.0,
    `MWS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `nas_hw_config` (
    `NAS_CPU` DOUBLE DEFAULT 0.0,
    `NAS_MEM` DOUBLE DEFAULT 0.0,
    `NAS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `nedss_hw_config` (
    `NEDSS_CPU` DOUBLE DEFAULT 0.0,
    `NEDSS_MEM` DOUBLE DEFAULT 0.0,
    `NEDSS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `oap_hw_config` (
    `OAP_CPU` DOUBLE DEFAULT 0.0,
    `OAP_MEM` DOUBLE DEFAULT 0.0,
    `OAP_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `ombs_hw_config` (
    `OMBS_CPU` DOUBLE DEFAULT 0.0,
    `OMBS_MEM` DOUBLE DEFAULT 0.0,
    `OMBS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `storage_hw_config` (
    `STORAGE_CPU` DOUBLE DEFAULT 0.0,
    `STORAGE_MEM` DOUBLE DEFAULT 0.0,
    `STORAGE_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `uas_hw_config` (
    `UAS_CPU` DOUBLE DEFAULT 0.0,        
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `was_hw_config` (
    `WAS_CPU` DOUBLE DEFAULT 0.0,
    `WAS_MEM` DOUBLE DEFAULT 0.0,
    `WAS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `omsas_hw_config` (
    `OMSAS_CPU` DOUBLE DEFAULT 0.0,
    `OMSAS_MEM` DOUBLE DEFAULT 0.0,
    `OMSAS_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `dae_hw_config` (
    `DAE_CPU` DOUBLE DEFAULT 0.0,
    `DAE_MEM` DOUBLE DEFAULT 0.0,
    `DAE_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `switch_cable_hw_config` (
    `SWITCH_CPU` DOUBLE DEFAULT 0.0,
    `SWITCH_MEM` DOUBLE DEFAULT 0.0,
    `SWITCH_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `ee_hw_config` (
    `EE_HARDWARE_SEL` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `enclosure_hw_config` (
    `ENCLOSURE_NUMBER` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `notes` (
    `note_text` TEXT NOT NULL,
    `note_shown` BOOLEAN DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `iqserver_hw_config` (
    `IQSERVER_CPU` DOUBLE DEFAULT 0.0,
    `IQSERVER_MEM` DOUBLE DEFAULT 0.0,
    `IQSERVER_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `coordinator_hw_config` (
    `COORDINATOR_CPU` DOUBLE DEFAULT 0.0,
    `COORDINATOR_MEM` DOUBLE DEFAULT 0.0,
    `COORDINATOR_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `mediation_hw_config` (
    `MEDIATION_CPU` DOUBLE DEFAULT 0.0,
    `MEDIATION_MEM` DOUBLE DEFAULT 0.0,
    `MEDIATION_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `presentation_hw_config` (
    `PRESENTATION_CPU` DOUBLE DEFAULT 0.0,
    `PRESENTATION_MEM` DOUBLE DEFAULT 0.0,
    `PRESENTATION_IO` DOUBLE DEFAULT 0.0,    
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;

CREATE TABLE `test_hw_config` (
    `TEST_CPU` DOUBLE DEFAULT 0.0,        
    `hw_config_name` CHAR(50) NOT NULL DEFAULT '',
    `hw_config_revision` INT DEFAULT 1,
    `hw_config_status` INT DEFAULT 0
)
ENGINE = MYISAM
ROW_FORMAT = FIXED;
