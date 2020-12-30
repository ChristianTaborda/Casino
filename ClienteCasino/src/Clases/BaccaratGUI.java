package Clases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class BaccaratGUI extends JFrame{
	
	private JPanel panelPrincipal;
	private JLabel fondo;
	private JLabel[] cartas,mensajes;
	private JButton[] monedas;
	private JButton[]  apuestas;
	private JButton regresar;
	private int tipo,acumulado=0,empate=0, banca=0, jugador=0;//
	private double valor=0;
	private EscuchaMonedas escucha1= new EscuchaMonedas();
	private EscuchaApuestas escucha2= new EscuchaApuestas();
	private ControlBaccarat controlador= new ControlBaccarat();
	private int numJugador;
	static double creditos;
	static boolean barajar;
	static DJ musica;
	
	public BaccaratGUI(int numJugador,double creditos){
		barajar = false;
		this.numJugador=numJugador;
		this.creditos=creditos;
		musica= new DJ();
		initGUI();
		mensajes[5].setText("Jugador:"+ numJugador);
		mensajes[2].setText("Credito: "+ creditos);
		this.setVisible(true);
		this.setTitle("Baccarat");
		this.setResizable(false);
		this.setMinimumSize(new Dimension(1250,720));
	}
	
	public void initGUI(){
		monedas= new JButton[4];
		apuestas= new JButton[12];
		panelPrincipal= new JPanel(); //Panel principal
		panelPrincipal.setLayout(null);
		panelPrincipal.setOpaque(false);

		controlador.initBotones(numJugador,monedas,apuestas,escucha1,escucha2,panelPrincipal);
		
		regresar= new JButton("REGRESAR");
		regresar.setBounds(1150,0,100,40);
		regresar.setBackground(Color.RED);
		regresar.setForeground(Color.WHITE);
		regresar.setFocusable(false);
		regresar.addActionListener(escucha2);
		
		panelPrincipal.add(regresar);		
		
		initLabels();//Se crean los label a usar
		
		panelPrincipal.add(fondo);
		add(panelPrincipal);
		
	}
	public void initLabels(){
		cartas= new JLabel[6];//Se inicializa el arreglo de JLabel usado para las cartas
		mensajes= new JLabel[6];//Se inicializa el arreglo de JLabel usado para los mensajes que se van a mostrar
		fondo= new JLabel(); //JLabel usado para la imagen de fondo
		controlador.initLabel(fondo, cartas, mensajes, panelPrincipal);
	}
	
	public ControlBaccarat getControlador() {
		return controlador;
	}
	public void getEmpate(){
		controlador.getEmpate();
	}
	public void getBanca(){
		controlador.getBanca();
	}
	public void getJugador(){
		controlador.getJugador();
	}
	
	public JButton[] getApuestas() {
		return apuestas;
	}
	
	public void setBarajar(){
		barajar = true;
	}
	
	public JLabel[] getCartas() {
		return cartas;
	}

	public JLabel[] getMensajes() {
		return mensajes;
	}

	public void setMensajes(JLabel[] mensajes) {
		this.mensajes = mensajes;
	}

	public Double getCredito(){
		return creditos;
	}

	//Escucha de los botones de monedas 
	private class EscuchaMonedas implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton botonpres =(JButton)e.getSource();
			valor=controlador.accionEscuchaM(apuestas,mensajes,cartas,botonpres,monedas,acumulado);//Valor de la moneda
			tipo=controlador.getTipo();
			musica.reproducirAudio("ficha.wav");
		}
		
	}
	//Escucha de cada campo de apuesta
	private class EscuchaApuestas implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {

			JButton botonpres =(JButton)e.getSource();
			if(botonpres==regresar){//Boton para regresar al menu principal
				Cliente.tipoJuego = "n";
				try {
					Cliente.salida.writeObject("n");
					Cliente.salida.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				dispose();
				Cliente.puntajeGlobal = creditos;
				Cliente.elegirJuego();
			}else{
				if(!barajar){
					if(creditos>=valor){
						// TODO Auto-generated method stub
						musica.reproducirAudio("ficha.wav");
						botonpres.setIcon((new ImageIcon(getClass().getResource("/Imagenes/"+tipo+"S.png"))));
						Cliente.enviarDatos(controlador.apuesta(numJugador,valor,botonpres)); //Suma para la apuesta total y retorna el campo jugado para pintarlo en los demas tableros
						creditos-=valor;
						mensajes[2].setText("Creditos: "+ creditos);
						empate=controlador.getEmpate();
						banca=controlador.getBanca();
						jugador=controlador.getJugador();
						acumulado=controlador.getBanca()+controlador.getEmpate()+controlador.getJugador();
						mensajes[1].setText("Apuesta: "+acumulado);
					}else{
						JOptionPane.showMessageDialog(null, "No tienes suficientes creditos para esta apuesta");
					}
				}
			}	
		}
		
	}
}
