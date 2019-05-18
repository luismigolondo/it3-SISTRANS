/**
 * 
 */
package negocio;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.JsonObject;

import persistencia.PersistenciaCadenaHoteles;

/**
 * Clase principal de la cadena de hoteles.
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
public class CadenaHoteles {
	private static Logger log = Logger.getLogger(Hotel.class.getName());

	private PersistenciaCadenaHoteles persistencia;

	/* ****************************************************************
	 * 			M�todos para manejar las CONVENCIONES
	 *****************************************************************/

	public Convencion adicionarConvencion(String fIni, String fFin, long idHotel, long pIdConvencion)
	{
		log.info("Adicionando convencion: ");
		Convencion convencion = persistencia.adicionarConvencion(fIni,fFin,idHotel, pIdConvencion);
		log.info("Se adiciono convencion: ");
		return convencion;
	}

	public String eliminarConvencion(long id)
	{
		log.info("Eliminando convencion: ");
		String convencion = persistencia.eliminarConvencion(id);
		log.info("Se aelimino convencion: ");
		return convencion;
	}

	public Convencion darConvencion(long id)
	{
		log.info("Obteniendo convencion: ");
		Convencion convencion = persistencia.darConvencion(id);
		log.info("Se obtuvo convencion: ");
		return convencion;
	}

	public CadenaHoteles() {
		persistencia = PersistenciaCadenaHoteles.getInstance();
	}
	public CadenaHoteles(JsonObject configuracionTabla)
	{
		persistencia = PersistenciaCadenaHoteles.getInstance(configuracionTabla);
	}

	public void cerrarPersistencia() {
		persistencia.cerrarPersistencia();
	}

	public long pazYSalvoCliente(Long idRH) {
		log.info("Registrando paz y salvo cliente: " );
		long checkout = persistencia.pazYSalvoCliente(idRH);
		log.info("Se realiz� el registro de paz y salvo de cliente...");
		return checkout;

	}

	//RF7
	public ReservaHabitacion adicionarReservaHabitacion(long pId, long pIdCliente, long pIdTipoId, long pIdHabitacion,
			long pIdPlanDeConsumo, String pFechaInicio, String pFechaFin)
	{
		log.info("Creando reserva habitacion: " + pId + ", Para el cliente: " + pIdCliente);
		ReservaHabitacion reserva = persistencia.RF7adicionarReservaHabitacion(pId, pIdCliente, pIdTipoId, pIdHabitacion, pIdPlanDeConsumo, pFechaInicio, pFechaFin);
		log.info("Reserva creada: " + reserva);
		return reserva;
	}

	//RF8
	public ReservaServicio adicionarReservaServicio(long id, String horaInicio, String horaFin, long pIdCliente, 
			long pIdTipoId, long servicio)
	{
		log.info("Creando reserva servicio: " + id + ", Para el cliente: " + pIdCliente);
		ReservaServicio reserva = persistencia.RF8adicionarReservaServicio(id, horaInicio, horaFin, pIdCliente, pIdTipoId, servicio);
		persistencia.cambiarReservaServicioCliente(pIdCliente, servicio);
		log.info("Reserva creada: " + reserva);
		return reserva;
	}
	//RF9
	public long registrarLlegadaCliente(Long pIdReserva)
	{
		log.info("Registrando cliente:  de la reserva: " + pIdReserva);
		long registrar = persistencia.RF9registrarLlegadaCliente(pIdReserva);
		log.info("La reserva: " + registrar + " fue activada.");
		return registrar;
	}

	//RF10
	public Gasto registrarConsumoServicio(long idHabitacion, long idProducto, String pFecha)
	{
		log.info("Registrando consumo cliente de la habitacion : "+ idHabitacion +" el producto " + idProducto + " para la fecha: " + pFecha);
		Gasto gasto = persistencia.RF10registrarConsumoServicio(idHabitacion, idProducto, pFecha);
		log.info("Se agrego el producto " + idProducto + " a la cuenta de la habitación "+idHabitacion);
		return gasto;
	}

	//RF11
	public long registrarSalidaCliente(long pIdReserva)
	{
		log.info("Realizando checkout de la reserva: " + pIdReserva);
		long checkout = persistencia.RF11registrarSalidaCliente(pIdReserva);
		return checkout;
	}

	//RF12
	public String reservarHabServs(long idConvencion, long idHotel, String habs, String servs)
	{
		log.info("Realizando la reserva de habitaciones y servicios: " );
		String checkout = persistencia.RF12reservarHabServs(idConvencion, idHotel, habs, servs);
		log.info("Se realiz� la reserva de habitaciones y servicios");
		return checkout;
	}

	//RF13
	public String cancelarReservasConvencion(long idConvencion, String habs, String servs)
	{
		log.info("Realizando cancelacion de las reserva de habitaciones y servicios: " );
		String checkout = persistencia.RF13cancelarReservasConvencion(idConvencion,habs,servs);
		log.info("Se realiz� la cancelacioj de las reservas de habitaciones y servicios");
		return checkout;
	}

	//RF14
	public String registrarFinConvencion(long id)
	{
		log.info("Registrando fin de la convencion: " );
		String checkout = persistencia.RF14registrarFinConvencion(id);
		log.info("Se realiz� el fin de la convencion");
		return checkout;
	}

	public long pazYSalvoConvencion(long IdConv)
	{
		log.info("Registrando paz y salvo convencion: " );
		long checkout = persistencia.pazYSalvoConvencion(IdConv);
		log.info("Se realiz� el fin de la convencion");
		return checkout;
	}

	//RF15
	public String registrarEntradaMantenimiento(long id, long idHotel, String habs, String servs, String fechaIni, String fechaFin)
	{
		log.info("Registrando entrada a mantenimiento: " );
		String checkout = persistencia.RF15registrarEntradaMantenimiento(id, idHotel, habs, servs, fechaIni,fechaFin);
		log.info("Se realiz� el registro a entrada de mantenimiento de...");
		return checkout;
	}

	//RF16
	public String registrarFinMantenimiento(long idHotel, String habs, String servs)
	{
		log.info("Registrando salida (fin) a mantenimiento: " );
		String checkout = persistencia.RF16registrarFinMantenimiento(idHotel,habs,servs);
		log.info("Se realiz� el registro de finalizacion de mantenimiento de...");
		return checkout;
	}

	/**
	 * Encuentra un cliente según su identificador
	 * @param idCliente - El identificador del cliente buscado
	 * @return Un objeto del cliente que se busca por id y tipo
	 */
	public Cliente darClientePorId (long idCliente)
	{
		log.info ("Dar información de un cliente por id: " + idCliente);
		Cliente cliente = persistencia.darCLientePorId(idCliente);
		log.info ("Buscando cliente por Id: " + idCliente != null ? idCliente  + "": "NO EXISTE");
		return cliente;
	}

	public long[] limpiarHoteles() {
		log.info ("Limpiando la BD de Cadena de Hoteles");
		long [] borrrados = persistencia.limpiarParranderos();	
		log.info ("Limpiando la BD de Hoteles: Listo!");
		return borrrados;
	}
	public VOCliente adicionarCliente(long idHotel, long idCliente, long tipoId, long idHabitacion, long idServicio, String nombreUsuario,
			String correoUsuario) {
		log.info ("Adicionando cliente: " + nombreUsuario);
		Cliente bebedor = persistencia.adicionarCliente(idHotel, idCliente, tipoId, idHabitacion, idServicio, nombreUsuario, correoUsuario);
		log.info ("Adicionando cliente: " + nombreUsuario);
		return bebedor;
	}

	public List<RFC1> rfc1(String fechaInicio, String fechaFin) {
		log.info("Generando consulta RFC1");
		List<RFC1> lista = new LinkedList<RFC1>();
		for (RFC1 r : persistencia.rfc1(fechaInicio, fechaFin))
		{
			lista.add(r);
		}
		log.info("Generando " + lista.size() + " ganancias de habitaciones");
		return lista;
	}

	public List<RFC2> rfc2() {
		log.info("Generando consulta RFC2");
		List<RFC2> lista = new LinkedList<RFC2>(persistencia.rfc2());
		log.info("Generando " + lista.size() + " servicios mas populares");
		return lista;
	}

	public List<RFC3> rfc3(String fechaInicio, String fechaFin) {
		log.info("Generando consulta RFC3");
		List<RFC3> lista = new LinkedList<RFC3>();
		for (RFC3 r : persistencia.rfc3(fechaInicio, fechaFin))
		{
			lista.add(r);
		}
		log.info("Generando " + lista.size() + " indices de ocupacion habitaciones");
		return lista;
	}

	public List<Servicio> rfc4(long idServicio, long idHotel, String nombre, int horaA, int horaC, String tipo)
	{
		log.info("Generando consulta RFC4");
		List<Servicio> lista = new LinkedList<Servicio>();
		List<Servicio> l = persistencia.rfc4(idServicio, idHotel, nombre, horaA, horaC, tipo);
		for (Servicio r : l)
		{
			lista.add(r);
		}
		log.info("Generando " + lista.size() + " servicios con caracteristica " + tipo);
		return lista;
	}

	public List<RFC6> rfc6() {
		log.info("Generando consulta RFC6");
		List<RFC6> lista = new LinkedList<RFC6>();
		for (RFC6 r : persistencia.rfc6())
		{
			lista.add(r);
		}
		log.info("Generando " + lista.size() + " analisis de operacion");
		return lista;
	}

	public List<RFC7> rfc7() {
		log.info("Generando consulta RFC7");
		List<RFC7> lista = new LinkedList<RFC7>();
		for (RFC7 r : persistencia.rfc7())
		{
			lista.add(r);
		}
		log.info("Generando " + lista.size() + " buenos clientes");
		return lista;
	}

	public List<RFC9> rfc9(int servicioSeleccionado, String ascdesc, String inic, String fin) {
		log.info("Generando consulta RFC9");
		List<RFC9> lista = new LinkedList<RFC9>();
		List<RFC9> l = persistencia.rfc9(servicioSeleccionado, ascdesc, inic, fin);
		for (RFC9 r : l)
		{
			lista.add(r);
		}
		log.info("Generando " + lista.size() + " consumo de clientes");
		return lista;
	}

	public String rfc10(int servicioSeleccionado, String ascdesc) {
		// TODO Auto-generated method stub
		log.info ("Realizando consulta...: ");
		String bebedor = persistencia.rfc10(servicioSeleccionado,ascdesc);
		log.info ("Procesando informacion obtenida....");
		return bebedor;
	}
	
	public String rfc11() {
		return "";
	}

	public String rfc12() {
		// TODO Auto-generated method stub
		log.info ("Realizando consulta RFC12...: ");
		String bebedor = persistencia.rfc12();
		log.info ("Procesando informacion obtenida....");
		return bebedor;
	}

}


