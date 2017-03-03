INSERT INTO crops (name) VALUES ('Trigo');
INSERT INTO crops (name) VALUES ('Maíz');
INSERT INTO crops (name) VALUES ('Sorgo');
INSERT INTO stages (name, crop_id) VALUES ('Inicial', 1);
INSERT INTO stages (name, crop_id) VALUES ('Desarrollo', 1);
INSERT INTO stages (name, crop_id) VALUES ('Espigamiento', 1);
INSERT INTO stages (name, crop_id) VALUES ('Cosecha', 1);
INSERT INTO kcs (value, stage_id) VALUES (3.1, 1);
INSERT INTO kcs (value, stage_id) VALUES (3.2, 2);
INSERT INTO kcs (value, stage_id) VALUES (3.3, 3);
INSERT INTO kcs (value, stage_id) VALUES (3.4, 4);
INSERT INTO prs (value, stage_id) VALUES (1.1, 1);
INSERT INTO prs (value, stage_id) VALUES (1.2, 2);
INSERT INTO prs (value, stage_id) VALUES (1.3, 3);
INSERT INTO prs (value, stage_id) VALUES (1.4, 4);
INSERT INTO records (name, crop_id, stage_id, result, cc, ha, date, latitude, longitude, lote) VALUES ("Pedro Gonzalez", 1, 1, 3.4, 0.45, 0.33, "02/03/2017", 0,0, "Lote 23");

