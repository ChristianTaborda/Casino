package Clases;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControlBaccarat {
	
	private int  empate=0, banca=0, jugador=0,valor=0, tipo=0;
	private String empateS="E-",bancaS="B-", jugadorS="J-",apuestaFinal;
	private String campo;
	private double ganancia;

	public ControlBaccarat(){}
	
	//Funcion que crea los botones con lo requerido
	public void initBotones(int jugador,JButton[] monedas,JButton[] apuestas,java.awt.event.ActionListener escucha1,java.awt.event.ActionListener escucha2,JPanel panelPrincipal){
		
		for(int i=0; i<4; i++){			//Crea los botones correspondientes a las monedas con sus respectivas imagenes
			monedas[i]= new JButton();
			monedas[i].setIcon((new ImageIcon(getClass().getResource("/Imagenes/"+i+"O.png"))));
			monedas[i].setContentAreaFilled(false);
			monedas[i].setBorderPainted(false);
			monedas[i].setFocusPainted(false);
			monedas[i].addActionListener(escucha1);		//Se le añade a escucha
					
			panelPrincipal.add(monedas[i]);
		}
		//Se ubican en la posicion deseada 
		monedas[0].setBounds(1005,610,30,30);
		monedas[1].setBounds(1040,610,30,30);
		monedas[2].setBounds(995,640,30,30);
		monedas[3].setBounds(1048,640,30,30);
		
		int contador=1;
		int contador1=1;
		int contador2=1;
		//Crea los botones sobre los cuales se van a las apuestas(empate,banca y jugador)
		for(int i=0; i<12; i++){
			apuestas[i]= new JButton();
			apuestas[i].setContentAreaFilled(false);
			apuestas[i].setBorderPainted(false);
			apuestas[i].setFocusPainted(false);
			if(i>=0&&i<=3){  //Se asignan los nombres dependiendo del campo(empate,banca o jugador)
				apuestas[i].setName("E"+contador);
				contador++;
			}else{
				if(i>=4&&i<=7){
					apuestas[i].setName("B"+contador1);
					contador1++;
				}else{
					apuestas[i].setName("J"+contador2);
					contador2++;
				}
			}
			escuchaJugador(jugador,i,apuestas[i],escucha2);//Se les añade la escucha 
			panelPrincipal.add(apuestas[i]);				//se añaden al panel
		}
		
		//se ubican en la posicion deseada
		//Empate
		apuestas[0].setBounds(430,390,70,50);
		apuestas[1].setBounds(520,425,90,50);
		apuestas[2].setBounds(630,425,90,50);
		apuestas[3].setBounds(750,390,70,50);
		//Banca	
		apuestas[4].setBounds(325,415,90,70);
		apuestas[5].setBounds(455,478,140,60);
		apuestas[6].setBounds(640,478,140,60);
		apuestas[7].setBounds(830,430,90,60);
		//Jugador	
		apuestas[8].setBounds(110,460,200,100);
		apuestas[9].setBounds(370,550,230,100);
		apuestas[10].setBounds(630,550,240,100);
		apuestas[11].setBounds(920,463,150,100);
		
		
	}
	public void initLabel(JLabel fondo,JLabel[] cartas,JLabel[] mensajes,JPanel panelPrincipal){
		
		fondo.setOpaque(false);
		fondo.setIcon(new ImageIcon(getClass().getResource("/Imagenes/FONDOBA.png")));
		fondo.setBounds(0,0,1250,690);
		
	
		int contador=0;
		for(int i=0; i<6; i++){//Se crean los label´s de las cartas
			cartas[i]=new JLabel(); 
			cartas[i].setBounds(340+contador,215,100,145);
			cartas[i].setHorizontalAlignment(JLabel.CENTER);
			cartas[i].setVerticalAlignment(JLabel.CENTER);
			contador+=70;
			if(i==2){
				contador+=95;
			}
		}
		for(int i=5; i>=0;i--){ 
			panelPrincipal.add(cartas[i]);
		}
				
		for(int i=0; i<6; i++){//Se crean los label´s de los mensajes a mostrar
			mensajes[i]=new JLabel();
			mensajes[i].setFont(new Font("TimesRoman", Font.BOLD, 20));
			mensajes[i].setForeground(Color.WHITE);
			panelPrincipal.add(mensajes[i]);
		}
		
		mensajes[0].setBounds(525,370,200,60);//Muestra el tipo de jugada y ganancia
		mensajes[0].setHorizontalAlignment(JLabel.CENTER);
		
		mensajes[1].setText("Apuesta: 0"); //Muetra la apuesta total en cada ronda
		mensajes[1].setBounds(50,560,200,40);
		
		mensajes[2].setText("Credito: 0"); //Muestra el credito del jugador
		mensajes[2].setBounds(50,620,200,40);
	
		mensajes[3].setBounds(320,180,200,40);//Muestra el puntaje del jugador
		mensajes[4].setBounds(810,180,200,40);//Muestra el puntaje de la banca
		
		mensajes[5].setText("Jugador:");//Muestra el numero de jugador que es
		mensajes[5].setBounds(50,540,200,20);
	}
	
   //Le añade las escucha a los campos de apuestas dependiendo del numero del jugador
	public void escuchaJugador(int j,int a,JButton b,java.awt.event.ActionListener escucha2){
		if(j==1){
			if(a==0||a==4||a==8){
				b.addActionListener(escucha2);
			}
		}else{
			if(j==2){
				if(a==1||a==5||a==9){
					b.addActionListener(escucha2);
				}
			}else{
				if(j==3){
					if(a==2||a==6||a==10){
						b.addActionListener(escucha2);
					}
				}else{	
					if(j==4){
						if(a==3||a==7||a==11){
							b.addActionListener(escucha2);
						}
					}
				}	
			}
		}
	}
	//Muestra las cartas que salieron en la ronda,la ganacia,el puntaje del jugador y de la banca, a partir del arreglo que envia el servidor
	public void recibirDatosS(String datos,JButton[] apuestas,JLabel[] mensajes,JLabel[] cartas){
		String[] arreglo=datos.split(" ");
			ganancia=Double.parseDouble(arreglo[0]);
			BaccaratGUI.creditos+=ganancia;
			Thread hilo = new Thread(){    //Se crea el hilo para poder tener las esperas.
	            @Override
	            public synchronized void run(){
	            	try {  	
	            		//Se van mostrando las cartas de forma ordenada, para eso se invoca a la funcion setImage que realiza el giro de la carta 
		            	setImage(arreglo,cartas, arreglo[4],0);  
		            	sleep(500);
		            	setImage(arreglo,cartas, arreglo[5],1);
		            	sleep(500);
						if(arreglo.length==8){	//quiere decir que tanto jugador como banca tuvieron dos cartas
							mensajes[3].setText("Puntaje: "+arreglo[2]);	//Se muestra el puntaje del jugador
							setImage(arreglo,cartas, arreglo[6],3);
							sleep(500);
			            	setImage(arreglo,cartas, arreglo[7],4);
			            	sleep(300);
			            	mensajes[4].setText("Puntaje: "+arreglo[3]);	//Se muestra el puntaje de la banca
						}
	
						if(arreglo.length==9){ //quiere decir que el jugador tuvo 3 cartas y la banca 2
							setImage(arreglo,cartas, arreglo[6],2);
							sleep(500);
							mensajes[3].setText("Puntaje: "+arreglo[2]);
			            	setImage(arreglo,cartas, arreglo[7],3);
			            	sleep(500);
			            	setImage(arreglo,cartas, arreglo[8],4);
			            	sleep(300);
			            	mensajes[4].setText("Puntaje: "+arreglo[3]);
							
						}
						if(arreglo.length==10){ //quiere decir que tanto jugador como banca tuvieron 3 cartas
							setImage(arreglo,cartas, arreglo[6],2);
							sleep(500);
							mensajes[3].setText("Puntaje: "+arreglo[2]);
			            	setImage(arreglo,cartas, arreglo[7],3);
			            	sleep(500);
			            	setImage(arreglo,cartas, arreglo[8],4);
			            	sleep(500);
			            	setImage(arreglo,cartas, arreglo[9],5);
			            	sleep(300);
			            	mensajes[4].setText("Puntaje: "+arreglo[3]);			     
						}
						caso(arreglo[1],mensajes); //Setea el label que muestra el tipo de jugada y la ganancia
						if(ganancia!=0.0){
							BaccaratGUI.musica.reproducirAudio("dinero.wav");
						}
						mensajes[2].setText("Credito: "+BaccaratGUI.creditos);
						sleep(3000);
	            } catch (InterruptedException ex) {
	 	             ex.printStackTrace();
	            	}
	            BaccaratGUI.barajar = false;
	            limpiarTablero(apuestas,mensajes,cartas,valor);
	            
	            
	            }
	 		};
	 		hilo.start(); //Se inicia el hilo
	           
			
	}
	
	//Funcion que realiza el giro de la carta
	public void setImage(String[] arreglo,JLabel[] cartas, String tipo, int f){
		Thread hilo = new Thread(){    //Se crea el hilo para poder tener las esperas.
            @Override
            public synchronized void run(){
                try {  
                	BaccaratGUI.musica.reproducirAudio("carta.wav");
                	cartas[f].setIcon(new ImageIcon(getClass().getResource("/Imagenes/CV.png")));//se muestra la carta boca abajp
	                int aux=100;
	                for(int i=1;i<92;i+=5){
	                	ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/CV.png"));
	                	img = new ImageIcon((img.getImage()).getScaledInstance(Math.abs(aux-i), 140, java.awt.Image.SCALE_SMOOTH));//Se reduce su tamaño
	                	cartas[f].setIcon(img);
	                	sleep(3);
	                }
	                //Cuando se haya reducido hasta cierto tamaño se cambia por la imagen de la carta boca arriba	
		            for(int i=1;i<105;i+=5){
		               	ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/"+tipo+".png"));
		               	img = new ImageIcon((img.getImage()).getScaledInstance(Math.abs(i),140, java.awt.Image.SCALE_SMOOTH));//Vuelve a su tamaño normal
		               	cartas[f].setIcon(img);
		               	sleep(3);
		            }
                  
                } catch (InterruptedException ex) {
	                   ex.printStackTrace();
                }
           }
		};
		hilo.start(); //Se inicia el hilo
	}
	//Setea el label que muestra el tipo de jugada y la ganancia
	public void caso(String a,JLabel[] mensajes){
		switch(a){
			case "E": mensajes[0].setText("<html><center>EMPATE<br/> Ganancia: "+ganancia+"<center><html>");  break;
			case "B": mensajes[0].setText("<html><center>BANCA<br/>Ganancia: "+ganancia+"<center><html>"); break;
			case "J": mensajes[0].setText("<html><center>JUGADOR<br/>Ganancia: "+ganancia+"<center><html>"); break;
			case "NE": mensajes[0].setText("<html><center>NATURAL EMPATE<br/>Ganancia: "+ganancia+"<center><html>"); break;
			case "NB": mensajes[0].setText("<html><center>NATURAL BANCA<br/>Ganancia: "+ganancia+"<center><html>"); break;
			case "NJ": mensajes[0].setText("<html><center>NATURAL JUGADOR<br/>Ganancia: "+ganancia+"<center><html>"); break;
		}
		
		mensajes[0].setBackground(Color.BLACK);
		mensajes[0].setOpaque(true);
	}
	//Pinta las monedas de las apuestas realizadas por los demas jugadores, en sus respectivos campos
	//Recibe un string que especifica el campo, el numero del jugador y el tipo de moneda
	public void actualizarTablero(String a,JButton[] apuestas){
		String[] arreglo=a.split(",");
		if(arreglo[0].equals("E")){
			pintarOtrasApuestas(apuestas,arreglo[1],arreglo[2],0,1,2,3);//setea el icono del campo(empate,banca o jugador) de acuerdo al numero del jugador
		}else{
			if(arreglo[0].equals("B")){
				pintarOtrasApuestas(apuestas,arreglo[1],arreglo[2],4,5,6,7);
				
			}else{
				if(arreglo[0].equals("J")){
					pintarOtrasApuestas(apuestas,arreglo[1],arreglo[2],8,9,10,11);
				}
			}
		}
	}
	//De acuerdo al numero del jugador y del campo respectivo setea el icono por la moneda respectiva
	public void pintarOtrasApuestas(JButton[] apuestas,String n,String k,int a,int b,int c,int d){
		System.out.println(k+"S");
		switch(n){
		case "1": apuestas[a].setIcon(new ImageIcon(getClass().getResource("/Imagenes/"+k+"S.png"))); break;
		case "2": apuestas[b].setIcon(new ImageIcon(getClass().getResource("/Imagenes/"+k+"S.png"))); break;
		case "3": apuestas[c].setIcon(new ImageIcon(getClass().getResource("/Imagenes/"+k+"S.png"))); break;
		case "4": apuestas[d].setIcon(new ImageIcon(getClass().getResource("/Imagenes/"+k+"S.png"))); break;
		}
	}
	public int getEmpate() {
		return empate;
	}

	public int getBanca() {
		return banca;
	}
	public int getJugador() {
		return jugador;
	}

	public int getTipo() {
		return tipo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	//Funcion que limpia el tablero y setea las variables para una nueva ronda
	public void limpiarTablero(JButton[] apuestas,JLabel[] mensajes,JLabel[] cartas, int a ){//
		a=0;
		empate=0;
		banca=0;
		jugador=0;
		empateS="E-";
		bancaS="B-";
		jugadorS="J-";
		for(int i=0; i<12; i++){
			apuestas[i].setIcon(null);
			if(i<6){
				cartas[i].setIcon(null);
			}
		}
		empate=0;
		banca=0;
		jugador=0;
		mensajes[0].setText(null);
		mensajes[0].setOpaque(false);
		mensajes[1].setText("Apuesta: 0");
		mensajes[3].setText(null);
		mensajes[4].setText(null);
	}
	
	//Funcion que realiza la escucha de la moneda 
	public double accionEscuchaM(JButton[] apuestas,JLabel[] mensajes,JLabel[] cartas,JButton botonpres,JButton[] monedas,int a){
		
		for(int i=0; i<4; i++){
			monedas[i].setIcon((new ImageIcon(getClass().getResource("/Imagenes/"+i+"O.png"))));
		}
		if(botonpres==monedas[0]){
			valor=1; //Valor de la moneda
			tipo=0; //Variable usada para saber el tipo de moneda que se presiono de acuerdo a como esta nombrada
			monedas[0].setIcon((new ImageIcon(getClass().getResource("/Imagenes/0S.png"))));
		}else{
			if(botonpres==monedas[1]){
				valor=5;		//Valor de la moneda
				tipo=1;			//Variable usada para saber el tipo de moneda que se presiono de acuerdo a como esta nombrada
				monedas[1].setIcon((new ImageIcon(getClass().getResource("/Imagenes/1S.png"))));
			}else{
				if(botonpres==monedas[2]){
					valor=25;
					tipo=2;
					monedas[2].setIcon((new ImageIcon(getClass().getResource("/Imagenes/2S.png"))));
				}else{
					if(botonpres==monedas[3]){
						valor=100;
						tipo=3;
						monedas[3].setIcon((new ImageIcon(getClass().getResource("/Imagenes/3S.png"))));
					}
				}
			}
		}
		return (double)valor;
		
	}
	//Suma la apuesta total
	public String apuesta(int l,double a,JButton b){
		if(b.getName().equals("E"+l)){
			empate+=a;
			campo="E,"+l+","+tipo;
		}
		else{
			if(b.getName().equals("B"+l)){
				banca+=a;
				campo="B,"+l+","+tipo;
			}else{
				if(b.getName().equals("J"+l)){
					jugador+=a;
					campo="J,"+l+","+tipo;
				}
					
			}
		}
		return campo;
	}
	//Concatena el String que se va a enviar al servidor
	public String concatenarEnvio(){
		empateS+=empate;
		bancaS+=banca;
		jugadorS+=jugador;		
		apuestaFinal= empateS +" "+bancaS+" "+jugadorS;
		return apuestaFinal;
	}
}
