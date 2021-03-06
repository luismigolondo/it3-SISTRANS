SELECT p.SEMANA, p.ID_SERVICIO_MAX, p.MAXIMO, p.ID_SERVICIO_MIN, p.MINIMO, d.ID_HABITACION_MAX, d.MAXIMO AS MAX_HABITACION, d.ID_HABITACION_MIN, d.MINIMO AS MIN_HABITACION
FROM (SELECT w.SEMANA, w.SERVICIOS AS ID_SERVICIO_MAX, w.MAXIMO, j.SERVICIOS AS ID_SERVICIO_MIN, j.MINIMO
FROM (SELECT z.SEMANA, l.SERVICIOS, z.MAXIMO
FROM (SELECT SEMANA, MAX(CUENTA) MAXIMO
FROM (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA
FROM RESERVAS_SERVICIOS r
GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO
ORDER BY SEMANA ASC) tabla
GROUP BY SEMANA) z, (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA
FROM RESERVAS_SERVICIOS r
GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO
ORDER BY SEMANA ASC) l
WHERE z.MAXIMO = l.CUENTA) w, (SELECT z.SEMANA, l.SERVICIOS, z.MINIMO
FROM (SELECT SEMANA, MIN(CUENTA) MINIMO
FROM (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA
FROM RESERVAS_SERVICIOS r
GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO
ORDER BY SEMANA ASC) tabla
GROUP BY SEMANA) z, (SELECT to_char(r.HORA_APERTURA,'WW') SEMANA, r.ID_SERVICIO SERVICIOS, COUNT(ID_SERVICIO) CUENTA
FROM RESERVAS_SERVICIOS r
GROUP BY to_char(r.HORA_APERTURA,'WW'), r.ID_SERVICIO
ORDER BY SEMANA ASC) l
WHERE z.MINIMO = l.CUENTA) j
WHERE w.SEMANA = j.SEMANA) p, (SELECT x.SEMANA, x.HABITACIONES AS ID_HABITACION_MAX, x.MAXIMO, n.HABITACIONES AS ID_HABITACION_MIN, n.MINIMO
FROM (SELECT v.SEMANA, m.HABITACIONES, v.MAXIMO
FROM (SELECT SEMANA, MAX(CUENTA) MAXIMO
FROM (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA
FROM RESERVAS_HABITACIONES c
GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION
ORDER BY SEMANA ASC) a
GROUP BY SEMANA) v, (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA
FROM RESERVAS_HABITACIONES c
GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION
ORDER BY SEMANA ASC) m
WHERE v.MAXIMO = m.CUENTA) x, (SELECT v.SEMANA, m.HABITACIONES, v.MINIMO
FROM (SELECT SEMANA, MIN(CUENTA) MINIMO
FROM (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA
FROM RESERVAS_HABITACIONES c
GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION
ORDER BY SEMANA ASC) a
GROUP BY SEMANA) v, (SELECT to_char(c.FECHA_INICIO,'WW') SEMANA, c.ID_HABITACION HABITACIONES, COUNT(ID_HABITACION) CUENTA
FROM RESERVAS_HABITACIONES c
GROUP BY to_char(c.FECHA_INICIO,'WW'), c.ID_HABITACION
ORDER BY SEMANA ASC) m
WHERE v.MINIMO = m.CUENTA) n
WHERE x.SEMANA = n.SEMANA) d
WHERE p.SEMANA = d.SEMANA;