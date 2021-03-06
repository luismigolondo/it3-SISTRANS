/*
Este archivo contiene el codigo para la creacion de los indices necesarios
para la optimizacion de consultas de la base de datos. 
*/
--RFC9 Y RFC10
CREATE INDEX RFC91 ON CLIENTES (ID);
CREATE INDEX RFC92 ON RESERVAS_SERVICIOS (ID_CLIENTE, ID_SERVICIO, HORA_APERTURA);

--RFC11
CREATE INDEX RFC111 ON RESERVAS_SERVICIOS (ID_SERVICIO, HORA_APERTURA);
CREATE INDEX RFC112 ON RESERVAS_HABITACIONES (ID_HABITACION, FECHA_INICIO);

--RFC12
CREATE INDEX RFC121 ON CLIENTES (ID, ID_RESERVA_HABITACION);
CREATE INDEX RFC122 ON GASTOS (ID_PRODUCTO);
CREATE INDEX RFC123 ON PRODUCTOS (ID);
CREATE INDEX RFC124 ON RESERVAS_HABITACIONES (ID);