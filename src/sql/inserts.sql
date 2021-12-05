PRAGMA foreign_keys = ON;

INSERT INTO stop(stop_name) VALUES
('a'),
('b'),
('c'),
('d');

INSERT INTO line(line_name,first_stop) VALUES
('1',1),
('2',2);

INSERT INTO stop_line(stop_id,line_id) VALUES
(1,1),
(2,1),
(2,2),
(3,1),
(3,2),
(4,2);

INSERT INTO line_segment(line_id,time_diff,next_stop,capacity) VALUES
(1,5,2,25),
(1,24,3,15),
(2,2,4,12),
(2,5,3,17);

INSERT INTO bus(line_id,starting_time) VALUES
(1,2),
(1,5),
(1,8),
(2,3),
(2,11),
(2,21);

INSERT INTO bus_segment(bus_id,capacity,passengers_count,line_segment_id) VALUES
(1,25,0,1),
(1,15,0,2),
(2,25,0,1),
(2,15,0,2),
(3,25,0,1),
(3,15,0,2),
(4,12,0,3),
(4,17,0,4),
(5,12,0,3),
(5,17,0,4),
(6,12,0,3),
(6,17,0,4);
