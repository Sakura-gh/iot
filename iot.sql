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
