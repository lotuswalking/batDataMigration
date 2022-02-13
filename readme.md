2022-02-12:

将数据映射功能调整为采用ModelMapper实现,完成后,可以实现自动从entity中导出所有字段,映射到新的entity中去

下一步任务，实现断点续传功能
Database SQL Query

There are two different database is created to read data from one database table and write data to another database table. The first database will contain employee table. The table contains three columns id, name and salary. The second database contains manager table. The manager table contains three columns id, name and salary.

Database – testdb1

`
create table employee (

id int primary key,

name varchar(100),

salary int

);

insert into employee values (1,'name01',1000);

insert into employee values (2,'name02',2000);

insert into employee values (3,'name03',3000);
`
Database – testdb2

`create table manager (

id int primary key auto_increment,

name varchar(100),

salary int

);
`