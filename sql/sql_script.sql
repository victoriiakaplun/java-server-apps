DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore;
USE bookstore;

CREATE TABLE books (
id INT(11) NOT NULL AUTO_INCREMENT,
title VARCHAR(50) NOT NULL,
year INT NOT NULL,
pages INT NOT NULL,
PRIMARY KEY (id)
/*KEY pages (pages)*/
)
ENGINE=INNODB DEFAULT CHARSET=utf8;

insert into books(title, year, pages)
values
('first_title', 2000, 100),
('second_title', 2001, 101),
('third_title', 2002, 102),
('title_other', 2003, 103);

select * from books;
select * from books limit 2;
select * from books where id = 1;
select * from books where title='other_title';
select * from books where title like 'first%';
select * from books where title like '%title';

explain select * from books where year between 2000 and 2002;
select * from books where pages between 100 and 101;
/*create index year on books(year);*/
/*create index pages on books(pages);*/
explain select * from books
where
year between 2000 and 2002
and
pages between 101 and 103;

update books set pages = 200;
update books set pages = 200 where pages > 101;
update books set pages = 201 where pages > 200 and year < 2001;
update books set pages = 202 where pages > 200 and title like 'title%';

delete from books where pages > 1000;
delete from books where title like 'title%';
delete from books where id between 1 and 2;
delete from books where title = 'first_title';
delete from books where title='title' or title= 'first_title' or title = 'second_title';
delete from books;

alter table books add publishing_house varchar(40) default null;
alter table books add binding enum('no', 'soft', 'hard') not null default 'no';
update books set publishing_house = 'house' where id between 1 and 4;
alter table books change  title bookname varchar(50);
rename table books to book;

drop table book;