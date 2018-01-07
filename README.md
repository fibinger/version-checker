Mysql init commands:

create database version_checker;
create user 'tide'@'%' identified by 'tide';
grant all on version_checker.* to 'tide'@'%';
