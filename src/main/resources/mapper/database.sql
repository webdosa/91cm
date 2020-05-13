drop database nocm;
create database nocm;
use nocm;


create table member (
email varchar(100) primary key not null,
password varchar(255),
name varchar(100) not null,
phone varchar(20) not null,
picture varchar(200)
);

create table sns_info (
identifier varchar(50) primary key not null,
member_email varchar(100) not null,
foreign key (member_email) references member(email) on delete cascade on update cascade
);


create table channel(
id int unsigned  primary key auto_increment not null,
name varchar(100) not null,
register_date datetime not null default CURRENT_TIMESTAMP
);

create table joininfo(
id int unsigned  primary key auto_increment not null,
channel_id int unsigned not null,
register_date  datetime not null default CURRENT_TIMESTAMP,
member_email varchar(100) not null,
last_access_date datetime default CURRENT_TIMESTAMP,
foreign key (member_email) references member(email) on delete cascade on update cascade,
foreign key (channel_id) references channel(id) on delete cascade on update cascade
);

create table message(
id int unsigned  primary key auto_increment not null,
channel_id int unsigned not null,
content text,
sender varchar(100) not null,
send_date datetime not null default CURRENT_TIMESTAMP,
foreign key (sender) references member(email) on update cascade
);

create table file(
id int unsigned  primary key auto_increment not null,
original_name varchar(130) not null,
server_name varchar(130) not null,
path varchar(130) not null,
extension varchar(10),
message_id int unsigned not null,
file_size int unsigned not null,
send_date datetime not null default CURRENT_TIMESTAMP,
foreign key (message_id) references message(id) on update cascade
);


create table invite(
id int unsigned  primary key auto_increment not null,
channel_id int unsigned not null,
sender varchar(100) not null,
recipient varchar(100) not null,
send_date datetime not null default CURRENT_TIMESTAMP,
invite_state ENUM('STAND_BY' , 'ACCEPT' , 'REFUSE' ) default 'STAND_BY',
foreign key (channel_id) references channel(id) on delete cascade on update cascade,
foreign key(recipient) references member(email) on update cascade
);

create table tasklist(
id int unsigned  primary key auto_increment not null,
name varchar(80) not null,
channel_id int unsigned not null,
register_date  datetime not null default CURRENT_TIMESTAMP,
edit_date  datetime not null default CURRENT_TIMESTAMP,
position int not null,
foreign key (channel_id) references channel(id) on delete cascade on update cascade
);


create table task(
id int unsigned  primary key auto_increment not null,
tasklist_id int unsigned not null,
content text,
register_date  datetime not null default CURRENT_TIMESTAMP,
edit_date  datetime not null default CURRENT_TIMESTAMP,
member_email varchar(100) not null,
state tinyint,
position int not null,
start_date datetime,
end_date datetime,
allday tinyint,
color varchar(20),
title varchar(45),
foreign key (tasklist_id) references tasklist(id) on delete cascade on update cascade,
foreign key (member_email) references member(email) on update cascade
);
create table roles (
authority varchar(50) not null primary key,
description varchar(100)
);

create table authorities (
member_email varchar(100) not null,
roles_authority varchar(50) not null,
primary key(member_email,roles_authority),
foreign key (member_email) references member(email) on delete cascade on update cascade,
foreign key (roles_authority) references roles(authority) on delete cascade on update cascade
);

create table permission (
id int unsigned  primary key auto_increment not null,
member_email varchar(100) not null,
roles_authority varchar(50) not null,
state ENUM('STAND_BY' , 'ACCEPT' , 'REFUSE' ) default 'STAND_BY',
foreign key (member_email) references member(email) on delete cascade on update cascade,
foreign key (roles_authority) references roles(authority)on delete cascade on update cascade
);



insert roles (authority, description) values ('ROLE_RESTRICTED', '제한된 사용자');
insert roles (authority, description) values ('ROLE_USER', '일반 사용자');
insert roles (authority, description) values ('ROLE_ADMIN', '관리자');


delimiter $$
drop procedure if exists testUser $$
create procedure testUser()
begin
    declare i int;
    set i = 1;
    while i <= 10 do
            insert into member (email, name, phone) value (concat('유저',cast(i as char )),concat('테스터',i),'qw');
            set i = i+1;
        end while;
end $$

delete from member;
delete from channel;
delete from invite;
delete from message;
delete from sns_info;
delete from joininfo;
delete from message;
delete from file;
delete from task;
delete from tasklist;
delete from authorities;