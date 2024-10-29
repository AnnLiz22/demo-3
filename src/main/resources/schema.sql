
CREATE TABLE users (
id int primary key,
name varchar(50),
role varchar(50)
);

CREATE TABLE tasks (
id int primary key,
name varchar (50),
description varchar(255),
createdOn date
);
