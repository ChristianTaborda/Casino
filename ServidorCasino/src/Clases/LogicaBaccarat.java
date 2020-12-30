package Clases;

import java.util.Random;
import java.util.Vector;

public class LogicaBaccarat{
	
	/*************
	 * ATRIBUTOS *
	 *************/
	
	private Vector <String> baraja, descarte, manoJugador, manoCrupier;
	private String decision;
	private boolean natural;
	
	/***********
	 * MÉTODOS *
	 ***********/
	
	//Constructor:
	public LogicaBaccarat(){
		
		//Inicialización del vector para la baraja:
		baraja = new Vector<String>();
		
		//Adición de las cartas con corazones:
		for(int x=0; x<10; x++){
			baraja.add(String.valueOf(x+1) + "C");
			baraja.add(String.valueOf(x+1) + "C");
		}
		baraja.add("KC");
		baraja.add("QC");
		baraja.add("JC");
		baraja.add("KC");
		baraja.add("QC");
		baraja.add("JC");
		
		//Adición de las cartas con picas:
		for(int x=0; x<10; x++){
			baraja.add(String.valueOf(x+1) + "P");
			baraja.add(String.valueOf(x+1) + "P");
		}
		baraja.add("KP");
		baraja.add("QP");
		baraja.add("JP");
		baraja.add("KP");
		baraja.add("QP");
		baraja.add("JP");
		
		//Adición de las cartas con diamantes:
		for(int x=0; x<10; x++){
			baraja.add(String.valueOf(x+1) + "D");
			baraja.add(String.valueOf(x+1) + "D");
		}
		baraja.add("KD");
		baraja.add("QD");
		baraja.add("JD");
		baraja.add("KD");
		baraja.add("QD");
		baraja.add("JD");
		
		//Adición de las cartas con tréboles:
		for(int x=0; x<10; x++){
			baraja.add(String.valueOf(x+1) + "T");
			baraja.add(String.valueOf(x+1) + "T");
		}
		baraja.add("KT");
		baraja.add("QT");
		baraja.add("JT");
		baraja.add("KT");
		baraja.add("QT");
		baraja.add("JT");
		
		//Inicialización de los vectores para las manos:
		manoJugador = new Vector<String>();
		manoCrupier = new Vector<String>();
		
		//Inicialización del vector para las cartas descartadas:
		descarte = new Vector<String>();
		
		//Inicialización de las variables para validar cada ronda:
		decision = "";
		natural = false;
		
	}
	
	//Retorna los puntos de una carta:
	public int extraerPuntosCarta(String carta){
		
		//Inicialización de las variables a utilizar:
		String valor;
		int puntos;
		
		//Validación para las cartas con el número 10:
		if(carta.length() == 3){
			
			//Asignación del valor de la carta:
			valor = carta.charAt(0) + "" + carta.charAt(1); 
			
		}else{
			
			//Asignación del valor de la carta:
			valor = carta.charAt(0) + ""; 
			
		}
		
		//Validación para las cartas sin puntos:
		if(valor.equals("K") || valor.equals("Q") || valor.equals("J") || valor.equals("10")){
			
			//Asignación de los puntos:
			puntos = 0;
			
		}else{
			
			//Asignación de los puntos:
			puntos = Integer.valueOf(valor);
			
		}
		
		//Retorno:
		return puntos;
		
	}
	
	//Retorna los puntos de una mano:
	public int extraerPuntosMano(Vector<String> mano){
		
		//Inicialización de la variable a retornar:
		int puntos = 0;
		
		//Extracción de los puntos de cada carta:
		for(int x=0; x<mano.size(); x++){
			puntos += extraerPuntosCarta(mano.elementAt(x));
		}
		
		//Validación del límite de los puntos:
		if(puntos > 19){
			puntos -= 10;
		}
		if(puntos > 9){
			puntos -= 10;
		}
		
		//Retorno:
		return puntos;
		
	}
	
	//Decide si una mano tiene natural:
	public boolean validarNatural(Vector<String> mano){
		
		//Inicialización de la variable a retornar:
		boolean salida = false;
		
		//Extracción de los puntos de la mano:
		int puntos = extraerPuntosMano(mano);
		
		//Validación de la jugada natural:
		if(puntos == 8 || puntos == 9){
			salida = true;
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna una carta de un vector y la mueve a otro:
	public String sacarCarta(){
		
		//Inicialización de la variable a retornar:
		String salida;
		
		//Inicialización del random:
		Random aleatorio = new Random();
		
		//Inicialización de la posición a utilizar:
		int posicion;
		
		//Validación del estado de la baraja:
		if(baraja.isEmpty()){
			
			//Generación aleatoria de una posición de la pila de descartes:
			posicion = aleatorio.nextInt(descarte.size());
			
			//Traspaso de la carta a la baraja:
			baraja.add(descarte.elementAt(posicion));
			
			//Asignación y eliminación de la carta a retornar:
			salida = descarte.remove(posicion);
			
		}else{
			
			//Generación aleatoria de una posición de la baraja:
			posicion = aleatorio.nextInt(baraja.size());
			
			//Traspaso de la carta a la pila de descartes:
			descarte.add(baraja.elementAt(posicion));
			
			//Asignación y eliminación de la carta a retornar:
			salida = baraja.remove(posicion);
			
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna el estado final del juego con o sin terceras cartas:
	public String validarTercerasCartas(Vector<String> manoJugador,Vector<String> manoCrupier){
		
		//Inicialización de variable a utilizar:
		boolean control = true;
		
		//Validación de la tercera carta del jugador:
		if(extraerPuntosMano(manoJugador) >= 0 && extraerPuntosMano(manoJugador) <= 5){
			
			//Robo de la tercera carta del jugador:
			manoJugador.add(sacarCarta());
			
			//Inicialización del valor de la tercera carta robada:
			int terceraCartaJugador = extraerPuntosCarta(manoJugador.elementAt(2));
			
			//Validación y robo de la tercera carta del crupier:
			if(extraerPuntosMano(manoCrupier) == 6 && (terceraCartaJugador == 6 || terceraCartaJugador == 7) && control){
				manoCrupier.add(sacarCarta());
				control = false;
			}
			if(extraerPuntosMano(manoCrupier) == 5 && (terceraCartaJugador == 7 || terceraCartaJugador == 4) && control){
				manoCrupier.add(sacarCarta());
				control = false;
			}
			if(extraerPuntosMano(manoCrupier) == 4 && (terceraCartaJugador == 7 || terceraCartaJugador == 2) && control){
				manoCrupier.add(sacarCarta());
				control = false;
			}
			if(extraerPuntosMano(manoCrupier) == 3 && terceraCartaJugador != 8 && control){
				manoCrupier.add(sacarCarta());
				control = false;
			}
			if(extraerPuntosMano(manoCrupier) >= 0 && extraerPuntosMano(manoCrupier) <= 2 && control){
				manoCrupier.add(sacarCarta());
				control = false;
			}
			
		}
		
		//Inicialización de la variable a retornar:
		String salida = "";
		
		//Decisión del ganador de la ronda:
		if(extraerPuntosMano(manoJugador) > extraerPuntosMano(manoCrupier)){
			salida = "J";
		}else{
			if(extraerPuntosMano(manoJugador) < extraerPuntosMano(manoCrupier)){
				salida = "B";
			}else{
				salida = "E";
			}
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna el estado final del juego si hay jugadas naturales:
	public String validarJugadaNatural(Vector<String> manoJugador,Vector<String> manoCrupier){
		
		//Inicialización de las variables a utilizar:
		boolean naturalJugador, naturalCrupier;
		String salida = "";
		
		//Validación del natural en ambas manos:
		naturalJugador = validarNatural(manoJugador);
		naturalCrupier = validarNatural(manoCrupier);
		
		//Decisión del ganador de la ronda:
		if(naturalJugador && naturalCrupier){
			if(extraerPuntosMano(manoJugador) == extraerPuntosMano(manoCrupier)){
				salida = "E";
			}else{
				if(extraerPuntosMano(manoJugador) > extraerPuntosMano(manoCrupier)){
					salida = "J";
				}else{
					salida = "B";
				}
			}
		}else{
			if(naturalJugador){
				salida = "J";
			}
			if(naturalCrupier){
				salida = "B";
			}
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna las ganancias de una apuesta:
	public double calcularGanancias(String apuesta, String decision){
		
		//Inicialización de la variable a retornar:
		double salida = 0;
		
		//Separación de los datos de la apuesta:
		String[] division = apuesta.split("-");
		String campo = division[0];
		String cantidad = division[1];
		
		//Validación del acierto de la apuesta:
		if(campo.equals(decision)){
			
			//Asignación de las ganancias de acuerdo a la apuesta:
			switch(campo){
				case "J":
					salida = Double.valueOf(cantidad)*2;
					break;
				case "B":
					salida = Double.valueOf(cantidad)*2;
					salida -= salida*(0.05);
					break;
				case "E":
					salida = Double.valueOf(cantidad)*8;
					break;
			}
			
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna las cartas de una mano en forma de String:
	public String tomarCartas(Vector<String> mano){
		
		//Inicialización de la variable a retornar:
		String salida = "";
		
		//Concatenación de las cartas de la mano:
		for(int x=0; x<mano.size(); x++){
			salida += " " + mano.elementAt(x);
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Reinicia el juego para una nueva ronda:
	public void limpiar(){
		
		//Reinicialización de las variables y limpieza de los vectores:
		decision = "";
		manoJugador.clear();
		manoCrupier.clear();
		natural = false;
		
	}
	
	//Reparte las cartas a ambas manos y decide el estado final del juego:
	public void repartir(){
		
		//Repartición de las cartas del jugador:
		manoJugador.add(sacarCarta());
		manoJugador.add(sacarCarta());
		
		//Repartición de las cartas del crupier:
		manoCrupier.add(sacarCarta());
		manoCrupier.add(sacarCarta());
		
		//Validación de jugadas naturales:
		decision = validarJugadaNatural(manoJugador, manoCrupier);
		natural = true;
		
		//Validación de las terceras cartas:
		if(decision.equals("")){
			decision = validarTercerasCartas(manoJugador, manoCrupier);
			natural = false;
		}
		
	}
	
	//Retorna la información final de la ronda como String:
	public String jugar(String[] paquete){
		
		//Inicialización de la variable de las ganancias:
		double ganancias = 0;
		
		//Cálculo de los pagos de cada apuesta:
		for(int x=0; x<paquete.length; x++){
			ganancias += calcularGanancias(paquete[x], decision);
		}
		
		//Concatenación de las ganancias:
		String salida = String.valueOf(ganancias);
		
		//Validación y concatenación del tipo de ronda:
		if(natural){
			salida += " N" +  decision;
		}else{
			salida += " " + decision;
		}	
		
		//Concatenación de los puntos obtenidos:
		salida += " " + extraerPuntosMano(manoJugador);
		salida += " " + extraerPuntosMano(manoCrupier);
		
		//Concatenación de las cartas jugadas:
		salida += tomarCartas(manoJugador);
		salida += tomarCartas(manoCrupier);
		
		//Retorno:
		return salida;
		
	}
}
