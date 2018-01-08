This project demonstrates a way in which we can manage mobile application versions and restrict features
both globally and by user.

In order to setup a project locally run following MySQL commands:

```
create database version_checker;
create user 'tide'@'%' identified by 'tide';
grant all on version_checker.* to 'tide'@'%';
```
