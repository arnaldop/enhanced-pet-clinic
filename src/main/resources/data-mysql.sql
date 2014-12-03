create table if not exists users (
  userName varchar(50) not null,
  password varchar(255) not null,
  enabled boolean not null,
  constraint pk_users primary key (userName)
);

create table if not exists authorities (
  userName varchar(50) not null,
  authority varchar(50) not null,
  constraint pk_authorities primary key (userName, authority),
  foreign key fk_authorities (userName) references users (userName),
  unique uc_authorities (userName, authority)
);

create table if not exists persistent_logins (
  userName varchar(100) not null,
  series varchar(64) not null,
  token varchar(64) not null,
  last_used timestamp not null default current_timestamp on update current_timestamp,
  constraint pk_persistent_logins primary key (userName, series),
  foreign key fk_persistent_logins (userName) references users (userName)
);

-- Security
insert ignore into users(userName, password, enabled) values ('myuser', 'password', true);
insert ignore into users(userName, password, enabled) values ('myadmin', 'password', true);

insert ignore into authorities(userName, authority) values ('myuser', 'ROLE_USER');
insert ignore into authorities(userName, authority) values ('myadmin', 'ROLE_USER, ROLE_ADMIN');
