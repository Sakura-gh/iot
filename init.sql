create database if not exists iot;
use iot;
create table if not exists iot_message (
	client_id		varchar(32),
	info 			varchar(100),
	value			int,
	alert			int,
	lng				double,
	lat				double,
	timestamp		bigint,
	primary key(client_id, timestamp)
);

create table if not exists user (
	username		varchar(32),
	password		varchar(32),
	email			varchar(64),
	primary key(username)
);

insert into user values('gehao', '123', 'sakura.gehao@gmail.com');
