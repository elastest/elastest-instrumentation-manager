create database eim;
GRANT ALL PRIVILEGES ON eim.* TO 'elastest'@'localhost' identified by 'elastest';
grant usage on eim.* to 'elastest'@'localhost' identified by 'elastest';
GRANT ALL PRIVILEGES ON eim.* TO 'elastest'@'ET_EIM_SERVER' identified by 'elastest';
grant usage on eim.* to 'elastest'@'ET_EIM_SERVER' identified by 'elastest';
flush privileges;
