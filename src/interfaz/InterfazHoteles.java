/**
 * 
 */
package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.DataBufferDouble;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import negocio.RFC1;
import negocio.RFC2;
import negocio.RFC3;
import negocio.RFC4;
import negocio.RFC6;
import negocio.RFC7;
import negocio.ReservaHabitacion.TIPOS_DE_RESERVA;
import negocio.Servicio;
import negocio.VOCliente;
import negocio.VOReservaHabitacion;
import negocio.VOReservaServicio;

/**
 * Clase principal de la interfaz
 * @author Germï¿½n Bravo
 * MODIFICADO POR LUIS MIGUEL GOMEZ Y JUAN DAVID DIAZ PARA LA ITERACION 1 DE SISTEMAS TRANSACCIONALES.
 */
@SuppressWarnings("serial")
public class InterfazHoteles extends JFrame implements ActionListener{

	//first commit juancris

	//---------------------------------------------------
	// CONSTANTES
	//---------------------------------------------------
	/**
	 * Logger para escribir la traza de la ejecuciï¿½n
	 */
	private static Logger log = Logger.getLogger(InterfazHoteles.class.getName());

	/**
	 * Ruta al archivo de configuraciï¿½n de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./resources/config/interfaceConfigApp.json"; 


	/**
	 * Ruta al archivo de configuraciï¿½n de los nombres de tablas de la base de datos
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

	private int usuarioNeutro = 1;

	private int recepcionista = 2;
	private int contraRecepcionista=123;


	/* ****************************************************************
	 * 			Mï¿½todos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicaciï¿½n. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazHoteles( )
	{
		// Carga la configuraciï¿½n de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz grï¿½fica
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
	 * 			Mï¿½todos de configuraciï¿½n de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuraciï¿½n para la aplicaciï¿½, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuraciï¿½n deseada
	 * @param archConfig - Archivo Json que contiene la configuraciï¿½n
	 * @return Un objeto JSON con la configuraciï¿½n del tipo especificado
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
			log.info ("Se encontrï¿½ un archivo de configuraciï¿½n vï¿½lido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontrï¿½ un archivo de configuraciï¿½n vï¿½lido");			
			JOptionPane.showMessageDialog(null, "No se encontrï¿½ un archivo de configuraciï¿½n de interfaz vï¿½lido: " + tipo, "Cadena de Hoteles", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Mï¿½todo para configurar el frame principal de la aplicaciï¿½n
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuraciï¿½n por defecto" );			
			titulo = "Cadena de Hoteles";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuraciï¿½n indicada en el archivo de configuraciï¿½n" );
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
	 * Mï¿½todo para crear el menï¿½ de la aplicaciï¿½n con base em el objeto JSON leï¿½do
	 * Genera una barra de menï¿½ y los menï¿½s con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menï¿½s deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creaciï¿½n de la barra de menï¿½s
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creaciï¿½n de cada uno de los menï¿½s
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creaciï¿½n de cada una de las opciones del menï¿½
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

	public boolean login(){
		int contra = Integer.parseInt(JOptionPane.showInputDialog (this, "Ingrese la clave", "Operacion restringida", JOptionPane.QUESTION_MESSAGE));
		if(contra==contraRecepcionista)
			return true;
		JOptionPane.showMessageDialog(this, "Clave incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	public void RF7registrarReservaHabitacion( )
	{
		if(login()==true){
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
					panelDatos.actualizarInterfaz("Operaciï¿½n cancelada por recepcion");
				}
			} 
			catch (Exception e) 
			{
				//			e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF8registrarReservaServicio( )
	{
		if(login()==true){
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
					panelDatos.actualizarInterfaz("Operaciï¿½n cancelada por recepcion");
				}
			} 
			catch (Exception e) 
			{
				//			e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF9registrarLlegadaCliente(long pIdReserva)
	{
		if(login()==true){
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
					resultado += "\n Operaciï¿½n terminada";
					panelDatos.actualizarInterfaz(resultado);
				}
			}
			catch(Exception e)
			{
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF10registrarConsumoServicio()
	{
		if(login()==true){
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
					resultado += "\n Operaciï¿½n terminada";
					panelDatos.actualizarInterfaz(resultado);
				}
			}
			catch(Exception e)
			{
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF11registrarSalidaCliente()
	{
		if(login()==true){
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
					resultado += "\n Operaciï¿½n terminada";
					panelDatos.actualizarInterfaz(resultado);
				}
				else
				{
					panelDatos.actualizarInterfaz("Operaciï¿½n cancelada por recepcion");
				}

			}catch(Exception e){
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF12reservarHabServs(){
		if(login()==true){
			try 
			{
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
				String b = hoteles.reservarHabServs(idConvencion,idHotel,concat,servicios);
				if(b.equals(""))
				{
					throw new Exception("No se puede realizar las reservas para la convencion");
				}
				String resultado = "En reservarHabitacionesYServicios\n\n";
				resultado += "Reservas realizada exitosamente";
				resultado += "\n Operaciï¿½n terminada";
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

	}

	public void RF13cancelarReservasConvencion(){
		if(login()==true){
			try 
			{
				long idConvencion = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la convencion", "", JOptionPane.QUESTION_MESSAGE));

				String habs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de las habitaciones a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
				String servs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de los servicios a desreservar", "", JOptionPane.QUESTION_MESSAGE); 

				String b = hoteles.cancelarReservasConvencion(idConvencion,habs,servs);

				String resultado = "En cancelarReservaHabitacionesYServicios\n\n";
				resultado += "DESRESERVACION realizada exitosamente";
				resultado += "\n Operaciï¿½n terminada";
				resultado+="\n IDS DE LAS RESERVAS CANCELADAS"+b;
				panelDatos.actualizarInterfaz(resultado);
			}
			catch(Exception e) {
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF14registrarFinConvencion(){
		if(login()==true){

			try{
				String resultado="";
				long idConvencion = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador de la convencion", "", JOptionPane.QUESTION_MESSAGE));
				resultado+="En finalizar convencion \n";
				String b = hoteles.registrarFinConvencion(idConvencion);
				resultado+="\n CUENTAS CALCULADAS \n";
				resultado+="PASO A COBRAR INDIVIDUALMENTE Y A LA CONVENCION \n";
				resultado+="\n"+b;
				resultado += "\n Operaciï¿½n terminada";
				panelDatos.actualizarInterfaz(resultado);
				String[] deudores = b.split(",");
				for(int e=1;e<deudores.length;e++){
					//					System.out.println(deudores[e]);
					String[] simplif = deudores[e].split("DEBE PAGAR");
					System.out.println(simplif[0]);
					System.out.println(e+" EL PERRO NELLLL");
					System.out.println(deudores[e].split("\\*")[0]+"[0]");
					System.out.println(deudores[e].split("\\*")[1]+"[1]");
					System.out.println(deudores[e].split("\\*")[2]+"[2]");
					boolean pazYSalvo=false;
					if(e!=deudores.length-1){
						while(!pazYSalvo){	
							Long idRH = new Double(Double.parseDouble((deudores[e].split("\\*")[1]))).longValue();
							System.out.println("EL IDDDDDD"+idRH);
							//							Long idRH = Long.parseLong(deudores[e].split("\\*")[1].split(".")[0]);
							Long dinero = Long.parseLong(JOptionPane.showInputDialog (this, "El cliente "+simplif[0]+" debe pagar "+simplif[1], "", JOptionPane.QUESTION_MESSAGE));
							Double pm = dinero.doubleValue();
							Double pm2 = 0.0;
							try{
								pm2 = Double.valueOf(simplif[1]);
							}
							catch(Exception y){
								System.out.println("SE PUTEO EL TRIPLE MIERDA");
							}
							if(pm.compareTo(pm2)>=0){
								JOptionPane.showMessageDialog(this, "PAZ Y SALVO!");
								pazYSalvo=true;
								//llamar metodo paz y salvo cliente
								hoteles.pazYSalvoCliente(idRH);
							}
							else{
								JOptionPane.showMessageDialog(this, "El dinero ingresado no cubre la deuda, debe ingresar al menos "+simplif[1]);
							}
						}
						pazYSalvo=false;
					}
					else{
						while(!pazYSalvo){
							Long dinero = Long.parseLong(JOptionPane.showInputDialog (this, simplif[0]+" debe pagar "+simplif[1], "", JOptionPane.QUESTION_MESSAGE));
							long idConv = Long.parseLong(simplif[0].split("\\*")[1]);
							//							String proces = simplif[1].split("E")[0]+"E+0"+ simplif[1].split("E")[1];
							//							System.out.println(proces+"PERRO LEON");
							//							BigDecimal test = new BigDecimal(proces) ;
							//							BigDecimal test1 = new BigDecimal(dinero);

							if(true){
								JOptionPane.showMessageDialog(this, "PAZ Y SALVO!");
								pazYSalvo=true;
								//llamar metodo paz y salvo con
								hoteles.pazYSalvoConvencion(idConv);
							}
							else{
								JOptionPane.showMessageDialog(this, "El dinero ingresado no cubre la deuda, debe ingresar al menos "+simplif[1]);
							}
						}
					}
					System.out.println(simplif[1]);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void RF15registrarEntradaMantenimiento(){
		if(login()==true){
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
				resultado += "\n Operaciï¿½n terminada";
				resultado+="\n IDS DE LAS RESERVAS CANCELADAS"+b;
				panelDatos.actualizarInterfaz(resultado);
			}
			catch(Exception e) {
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	public void RF16registrarFinMantenimiento(){
		if(login()==true){
			try 
			{
				long idHotel = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "", JOptionPane.QUESTION_MESSAGE));

				String habs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de las habitaciones a desreservar", "", JOptionPane.QUESTION_MESSAGE); 
				String servs = JOptionPane.showInputDialog (this, "Ingrese separado por comas los IDs de los servicios a desreservar", "", JOptionPane.QUESTION_MESSAGE); 

				String b = hoteles.registrarFinMantenimiento(idHotel,habs,servs);

				String resultado = "En cancelarReservaHabitacionesYServicios\n\n";
				resultado += "DESRESEVACION realizada exitosamente";
				resultado += "\n Operaciï¿½n terminada";
				resultado+="\n IDS DE LAS RESERVAS CANCELADAS"+b;
				panelDatos.actualizarInterfaz(resultado);
			}
			catch(Exception e) {
				e.printStackTrace();
				String resultado = generarMensajeError(e);
				panelDatos.actualizarInterfaz(resultado);
			}
		}
	}

	/**
	 * Metodos de requerimientos de consulta
	 */

	public void rfc1()
	{
		try
		{
			String idFinic = JOptionPane.showInputDialog (this, "Ingrese la fecha de inicio DD/MM/AAAA Ej: 18/03/2019", "Consultar ganancia habitaciones", JOptionPane.QUESTION_MESSAGE);
			String idFfin= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA Ej: 19/03/2019", "Consultar ganancia habitaciones", JOptionPane.QUESTION_MESSAGE);
			List<RFC1> lista = hoteles.rfc1(idFinic, idFfin);

			String resultado = "Requerimiento funcional de consulta 1: \n";
			int i = 1;
			for(RFC1 r : lista)
			{
				resultado += i++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void rfc2()
	{
		try
		{
			List<RFC2> lista = hoteles.rfc2();

			String resultado = "Requerimiento funcional de consulta 2: \n";
			int i = 1;
			for(RFC2 r : lista)
			{
				resultado += i++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void rfc3()
	{
		try
		{
			String idFinic = JOptionPane.showInputDialog (this, "Ingrese la fecha de inicio DD/MM/AAAA Ej: 17/03/2019", "Indice ocupacion habitaciones", JOptionPane.QUESTION_MESSAGE);
			String idFfin= JOptionPane.showInputDialog (this, "Ingrese la fecha de fin DD/MM/AAAA Ej: 17/03/2019", "Indice ocupacion habitaciones", JOptionPane.QUESTION_MESSAGE);
			List<RFC3> lista = hoteles.rfc3(idFinic, idFfin);

			String resultado = "Requerimiento funcional de consulta 3: \n";
			int i = 1;
			for(RFC3 r : lista)
			{
				resultado += i++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void rfc4()
	{
		try
		{
			long idServicio = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del servicio Ej: 5", "Servicios con caracteristica", JOptionPane.QUESTION_MESSAGE));
			long idHotel = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el identificador del hotel", "Servicios con caracteristica", JOptionPane.QUESTION_MESSAGE));
			String nombre = JOptionPane.showInputDialog (this, "Ingrese el nombre del servicio Ej: Restaurante", "Servicios con caracteristica", JOptionPane.QUESTION_MESSAGE);
			int horaA = Integer.parseInt(JOptionPane.showInputDialog (this, "Ingrese hora de apertura Ej: 8", "Servicios con caracteristica", JOptionPane.QUESTION_MESSAGE));
			int horaC = Integer.parseInt(JOptionPane.showInputDialog (this, "Ingrese hora de cierre Ej: 21", "Servicios con caracteristica", JOptionPane.QUESTION_MESSAGE));
			String tipo = JOptionPane.showInputDialog (this, "Ingrese tipo Ej: Alimentacion", "Servicios con caracteristica", JOptionPane.QUESTION_MESSAGE);

			List<Servicio> lista = hoteles.rfc4(idServicio, idHotel, nombre, horaA, horaC, tipo);

			String resultado = "Requerimiento funcional de consulta 4: \n";
			int i = 1;
			for(Servicio r : lista)
			{
				resultado += i++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void rfc6()
	{
		try
		{
			List<RFC6> lista = hoteles.rfc6();

			String resultado = "Requerimiento funcional de consulta 6: \n";
			int i = 1;
			for(RFC6 r : lista)
			{
				resultado += i++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void rfc7()
	{
		try
		{
			List<RFC7> lista = hoteles.rfc7();

			String resultado = "Requerimiento funcional de consulta 7: \n";
			int i = 1;
			for(RFC7 r : lista)
			{
				resultado += i++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e) {
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	public void rfc9(){
		try
		{
			String[] servicios = {"Piscina", "Gimnasio", "Internet", "Bar HardRock", "Subway", "Supermarket", "Souvenir", "SPA", "Lavado", "Prestamo Toalla"};
			String[] sort = {"Ascendente", "Descendente"};
			String servicio = (String) JOptionPane.showInputDialog(this, "RFC9 - Consulta Consumo", "Filtrar por servicio:", JOptionPane.QUESTION_MESSAGE, null, servicios, servicios[0]);
			int servicioSeleccionado = 0;
			int i = 0;
			for(String s: servicios)
			{
				if(s.equals(servicio))
				{
					servicioSeleccionado = i + 1;
					break;
				}
				i++;
			}
			String ascdesc = (String) JOptionPane.showInputDialog(this, "RFC9 - Consulta Consumo", "Ordenar por numero de reservas:", JOptionPane.QUESTION_MESSAGE, null, sort, sort[0]);
			if(ascdesc.equals("Ascendente"))
				ascdesc = "ASC";
			else
				ascdesc = "DESC";
			String inic = JOptionPane.showInputDialog (this, "Ingrese la fecha de rango inicial DD/MM/AAAA", "RFC9 - Consulta Consumo", JOptionPane.QUESTION_MESSAGE);
			String fin = JOptionPane.showInputDialog (this, "Ingrese la fecha de rango final DD/MM/AAAA", "RFC9 - Consulta Consumo", JOptionPane.QUESTION_MESSAGE);

			List<RFC9> lista = hoteles.rfc9(servicioSeleccionado, ascdesc, inic, fin);

			String resultado = "Requerimiento funcional de consulta 7: \n";
			int j = 1;
			for(RFC9 r : lista)
			{
				resultado += j++ + ". " + r.toString() + "\n";
			}
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";

		}
		catch (Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/* ****************************************************************
	 * 			Mï¿½todos administrativos
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
	 * Muestra en el panel de datos la traza de la ejecuciï¿½n
	 */
	public void limpiarLogHoteles ()
	{
		// Ejecuciï¿½n de la operaciï¿½n y recolecciï¿½n de los resultados
		boolean resp = limpiarArchivo ("hoteles.log");

		// Generaciï¿½n de la cadena de caracteres con la traza de la ejecuciï¿½n de la demo
		String resultado = "\n\n************ Limpiando el log de hoteles ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecuciï¿½n
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecuciï¿½n de la operaciï¿½n y recolecciï¿½n de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generaciï¿½n de la cadena de caracteres con la traza de la ejecuciï¿½n de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de hoteles
	 * Muestra en el panel de datos el nï¿½mero de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecuciï¿½n de la demo y recolecciï¿½n de los resultados
			long eliminados [] = hoteles.limpiarHoteles();

			// Generaciï¿½n de la cadena de caracteres con la traza de la ejecuciï¿½n de la demo
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
	 * Muestra la presentaciï¿½n general del proyecto
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
	 * Muestra el script de creaciï¿½n de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("docs/scripts/EsquemaCadenaHoteles.sql");
	}

	/**
	 * Muestra la documentaciï¿½n Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("docs/javadoc/index.html");
	}

	/**
	 * Muestra la informaciï¿½n acerca del desarrollo de esta apicaciï¿½n
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotï¿½	- Colombia)\n";		
		resultado += " * Curso: ISIS2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: HotelUniandes \n";
		resultado += " * Hecho por Luis Miguel Gomez Londoï¿½o y Juan David Diaz Cristancho\n";
		resultado += " * Inspirado y referenciado principalmente por el proyecto PARRANDEROS elaborado\n";
		resultado += " * por el profesor GERMAN BRAVO\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}


	/* ****************************************************************
	 * 			Mï¿½todos privados para la presentaciï¿½n de resultados y otras operaciones
	 *****************************************************************/


	/**
	 * Genera una cadena de caracteres con la descripciï¿½n de la excepcion e, haciendo ï¿½nfasis en las excepcionsde JDO
	 * @param e - La excepciï¿½n recibida
	 * @return La descripciï¿½n de la excepciï¿½n, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicaciï¿½n
	 * @param e - La excepciï¿½n generada
	 * @return La cadena con la informaciï¿½n de la excepciï¿½n y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecuciï¿½n\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y hoteles.log para mï¿½s detalles";
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
	 * Abre el archivo dado como parï¿½metro con la aplicaciï¿½n por defecto del sistema
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
	 * 			Mï¿½todos de la Interacciï¿½n
	 *****************************************************************/
	/**
	 * Mï¿½todo para la ejecuciï¿½n de los eventos que enlazan el menï¿½ con los mï¿½todos de negocio
	 * Invoca al mï¿½todo correspondiente segï¿½n el evento recibido
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
	 * Este mï¿½todo ejecuta la aplicaciï¿½n, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por lï¿½nea de comandos
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
