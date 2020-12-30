package Clases;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Cliente {
	static VentanaGUI main;
	static RuletaGUI vista;
	static BaccaratGUI vistaB;
	static final int puerto = 9090;
	private Socket conexion;
	private ObjectInputStream entrada;
	static ObjectOutputStream salida;
	static double puntajeGlobal;
	private boolean encontrado;
	static int nJugador;
	private String mensaje, usuario, clave, datos;
	static String tipoJuego = "ruleta";
	static ImageIcon I;
	
	public Cliente(String ip) { //Recibimos ip
		main = new VentanaGUI();
		 I = new ImageIcon(getClass().getResource("/Imagenes/bienvenida.png"));
		// Se inicia la busqueda del servidor									
		System.out.println("Buscando servidor:");
		try {
			conexion = new Socket(ip,puerto);	//Establece conexion		
			System.out.println("conectado:");
			flujos();							
			datos = main.ventanaRegistro(salida, entrada);
			String[] datosCadena = datos.split(",");
			usuario = datosCadena[0];
			clave = datosCadena[1];
		    //Si todo esta bien
		    puntajeGlobal = (double)entrada.readObject(); // Se recibe el puntaje del usuario
		    //System.out.println(usuario+" "+clave+" "+puntajeGlobal);
		    nJugador = (Integer)entrada.readObject(); // Se recibe el puntaje del usuario
		    
		    tipoJuego = main.elegriJuego(); //Muestra ventana para pedir que escoja juego	 
		   
			 try {
					salida.writeObject(tipoJuego);
					salida.flush();
				} catch (IOException e) {
					System.out.println("Error en salida de tipo juego: "+e.getMessage());
				}
				   
				   System.out.println("Server: Hola usuario "+nJugador);
				   if(tipoJuego.equalsIgnoreCase("ruleta")){
					   vista = new RuletaGUI();
					   vista.setPuntaje(puntajeGlobal);
					   vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				   }else{
					   vistaB = new BaccaratGUI(nJugador, puntajeGlobal);
					   vistaB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				   }	  
		   
		   recibirDatos(); //reciba datos para
		} catch (ClassNotFoundException | IOException e) {			
			System.out.println("Error en la conexion: "+e.getMessage());
			cerrarConexion();
		}
	}
	
	public static void elegirJuego(){		
		tipoJuego = main.elegriJuego();
		try {
			salida.writeObject(tipoJuego);
			salida.flush();
		} catch (IOException e) {
			System.out.println("Error al reelegir juego: "+e.getMessage());
		}
		if(tipoJuego.equalsIgnoreCase("ruleta")){
			   vista = new RuletaGUI();
			   vista.setPuntaje(puntajeGlobal);
			   vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   }else{
			   vistaB = new BaccaratGUI(nJugador, puntajeGlobal);
			   vistaB.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   }
	}
	
	public void flujos() { // Se abren los flujos de entrada/salida
		try {			
			salida = new ObjectOutputStream(conexion.getOutputStream());
			salida.flush();
			entrada = new ObjectInputStream(conexion.getInputStream());
		} catch (IOException e) {
			System.out.println("Error al instanciar flujos: "+e.getMessage());
		}		
	}
	
	public void cerrarConexion() { // Cerrar la conexaion
		try {
			salida.close();
			entrada.close();
			conexion.close();
		} catch (IOException e) {
			System.out.println("Error al cerrar conexion: "+e.getMessage());
		}
	}
	
	//Envia al servidor el caso elegido para que el lo procese 
	public static void enviarDatos(String paquete) {
		try {		
			if(tipoJuego.equalsIgnoreCase("ruleta")){
				salida.writeObject("M:"+paquete);
				salida.flush();	
			}else{
				salida.writeObject("M:"+paquete);
				salida.flush();	
			}
		} catch (IOException e) {
			System.out.println("El envio de datos no se pudo completar");
			e.printStackTrace();
		}// Y se muestra el puntaje-caso
		//setContadores(label,String.valueOf(puntajeGlobal-caso));
	}
	
	
	public void recibirDatos() {
		boolean terminar = false;
		do{
			try {
				String paquete = (String)entrada.readObject();
				if(!tipoJuego.equalsIgnoreCase("n")){
				if(tipoJuego.equalsIgnoreCase("ruleta")) { //Si es la ruleta
					String[] paquete2 = paquete.split(",");
					mensaje = paquete2[0];					
					if(mensaje.equalsIgnoreCase("gira")) { //Si manda a girar la ruleta
						vista.setGirar(true);
						int numeroG = Integer.parseInt(paquete2[1]);
						vista.setRuleta(numeroG); //Numero ganador
						salida.writeObject("A:"+vista.getApuestas()); // le manda la apuesta
						salida.flush();
						String ganacia, ganacia2;
						String[] comprobar;
						do{
						ganacia = (String)entrada.readObject(); //Recibe ganacia
						comprobar = ganacia.split(",");
						ganacia2 = comprobar[0];
						}while(ganacia2.equalsIgnoreCase("gira"));
						ganacia = ganacia2;
						vista.setGanancia(ganacia);
						salida.writeObject(vista.getPuntaje()); //Le manda puntaje
						salida.flush();
					}else{ //Si entra la moneda de otro jugador
						String tipo = mensaje;
						int posicion = Integer.parseInt(paquete2[1]);
						String moneda = paquete2[2];
						vista.setIcon(tipo, posicion, moneda);
					}
				}else{
					//Si es el baccarat
					//String paquete = (String)entrada.readObject();
					if(paquete.equalsIgnoreCase("barajar")) { //Si lo mando a barajar
						vistaB.setBarajar();
						salida.writeObject("A:"+vistaB.getControlador().concatenarEnvio()); //Manda apuestas
						salida.flush();
						String resultados;
						do{
							resultados = (String)entrada.readObject();
						}while(resultados.equalsIgnoreCase("barajar"));
						
						//(!resultados.equalsIgnoreCase("barajar"))
						vistaB.getControlador().recibirDatosS(resultados,vistaB.getApuestas(),vistaB.getMensajes(), vistaB.getCartas());	
						salida.writeObject(vistaB.getCredito());
						salida.flush();
					}else{	
						vistaB.getControlador().actualizarTablero(paquete, vistaB.getApuestas());
					}
				}
			}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}while(!terminar);
	}

}
