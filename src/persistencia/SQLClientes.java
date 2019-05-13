package persistencia;

import javax.jdo.PersistenceManager;
/**
 * Clase de sql del cliente de la cadena de hoteles.
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
import javax.jdo.Query;

import negocio.Cliente;
public class SQLClientes {

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaCadenaHoteles ph;
	
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaCadenaHoteles.SQL;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param ph - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLClientes(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}

	public Cliente darClientePorId(PersistenceManager persistenceManager, long idCliente) {
		Query q = persistenceManager.newQuery("SELECT * FROM " + "CLIENTES" + " WHERE id = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(idCliente);
		return (Cliente) q.executeUnique();
	}

	public long adicionarCliente(PersistenceManager persistenceManager, long idCliente, long tipoId, long id,
			long idHabitacion, long idServicio, String nombreUsuario, String correoUsuario) {
		
		Query q = persistenceManager.newQuery(SQL, "INSERT INTO " + "CLIENTES" + "(ID, TIPO_IDENTIFICACION, ID_HOTEL, ID_RESERVA_HABITACION, ID_RESERVA_SERVICIO, NOMBRE, CORREO_ELECTRONICO) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(idCliente, tipoId, id, idHabitacion, idServicio, nombreUsuario, correoUsuario);
        return (long) q.executeUnique();
	}

	public long cambiarReservaClietne(PersistenceManager pm, long idCliente, long idSerivcio) {
		Query q = pm.newQuery(SQL, "UPDATE " + "CLIENTES" + " SET ID_RESERVA_SERVICIO = ? WHERE ID = ?");
	     q.setParameters(idSerivcio, idCliente);
	     return (long) q.executeUnique(); 
	}
}
