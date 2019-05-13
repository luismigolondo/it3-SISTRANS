--- Sentencias SQL para la creación del esquema de Cadena de Hoteles

-- USO
-- Copie el contenido de este archivo en una pestaña SQL de SQL Developer
-- Ejecútelo como un script - Utilice el botón correspondiente de la pestaña utilizada

-- Creación del secuenciador
create sequence CadenaHoteles_sequence;

/*
CREACION DE LAS ENUMERACIONES...
*/

CREATE TABLE TIPOS_RESERVA_HABITACION (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL
);


CREATE TABLE TIPOS_IDENTIFICACION (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL
);


CREATE TABLE TIPOS_PLAN_DE_CONSUMOS (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL
);

CREATE TABLE TIPOS_ROL (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL
);

CREATE TABLE TIPOS_MANTENIMIENTOS (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL
);


/*
CREACION DE LAS TABLAS Y COLUMNAS
*/

CREATE TABLE HOTELES (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL,
	ESTRELLAS NUMBER NOT NULL
);


CREATE TABLE SERVICIOS (
	ID NUMBER NOT NULL,
	ID_HOTEL NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL,
	HORA_APERTURA VARCHAR(255) NOT NULL,
	HORA_CIERRE VARCHAR(255) NOT NULL,
	TIPO VARCHAR(255) NOT NULL,
	DESCRIPCION VARCHAR(255) NOT NULL
);

CREATE TABLE PRODUCTOS (
	ID NUMBER NOT NULL,
	ID_SERVICIO NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL,
	VALOR NUMBER NOT NULL
);

CREATE TABLE RESERVAS_HABITACIONES (
	ID NUMBER NOT NULL,
	ID_CLIENTE NUMBER NOT NULL,
	TIPO_IDENTIFICACION NUMBER NOT NULL,
	ID_HABITACION NUMBER NOT NULL,
	ID_PLAN_DE_CONSUMO NUMBER NOT NULL,
	FECHA_INICIO DATE NOT NULL,
	FECHA_FIN DATE NOT NULL,
	CHECKED_IN NUMBER(1) NOT NULL,
	CHECKED_OUT NUMBER(1) NOT NULL
);

CREATE TABLE RESERVAS_SERVICIOS (
	ID NUMBER NOT NULL,
	ID_CLIENTE NUMBER NOT NULL,
	TIPO_IDENTIFICACION NUMBER NOT NULL,
	ID_SERVICIO NUMBER NOT NULL,
	HORA_APERTURA DATE NOT NULL,
	HORA_CIERRE DATE NOT NULL
);

CREATE TABLE HABITACIONES (
	ID NUMBER NOT NULL,
	ID_HOTEL NUMBER NOT NULL,
	CAPACIDAD NUMBER NOT NULL,
	DOTACION VARCHAR(255) NOT NULL,
	TIPO_HABITACION NUMBER NOT NULL
);


CREATE TABLE CLIENTES (
	ID NUMBER NOT NULL,
	TIPO_IDENTIFICACION NUMBER NOT NULL,
	ID_HOTEL NUMBER NOT NULL,
	ID_RESERVA_HABITACION NUMBER,
	ID_RESERVA_SERVICIO NUMBER,
	ID_CONVENCION NUMBER,
	NOMBRE VARCHAR(255) NOT NULL,
	CORREO_ELECTRONICO VARCHAR(255) NOT NULL
);


CREATE TABLE PLANES_DE_CONSUMO (
	ID NUMBER NOT NULL,
	ID_HOTEL NUMBER NOT NULL,
	TIPO_PLAN_DE_CONSUMO NUMBER NOT NULL,
	MINIMO_DE_NOCHES NUMBER NOT NULL,
	DESCUENTO NUMBER,
	COSTO_FIJO NUMBER NOT NULL
);


CREATE TABLE EMPLEADOS (
	ID NUMBER NOT NULL,
	TIPO_IDENTIFICACION NUMBER NOT NULL,
	ID_HOTEL NUMBER NOT NULL,
	ID_ROL NUMBER NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL,
	CORREO_ELECTRONICO VARCHAR(255) NOT NULL,
	SALARIO NUMBER NOT NULL
);


CREATE TABLE GASTOS (
	ID NUMBER NOT NULL,
	ID_PRODUCTO NUMBER NOT NULL,
	ID_HABITACION NUMBER NOT NULL,
	FECHA_DE_GASTO DATE NOT NULL
);

CREATE TABLE RESERVAS_MANTENIMIENTOS (
	ID NUMBER NOT NULL,
	ID_HABITACION NUMBER,
	ID_SERVICIO NUMBER,
	TIPO_MANTENIMIENTO VARCHAR(255) NOT NULL,
	EN_MANTENIMIENTO NUMBER(1) NOT NULL,
	FECHA_INICIO DATE NOT NULL,
	FECHA_FIN DATE NOT NULL	
);

CREATE TABLE CONVENCIONES (
	ID NUMBER NOT NULL,
	ID_HOTEL NUMBER NOT NULL,
	FECHA_INICIO DATE NOT NULL,
	FECHA_FIN DATE NOT NULL
);

CREATE TABLE RESERVAS_CONVENCIONES (
	ID NUMBER NOT NULL,
	ID_CONVENCION NUMBER NOT NULL,
	ID_HABITACION NUMBER,
	ID_SERVICIO NUMBER
);

/*Primary Keys*/

ALTER TABLE TIPOS_RESERVA_HABITACION
	ADD CONSTRAINT IdTipoReserva_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE TIPOS_IDENTIFICACION
	ADD CONSTRAINT IdTipoIdentificacion_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE TIPOS_PLAN_DE_CONSUMOS
	ADD CONSTRAINT IdTipoPDC_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE TIPOS_ROL
	ADD CONSTRAINT IdTipoRol_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE TIPOS_MANTENIMIENTOS
	ADD CONSTRAINT IdTipoMantenimiento_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE HOTELES
	ADD CONSTRAINT IdHotel_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE SERVICIOS
	ADD CONSTRAINT IdServicio_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE PRODUCTOS
	ADD CONSTRAINT IdProducto_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_HABITACIONES
	ADD CONSTRAINT IdReservaHabitacion_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_SERVICIOS
	ADD CONSTRAINT IdReservaServicio_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE HABITACIONES
	ADD CONSTRAINT IdHabitacion_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE CLIENTES
	ADD CONSTRAINT IdCliente_PK
	PRIMARY KEY (ID, TIPO_IDENTIFICACION)
	RELY DISABLE NOVALIDATE;


ALTER TABLE PLANES_DE_CONSUMO
	ADD CONSTRAINT IdPConsumo_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE EMPLEADOS
	ADD CONSTRAINT IdEmpleado_PK
	PRIMARY KEY (ID, TIPO_IDENTIFICACION)
	RELY DISABLE NOVALIDATE;


ALTER TABLE GASTOS
	ADD CONSTRAINT IdGasto_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_MANTENIMIENTOS
	ADD CONSTRAINT IdReservaMantenimiento_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE CONVENCIONES
	ADD CONSTRAINT IdConvencion_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_CONVENCIONES
	ADD CONSTRAINT IdReservaConvencion_PK
	PRIMARY KEY (ID)
	RELY DISABLE NOVALIDATE;

/*FOREIGN KEYS*/

ALTER TABLE SERVICIOS
	ADD CONSTRAINT HotelDelServicio_FK
	FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE PRODUCTOS
	ADD CONSTRAINT ServicioDelProducto_FK
	FOREIGN KEY (ID_SERVICIO) REFERENCES SERVICIOS(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_HABITACIONES
	ADD CONSTRAINT ClienteDeLaReserva_FK
	FOREIGN KEY (ID_CLIENTE, TIPO_IDENTIFICACION) REFERENCES CLIENTES(ID, TIPO_IDENTIFICACION)
	RELY DISABLE NOVALIDATE;
ALTER TABLE RESERVAS_HABITACIONES
	ADD CONSTRAINT HabitacionDeLaReserva_FK
	FOREIGN KEY (ID_HABITACION) REFERENCES HABITACIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE RESERVAS_HABITACIONES
	ADD CONSTRAINT PlanConsumoDeLaReserva_FK
	FOREIGN KEY (ID_PLAN_DE_CONSUMO) REFERENCES PLANES_DE_CONSUMO(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_SERVICIOS
	ADD CONSTRAINT ClienteDeLaReservaServicio_FK
	FOREIGN KEY (ID_CLIENTE, TIPO_IDENTIFICACION) REFERENCES CLIENTES(ID, TIPO_IDENTIFICACION)
	RELY DISABLE NOVALIDATE;
ALTER TABLE RESERVAS_SERVICIOS
	ADD CONSTRAINT ServicioDeLaReservaServicio_FK
	FOREIGN KEY (ID_SERVICIO) REFERENCES SERVICIOS(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE HABITACIONES
	ADD CONSTRAINT HotelDeLaHabitacion_FK
	FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE HABITACIONES
	ADD CONSTRAINT TipoDeLaHabitacion_FK
	FOREIGN KEY (TIPO_HABITACION) REFERENCES TIPOS_RESERVA_HABITACION(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE CLIENTES
	ADD CONSTRAINT TipoIdDelCliente_FK
	FOREIGN KEY (TIPO_IDENTIFICACION) REFERENCES TIPOS_IDENTIFICACION(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE CLIENTES
	ADD CONSTRAINT ConvencionDelCliente_FK
	FOREIGN KEY (ID_CONVENCION) REFERENCES CONVENCIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE CLIENTES
	ADD CONSTRAINT HotelDelCliente_FK
	FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE CLIENTES
	ADD CONSTRAINT ReservaHDelCliente_FK
	FOREIGN KEY (ID_RESERVA_HABITACION) REFERENCES RESERVAS_HABITACIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE CLIENTES
	ADD CONSTRAINT ReservaSDelCliente_FK
	FOREIGN KEY (ID_RESERVA_SERVICIO) REFERENCES RESERVAS_SERVICIOS(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE PLANES_DE_CONSUMO
	ADD CONSTRAINT HotelDelPlan_FK
	FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE PLANES_DE_CONSUMO
	ADD CONSTRAINT TipoDePlan_FK
	FOREIGN KEY (TIPO_PLAN_DE_CONSUMO) REFERENCES TIPOS_PLAN_DE_CONSUMOS(ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE EMPLEADOS
	ADD CONSTRAINT HotelDelEmpleado_FK
	FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE EMPLEADOS
	ADD CONSTRAINT RolDelEmpleado_FK
	FOREIGN KEY (ID_ROL) REFERENCES TIPOS_ROL(ID)
	RELY DISABLE NOVALIDATE;


ALTER TABLE GASTOS
	ADD CONSTRAINT HabitacionDelGasto_FK
	FOREIGN KEY (ID_HABITACION) REFERENCES HABITACIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE GASTOS
	ADD CONSTRAINT ProductoDelGasto_FK
	FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_MANTENIMIENTOS
	ADD CONSTRAINT HabitacionDeReserva_FK
	FOREIGN KEY (ID_HABITACION) REFERENCES HABITACIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE RESERVAS_MANTENIMIENTOS
	ADD CONSTRAINT ServicioDeReserva_FK
	FOREIGN KEY (ID_SERVICIO) REFERENCES SERVICIOS(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE CONVENCIONES
	ADD CONSTRAINT IdDelHotel_FK
	FOREIGN KEY (ID_HOTEL) REFERENCES HOTELES(ID)
	RELY DISABLE NOVALIDATE;

ALTER TABLE RESERVAS_CONVENCIONES
	ADD CONSTRAINT IdConvencionReserva_FK
	FOREIGN KEY (ID_CONVENCION) REFERENCES CONVENCIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE RESERVAS_CONVENCIONES
	ADD CONSTRAINT HReservaConvencion_FK
	FOREIGN KEY (ID_HABITACION) REFERENCES HABITACIONES(ID)
	RELY DISABLE NOVALIDATE;
ALTER TABLE RESERVAS_CONVENCIONES
	ADD CONSTRAINT SReservaConvencion_FK
	FOREIGN KEY (ID_SERVICIO) REFERENCES SERVICIOS(ID)
	RELY DISABLE NOVALIDATE;

/*CHECKS*/

ALTER TABLE TIPOS_RESERVA_HABITACION
	ADD CONSTRAINT TipoReserva_CK
	CHECK ((ID > 0) 
		AND (NOMBRE IS NOT NULL)
	);

ALTER TABLE TIPOS_IDENTIFICACION
	ADD CONSTRAINT TipoID_CK
	CHECK ((ID > 0) 
		AND (NOMBRE IS NOT NULL)
	);

ALTER TABLE TIPOS_PLAN_DE_CONSUMOS
	ADD CONSTRAINT TipoPDC_CK
	CHECK ((ID > 0) 
		AND (NOMBRE IS NOT NULL)
	);

ALTER TABLE TIPOS_ROL
	ADD CONSTRAINT TipoRol_CK
	CHECK ((ID > 0) 
		AND (NOMBRE IS NOT NULL)
	);

ALTER TABLE TIPOS_MANTENIMIENTOS
	ADD CONSTRAINT TipoMantenimiento_CK
	CHECK (ID > 0);	

ALTER TABLE HOTELES
	ADD CONSTRAINT Hotel_CK
	CHECK ((ID > 0) 
		AND (NOMBRE IS NOT NULL)
		AND (ESTRELLAS BETWEEN 1 AND 5)
	);

ALTER TABLE SERVICIOS
	ADD CONSTRAINT Servicios_CK
	CHECK ((ID > 0)
		AND (ID_HOTEL > 0)
		AND (NOMBRE IS NOT NULL)
		AND (HORA_APERTURA IS NOT NULL)
		AND (HORA_CIERRE IS NOT NULL)
		AND (TIPO IS NOT NULL)
		AND (DESCRIPCION IS NOT NULL)
	);

ALTER TABLE PRODUCTOS
	ADD CONSTRAINT Producto_CK
	CHECK ((ID > 0) 
		AND (ID_SERVICIO > 0)
		AND (NOMBRE IS NOT NULL)
		AND (VALOR > 0)
	);

ALTER TABLE RESERVAS_HABITACIONES
	ADD CONSTRAINT Reserva_CK
	CHECK ((ID > 0) 
		AND (ID_CLIENTE > 0)
		AND (TIPO_IDENTIFICACION > 0)
		AND (ID_HABITACION > 0)
		AND (ID_PLAN_DE_CONSUMO > 0)
		AND (CHECKED_IN BETWEEN 0 AND 1)
		AND (CHECKED_OUT BETWEEN 0 AND 1)
	);

ALTER TABLE RESERVAS_SERVICIOS
	ADD CONSTRAINT ReservaServicios_CK
	CHECK ((ID > 0) 
		AND (ID_CLIENTE > 0)
		AND (TIPO_IDENTIFICACION > 0)
		AND (ID_SERVICIO > 0)
		AND (HORA_APERTURA IS NOT NULL)
		AND (HORA_CIERRE IS NOT NULL)
	);

ALTER TABLE HABITACIONES
	ADD CONSTRAINT Habitacion_CK
	CHECK ((ID > 0) 
		AND (ID_HOTEL > 0)
		AND (TIPO_HABITACION > 0)
		AND (CAPACIDAD > 0)
		AND (DOTACION IS NOT NULL)
	);

ALTER TABLE CLIENTES
	ADD CONSTRAINT Cliente_CK
	CHECK ((ID > 0) 
		AND (ID_HOTEL > 0)
		AND (ID_RESERVA_SERVICIO >= 0)
		AND (ID_RESERVA_HABITACION >= 0)
		AND (ID_CONVENCION >= 0)
		AND (NOMBRE IS NOT NULL)
		AND (CORREO_ELECTRONICO IS NOT NULL)
		AND (TIPO_IDENTIFICACION > 0)
	);

ALTER TABLE PLANES_DE_CONSUMO
	ADD CONSTRAINT PDC_CK
	CHECK ((ID > 0) 
		AND (ID_HOTEL > 0)
		AND (TIPO_PLAN_DE_CONSUMO > 0)
		AND (MINIMO_DE_NOCHES > 0)
		AND (COSTO_FIJO > 0)
	);

ALTER TABLE EMPLEADOS
	ADD CONSTRAINT Empleado_CK
	CHECK ((ID > 0) 
		AND (ID_HOTEL > 0)
		AND (ID_ROL > 0)
		AND (NOMBRE IS NOT NULL)
		AND (CORREO_ELECTRONICO IS NOT NULL)
		AND (TIPO_IDENTIFICACION > 0)
		AND (SALARIO > 0)
	);

ALTER TABLE GASTOS
	ADD CONSTRAINT Gasto_CK
	CHECK ((ID > 0) 
		AND (ID_PRODUCTO > 0)
		AND (ID_HABITACION > 0)
	);

ALTER TABLE CONVENCIONES
	ADD CONSTRAINT Convencion_CK
	CHECK ((ID >= 0)
		AND (ID_HOTEL > 0)
	);

ALTER TABLE RESERVAS_CONVENCIONES
	ADD CONSTRAINT ReservaConvencion_CK
	CHECK ((ID >= 0)
		AND (ID_CONVENCION > 0)
	);
COMMIT;