------------------------------------------------------------
-- Users
------------------------------------------------------------

insert into users (id, email, user_name, password, name) values (1, 'user@aol.com', 'user', '5f4dcc3b5aa765d61d8327deb882cf99', 'User');
insert into users (id, email, user_name, password, name) values (2, 'keith@aol.com', 'keith', '417c7382b16c395bc25b5da1398cf076', 'Keith');
insert into users (id, email, user_name, password, name) values (3, 'erwin@aol.com', 'erwin', '12430911a8af075c6f41c6976af22b09', 'Erwin');
insert into users (id, email, user_name, password, name) values (4, 'jeremy@aol.com', 'jeremy', '57c6cbff0d421449be820763f03139eb', 'Jeremy');
insert into users (id, email, user_name, password, name) values (5, 'scott@aol.com', 'scott', '942f2339bf50796de535a384f0d1af3e', 'Scott');

------------------------------------------------------------
-- PetClinic
------------------------------------------------------------

insert into vets(id, first_name, last_name) values (1, 'James', 'Carter');
insert into vets(id, first_name, last_name) values (2, 'Helen', 'Leary');
insert into vets(id, first_name, last_name) values (3, 'Linda', 'Douglas');
insert into vets(id, first_name, last_name) values (4, 'Rafael', 'Ortega');
insert into vets(id, first_name, last_name) values (5, 'Henry', 'Stevens');
insert into vets(id, first_name, last_name) values (6, 'Sharon', 'Jenkins');

insert into specialties(id, name) values (1, 'radiology');
insert into specialties(id, name) values (2, 'surgery');
insert into specialties(id, name) values (3, 'dentistry');

insert into vet_specialties(vet_id, specialty_id) values (2, 1);
insert into vet_specialties(vet_id, specialty_id) values (3, 2);
insert into vet_specialties(vet_id, specialty_id) values (3, 3);
insert into vet_specialties(vet_id, specialty_id) values (4, 2);
insert into vet_specialties(vet_id, specialty_id) values (5, 1);

insert into types(id, name) values (1, 'cat');
insert into types(id, name) values (2, 'dog');
insert into types(id, name) values (3, 'lizard');
insert into types(id, name) values (4, 'snake');
insert into types(id, name) values (5, 'bird');
insert into types(id, name) values (6, 'hamster');

insert into owners(id, first_name, last_name, address, city, telephone) values (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
insert into owners(id, first_name, last_name, address, city, telephone) values (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
insert into owners(id, first_name, last_name, address, city, telephone) values (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
insert into owners(id, first_name, last_name, address, city, telephone) values (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
insert into owners(id, first_name, last_name, address, city, telephone) values (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
insert into owners(id, first_name, last_name, address, city, telephone) values (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
insert into owners(id, first_name, last_name, address, city, telephone) values (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
insert into owners(id, first_name, last_name, address, city, telephone) values (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
insert into owners(id, first_name, last_name, address, city, telephone) values (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
insert into owners(id, first_name, last_name, address, city, telephone) values (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');
insert into owners(id, first_name, last_name, address, city, telephone) values (11, 'Kim', 'Picadilli', '2715 Amanda Way', 'Kissimmee', '7072883864');

insert into pets(id, name, birth_date, type_id, owner_id) values (1, 'Leo', '2010-09-07', 1, 1);
insert into pets(id, name, birth_date, type_id, owner_id) values (2, 'Basil', '2012-08-06', 6, 2);
insert into pets(id, name, birth_date, type_id, owner_id) values (3, 'Rosy', '2011-04-17', 2, 3);
insert into pets(id, name, birth_date, type_id, owner_id) values (4, 'Jewel', '2010-03-07', 2, 3);
insert into pets(id, name, birth_date, type_id, owner_id) values (5, 'Iggy', '2010-11-30', 3, 4);
insert into pets(id, name, birth_date, type_id, owner_id) values (6, 'George', '2010-01-20', 4, 5);
insert into pets(id, name, birth_date, type_id, owner_id) values (7, 'Samantha', '2012-09-04', 1, 6);
insert into pets(id, name, birth_date, type_id, owner_id) values (8, 'Max', '2012-09-04', 1, 6);
insert into pets(id, name, birth_date, type_id, owner_id) values (9, 'Lucky', '2011-08-06', 5, 7);
insert into pets(id, name, birth_date, type_id, owner_id) values (10, 'Mulligan', '2007-02-24', 2, 8);
insert into pets(id, name, birth_date, type_id, owner_id) values (11, 'Freddy', '2010-03-09', 5, 9);
insert into pets(id, name, birth_date, type_id, owner_id) values (12, 'Lucky', '2010-06-24', 2, 10);
insert into pets(id, name, birth_date, type_id, owner_id) values (13, 'Sly', '2012-06-08', 1, 10);

insert into visits(id, pet_id, visit_date, description) values (1, 7, '2013-01-01', 'rabies shot');
insert into visits(id, pet_id, visit_date, description) values (2, 8, '2013-01-02', 'rabies shot');
insert into visits(id, pet_id, visit_date, description) values (3, 8, '2013-01-03', 'neutered');
insert into visits(id, pet_id, visit_date, description) values (4, 7, '2013-01-04', 'spayed');
