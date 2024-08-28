SELECT * FROM `parameter_values` AS t1 JOIN `parameters` as t2 ON t1.`parameter_name`=t2.`parameter_name` WHERE
(`bundle_name`='ENIQ Statistics Config A' OR `bundle_name`='UNDEFINED')
AND (`role_name` IN (SELECT `role_name` FROM `system_role` WHERE system_role.`system_name`=t1.`system_name`) OR `role_name`='ANY_ROLE')
AND (`network_name`='LR_CN' OR `network_name`='ANY_NETWORK')
AND (`system_name`='ES112' OR `system_name`='ANY_SYSTEM') AND `visibility` != "1"