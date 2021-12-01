DROP TABLE IF EXISTS stop;
DROP TABLE IF EXISTS line;
DROP TABLE IF EXISTS line_segment;

CREATE TABLE stop(
	stop_id integer PRIMARY KEY,
	stop_name varchar(30) NOT NULL,
	UNIQUE(stop_name)
);

CREATE TABLE stop_line(
	stop_id integer NOT NULL REFERENCES stop ON DELETE RESTRICT ON UPDATE CASCADE,
	line_id integer NOT NULL REFERENCES line ON DELETE RESTRICT ON UDATE CASCADE
);

CREATE TABLE line(
	line_id integer PRIMARY KEY,
	line_name varchar(30) NOT NULL,
	UNIQUE(line_name)
);

CREATE TABLE line_segment(
	line_segment_id integer PRIMARY KEY,
	line_id integer REFERENCES line ON DELETE RESTRICT ON UPDATE CASCADE,
	capacity integer NOT NULL,
	time_diff integer NOT NULL,
	next_stop integer REFERENCES stop ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE bus(
	bus_id integer PRIMARY KEY,

