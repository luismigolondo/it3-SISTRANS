package persistencia;

import java.util.Date;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class SQLReservas_Convencion {

	private final static String SQL = PersistenciaCadenaHoteles.SQL;


	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaCadenaHoteles ph;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param ph - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLReservas_Convencion(PersistenciaCadenaHoteles ph)
	{
		this.ph = ph;
	}

	public String registrarHabsServs(PersistenceManager pm,long id,long idConvencion, long idHotel, String habs, String servs) throws Exception
	{
		String respuesta = "";
		//EL ID DE CADA RESERVA ES A PARTIR DEL ID QUE PROVEE EL USUARIO (llega por parametro a este metodo)
		//ES DECIR, LA PRIMERA RESERVA VA A TENER EL ID DADO PRO EL USUARIO, LUEGO SE SUMA DE A 1
		String[] habitacionesCant = habs.split(";");
		//AQUI AVERIGUO LA FECHA INICIO DE LA CONVENCION SOBRE LA CUAL QUIERO HACER RESERVAS
		Query consultaFechaInic = pm.newQuery(SQL,"SELECT c.FECHA_INICIO FROM CONVENCIONES c WHERE c.ID="+idConvencion);
		Object objectsfecha = consultaFechaInic.execute();
		String anio = objectsfecha.toString().split(" ")[0].split("-")[0];
		String mes = objectsfecha.toString().split(" ")[0].split("-")[1];
		String dia = objectsfecha.toString().split(" ")[0].split("-")[2];
		String fecha=objectsfecha.toString();
		String fechaOrganizada = dia+"/"+mes+"/"+anio.substring(1, anio.length());
		Date fechainicialConv = convertStringToDate(fechaOrganizada);
		//tipoHabitacion:cantidad;tipoHabitacion:cantidad
		long contador = id;
		int contHabAgreg=0;
		int contServAgreg=0;
		respuesta="LOS IDs DE LAS HABITACIONES RESERVADAS PARA LA CONVENCION SON: \n";
		for(int e=0; e<habitacionesCant.length;e++) {
			String[] tipoHabAct = habitacionesCant[e].split(":");
			if(Integer.parseInt(tipoHabAct[1])>0) {
				Query x = pm.newQuery(SQL, "SELECT h.ID "
						+ "FROM ((HABITACIONES h LEFT JOIN RESERVAS_HABITACIONES hr ON hr.ID=h.ID) LEFT JOIN (RESERVAS_CONVENCIONES rc INNER JOIN CONVENCIONES c ON rc.ID_CONVENCION = c.ID) ON rc.ID_HABITACION = h.ID) "
						+ "WHERE (hr.ID IS NULL OR hr.FECHA_FIN < '"+ fechaOrganizada +"') AND (rc.ID IS NULL OR c.FECHA_INICIO < '"+ fechaOrganizada +"') AND h.TIPO_HABITACION=" + tipoHabAct[0]);
				List<Object> objects = x.executeList();
				respuesta+=" TIPO "+tipoHabAct[0]+": ";
				try{
					for(int p=0;p<objects.size() && p<Integer.parseInt(tipoHabAct[1]);p++){
						long idHabitacion = (long)Long.parseLong(objects.get(p).toString());

						Query xy = pm.newQuery(SQL,"INSERT INTO "+"RESERVAS_CONVENCIONES"+" (ID,ID_CONVENCION,ID_HABITACION,ID_SERVICIO)"
								+ "VALUES (?,?,?,?)");
						xy.setParameters(contador,idConvencion,idHabitacion,null);
						xy.executeUnique();
						contador++;
						contHabAgreg++;
						respuesta += idHabitacion + "  ";
					}
					respuesta += "\n";
				}
				catch(Exception q){
					q.printStackTrace();
				}
			}
		}
		//USO VARIABLE CONTADOR PORQUE EL PRIMER ID LO USA LA PRIMERA HABITACION, LA SIGUIENTE HABITACION USA ID+1
		//ENTONCES PARA NO GENERAR DOS RESERVAS CON EL MISMO ID, LLEVO LA CUENTA DEL ULTIMO ID CREADO CON EL CONTADOR
		String[] servicios = servs.split(",");
		respuesta="LOS IDs DE LOS SERVICIOS RESERVADOS PARA LA CONVENCION SON: \n";
		for(int e=0;e<servicios.length;e++) {


			Query xy = pm.newQuery(SQL,"INSERT INTO "+"RESERVAS_CONVENCIONES"+" (ID,ID_CONVENCION,ID_HABITACION,ID_SERVICIO)"
					+ "VALUES (?,?,?,?)");
			long idd2 = contador+e;
			xy.setParameters(idd2,idConvencion,null,servicios[e]);
			xy.executeUnique();
			respuesta += servicios[e] + "   ";
		}


		return respuesta;
	}

	public String eliminarReservas(PersistenceManager pm, long idConvencion, String habs, String servs)
	{
		//LAS "RESERVAS" EN REALIDAD SON INGRESAR EN LA TABLA RESERVACONVENCIONES LOS IDS DE HABITACIONES QUE SE
		//USARAN DURANTE LA CONVENCION. DE IGUAL SE HACE CON LOS SERVICIOS
		//ESTO SE HACE DE ESTA FORMA PUES NO SE PUEDE REALIZAR UNA RESERVA AL NO TENER EL CLIENTE.
		//PERO CON AGREGARLOS EN LA MENCIONADA TABLA BASTA, PUES AL INTENTAR RESERVAR UNA HABITACION SE PUEDE VERIFICAR
		//QUE NO ESTE PRESENTE EN LA TABLA DE HABITACIONES RESERVADAS PARA LA CONVENCION PARA ESA FECHA
		try {

			String[] habitaciones = habs.split(",");
			for(int e=0;e<habitaciones.length;e++)
			{
				Query xy=pm.newQuery(SQL,"DELETE FROM RESERVAS_CONVENCIONES WHERE ID_HABITACION= "+habitaciones[e] + " AND ID_CONVENCION= "+idConvencion);
				xy.executeUnique();
			}

			String[] servicios = servs.split(",");

			for(int e=0;e<servicios.length;e++)
			{
				Query xy=pm.newQuery(SQL,"DELETE FROM RESERVAS_CONVENCIONES WHERE ID_SERVICIO= "+servicios[e] + " AND ID_CONVENCION= "+idConvencion);
				xy.executeUnique();
			}

			return "Habitaciones desreservadas:"+habs+"Servicios desreservados:"+servs;
		}
		catch(Exception e) {
			return "No se desreservo ninguna habitacion ni servicio";
		}
	}

	public Date convertStringToDate(String dateInString) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));
			return date;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}



}
