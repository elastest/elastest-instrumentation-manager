create database eim;
GRANT ALL PRIVILEGES ON eim.* TO 'elastest'@'localhost' identified by 'elastest';
grant usage on eim.* to 'elastest'@'localhost' identified by 'elastest';
flush privileges;
