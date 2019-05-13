/**
 * 
 */
package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import negocio.CadenaHoteles;
import negocio.Convencion;
import negocio.Gasto;
import negocio.Hotel;
import negocio.ReservaHabitacion.TIPOS_DE_RESERVA;
import negocio.VOCliente;
import negocio.VOReservaHabitacion;
import negocio.VOReservaServicio;

/**
 * Clase principal de la interfaz
 * @author Germ�n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
@SuppressWarnings("serial")
public class InterfazHoteles extends JFrame implements ActionListener{
	
	//first commit juancris
	
	//---------------------------------------------------
	// CONSTANTES
	//---------------------------------------------------
	/**
	 * Logger para escribir la traza de la ejecuci�n
	 */
	private static Logger log = Logger.getLogger(InterfazHoteles.class.getName());

	/**
	 * Ruta al archivo de configuraci�n de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./resources/config/interfaceConfigApp.json"; 


	/**
	 * Ruta al archivo de configuraci�n de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./resources/config/TablasBD_A.json"; 

	//---------------------------------------------------
	// ATRIBUTOS
	//---------------------------------------------------

	private CadenaHoteles hoteles;

	private JsonObject guiConfig;

	private JsonObject tableConfig;

	private PanelDeDatos panelDatos;

	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicaci�n. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazHoteles( )
	{
		// Carga la configuraci�n de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gr�fica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig("Tablas BD", CONFIG_TABLAS);
		hoteles = new CadenaHoteles(tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDeDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			M�todos de configuraci�n de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuraci�n para la aplicaci�, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuraci�n deseada
	 * @param archConfig - Archivo Json que contiene la configuraci�n
	 * @return Un objeto JSON con la configuraci�n del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontr� un archivo de configuraci�n v�lido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontr� un archivo de configuraci�n v�lido");			
			JOptionPane.showMessageDialog(null, "No se encontr� un archivo de configuraci�n de interfaz v�lido: " + tipo, "Cadena de Hoteles", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * M�todo para configurar el frame principal de la aplicaci�n
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuraci�n por defecto" );			
			titulo = "Cadena de Hoteles";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuraci�n indicada en el archivo de configuraci�n" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * M�todo para crear el men� de la aplicaci�n con base em el objeto JSON le�do
	 * Genera una barra de men� y los men�s con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los men�s deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creaci�n de la barra de men�s
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creaci�n de cada uno de los men�s
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creaci�n de cada una de las opciones del men�
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();
				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	//------------------------------------
	// Metodos de requerimientos
	//------------------------------------
	public void RF7registrarReservaHabitacion( )
	{
		try 
		{
			long hotel =  Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "Ingrese ID del Hotel", JOptionPane.QUESTION_MESSAGE));
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la reserva a crear", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE));
			long idCliente = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese la cedula de ciduadania del cliente", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE));
			long tipoId = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el tipo de identificacion", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE));
			String nombreUsuario = JOptionPane.showInputDialog (this, "Ingrese el nombre del usuario para registrar", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE);
			String correoUsuario = JOptionPane.showInputDialog (this, "Ingrese el correo electronico del usuario para registrar", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE);
			long idHabitacion = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el numero de la habitacion", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE));
			long idPlanDeConsumo = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el plan de consumo para la reserva", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE));
			String idFinic = JOptionPane.showInputDialog (this, "Ingrese la fecha de inicio DD/MM/AAAA", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE);
			String idFfin= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA", "Agregar nueva reserva habitacion", JOptionPane.QUESTION_MESSAGE);

			if (id != 0 && idCliente != 0 && tipoId != 0&& idHabitacion != 0&& idPlanDeConsumo != 0&& idFinic != null&& idFfin != null)
			{
				hoteles.adicionarCliente(hotel, idCliente, tipoId, idHabitacion, 0, nombreUsuario, correoUsuario);
				VOReservaHabitacion tb = hoteles.adicionarReservaHabitacion(id, idCliente, tipoId, idHabitacion, idPlanDeConsumo, idFinic, idFfin);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear la reserva " + id + ", para el cliente: " + nombreUsuario);
				}
				String resultado = "En adicionarReservaHabitacion\n\n";
				resultado += "Reserva adicionada exitosamente: " + tb;
				resultado += "\n Operacion terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por recepcion");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RF8registrarReservaServicio( )
	{
		try 
		{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la reserva de servicio a crear", "Agregar nueva reserva de Servicio", JOptionPane.QUESTION_MESSAGE));
			long idCliente = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese la cedula de ciduadania del cliente", "Agregar nueva reserva de Servicio", JOptionPane.QUESTION_MESSAGE));
			long tipoId = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el tipo de identificacion", "Agregar nueva reserva de Servicio", JOptionPane.QUESTION_MESSAGE));
			long idServicio = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el tipo de servicio", "Agregar nueva reserva de Servicio", JOptionPane.QUESTION_MESSAGE));
			String idFinic = JOptionPane.showInputDialog (this, "Ingrese la fecha de inicio DD/MM/AAAA", "Agregar nueva reserva de Servicio", JOptionPane.QUESTION_MESSAGE);
			String idFfin= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA", "Agregar nueva reserva de Servicion", JOptionPane.QUESTION_MESSAGE);

			if (id != 0)
			{
				VOReservaServicio tb = hoteles.adicionarReservaServicio(id, idFinic, idFfin, idCliente, tipoId, idServicio);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear la reserva: " + id);
				}
				String resultado = "En adicionarReservaServicio\n\n";
				resultado += "Reserva adicionada exitosamente: " + tb;
				resultado += "\n Operacion terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por recepcion");
			}
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RF9registrarLlegadaCliente(long pIdReserva)
	{
		try
		{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la reserva para activar el checkin", "Realizar checkin", JOptionPane.QUESTION_MESSAGE));
			if(id!=0)
			{
				long tb = hoteles.registrarLlegadaCliente(pIdReserva);
				if(tb==0)
				{
					throw new Exception("No se puede realizar el checkin a la reserva: "+id);
				}
				String resultado = "En activar checkin usuario\n\n";
				resultado += "Checkin realizado exitosamente: " + tb;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e)
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RF10registrarConsumoServicio()
	{
		try {    		
			long idHabitacion = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el id de la habitacion", "Ingresar Gasto", JOptionPane.QUESTION_MESSAGE));
			long idProducto = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el id del producto para cobrar", "Ingresar Gasto", JOptionPane.QUESTION_MESSAGE));
			String pFecha= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA", "Ingresar Gasto", JOptionPane.QUESTION_MESSAGE);

			if(idHabitacion!=0 && idProducto!=0)
			{
				Gasto tb = hoteles.registrarConsumoServicio(idHabitacion, idProducto, pFecha);
				if(tb==null)
				{
					throw new Exception ("No se puedo registrar el gasto a la habitacion "+idHabitacion+" del producto "+idProducto);
				}
				String resultado = "En registrarConsumoServicio\n\n";
				resultado += "Gasto adicionado exitosamente: " + tb;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
		}
		catch(Exception e)
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	public void RF11registrarSalidaCliente()
	{
		try {
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la reserva para checkout", "Realizar checkout", JOptionPane.QUESTION_MESSAGE));
			if(id!=0)
			{
				long tb = hoteles.registrarSalidaCliente(id);
				if(tb==0)
				{
					throw new Exception("No se puede realizar el checkout a la reserva: "+id);
				}
				String resultado = "En adicionarReservaHabitacion\n\n";
				resultado += "Checkout realizado exitosamente: " + tb;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por recepcion");
			}

		}catch(Exception e){
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RF12reservarHabServs(){
		try 
		{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la reserva a crear", "", JOptionPane.QUESTION_MESSAGE));
			long idConvencion = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la convencion", "", JOptionPane.QUESTION_MESSAGE));
			long idHotel = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "", JOptionPane.QUESTION_MESSAGE));

			if(hoteles.darConvencion(idConvencion) == null)
			{
				JOptionPane.showMessageDialog(this, "Se procede a crear la convencion", "Creacion de convencion", JOptionPane.ERROR_MESSAGE);
				String pFechaInic= JOptionPane.showInputDialog (this, "Ingrese la fecha de inicio DD/MM/AAAA", "Crear Convencion", JOptionPane.QUESTION_MESSAGE);
				String pFechaFin= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA", "Crear Convencion", JOptionPane.QUESTION_MESSAGE);
				Convencion c = hoteles.adicionarConvencion(pFechaInic, pFechaFin, idHotel, idConvencion);
				if(c == null)
					throw new Exception("No se pudo crear la convencion");
				JOptionPane.showMessageDialog(this, "Enhorabuena! Se ha creado la convencion", "Creacion convencion exitosa", JOptionPane.INFORMATION_MESSAGE);
			}
			
			String habSuite = JOptionPane.showInputDialog (this, "Ingrese la cantidad de habitaciones SUITE", "Ingresar numero de habitaciones", JOptionPane.QUESTION_MESSAGE); 
			String habSuite_P = JOptionPane.showInputDialog (this, "Ingrese la cantidad de habitaciones SUITE PRESIDENCIAL", "Ingresar numero de habitaciones", JOptionPane.QUESTION_MESSAGE); 
			String habSencilla = JOptionPane.showInputDialog (this, "Ingrese la cantidad de habitaciones SENCLLAS", "Ingresar numero de habitaciones", JOptionPane.QUESTION_MESSAGE); 
			String concat = "1:"+habSuite;
			concat+=";2:"+habSuite_P;
			concat+=";3:"+habSencilla;
			System.out.println("ESTO SE IMPRIME HABITACIONES:"+concat);
			String servicios = JOptionPane.showInputDialog (this, "Ingrese los servicios requeridos separados por una coma", "Ingresar servicios", JOptionPane.QUESTION_MESSAGE); 
			System.out.println("ESTO IMPRIME SERVICIOS: "+servicios);
			String b = hoteles.reservarHabServs(id,idConvencion,idHotel,concat,servicios);
			if(b.equals(""))
			{
				throw new Exception("No se puede realizar las reservas para la convencion");
			}
			String resultado = "En reservarHabitacionesYServicios\n\n";
			resultado += "Reservas realizada exitosamente";
			resultado += "\n Operaci�n terminada";
			resultado+="\n IDS DE LAS RESERVAS REALIZADAS"+b;
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		
	}

	public void RF13cancelarReservasConvencion(){
		try 
		{
			long idConvencion = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la convencion", "", JOptionPane.QUESTION_MESSAGE));
			long idHotel = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "", JOptionPane.QUESTION_MESSAGE));

			
			String habs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de las habitaciones a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
			String servs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de los servicios a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
			
			String b = hoteles.cancelarReservasConvencion(idConvencion, idHotel,habs,servs);
			
			String resultado = "En cancelarReservaHabitacionesYServicios\n\n";
			resultado += "DESRESEVACION realizada exitosamente";
			resultado += "\n Operaci�n terminada";
			resultado+="\n IDS DE LAS RESERVAS CANCELADAS"+b;
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		
	}

	public void RF14registrarFinConvencion(){
		
		System.out.println("funciona14");
	}

	public void RF15registrarEntradaMantenimiento(){
		
		try 
		{
			long id = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador", "", JOptionPane.QUESTION_MESSAGE));

			long idHotel = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "", JOptionPane.QUESTION_MESSAGE));

			String habs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de las habitaciones a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
			String servs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de los servicios a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
			
			String idFinic = JOptionPane.showInputDialog (this, "Ingrese la fecha de inicio DD/MM/AAAA", "Agregar nueva reserva de Servicio", JOptionPane.QUESTION_MESSAGE);
			String idFfin= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA", "Agregar nueva reserva de Servicion", JOptionPane.QUESTION_MESSAGE);

			
			String b = hoteles.registrarEntradaMantenimiento(id, idHotel,habs,servs,idFinic,idFfin);
			
			String resultado = "En cancelarReservaHabitacionesYServicios\n\n";
			resultado += "DESRESEVACION realizada exitosamente";
			resultado += "\n Operaci�n terminada";
			resultado+="\n IDS DE LAS RESERVAS CANCELADAS"+b;
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void RF16registrarFinMantenimiento(){
		try 
		{
			long idHotel = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "", JOptionPane.QUESTION_MESSAGE));

			String habs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de las habitaciones a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
			String servs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de los servicios a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
			
			String b = hoteles.registrarFinMantenimiento(idHotel,habs,servs);
			
			String resultado = "En cancelarReservaHabitacionesYServicios\n\n";
			resultado += "DESRESEVACION realizada exitosamente";
			resultado += "\n Operaci�n terminada";
			resultado+="\n IDS DE LAS RESERVAS CANCELADAS"+b;
			panelDatos.actualizarInterfaz(resultado);
		}
		catch(Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}





	/* ****************************************************************
	 * 			M�todos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Hoteles
	 */
	public void mostrarLogHoteles ()
	{
		mostrarArchivo ("cadenahoteles.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de Hoteles
	 * Muestra en el panel de datos la traza de la ejecuci�n
	 */
	public void limpiarLogHoteles ()
	{
		// Ejecuci�n de la operaci�n y recolecci�n de los resultados
		boolean resp = limpiarArchivo ("hoteles.log");

		// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
		String resultado = "\n\n************ Limpiando el log de hoteles ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecuci�n
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecuci�n de la operaci�n y recolecci�n de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de hoteles
	 * Muestra en el panel de datos el n�mero de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecuci�n de la demo y recolecci�n de los resultados
			long eliminados [] = hoteles.limpiarHoteles();

			// Generaci�n de la cadena de caracteres con la traza de la ejecuci�n de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Empleados eliminados\n";
			resultado += eliminados [1] + " Gastos eliminados\n";
			resultado += eliminados [2] + " Habitaciones eliminados\n";
			resultado += eliminados [3] + " Gastos eliminadas\n";
			resultado += eliminados [4] + " Reservas de habitaciones eliminadas\n";
			resultado += eliminados [5] + " Clientes eliminados\n";
			resultado += eliminados [6] + " Planes de Consumo eliminados\n";
			resultado += eliminados [7] + " Servicios eliminados\n";
			resultado += eliminados [8] + " Hoteles eliminados\n";

			resultado += "\nLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra la presentaci�n general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("docs/It1-_C-07_jd.diazc_lm.londono.pdf");
	}

	/**
	 * Muestra el modelo conceptual de hoteles
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("docs/UML_Iteracion1.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de hoteles
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("docs/esquemaBaseDatos Hoja1.pdf");
	}

	/**
	 * Muestra el script de creaci�n de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("docs/scripts/EsquemaCadenaHoteles.sql");
	}

	/**
	 * Muestra la documentaci�n Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("docs/javadoc/index.html");
	}

	/**
	 * Muestra la informaci�n acerca del desarrollo de esta apicaci�n
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogot�	- Colombia)\n";		
		resultado += " * Curso: ISIS2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: HotelUniandes \n";
		resultado += " * Hecho por Luis Miguel Gomez Londo�o y Juan David Diaz Cristancho\n";
		resultado += " * Inspirado y referenciado principalmente por el proyecto PARRANDEROS elaborado\n";
		resultado += " * por el profesor GERMAN BRAVO\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}


	/* ****************************************************************
	 * 			M�todos privados para la presentaci�n de resultados y otras operaciones
	 *****************************************************************/


	/**
	 * Genera una cadena de caracteres con la descripci�n de la excepcion e, haciendo �nfasis en las excepcionsde JDO
	 * @param e - La excepci�n recibida
	 * @return La descripci�n de la excepci�n, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicaci�n
	 * @param e - La excepci�n generada
	 * @return La cadena con la informaci�n de la excepci�n y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecuci�n\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y hoteles.log para m�s detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como par�metro con la aplicaci�n por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			M�todos de la Interacci�n
	 *****************************************************************/
	/**
	 * M�todo para la ejecuci�n de los eventos que enlazan el men� con los m�todos de negocio
	 * Invoca al m�todo correspondiente seg�n el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
		try 
		{
			Method req = InterfazHoteles.class.getMethod(evento);			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este m�todo ejecuta la aplicaci�n, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por l�nea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazHoteles interfaz = new InterfazHoteles( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}

}
