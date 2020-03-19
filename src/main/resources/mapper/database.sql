drop database nocm;
create database nocm;
use nocm;


create table member (
email varchar(100) primary key not null,
name varchar(100) not null,
phone varchar(20) not null,
picture varchar(100)
);

create table sns_info (
identifier varchar(50) primary key not null,
member_email varchar(100) not null,
foreign key (member_email) references member(email) on delete cascade on update cascade
);


create table channel(
id int unsigned  primary key auto_increment not null,
name varchar(100) not null,
register_date datetime not null default CURRENT_TIMESTAMP,
member_email varchar(100) not null,
foreign key (member_email) references member(email) on delete cascade on update cascade
);

create table joininfo(
id int unsigned  primary key auto_increment not null,
channel_id int unsigned not null,
register_date  datetime not null default CURRENT_TIMESTAMP,
member_email varchar(100) not null,
foreign key (member_email) references member(email) on delete cascade on update cascade,
foreign key (channel_id) references channel(id) on delete cascade on update cascade
);

create table message(
id int unsigned  primary key auto_increment not null,
channel_id int unsigned not null,
content text,
sender varchar(100) not null,
send_date datetime not null default CURRENT_TIMESTAMP,
foreign key (sender) references member(email) on delete cascade on update cascade
);

create table file(
id int unsigned  primary key auto_increment not null,
original_name varchar(130) not null,
server_name varchar(130) not null,
path varchar(130) not null,
extension varchar(10),
message_id int unsigned not null,
byte int unsigned not null,
sender varchar(100) not null,
send_date datetime not null default CURRENT_TIMESTAMP,
foreign key (sender) references member(email) on delete cascade on update cascade,
foreign key (message_id) references message(id) on delete cascade on update cascade
);


create table invite(
id int unsigned  primary key auto_increment not null,
channel_id int unsigned not null,
sender varchar(100) not null,
recipient varchar(100) not null,
send_date datetime not null default CURRENT_TIMESTAMP,
invite_state ENUM('STAND_BY' , 'ACCEPT' , 'REFUSE' ),
foreign key (sender) references member(email) on delete cascade on update cascade,
foreign key (recipient) references member(email) on delete cascade on update cascade,
foreign key (channel_id) references channel(id) on delete cascade on update cascade
);

create table list(
id int unsigned  primary key auto_increment not null,
name varchar(80) not null,
channel_id int unsigned not null,
register_date  datetime not null default CURRENT_TIMESTAMP,
edit_date  datetime not null default CURRENT_TIMESTAMP,
position int not null,
register varchar(100) not null,
foreign key (channel_id) references channel(id) on delete cascade on update cascade,
foreign key (register) references member(email) on delete cascade on update cascade
);


create table task(
id int unsigned  primary key auto_increment not null,
list_id int unsigned not null,
content text,
register_date  datetime not null default CURRENT_TIMESTAMP,
edit_date  datetime not null default CURRENT_TIMESTAMP,
register varchar(100) not null,
state boolean not null,
position int not null,
foreign key (list_id) references list(id) on delete cascade on update cascade,
foreign key (register) references member(email) on delete cascade on update cascade
);


delete from member;
delete from channel;
delete from invite;
delete from message;
delete from sns_info;
delete from joininfo;