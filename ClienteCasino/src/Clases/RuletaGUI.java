package Clases;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RuletaGUI extends JFrame{
	private Celdas[] casillas0, casillas1, casillas2, casillas3;
	private Celdas[] casillas4, casillas5, casillas6, casillas7;
	private Celdas[] monedas;
	private Ruleta ruleta;
	private DJ tocador;
	private JLabel fondo, creditos, apuesta, ganancia;
	private JPanel panel;
	private Font fuente;
	private EventoMouse eventoM;
	private EventoBoton eventoB;
	private ArrayList<String> marcadas;
	private String moneda = "0", mensaje="", catGanada, apuestas;	
	private boolean girar;	
	private int cantApuesta; 
	private double puntajeParcial;
	private Celdas menu;
	
	RuletaGUI(){
		casillas0 = new Celdas[39];
		casillas1 = new Celdas[33];
		casillas2 = new Celdas[24];
		casillas3 = new Celdas[24];
		casillas4 = new Celdas[16];
		casillas5 = new Celdas[11];
		casillas6 = new Celdas[11];
		casillas7 = new Celdas[6];
		tocador = new DJ();
		ruleta = new Ruleta();
		creditos = new JLabel();
		ganancia = new JLabel();
		apuesta = new JLabel("Apuesta: 0");
		eventoM = new EventoMouse();
		eventoB = new EventoBoton();
		panel = new JPanel();
		fondo = new JLabel();
		fuente = new Font("Arial",Font.BOLD,17);
		marcadas = new ArrayList<String>();
		cantApuesta = 0;
		girar = false;	
		apuestas = "";
				
		panel.setLayout(null);
		ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/tablero.png"));
		fondo.setIcon(img); //Imagen de fondo
		
		
		//Organizacion, tipo apuesta-numero-arregflo-botonena
		
		//Cuadritos 30x30 tercera linea, pegados a las decenas
		for(int i =0 ; i<11;i++){
			casillas5[i] = new Celdas();
			casillas6[i] = new Celdas();
			casillas5[i].setName("7,"+(i)+"=5="+(i));
			casillas5[i].addMouseListener(eventoM);
			casillas5[i].setBounds(59*i+130,370,30,30);
			casillas6[i].setName("6,"+(i)+"=6="+(i));
			casillas6[i].addMouseListener(eventoM);
			casillas6[i].setBounds(59*i+160,370,30,30);
			panel.add(casillas5[i]);
			panel.add(casillas6[i]);
		}
		casillas6[10].setVisible(false);
		
		//Botones deproporcionales, decenas, rojo,19-36 etc
		for(int i=0;i<6;i++){
			casillas4[i] = new Celdas();			
			casillas4[i].setName("99,"+(11*i+44)+"=4="+(i)); //cambio
			casillas4[i].addMouseListener(eventoM);
			casillas4[i].setBounds(118*i+87,37,115,85);
			panel.add(casillas4[i]);
		}
		
		//Botones 30x30 entre docenas
		for(int i=14;i<16;i++){
			casillas4[i] = new Celdas();
			casillas4[i].addMouseListener(eventoM);
			casillas4[i].setName("12,"+(i-14)+"=4="+(i));
			casillas4[i].setBounds((i-14)*236+307,415,30,30);
			panel.add(casillas4[i]);
		}
		
		//Docenas
		for(int i=6;i<9;i++){
			casillas4[i] = new Celdas();			
			casillas4[i].setName("8,"+(i-6)+"=4="+(i));
			casillas4[i].addMouseListener(eventoM);
			casillas4[i].setBounds(235*(i-6)+87,385,235,90);
			panel.add(casillas4[i]);
		}
		
		//Botones 30x30 con el 0 y entre 2a1
		int aux = 10;
		for(int i=9;i<12;i=i+2){
			casillas4[i] = new Celdas();
			casillas4[i+1] = new Celdas();			
			casillas4[i].setName((aux)+",0=4="+(i));
			casillas4[i].addMouseListener(eventoM);
			casillas4[i].setBounds(735*aux-7280,198,30,30);					
			casillas4[i+1].setName((aux)+",13=4="+(i+1));		
			casillas4[i+1].addMouseListener(eventoM);		
			casillas4[i+1].setBounds(735*aux-7280,285,30,30); //Cambio
			panel.add(casillas4[i]);
			panel.add(casillas4[i+1]);
			aux++;
		}
		casillas4[11].setName("11,12=4=11");
		casillas4[12].setName("11,25=4=12");		
		casillas4[13] = new Celdas(); // Boton "0"
		casillas4[13].setName("99,0=4=13");
		casillas4[13].addMouseListener(eventoM);		
		casillas4[13].setBounds(25,125,60,260);
		panel.add(casillas4[13]);
		
		
		//Cuadritos 30x30 fila entre filas 1, 2 y 3		
		for(int i =0 ; i<12;i++){
			casillas2[i] = new Celdas();
			casillas2[i+12] = new Celdas();
			casillas3[i] = new Celdas();
			casillas3[i+12] = new Celdas();
			casillas2[i].setName("1,"+(i)+"=2="+(i));
			casillas2[i].addMouseListener(eventoM);
			casillas2[i].setBounds(59*i+100,198,30,30);
			casillas3[i].setName("2,"+(i)+"=3="+(i));
			casillas3[i].addMouseListener(eventoM);
			casillas3[i].setBounds(59*i+130,198,30,30);
			casillas2[i+12].setName("3,"+(i+12)+"=2="+(i+12));
			casillas2[i+12].addMouseListener(eventoM);
			casillas2[i+12].setBounds(59*i+100,285,30,30);
			casillas3[i+12].setName("4,"+(i+12)+"=3="+(i+12));
			casillas3[i+12].addMouseListener(eventoM);
			casillas3[i+12].setBounds(59*i+130,285,30,30);
			panel.add(casillas2[i]);
			panel.add(casillas3[i]);
			panel.add(casillas2[i+12]);
			panel.add(casillas3[i+12]);

		}//2 botones sobrantes se quitan
		casillas3[11].setVisible(false);
		casillas3[23].setVisible(false);
		
		//Botones de 30x86, entre botones principales
		for(int i =0 ; i<11;i++){
			casillas1[i] = new Celdas();
			casillas1[i+11] = new Celdas();
			casillas1[i+22] = new Celdas();
			casillas1[i].setName("5,"+(i)+"=1="+(i));
			casillas1[i].addMouseListener(eventoM);
			casillas1[i].setBounds(59*i+130,125,30,86);
			casillas1[i+11].setName("5,"+(i+13)+"=1="+(i+11));
			casillas1[i+11].addMouseListener(eventoM);
			casillas1[i+11].setBounds(59*i+130,214,30,85);
			casillas1[i+22].setName("5,"+(i+26)+"=1="+(i+22));
			casillas1[i+22].addMouseListener(eventoM);
			casillas1[i+22].setBounds(59*i+130,301,30,84);							
			panel.add(casillas1[i]);
			panel.add(casillas1[i+11]);
			panel.add(casillas1[i+22]);
		}
		
		//Botones generales, del 1-36, incluyen los 3 2a1
		for(int i =0 ; i<13;i++){
			casillas0[i] = new Celdas();
			casillas0[i+13] = new Celdas();
			casillas0[i+26] = new Celdas();			
			casillas0[i].setName("99,"+(3*i+3)+"=0="+(i));
			casillas0[i].addMouseListener(eventoM);
			casillas0[i].setBounds(59*i+87,125,56,86);			
			casillas0[i+13].setName("99,"+(3*i+2)+"=0="+(i+13));
			casillas0[i+13].addMouseListener(eventoM);
			casillas0[i+13].setBounds(59*i+87,214,56,85);
			casillas0[i+26].setName("99,"+(3*i+1)+"=0="+(i+26));
			casillas0[i+26].addMouseListener(eventoM);
			casillas0[i+26].setBounds(59*i+87,301,56,83);
			panel.add(casillas0[i]);
			panel.add(casillas0[i+26]);
			panel.add(casillas0[i+13]);
		}
		//Botones "2a1"
		casillas0[12].setName("9,3=0=12");
		casillas0[25].setName("9,4=0=25");
		casillas0[38].setName("9,5=0=38");
		
		//Paneles sombrear de decenas
		for (int i=0; i<3;i++){
			casillas7[i] = new Celdas();
			casillas7[i].setBounds(235*i+87,125,235,260);
			casillas7[i].setName("99,9=99=9");
			panel.add(casillas7[i]);
		}
		
		//Paneles sombrear 2a1
		for (int i=3; i<6;i++){
			casillas7[i] = new Celdas();
			casillas7[i].setBounds(87,88*i-139,705,88-i);
			casillas7[i].setName("99,9=99=9");
			panel.add(casillas7[i]);
		}
		
		//Muestra las monedas para apostar
		monedas = new Celdas[4];
		for (int i=0; i<4;i++){
			monedas[i] = new Celdas();
			monedas[i].setBounds(60*i+865,650,30,30);
			ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/"+i+"N.png"));
			monedas[i].setIcon(icono);			
			monedas[i].setName(String.valueOf(i));
			monedas[i].addActionListener(eventoB);
			panel.add(monedas[i]);
		}

		//Agregar ultimos componentes, lables de apuesta ganacia y creditos
		ruleta.setBounds(870,87,340,340); //Agrega la ruleta
		creditos.setHorizontalAlignment(JLabel.CENTER);
		ganancia.setHorizontalAlignment(JLabel.CENTER);
		apuesta.setHorizontalAlignment(JLabel.CENTER);
		creditos.setBounds(40, 575, 170, 40);
		apuesta.setBounds(40, 640, 170, 40);
		ganancia.setBounds(450, 560, 170, 40);
		ganancia.setForeground(Color.WHITE);
		ganancia.setFont(fuente);
		apuesta.setForeground(Color.WHITE);
		apuesta.setFont(fuente);
		creditos.setForeground(Color.WHITE);
		creditos.setFont(fuente);		
		apuesta.setBackground(Color.BLACK);
		
		
		menu = new Celdas();
		menu.setBounds(450, 645, 120, 33);
		ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/regresar.png"));
		menu.setIcon(icono);
		menu.addActionListener(eventoB);
		panel.add(menu);
		fondo.setBounds(0,0,1250,700);
		
		panel.add(ganancia);
		panel.add(ruleta);
		panel.add(creditos);
		panel.add(apuesta);		
		panel.add(fondo);
		add(panel);
		setSize(1250,730);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public String getApuestas(){ //Obetener todas la jugadas
		if(apuestas.equalsIgnoreCase("")){
			apuestas = "0";
		}
		return apuestas;
	}
	
	public void setGirar(boolean bol){ //Esta girando la ruleta
		girar = bol;
	}
	public double getPuntaje() { //Retorna puntaje
		return puntajeParcial;
	}
	
	public void setPuntaje(double puntaje){ //Da el puntaje
		puntajeParcial = puntaje;
		creditos.setText("Creditos: "+String.valueOf(puntaje));
	}
	
	public void setGanancia(String gana) { //Obtener ganancia
		catGanada = gana;
		puntajeParcial += Double.parseDouble(gana);
	}
	
	public int tipoApuesta(String mon){ //Tipo de apuesta segun moneda
		int vMoneda = 0;
		switch(mon){
		case "0":vMoneda=1;break; 
		case "1":vMoneda=5;break; 
		case "2":vMoneda=25;break; 
		case "3":vMoneda=100;break; 
		}
		return vMoneda;
	}
	
	public void setApuesta(String mon) { //Tipo de apuesta
		int valorApuesta = tipoApuesta(mon);
		puntajeParcial-=valorApuesta;
		cantApuesta +=valorApuesta;
		apuesta.setText(String.valueOf("Apuesta: "+cantApuesta));		
		creditos.setText("Creditos: "+String.valueOf(puntajeParcial));
	}

	public void setIcon(String arr, int pos, String tMoneda){ //Muestra icono de otros jugadores
		ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/"+tMoneda+"N.png"));
		switch(arr){
		case "0":casillas0[pos].setIcon(img);break; 
		case "1":casillas1[pos].setIcon(img);break; 
		case "2":casillas2[pos].setIcon(img);break; 
		case "3":casillas3[pos].setIcon(img);break; 
		case "4":casillas4[pos].setIcon(img);break; 
		case "5":casillas5[pos].setIcon(img);break; 
		case "6":casillas6[pos].setIcon(img);break; 
		case "7":casillas7[pos].setIcon(img);break; 
		}
		marcadas.add(arr+","+pos);
	}
	
	public void limpiarTablero(){ //Limpia el tablero de monedas
		for(int i = 0; i < marcadas.size(); i++){
			String[] partir = marcadas.get(i).split(",");
			int pos = Integer.parseInt(partir[1]);
			switch(partir[0]){
			case "0":casillas0[pos].setIcon(null);break; 
			case "1":casillas1[pos].setIcon(null);break; 
			case "2":casillas2[pos].setIcon(null);break; 
			case "3":casillas3[pos].setIcon(null);break; 
			case "4":casillas4[pos].setIcon(null);break; 
			case "5":casillas5[pos].setIcon(null);break; 
			case "6":casillas6[pos].setIcon(null);break; 
			case "7":casillas7[pos].setIcon(null);break; 
			}
		}
		marcadas.clear();
		apuesta.setText(String.valueOf("Apuesta: 0"));
		cantApuesta = 0;
	}
	
	public void setRuleta(int numeroG) { //Pone a girar ruleta
		ruleta.setGanador(numeroG);
		Thread hilo = new Thread() {
        	int aux = 1;
			public synchronized void run() {
				 try {
					for(double i=0;i<361;i++){
						ruleta.setGrados(i);
						sleep(aux); 	
						if(i==359){i=0;aux++;}		
						if(i==180){aux++;}
						if(aux>5){if(i%9==0){aux++;}}
						if(aux>20){aux++;}
						if(aux>80){i=360;}				 
					}
					ruleta.setGrados(0);
					apuestas = "";					
					sleep(1000);
					
					ganancia.setText("Ganancia: "+catGanada); //Muestra ganancia
					creditos.setText("Creditos: "+puntajeParcial);
					
					if(!catGanada.equalsIgnoreCase("0.0")){
					tocador.reproducirAudio("dinero.wav");}
					catGanada = "";
					sleep(1500);
					limpiarTablero();
					girar = false;
					ganancia.setText("");
			} catch (InterruptedException e) {
				System.out.println("Error: "+e.getMessage());
			}
			}
			
		};hilo.start();	
		tocador.reproducirAudio("ruleta.wav");
		
		
	}
	
	private class EventoBoton implements ActionListener{ // solo botones tipo moneda
		public void actionPerformed(ActionEvent e) {			
			Celdas boton = (Celdas)e.getSource();
			if(boton==menu){
				
				try {
					Cliente.tipoJuego = "n";
					Cliente.salida.writeObject("n");
					Cliente.salida.flush();		
					Cliente.puntajeGlobal = puntajeParcial;
					limpiarTablero();
					dispose();					
					Cliente.elegirJuego();
					
				} catch (IOException e1) {
					System.out.println("Error al cambiar de juego: "+e1.getMessage());
				}
				
			}else{
			moneda = boton.getName();
			tocador.reproducirAudio("ficha.wav");
			}
		}		
	}
	
	private class EventoMouse implements MouseListener{ //Mmuesta moneda y guarda la apuesta
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!girar){
			Celdas boton = (Celdas)e.getSource();
			ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/"+moneda+".png"));
			if(puntajeParcial >= tipoApuesta(moneda)){ //Si no tiene suficientes creditos	
				boton.setIcon(img);
				tocador.reproducirAudio("ficha.wav");
				String tipoApuesta = "";
				String[] partes = boton.getName().split(",");
				System.out.println(boton.getName());
				int tipo = Integer.parseInt(partes[0]); // 004
				String resto[] = partes[1].split("=");			
				int num = Integer.parseInt(resto[0]); // 034556		
				int arreglo = Integer.parseInt(resto[1]);
				int posicion = Integer.parseInt(resto[2]);
				switch(tipo){
				case 1: mensaje = (3*num+3)+","+(3*num+2);
						tipoApuesta = "CA";break;	
				case 2:	mensaje = (3*num+3)+","+(3*num+2) +","+ (3*num+6)+","+(3*num+5); 
						tipoApuesta = "CU";break;				
				case 3:	mensaje = num*3-34+","+(num*3-35);
						tipoApuesta = "CA";break;	
				case 4: mensaje = 3*num-34+","+(3*num-35) +","+ (3*num-31)+","+(3*num-32);
						tipoApuesta = "CU";break;	
				case 5: if(num<13){
							mensaje = (3*num+3)+","+(3*num+6);						
						}else{ if(num<25){ mensaje = 3*num-37+","+(3*num-34); }
							   else{ mensaje = 3*num-77+","+(3*num-74); }
						}; tipoApuesta = "CA";break;
				case 6: mensaje = (3*num+1)+","+(3*num+4)+","+(3*num+7);
						tipoApuesta = "TR";break;	
				case 7: mensaje = (3*num+1)+","+(3*num+6);
						tipoApuesta = "SE";break;	
				case 8: mensaje = (12*num+1)+","+(12*num+12);	
						tipoApuesta = "DO";break;	
				case 9: if(num==3){ mensaje = num+",36";
						}else{  if(num==4){mensaje = num-2+",35";				
								}else{mensaje = num-4+",34";}			
						}tipoApuesta = "CO";break;	
				case 10:if(num==0){ mensaje = "0,2,3";
						}else{ mensaje ="0,1,2";}
						tipoApuesta = "TR";break;	
				case 11:if(num==12){mensaje = "3,36,2,35";
						}else{ mensaje = "2,35,1,34"; }
						tipoApuesta = "DC";break;	
				case 12:if(num==0){mensaje = "1,24";
						}else{ mensaje = "13,36"; }
						tipoApuesta = "DD";break;	
				default:switch(num){
						case 44:tipoApuesta = "PF";break;	
						case 55:tipoApuesta = "PI";break;	
						case 66:tipoApuesta = "RN";break;	
						case 77:tipoApuesta = "RN";break;	
						case 88:tipoApuesta = "PI";break;	
						case 99:tipoApuesta = "PF";break;	
						default:tipoApuesta = "PL";break;
						}
						mensaje=String.valueOf(num);
				break;
				}
				apuestas +=(tipoApuesta+"-"+moneda+"_"+mensaje+" ");
				marcadas.add(arreglo+","+posicion);
				setApuesta(moneda);
				Cliente.enviarDatos(arreglo+","+posicion+","+moneda);
				//setRuleta();
			}else{
				JOptionPane.showMessageDialog(null, "No tienes creditos suficientes para esta apuesta");
			}
		}	
		}
	
		public void mouseEntered(MouseEvent e) { //Si toca una casilla la marca
			if(!girar){
				Celdas boton = (Celdas)e.getSource();
				String[] partes = boton.getName().split(",");
				int tipo = Integer.parseInt(partes[0]); // 004
				String resto[] = partes[1].split("=");			
				int num = Integer.parseInt(resto[0]); // 034556		
				Color color = new Color(195,98,87,80);
				switch(tipo){
				case 1: casillas0[num].setBackground(color);
						casillas0[num+13].setBackground(color);break;
				case 2: casillas0[num].setBackground(color);
						casillas0[num+13].setBackground(color);
						casillas0[num+1].setBackground(color);
						casillas0[num+14].setBackground(color);break;
				case 3: casillas0[num+1].setBackground(color);
						casillas0[num+14].setBackground(color);break;
				case 4: casillas0[num+1].setBackground(color);
						casillas0[num+14].setBackground(color);
						casillas0[num+2].setBackground(color);
						casillas0[num+15].setBackground(color);break;
				case 5: casillas0[num].setBackground(color);
						casillas0[num+1].setBackground(color);break;
				case 6: casillas0[num+26].setBackground(color);
						casillas0[num+27].setBackground(color);
						casillas0[num+28].setBackground(color);break;
				case 7: casillas0[num].setBackground(color); casillas0[num+1].setBackground(color);
						casillas0[num+13].setBackground(color); casillas0[num+14].setBackground(color);
						casillas0[num+26].setBackground(color); casillas0[num+27].setBackground(color);break;
				case 8: casillas7[num].setBackground(color); boton.setBackground(color);break;
				case 9: casillas7[num].setBackground(color); boton.setBackground(color);break;
				case 10:casillas0[num].setBackground(color);casillas0[num+13].setBackground(color);
						casillas4[13].setBackground(color);break;
				case 11:casillas7[(num%4)+3].setBackground(color); casillas7[(num%4)+4].setBackground(color);
						casillas0[num].setBackground(color); casillas0[num+13].setBackground(color);break;
				case 12:casillas7[num].setBackground(color); casillas7[num+1].setBackground(color);
						casillas4[num+6].setBackground(color); casillas4[num+7].setBackground(color);
						break;
				default:boton.setBackground(color);break;
				}
			}
		}

		public void mouseExited(MouseEvent e) { //Si sale de una casilla la desmarca
			Celdas boton = (Celdas)e.getSource();
			String[] partes = boton.getName().split(",");
			int tipo = Integer.parseInt(partes[0]); // 004
			String resto[] = partes[1].split("=");			
			int num = Integer.parseInt(resto[0]); // 034556		
			switch(tipo){
				case 1: casillas0[num].setBackground(null);
						casillas0[num+13].setBackground(null);break;
				case 2: casillas0[num].setBackground(null);
						casillas0[num+13].setBackground(null);
						casillas0[num+1].setBackground(null);
						casillas0[num+14].setBackground(null);break;
				case 3: casillas0[num+1].setBackground(null);
						casillas0[num+14].setBackground(null);break;
				case 4: casillas0[num+1].setBackground(null);
						casillas0[num+14].setBackground(null);
						casillas0[num+2].setBackground(null);
						casillas0[num+15].setBackground(null);break;
				case 5: casillas0[num].setBackground(null);
						casillas0[num+1].setBackground(null);break;
				case 6: casillas0[num+26].setBackground(null);
						casillas0[num+27].setBackground(null);
						casillas0[num+28].setBackground(null);break;
				case 7: casillas0[num].setBackground(null); casillas0[num+1].setBackground(null);
						casillas0[num+13].setBackground(null); casillas0[num+14].setBackground(null);
						casillas0[num+26].setBackground(null); casillas0[num+27].setBackground(null);break;
				case 8: casillas7[num].setBackground(null); boton.setBackground(null);break;
				case 9: casillas7[num].setBackground(null); boton.setBackground(null);break;
				case 10:casillas0[num].setBackground(null);casillas0[num+13].setBackground(null);
						casillas4[13].setBackground(null);break;
				case 11:casillas7[(num%4)+3].setBackground(null); casillas7[(num%4)+4].setBackground(null);
						casillas0[num].setBackground(null); casillas0[num+13].setBackground(null);break;
				case 12:casillas7[num].setBackground(null); casillas7[num+1].setBackground(null);
						casillas4[num+6].setBackground(null); casillas4[num+7].setBackground(null);
				break;
				default:boton.setBackground(null);break;
			}			
			
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
