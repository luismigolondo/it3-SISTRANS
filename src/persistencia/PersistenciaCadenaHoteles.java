package persistencia;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import negocio.Cliente;
import negocio.Convencion;
import negocio.Gasto;
import negocio.Habitacion;
import negocio.PlanesDeConsumo;
import negocio.RFC1;
import negocio.RFC11;
import negocio.RFC2;
import negocio.RFC3;
import negocio.RFC6;
import negocio.RFC7;
import negocio.RFC9;
import negocio.ReservaHabitacion;
import negocio.ReservaServicio;
import negocio.Servicio;

/**
 * Clase de persistencia de la cadena de hoteles.
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
public class PersistenciaCadenaHoteles {


	///--------------------------------------
	///---CONSTANTES
	///--------------------------------------

	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(PersistenciaCadenaHoteles.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	///--------------------------------------
	///---ATRIBUTOS
	///--------------------------------------

	private static PersistenciaCadenaHoteles instance;

	private PersistenceManagerFactory pmf;

	private List<String> tablas;

	private SQLUtil sqlUtil = new SQLUtil(this);

	private SQLClientes sqlClientes;

	private SQLConvenciones sqlConvenciones;

	private SQLEmpleados sqlEmpleados;

	private SQLGastos sqlGastos;

	private SQLHabitaciones sqlHabitaciones;

	private SQLHoteles sqlHoteles;

	private SQLPlanes_De_Consumo sqlPlanes_De_Consumo;

	private SQLProductos sqlProductos;

	private SQLReservas_Convencion sqlReservas_Convencion;

	private SQLReservas_Habitaciones sqlReservas_Habitaciones;

	private SQLReservas_Mantenimientos sqlReservas_Mantenimientos;

	private SQLReservas_Servicios sqlReservas_Servicios;

	private SQLServicios sqlServicios;

	private SQLTipo_Identificacion sqlTipo_Identificacion;

	private SQLTipo_Plan_De_Consumo sqlTipo_Plan_De_Consumo;

	private SQLTipo_Reserva__Habitacion sqlTipo_Reserva_Habitacion;

	private SQLTipo_Rol sqlTipo_Rol;

	private SQLConsultas sqlConsultas;

	private PersistenciaCadenaHoteles(){
		pmf = JDOHelper.getPersistenceManagerFactory("CadenaHoteles");
		crearClasesSQL();

		tablas = new LinkedList<String>();
		tablas.add("CadenaHoteles_sequence");
		tablas.add("CLIENTES");
		tablas.add("CONVENCIONES");
		tablas.add("EMPLEADOS");
		tablas.add("GASTOS");
		tablas.add("HABITACIONES");
		tablas.add("HOTELES");
		tablas.add("PLANES_DE_CONSUMO");
		tablas.add("PRODUCTOS");
		tablas.add("RESERVAS_CONVENCIONES");
		tablas.add("RESERVAS_HABITACIONES");
		tablas.add("RESERVAS_MANTENIMIENTOS");
		tablas.add("RESERVAS_SERVICIOS");
		tablas.add("SERVICIOS");
		tablas.add("TIPOS_IDENTIFICACION");
		tablas.add("TIPOS_PLAN_DE_CONSUMOS");
		tablas.add("TIPOS_RESERVA_HABITACION");
		tablas.add("TIPOS_ROL");
	}

	private PersistenciaCadenaHoteles(JsonObject configTabla)
	{
		crearClasesSQL();
		tablas = leerNombresTablas(configTabla);

		String unidadPersistencia = configTabla.get("unidadPersistencia").getAsString();
		log.trace ("Se esta accediendo a la persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory(unidadPersistencia);		
	}


	/* ****************************************************************
	 * 			M�todos para manejar las CONVENCIONES
	 *****************************************************************/

	public Convencion adicionarConvencion(String fIni, String fFin, long idHotel, long pIdConvencion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlConvenciones.adicionarConvencion(pm, pIdConvencion,fIni,fFin,idHotel);
			tx.commit();

			log.trace ("Inserci�n de convencion " + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Convencion(pIdConvencion,fIni,fFin,idHotel);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public String eliminarConvencion(long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			String resp = sqlConvenciones.eliminarConvencion(pm, id);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return "No se pudo borrar la convencion";
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Convencion darConvencion(long id)
	{
		return sqlConvenciones.darConvencion (pmf.getPersistenceManager(), id);
	}

	public List<Convencion> darConvenciones()
	{
		return sqlConvenciones.darConvenciones((pmf.getPersistenceManager()));
	}

	private void crearClasesSQL ()
	{
		sqlClientes = new SQLClientes(this);
		sqlConvenciones = new SQLConvenciones(this);
		sqlEmpleados = new SQLEmpleados(this);
		sqlGastos = new SQLGastos(this);
		sqlHabitaciones = new SQLHabitaciones(this);
		sqlHoteles = new SQLHoteles(this);
		sqlPlanes_De_Consumo = new SQLPlanes_De_Consumo(this);
		sqlProductos = new SQLProductos(this);
		sqlReservas_Convencion=new SQLReservas_Convencion(this);
		sqlReservas_Habitaciones = new SQLReservas_Habitaciones(this);
		sqlReservas_Mantenimientos = new SQLReservas_Mantenimientos(this);
		sqlReservas_Servicios = new SQLReservas_Servicios(this);
		sqlServicios = new SQLServicios(this);
		sqlTipo_Identificacion = new SQLTipo_Identificacion(this);
		sqlTipo_Plan_De_Consumo = new SQLTipo_Plan_De_Consumo(this);
		sqlTipo_Reserva_Habitacion = new SQLTipo_Reserva__Habitacion(this);
		sqlTipo_Rol = new SQLTipo_Rol(this);
		sqlConsultas = new SQLConsultas(this);

	}

	public static PersistenciaCadenaHoteles getInstance() {
		if(instance == null)
			instance = new PersistenciaCadenaHoteles();
		return instance;
	}

	public static PersistenciaCadenaHoteles getInstance(JsonObject configuracionTabla) {
		if(instance == null)
			instance = new PersistenciaCadenaHoteles(configuracionTabla);
		return instance;
	}

	public void cerrarPersistencia() {
		pmf.close();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List<String> leerNombresTablas(JsonObject configTabla) {
		JsonArray nombres = configTabla.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}
		return resp;
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqCadenaHoteles ()
	{
		return tablas.get (0);
	}

	public String darTablaClientes()
	{
		return tablas.get(1);
	}

	public String darTablaConvenciones()
	{
		return tablas.get(2);
	}

	public String darTablaEmpleados()
	{
		return tablas.get(3);
	}

	public String darTablaGastos()
	{
		return tablas.get(4);
	}

	public String darTablaHabitaciones()
	{
		return tablas.get(5);
	}

	public String darTablaHoteles()
	{
		return  tablas.get(6);
	}

	public String darTablaPlanesDeConsumo()
	{
		return tablas.get(7);
	}

	public String darTablaProductos()
	{
		return tablas.get(8);
	}

	public String darTablaReservaConvencion()
	{
		return tablas.get(9);
	}

	public String darTablaReservasHabitaciones()
	{
		return tablas.get(10);
	}

	public String darTablaReservasMantenimientos()
	{
		return tablas.get(11);
	}

	public String darTablaReservasServicios()
	{
		return tablas.get(12);
	}

	public String darTablaServicios()
	{
		return tablas.get(13);
	}

	public String darTablaTipoIdentificacion(){
		return tablas.get(14);
	}

	public String darTablaTipoPlanDeConsumo()
	{
		return tablas.get(15);
	}

	public String darTablaTipoReservaHabitacion()
	{
		return tablas.get(16);
	}

	public String darTablaTipoRol()
	{
		return tablas.get(17);
	}

	/**
	 * Transacci�n para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicaci�n
	 * @return El siguiente n�mero del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		System.out.println(sqlUtil);
		System.out.println(pmf);
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle espec�fico del problema encontrado
	 * @param e - La excepci�n que ocurrio
	 * @return El mensaje de la excepci�n JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * FR7 REGISTRAR UNA RESERVA DE ALOJAMIENTO
	 *Reserva una habitación por un período de tiempo, por parte de un cliente, siempre y cuando esté disponible.
	 * Esta operación es realizada por un cliente.
	 * @param pId id de la reserva
	 * @param pIdCliente id del cliente de la reserva
	 * @param pIdTipoId tipo de cedula del cliente
	 * @param pIdHabitacion la habitacion asignada a la reserva
	 * @param pIdPlanDeConsumo el tipo de plan de reserva que se asigna
	 * @param pFechaInicio la fecha en la que inicia la reserva.
	 * @param pFechaFin la fecha en la que termina una reserva.
	 * @return
	 */
	public ReservaHabitacion RF7adicionarReservaHabitacion(long pId, long pIdCliente, long pIdTipoId, long pIdHabitacion,
			long pIdPlanDeConsumo, String pFechaInicio, String pFechaFin) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long tupla = sqlReservas_Habitaciones.adicionarReserva(pm, pId, pIdCliente, pIdTipoId, pIdHabitacion, pIdPlanDeConsumo, pFechaInicio, pFechaFin);
			tx.commit();

			log.trace("Insercion reserva habitacion: " + pId + ": " + tupla + " tuplas insertadas" );
			long [] idCliente = new long [] {pIdCliente, pIdTipoId};
			return new ReservaHabitacion(pId, idCliente, pIdHabitacion, pIdPlanDeConsumo, pFechaInicio, pFechaFin);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * 
	 * @return
	 */
	public ReservaServicio RF8adicionarReservaServicio(long id, String horaInicio, String horaFin, long pIdCliente, long pIdTipoId, long servicio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long tupla = sqlReservas_Servicios.adicionarReserva(pmf.getPersistenceManager(), id, horaInicio, horaFin, pIdCliente, pIdTipoId, servicio);
			tx.commit();

			log.trace("Insercion reserva servicio: " + id + ": " + tupla + " tuplas insertadas" );
			long [] cliente = new long [] {pIdCliente, pIdTipoId};
			return new ReservaServicio(id, horaInicio, horaFin, cliente, servicio);
		}
		catch (Exception e) {
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;		
		}
		finally
		{
			if(tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public long pazYSalvoCliente(Long idRH) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlReservas_Habitaciones.pazYSalvoCliente(pm,idRH);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return -1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public long RF9registrarLlegadaCliente(long pIdReserva) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlReservas_Habitaciones.registrarLlegadaCliente(pm,pIdReserva);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return -1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	//servicio, nosotros manejamos lo que ofrecen los servicios como productos.
	public Gasto RF10registrarConsumoServicio(long idHabitacion, long idProducto, String pFecha)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try{
			tx.begin();
			//ME DI CUENTA QUE EN NEGOCIO, GASTO NO TENIA ID
			//LO AGREGUE ATT JUAN DAVID
			//PARA ITERACION 2
			long idGasto = nextval();
			long tupla = sqlGastos.registrarConsumoServicio(pm,idGasto,idHabitacion,idProducto, pFecha);
			tx.commit();

			return new Gasto(idGasto,idHabitacion, idProducto);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long RF11registrarSalidaCliente(long idReserva)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlReservas_Habitaciones.registrarSalidaCliente(pm,idReserva);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return -1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}

	}

	public String RF12reservarHabServs(long idConvencion, long idHotel, String habs, String servs)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long idx = nextval();
			String resp = sqlReservas_Convencion.registrarHabsServs(pm,idx,idConvencion, idHotel, habs,servs);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return ""+-1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public String RF13cancelarReservasConvencion(long idConvencion, String habs, String servs)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			String resp = sqlReservas_Convencion.eliminarReservas(pm,idConvencion, habs,servs);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return ""+-1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public String RF14registrarFinConvencion(long id)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			String resp = sqlConvenciones.eliminarConvencion(pm, id);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return ""+-1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}

	}

	public long pazYSalvoConvencion(long IdConv)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			long resp = sqlConvenciones.pazYSalvoConvencion(pm, IdConv);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return -1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public String RF15registrarEntradaMantenimiento(long id, long idHotel, String habs, String servs, String fechaIni, String fechaFin)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			String resp = sqlReservas_Mantenimientos.registrarFinMantenimiento(pm, idHotel, habs,servs);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return ""+-1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}

	public String RF16registrarFinMantenimiento(long idHotel, String habs, String servs)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			String resp = sqlReservas_Mantenimientos.registrarFinMantenimiento(pm, idHotel, habs,servs);
			tx.commit();
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : "+e.getMessage()+ "\n" + darDetalleException(e));
			return ""+-1;
		}
		finally{
			if(tx.isActive())
				tx.rollback();
			pm.close();
		}
	}


	public long[] limpiarParranderos() {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarCadenaHoteles(pm);
			tx.commit();
			log.info("Borrada la base de datos de hoteles");
			return resp;
		}
		catch(Exception e)
		{
			log.error("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Metodo que consulta un cliente por su cc.
	 * @param idCliente el numero de id
	 * @param idTipo el tipo del id
	 * @return
	 */
	public Cliente darCLientePorId(long idCliente) {
		return (Cliente) sqlClientes.darClientePorId(pmf.getPersistenceManager(), idCliente);
	}

	public Cliente adicionarCliente(long idHotel, long idCliente, long tipoId, long idHabitacion, long idServicio, String nombreUsuario,
			String correoUsuario) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try
		{
			tx.begin();
			long tupla = sqlClientes.adicionarCliente(pmf.getPersistenceManager(), idCliente, tipoId, idHotel, 
					idHabitacion, idServicio, nombreUsuario, correoUsuario);
			tx.commit();

			log.trace("Insercion de cliente: " + nombreUsuario + " : " + tupla + "tuplas insertadas");
			return new Cliente(new long[] {idCliente, tipoId}, nombreUsuario, correoUsuario, null, idHotel, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public long cambiarReservaServicioCliente(long idCliente, long idSerivcio)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlClientes.cambiarReservaClietne(pm, idCliente, idSerivcio);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public List<RFC1> rfc1(String fechaInicio, String fechaFin) {
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc1(pm, fechaInicio, fechaFin);
	}

	public List<RFC2> rfc2() {
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc2(pm);
	}

	public List<RFC3> rfc3(String fechaInicio, String fechaFin) {
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc3(pm, fechaInicio, fechaFin);
	}

	public List<Servicio> rfc4(long idServicio, long idHotel, String nombre, int horaA, int horaC, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc4(pm, idServicio, idHotel, nombre, horaA, horaC, tipo);
	}

	public List<RFC6> rfc6()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc6(pm);
	}

	public List<RFC7> rfc7()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc7(pm);
	}

	public List<RFC9> rfc9(int servicioSeleccionado, String ascdesc, String inic, String fin) {
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc9(pm, servicioSeleccionado, ascdesc, inic, fin);
	}

	public String rfc10(int servicioSeleccionado, String ascdesc,String inic, String fin) {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc10(pm, servicioSeleccionado,ascdesc,inic, fin);
	}

	public List<RFC11> rfc11() {
		// TODO Auto-generated method stub
		return null;
	}

	public String rfc12() {
		// TODO Auto-generated method stub
		PersistenceManager pm = pmf.getPersistenceManager();
		return sqlConsultas.rfc12(pm);
	}

}
