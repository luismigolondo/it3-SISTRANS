-- USO
-- Copie el contenido deseado de este archivo en una pestaña SQL de SQL Developer
-- Ejecútelo como un script - Utilice el botón correspondiente de la pestaña utilizada

---- Insercion de tipos de documento
--- Sentencias SQL para la creacion de tipos de identificaciones
INSERT INTO TIPOS_IDENTIFICACION(ID, NOMBRE) VALUES (1, 'CC');
INSERT INTO TIPOS_IDENTIFICACION(ID, NOMBRE) VALUES (2, 'TI');
INSERT INTO TIPOS_IDENTIFICACION(ID, NOMBRE) VALUES (3, 'PASAPORTE');

---- Insercion de tipos de planes de consumo
--- Sentencias SQL para la creacion de tipos de planes de consumo
INSERT INTO TIPOS_PLAN_DE_CONSUMOS(ID, NOMBRE) VALUES(1, 'LARGA_ESTADIA');
INSERT INTO TIPOS_PLAN_DE_CONSUMOS(ID, NOMBRE) VALUES(2, 'TIEMPO_COMPARTIDO');
INSERT INTO TIPOS_PLAN_DE_CONSUMOS(ID, NOMBRE) VALUES(3, 'TODO_INCLUIDO');
INSERT INTO TIPOS_PLAN_DE_CONSUMOS(ID, NOMBRE) VALUES(4, 'PARTICULAR');

---- Insercion de tipos de reserva de habitacion
--- Sentencias SQL para la creacion de tipos de reserva de habitacion
INSERT INTO TIPOS_RESERVA_HABITACION(ID, NOMBRE) VALUES(1, 'HABITACION_SUITE');
INSERT INTO TIPOS_RESERVA_HABITACION(ID, NOMBRE) VALUES(2, 'HABITACION_SUITE_PRESIDENCIAL');
INSERT INTO TIPOS_RESERVA_HABITACION(ID, NOMBRE) VALUES(3, 'HABITACION_SENCILLA');

---- Insercion de tipos de roles en el hotel
--- Sentencias SQL para la creacion de roles en el hotel
INSERT INTO TIPOS_ROL(ID, NOMBRE) VALUES(1, 'RECEPCIONISTA');
INSERT INTO TIPOS_ROL(ID, NOMBRE) VALUES(2, 'EMPLEADO');
INSERT INTO TIPOS_ROL(ID, NOMBRE) VALUES(3, 'ADMINISTRADOR');
INSERT INTO TIPOS_ROL(ID, NOMBRE) VALUES(4, 'GERENTE');

---- Insercion de tipos de mantenimiento en el hotel
--- Sentencias SQL para la creacion de tipos de mantenimiento en el hotel
INSERT INTO TIPOS_MANTENIMIENTOS(ID, NOMBRE) VALUES(1, 'HABITACION');
INSERT INTO TIPOS_MANTENIMIENTOS(ID, NOMBRE) VALUES(2, 'SERVICIO');

COMMIT;