
--
-- Structure for table transparency_appointment
--

DROP TABLE IF EXISTS transparency_appointment;
CREATE TABLE transparency_appointment (
id_appointment int AUTO_INCREMENT,
title varchar(255) default '' NOT NULL,
description long varchar,
start_date date NOT NULL,
end_date date,
type_id int default '0' NOT NULL,
type_label varchar(255) default '',
url varchar(255) default '',
PRIMARY KEY (id_appointment)
);

--
-- Structure for table transparency_elected_official
--

DROP TABLE IF EXISTS transparency_elected_official;
CREATE TABLE transparency_elected_official (
id_elected_official int AUTO_INCREMENT,
first_name varchar(255) default '' NOT NULL,
last_name varchar(255) default '' NOT NULL,
title varchar(50) default '' NOT NULL,
PRIMARY KEY (id_elected_official)
);

--
-- Structure for table transparency_lobby
--

DROP TABLE IF EXISTS transparency_lobby;
CREATE TABLE transparency_lobby (
id_lobby int AUTO_INCREMENT,
name varchar(255) default '' NOT NULL,
national_id int default '0',
national_id_type varchar(50) default '',
url varchar(255) default '',
json_data long varchar,
version_date date NOT NULL,
PRIMARY KEY (id_lobby)
);
