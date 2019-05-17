/*
Este archivo contiene el codigo para la creacion de los indices necesarios
para la optimizacion de consultas de la base de datos. 
*/
--RFC9
CREATE INDEX RFC91 ON CLIENTES (ID);
CREATE INDEX RFC92 ON RESERVAS_SERVICIOS (ID_CLIENTE, ID_SERVICIO, HORA_APERTURA);

--RFC10

--RFC11

--RFC12