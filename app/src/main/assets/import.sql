INSERT INTO crops (name) VALUES ('Trigo');
INSERT INTO crops (name) VALUES ('Maíz');
INSERT INTO crops (name) VALUES ('Sorgo');
INSERT INTO stages (name, crop_id) VALUES ('Inicial', 1);
INSERT INTO stages (name, crop_id) VALUES ('Desarrollo', 1);
INSERT INTO stages (name, crop_id) VALUES ('Espigamiento', 1);
INSERT INTO stages (name, crop_id) VALUES ('Cosecha', 1);
INSERT INTO stages (name, crop_id) VALUES ('Inicial', 2);
INSERT INTO stages (name, crop_id) VALUES ('Desarrollo', 2);
INSERT INTO stages (name, crop_id) VALUES ('Granulación', 2);
INSERT INTO stages (name, crop_id) VALUES ('Cosecha', 2);
INSERT INTO stages (name, crop_id) VALUES ('Inicial', 3);
INSERT INTO stages (name, crop_id) VALUES ('Desarrollo', 3);
INSERT INTO stages (name, crop_id) VALUES ('Cosecha', 3);
INSERT INTO kcs (value, stage_id) VALUES (1, 1);
INSERT INTO kcs (value, stage_id) VALUES (1, 2);
INSERT INTO kcs (value, stage_id) VALUES (1, 3);
INSERT INTO kcs (value, stage_id) VALUES (1, 4);
INSERT INTO kcs (value, stage_id) VALUES (3.5, 5);
INSERT INTO kcs (value, stage_id) VALUES (3.6, 6);
INSERT INTO kcs (value, stage_id) VALUES (3.7, 7);
INSERT INTO kcs (value, stage_id) VALUES (3.8, 8);
INSERT INTO kcs (value, stage_id) VALUES (3.9, 9);
INSERT INTO kcs (value, stage_id) VALUES (3.10, 10);
INSERT INTO kcs (value, stage_id) VALUES (3.11, 11);
INSERT INTO prs (value, stage_id) VALUES (600, 1);
INSERT INTO prs (value, stage_id) VALUES (600, 2);
INSERT INTO prs (value, stage_id) VALUES (600, 3);
INSERT INTO prs (value, stage_id) VALUES (600, 4);
INSERT INTO prs (value, stage_id) VALUES (1.5, 5);
INSERT INTO prs (value, stage_id) VALUES (1.6, 6);
INSERT INTO prs (value, stage_id) VALUES (1.7, 7);
INSERT INTO prs (value, stage_id) VALUES (1.8, 8);
INSERT INTO prs (value, stage_id) VALUES (1.9, 9);
INSERT INTO prs (value, stage_id) VALUES (1.10, 10);
INSERT INTO prs (value, stage_id) VALUES (1.11, 11);
INSERT INTO records (name, crop_id, stage_id, result, cc, ha, date, latitude, longitude, lote) VALUES ("Pedro Gonzalez", 1, 1, "09/03/2017", 0.45, 0.33, "02/03/2017", 0,0, "Lote 23");

