/*****************************************************
 * -Christian Camilo Taborda Campiño    1632081-3743 *
 * -Cristian Camilo Vallecilla Cuellar  1628790-3743 *
 * -Esneider Arbey Manzano Arango       1628373-3743 *
 * -Fecha de creación:                  07/06/2017   *
 * -Fecha de última modificación:       15/06/2017   *
 *****************************************************/ 


package Clases;

import java.util.Random;

public class LogicaRuleta {
	
	/*************
	 * ATRIBUTOS *
	 *************/
	
	private int casilla;
	private Random aleatorio;
	
	/***********
	 * MÉTODOS *
	 ***********/
	
	//Constructor:
	public LogicaRuleta(){
		
		//Inicialización de los atributos:
		casilla = 0;
		aleatorio = new Random();
		
	}
	
	//Decide si el número final pertenece a los de una apuesta:
	public boolean validar(String[] numeros){
		
		//Inicialización de la variable a retornar:
		boolean salida = false;
		
		//Validación de pertenencia del número:
		for(int x=0; x<numeros.length; x++){
			if(numeros[x].equals(String.valueOf(casilla))){
				salida = true;
			}
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna el múltiplo correspondiente al tipo de apuesta:
	public double multiplo(String tipo){
		
		//Inicialización de la variable a retornar:
		double salida = 0;
		
		//Asignación del múltiplo de acuerdo a la apuesta:
		switch(tipo){
			case "RN":
				salida = 1;
				break;
			case "PI":
				salida = 1;
				break;
			case "PF":
				salida = 1;
				break;
			case "DO":
				salida = 2;
				break;
			case "CO":
				salida = 2;
				break;
			case "DD":
				salida = 0.5;
				break;
			case "DC":
				salida = 0.5;
				break;
			case "SE":
				salida = 5;
				break;
			case "CU":
				salida = 8;
				break;
			case "TR":
				salida = 11;
				break;
			case "CA":
				salida = 17;
				break;
			case "PL":
				salida = 35;
				break;
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna el pago obtenido por una apuesta:
	public double calcularPago(String tipo, String valor, String[] numeros){
		
		//Inicialización de la variable a retornar:
		double salida = 0;
		
		//Validación para el cero y asignación del pago:
		if(casilla == 0 && (tipo.equals("RN") || tipo.equals("PI") || tipo.equals("PF"))){
			salida = 0.5 * Double.valueOf(valor);
		}else{
			
			//Validación de la apuesta y asignación del pago:
			if(validar(numeros)){
				salida = multiplo(tipo) * Double.valueOf(valor);
			}
			
		}	
		
		//Retorno
		return salida;
		
	}
	
	//Extrae los números que hacen parte de una apuesta:
	public String[] extraerNumeros(String tipo, String numeros){
		
		//Inicialización de la variable a retornar y a utilizar:
		String[] salida, limites;
		int contador;
		
		//Validación del tipo de apuesta:
		switch(tipo){
		
			case "RN":
				
				//Validación de rojo o negro y asignación de números:
				if(numeros.equals("77")){
					String[] rojos = {"1","3","5","7","9","12","14","16","18","19","21","23","25","27","30","32","34","36"};
					salida = rojos;
				}else{
					String[] negros = {"2","4","6","8","10","11","13","15","17","20","22","24","26","28","29","31","33","35"};
					salida = negros;
				}
				break;
				
			case "PI":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[18];
				
				//Validación de par o impar y asignación de límites:
				if(numeros.equals("55")){
					String[] par = {"2","36"};
					limites = par;
				}else{
					String[] impar = {"1","35"};
					limites = impar;
				}
				
				//Inicialización del contador y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				for(int x=0; x<18; x++){
					salida[x] = String.valueOf(contador);
					contador+=2;
				}
				break;
				
			case "PF":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[18];
				
				//Validación de pasa o falta y asignación de límites:
				if(numeros.equals("44")){
					String[] pasa = {"1","18"};
					limites = pasa;
				}else{
					String[] falta = {"19","36"};
					limites = falta;
				}
				
				//Inicialización del contador y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				for(int x=0; x<18; x++){
					salida[x] = String.valueOf(contador);
					contador++;
				}
				break;
				
			case "DO":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[12];
				
				//Asignación de límites:
				limites = numeros.split(",");
				
				//Inicialización del contador y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				for(int x=0; x<12; x++){
					salida[x] = String.valueOf(contador);
					contador++;
				}
				break;
				
			case "CO":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[12];
				
				//Asignación de límites:
				limites = numeros.split(",");
				
				//Inicialización del contador y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				for(int x=0; x<12; x++){
					salida[x] = String.valueOf(contador);
					contador+=3;
				}
				break;
				
			case "DD":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[24];
				
				//Asignación de límites:
				limites = numeros.split(",");
				
				//Inicialización del contador y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				for(int x=0; x<24; x++){
					salida[x] = String.valueOf(contador);
					contador++;
				}
				break;
				
			case "DC":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[24];
				
				//Asignación de límites:
				limites = numeros.split(",");
				
				//Inicialización de los contadores y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				int contador2 = Integer.valueOf(limites[2]);
				for(int x=0; x<12; x++){
					salida[x] = String.valueOf(contador);
					contador+=3;
				}
				for(int x=12; x<24; x++){
					salida[x] = String.valueOf(contador2);
					contador2+=3;
				}
				break;
				
			case "SE":
				
				//Asignación del tamaño del arreglo a retornar:
				salida = new String[6];
				
				//Asignación de límites:
				limites = numeros.split(",");
				
				//Inicialización de los contadores y asignación de los números:
				contador = Integer.valueOf(limites[0]);
				for(int x=0; x<6; x++){
					salida[x] = String.valueOf(contador);
					contador++;
				}
				break;
				
			default:
				
				//Asignación de los números:
				salida = numeros.split(",");
				break;
				
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna la moneda apostada de acuerdo a un número:
	public String extraerMoneda(String valor){
		
		//Inicialización de la variable a retornar:
		String salida = "";
		
		//Validación del tipo de moneda:
		switch(valor){
			case "0":
				salida = "1";
				break;
			case "1":
				salida = "5";
				break;
			case "2":
				salida = "25";
				break;
			case "3":
				salida = "100";
				break;
		}
		
		//Retorno:
		return salida;
		
	}
	
	//Retorna el pago de una apuesta:
	public double pagar(String apuesta){
		
		//Separación de los datos de la apuesta:
		String[] division = apuesta.split("-");
		String tipo = division[0];
		String[] division2 = division[1].split("_");
		String valor = extraerMoneda(division2[0]);
		String[] numeros = extraerNumeros(tipo,division2[1]);
		
		//Retorno:
		return calcularPago(tipo,valor,numeros);
		
	}
	
	//Asigna una casilla aleatoria al juego:
	public int girar(){
		
		//Generación aleatoria del número:
		casilla = aleatorio.nextInt(37);
		
		return casilla;
		
	}
	
	//Retorna la información de una ronda del juego como String:
	public String jugar(String[] paquete){
		
		//Inicialización de la variable de las ganancias:
		double ganancias = 0;
		
		//Asignación de las ganancias:
		for(int x=0; x<paquete.length; x++){
			ganancias += pagar(paquete[x]);
		}
		
		//Concatenación de las ganancias:
		String salida = String.valueOf(ganancias);
		
		//Retorno
		return salida;
		
	}
}
