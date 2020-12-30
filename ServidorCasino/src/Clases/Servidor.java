package Clases;

import java.awt.Container;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

public class Servidor extends JFrame{
	private BaseDatos consultor;  // Buscara la info de los jugadores
	private final int puerto = 9090;
	private ServerSocket servidor;
	private ExecutorService ejecutarJuego;
	private LogicaRuleta controlRuleta;
	private LogicaBaccarat controlBaccarat;
	private int numGanador, Jugador = 1;
	private ArrayList<Integer> lugares;
	private ArrayList<Integer> ids;
	private ArrayList<Jugadores> jugadores;
	private Thread hilo;
	
	Servidor() throws IOException{
				consultor = new BaseDatos();
		ejecutarJuego = Executors.newFixedThreadPool(4); // crea el administrador de los hilos jugador
		controlRuleta = new LogicaRuleta();
		controlBaccarat = new LogicaBaccarat();		
		jugadores = new ArrayList<Jugadores>();
		lugares = new ArrayList<Integer>();			
		ids = new ArrayList<Integer>();
		ids.add(1); ids.add(2); ids.add(3); ids.add(4);
		//panel.setLayout(null);
		//panel.setBounds(0,0,400,400);
		//contr.add(panel);
							
				try { //Recibir clientes
					servidor = new ServerSocket(puerto);	
					System.out.println("Esperando clientes:");						
					cronometro();
					while(true){
						Jugadores jugador = new Jugadores(servidor.accept(), ids.get(0));
						jugadores.add(jugador); // Lo guarda
						ejecutarJuego.execute(jugador);
						System.out.println("Se coneco un Cliente, jugador "+ids.get(0));
						lugares.add(ids.get(0));
						ids.remove(0);
						if(jugadores.size()>=2){
							System.out.println("Espere en linea hasta que alguien salga");
						}
					}
				} catch (IOException e) {
					System.out.println("Error al recibir cliente: "+e.getMessage());
				}
	}
	
	//Elimina el cliente que sale
	public void eliminarCliente(int pos, int id){
		jugadores.remove(pos);
		lugares.remove(pos);
		ids.add(id);		
	}
	
	public void cronometro(){ //Inicia el tiempo para los juegos
		hilo = new Thread(){
				public synchronized void run() {			
					try {
						while(true){							
							sleep(20000);
							numGanador = controlRuleta.girar();
							controlBaccarat.limpiar();
							controlBaccarat.repartir();
							for(int i=0;i<jugadores.size();i++){
								//Si es jugador de ruleta
								if(jugadores.get(i).tipoJuego.equalsIgnoreCase("ruleta")){
									jugadores.get(i).salida.writeObject("gira,"+numGanador);
									jugadores.get(i).salida.flush();
								}else{								
								}
							}
							for(int i=0;i<jugadores.size();i++){
								if(jugadores.get(i).tipoJuego.equalsIgnoreCase("baccarat")){
									jugadores.get(i).salida.writeObject("barajar");
									jugadores.get(i).salida.flush();
								}else{}							
							}
						}
					} catch (InterruptedException | IOException e) {
						System.out.println("Error en el cronometro:"+e.getMessage() );
					}
				}
		};hilo.start();
	}
	
	//Extiende de ruunable, hilos para los clientes
	private class Jugadores implements Runnable {
		ObjectInputStream entrada; //Flucjos
		ObjectOutputStream salida;
		int ID; // N# jugador
		String tipoJuego;
		String mensaje; // mensaje que le llega del cliente
		Socket conexion;
		private String usuario, clave; // La clave que llega del cliente
		private double puntajeParcial; 
		
		Jugadores(Socket conex, int num) { //inicializar
			this.tipoJuego = "n";
			this.conexion = conex;
			this.ID = num;
			flujos();	
		}
		public void flujos(){ //Obtiene los Flujos
			try {
				this.salida = new ObjectOutputStream(conexion.getOutputStream());
				this.salida.flush();
				this.entrada = new ObjectInputStream(conexion.getInputStream());
			} catch (IOException e) {
				System.out.println("Error al obtener flujos: "+e.getMessage());
			}
			
		}
		public void cerrarConexion() { // cierra la conexion
			try {
				this.salida.close();
				this.entrada.close();
				//this.servidor.close();
			} catch (IOException e) {
				System.out.println("Se ha cerrado la conexion: "+e.getMessage());
			}
		}
		
		//Procesa todos los requisitos del cliente como el envio de mensajes
		public void run() {
			try {
				do{ // recibe intentos hasta que uno sea valido
					String datos = (String)entrada.readObject();
					String[] partes = datos.split(",");
					this.usuario = partes[0];  // Recive el ucuario del cliente
					this.clave = partes[1]; // Recive clave del cliente
					this.puntajeParcial = consultor.buscarEnBase(usuario, clave); // Busca puntaje segun datos
					if(puntajeParcial==-1){ // Manda alerta
						this.salida.writeObject(false);
						this.salida.flush();
					}else{ // Si todo esta bien comencemos
						//control.setPuntaje(puntajeParcial);
						this.salida.writeObject(true);
						this.salida.flush();
					}
				}while(puntajeParcial==-1);
				
				this.salida.writeObject(puntajeParcial); // La envia al cliente 
				this.salida.flush();	
				
			} catch (ClassNotFoundException | ArrayIndexOutOfBoundsException | IOException e1) {
				if(e1.getMessage().equalsIgnoreCase("0")){
					this.cerrarConexion();
				}else{
					System.out.println("Error en la validacion: "+e1.getMessage());

				}				
			}
			
			try {
				this.salida.writeObject(ID); //Le manda el n# jugador
				this.salida.flush();
				this.tipoJuego = (String)this.entrada.readObject(); //Esp[era tipo de juego
				
				while(!Thread.currentThread().isInterrupted()) { // Hasta que el hilo se interrumpa
					this.mensaje = (String)entrada.readObject();
					if(mensaje.equalsIgnoreCase("n")){
						this.tipoJuego = "n";
						this.tipoJuego = (String)entrada.readObject();
						
					}else{
						// Recibe un mnesaje del cliente
						String[] partes = mensaje.split(":");
						String tipoOperacion = partes[0];
						String tipoApuestas = partes[1];
						if(this.tipoJuego.equalsIgnoreCase("ruleta")) { // Si esta jugando ruleta						
							if(tipoOperacion.equalsIgnoreCase("A")) { //Si son todas las apuestas
								if(tipoApuestas.equalsIgnoreCase("0")) {
									this.salida.writeObject("0.0");
									this.salida.flush(); // le manda ganacia cero si no gano nada
								}else {
									String ganancia = controlRuleta.jugar(tipoApuestas.split(" "));
									this.salida.writeObject(ganancia);
									this.salida.flush(); // le manda plata 
								}	
								this.puntajeParcial = (double)this.entrada.readObject();
							}else { 
								for(int i=0;i<jugadores.size();i++) {
									if(this.ID==jugadores.get(i).ID) { //Es este mismo jugador?
									}else{
										try {
											if(this.tipoJuego.equalsIgnoreCase(jugadores.get(i).tipoJuego)) {
												//Si es otro jugador de ruleta le manda la moneda
												jugadores.get(i).salida.writeObject(tipoApuestas); //ms+": "+
												jugadores.get(i).salida.flush();
											}else{
											}
										} catch (IOException e) {
											System.out.println("Error al comparar juego: "+e.getMessage());
										}
									}						
								}
							}
					}else{ // Si esta jugando baccarat
						if(tipoOperacion.equalsIgnoreCase("A")) { //Son todas las apuestas?
							String ganancia = controlBaccarat.jugar(tipoApuestas.split(" "));
							this.salida.writeObject(ganancia); //Le manda ganancia
							this.salida.flush();
							this.puntajeParcial = (double)this.entrada.readObject();
						}else{						
							for(int i=0;i<jugadores.size();i++) {
								if(this.ID==jugadores.get(i).ID) { //Es es mismo jugador?
								}else{
									try {// hay otro jugadores en el bacarat?
										if(this.tipoJuego.equalsIgnoreCase(jugadores.get(i).tipoJuego)) {
											jugadores.get(i).salida.writeObject(tipoApuestas);
											jugadores.get(i).salida.flush(); //Le pinta las monedas
										}
									} catch (IOException e) {
										System.out.println("Error al conparar juego: "+e.getMessage());
									}
								}						
							}			
						}
					}
				}
			}
				
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Se desconecto el Cliente "+ID);
				lugares.indexOf(ID);
				eliminarCliente(lugares.indexOf(this.ID),ID);
				consultor.actualizarDatos(this.usuario, this.puntajeParcial, this.clave);
				cerrarConexion();
			}		
		}		
	}
}
