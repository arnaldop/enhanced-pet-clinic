create table if not exists users (
  username varchar(50) not null,
  password varchar(255) not null,
  enabled boolean not null,
  constraint pk_users primary key (username)
);

create table if not exists authorities (
  username varchar(50) not null,
  authority varchar(50) not null,
  constraint pk_authorities primary key (username, authority),
  foreign key fk_authorities (username) references users (username),
  unique uc_authorities (username, authority)
);

create table if not exists persistent_logins (
  username varchar(100) not null,
  series varchar(64) not null,
  token varchar(64) not null,
  last_used timestamp not null default current_timestamp on update current_timestamp,
  constraint pk_persistent_logins primary key (username, series),
  foreign key fk_persistent_logins (username) references users (username)
);

-- Security
insert ignore into users(username, password, enabled) values ('myuser', 'password', true);
insert ignore into users(username, password, enabled) values ('myadmin', 'password', true);

insert ignore into authorities(username, authority) values ('myuser', 'ROLE_USER');
insert ignore into authorities(username, authority) values ('myadmin', 'ROLE_USER, ROLE_ADMIN');
