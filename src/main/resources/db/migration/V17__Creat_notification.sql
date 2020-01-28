create table notification
(
	id bigint auto_increment,
	notifier bigint not null,
	receiver bigint not null,
	type int not null,
	out_id bigint not null,
	gmt_create bigint not null,
	states int default 0 not null,
	constraint notification_pk
		primary key (id)
);