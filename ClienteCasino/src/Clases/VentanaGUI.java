package Clases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class VentanaGUI extends JFrame{
	private String usuario, clave;
	private boolean encontrado;
	JPanel panel;
	JLabel fondo;
	JButton ruleta, baccarat;
	EventoBoton eventoB;
	String tipoJuego = "";
	
	VentanaGUI(){	
		eventoB = new EventoBoton();
		fondo = new JLabel();
		ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/principal.png"));
		fondo.setBackground(new Color(37,4,4));
		fondo.setIcon(img); //Imagen de fondo
		fondo.setBounds(0, 0, 800, 500);
		panel = new JPanel();
		panel.setLayout(null);
		ruleta = new JButton();
		baccarat = new JButton();
		ruleta.setBounds(70, 250, 280, 210);
		ruleta.setContentAreaFilled(false); // Quita ese relleno azul 
		ruleta.setFocusPainted(false); // Quita relleno selecionado
		ruleta.setBorderPainted( false ); // Quita borde del boton
		baccarat.setContentAreaFilled(false); // Quita ese relleno azul 
		baccarat.setFocusPainted(false); // Quita relleno selecionado
		baccarat.setBorderPainted( false ); // Quita borde del boton
		baccarat.setBounds(450, 250, 280, 210);
		ruleta.addActionListener(eventoB);
		baccarat.addActionListener(eventoB);
		panel.setBackground(new Color(37,4,4));
		panel.add(ruleta);
		panel.add(baccarat);
		panel.add(fondo);
		panel.setPreferredSize(new Dimension(800,500));
	}
	
	public void personalizarJPanel(){
		//Cambio de color de fondo y texto para los JOptionPane:
				UIManager.put("OptionPane.background",new ColorUIResource(new Color(37,4,4)));
				UIManager.put("Panel.background",new ColorUIResource(new Color(37,4,4)));
				UIManager.put("OptionPane.messageForeground", new Color(214,214,214));
				UIManager.put("Label.foreground", new Color(214,214,214));
				
				//Inicialización de fuentes para los JOptionPane:
				Font label = new Font("Arial Black",Font.BOLD,22);
				Font button = new Font("Arial Black",Font.BOLD,15);
				
				//Cambio de fuente para los JOptionPane:
				UIManager.put("OptionPane.messageFont", label);
				UIManager.put("OptionPane.buttonFont", button);
	}
	
	public String ventanaRegistro(ObjectOutputStream salida, ObjectInputStream entrada){
		personalizarJPanel();
		
	//Creamos una gran ventana para pedir datos -----------------------
		JTextField usua = new JTextField();
		usua.setBorder(null);
		JPasswordField  cont  = new JPasswordField (); //Asteristos para la contraseña
		cont.setBorder(null);
	    JPanel panelRegistro = new JPanel();	
	    JPanel panelInterno = new JPanel();	   
	    JLabel imagenRegistro = new JLabel();
	    JLabel mensaje = new JLabel("Ingrese los datos", JLabel.CENTER);
	    imagenRegistro.setIcon(new ImageIcon(getClass().getResource("/Imagenes/bienvenida.png")));
	    imagenRegistro.setBounds(0,0,200,200);
	    panelRegistro.setLayout(null);
	    panelInterno.setLayout(new GridLayout(2, 2,15,15)); // Para mensajes y campo de clave y usuario
	    panelInterno.setBounds(210,30,250,80);
	    panelInterno.add(new JLabel("Ingrese su usuario: "));
	    panelInterno.add(usua);
	    panelInterno.add(new JLabel("Ingrese su clave: "));
	    panelInterno.add(cont);
	    mensaje.setBounds(210,100,250,80);
	    panelRegistro.setPreferredSize(new Dimension(480,200));	    	    
	    panelRegistro.add(imagenRegistro);
	    panelRegistro.add(mensaje);	    
	    panelRegistro.add(panelInterno);
	    boolean paso = false;
	    String informacion = "";
	    String[] botones = {"Cerrar", "Intentar"};
	    int valor=0;
	    char[]  pass;
	    //-------------- Fin inicio codigo absurdo para interfaz bonita---------------
	    do{
		    do{ // No sale hasta escribir usuarios validos o crearlo	
				// No sale hasta escribir algo >:v
				    JOptionPane.showMessageDialog(null, panelRegistro, "Ingresar", JOptionPane.PLAIN_MESSAGE);
				    pass = cont.getPassword();
				    usuario = usua.getText();  clave = new String(pass);					
			   if(usuario.equalsIgnoreCase("")  || clave.equalsIgnoreCase("")){
				  paso = true; 
				  if(usuario.equalsIgnoreCase("")){informacion=informacion+"usuario ";}
				  if(clave.equalsIgnoreCase("")){informacion=informacion+"clave";}
			   }else{paso=false;valor=0;}
			    if(paso){
			    	ImageIcon vol = new ImageIcon(getClass().getResource("/Imagenes/bienvenida.png"));
			    	valor = JOptionPane.showOptionDialog(null, "Falta: "+informacion, "Ingresar", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, vol, botones,botones[0]);}			
			    informacion = "";
			    if(valor==-1){System.exit(-1);}
		    }while(valor!=0);	
		    
		    try {
				salida.writeObject(usuario+","+clave);
				salida.flush();
				encontrado = (boolean)entrada.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error en la conexion: "+e.getMessage());

			} //manda la clave y usuario					
		    if(!encontrado){ JOptionPane.showMessageDialog(null, "El USUARIO YA EXISTE, LA CONTRASEÑA ES ERRONEA", "Ingresar", JOptionPane.ERROR_MESSAGE);}
    }while(!encontrado);
	    return (usuario+","+clave);
	}
	
	public String elegriJuego(){
	    JOptionPane.showMessageDialog(null, panel, "Ingresar", JOptionPane.PLAIN_MESSAGE);
		return tipoJuego;
	}

	private class EventoBoton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton boton = (JButton) e.getSource();
			if(boton==ruleta){
				tipoJuego = "ruleta";				
			}else{
				tipoJuego = "baccarat";
			}
		}
		
	}
	
}
