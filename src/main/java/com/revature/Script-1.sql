drop table if exists reimbursements;
drop table if exists users;
drop table if exists role;



create table role(
role_id serial primary key,
rolename varchar(30)
);

create table users(
user_id serial primary key,
username varchar(30) unique,
password varchar(64),
firstname varchar(30),
lastname varchar(30),
role_id int,
foreign key (role_id) references role(role_id)
);

--alter table users
--add foreign key (role_id) references role(role_id);

create table reimbursements(
	reimbursement_id serial primary key,
	amount numeric(100,2),
	status varchar(30),
	reason varchar(60),
	employee_id int,
	manager_id int,
	foreign key (employee_id) references users(user_id),
	foreign key (manager_id) references users(user_id)
);

insert into role(rolename)
values
('employee'),
('manager');

insert into users(username, password, firstname, lastname, role_id)
values 
('John', 'password', 'John', 'Doe', 1),
('Jane', 'password', 'Jane', 'Doe', 2);

insert into reimbursements(amount, status, reason, employee_id, manager_id)
values 
('200.00', 'pending', 'Mileage', 1, 2);

select * from role;
select * from users;
select * from reimbursements;

select * from reimbursements inner join users on users.user_id = reimbursements.employee_id