create database EIM;
/* GRANT ALL PRIVILEGES ON eim.* TO 'elastest'@'localhost' identified by 'elastest';
*  grant usage on eim.* to 'elastest'@'localhost' identified by 'elastest';
*  GRANT ALL PRIVILEGES ON eim.* TO 'elastest'@'##EIM_SERVER##' identified by 'elastest';
*  grant usage on eim.* to 'elastest'@'##EIM_SERVER##' identified by 'elastest';
*/

GRANT ALL PRIVILEGES ON *.* TO 'elastest'@'%' identified by 'elastest';
grant usage on *.* to 'elastest'@'%' identified by 'elastest';

flush privileges;
