create table if not exists users (
  username varchar(50) not null,
  email varchar(150) not null,
  password varchar(255) not null,
  name varchar(255) not null,
  enabled boolean not null,
  account_expired boolean not null,
  account_locked boolean not null,
  credentials_expired boolean not null,
  constraint pk_users primary key (username)
);

create table if not exists authorities (
  authority varchar(50) not null,
--  constraint pk_authorities primary key (username, authority),
--  foreign key fk_authorities (username) references users (username),
  unique uc_authorities (authority)
);

create table if not exists user_authorities (
  user_id int not null,
  authority_id int not null,
--  constraint pk_authorities primary key (username, authority),
  foreign key fk_user_authorities_1 (user_id) references users (id),
  foreign key fk_user_authorities_2 (authority_id) references authorities (id),
  unique uc_user_authorities (user_id, authority_id)
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
--insert ignore into users(username, password, enabled) values ('myuser', 'password', true);
--insert ignore into users(username, password, enabled) values ('myadmin', 'password', true);
--
--insert ignore into authorities(username, authority) values ('myuser', 'ROLE_USER');
--insert ignore into authorities(username, authority) values ('myadmin', 'ROLE_USER, ROLE_ADMIN');
