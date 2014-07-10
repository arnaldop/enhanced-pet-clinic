create table users (
  username varchar(256),
  password varchar(256),
  enabled boolean
);

create table authorities (
  username varchar(256),
  authority varchar(256)
);

insert into users(username, password, enabled) values ('arnaldo', 'Kimmy', true)
insert into users(username, password, enabled) values ('arnaldo2', 'Kimmy2', true)
insert into users(username, password, enabled) values ('arnaldo3', 'Kimmy3', false)

insert into authorities(username, authority) values ('arnaldo', 'Kimmy')
