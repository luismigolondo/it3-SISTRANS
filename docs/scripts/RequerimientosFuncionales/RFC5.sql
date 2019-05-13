SELECT *
((FROM HABITACIONES h INNER JOIN Gastos g ON h.id=g.idHabitacion) INNER JOIN Productos p ON g.idProducto=p.id )	 
INNER JOIN ReservaHabitaciones r ON r.TIPO_IDENTIFICACION=numDado AND r.ID_CLIENTEnumDado AND r.fecha_inicio<=fechaDadaIni AND r.fecha_fin>=fechaDadaFin;