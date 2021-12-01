PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS stop_line;
DROP TABLE IF EXISTS stop;
DROP TABLE IF EXISTS line;
DROP TABLE IF EXISTS line_segment;
DROP TABLE IF EXISTS bus;
DROP TABLE IF EXISTS bus_segment;

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
	first_stop integer REFERENCES stop(stop_id) ON DELETE RESTRICT ON UPDATE CASCADE,
);

CREATE TABLE line_segment(
	line_segment_id integer PRIMARY KEY,
	line_id integer REFERENCES line ON DELETE RESTRICT ON UPDATE CASCADE,
	time_diff integer NOT NULL,
	next_stop integer REFERENCES stop(stop_id) ON DELETE RESTRICT ON UPDATE CASCADE,
	capacity integer NOT NULL
);

CREATE TABLE bus(
	bus_id integer PRIMARY KEY,
	line_id integer REFERENCES line ON DELETE RESTRICT ON UPDATE CASCADE,
	starting_time integer NOT NULL,
);

CREATE TABLE bus_segment(
	bus_id integer REFERENCES bus ON DELETE RESTRICT ON UPDATE CASCADE,
	capacity integer NOT NULL,
	passengers_count integer NOT NULL,
	line_segment_id integer REFERENCES line_segment ON DELETE RESTRICT ON UPDATE CASCADE
);
