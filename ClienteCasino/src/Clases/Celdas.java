package Clases;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Celdas extends JButton{
	
	Celdas(){
		setContentAreaFilled(false); // Quita ese relleno azul 
		setFocusPainted(false); // Quita relleno selecionado
		setBorderPainted( false ); // Quita borde del boton
	}
	 protected void paintComponent(Graphics g) {
         if (getBackground().getAlpha() < 255) {
             g.setColor(getBackground());
             g.fillRect(0, 0, getWidth(), getHeight());
         }
         super.paintComponent(g);
     }

}
