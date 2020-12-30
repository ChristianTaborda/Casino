package Clases;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Ruleta extends JPanel{
    private double grados = 0, grados2 = 0;
    private double bola;
    int valor =5;
    Random ram;
	
	Ruleta(){
		//setOpaque(true);
		//setBackground(new Color(0,0,0,0));
		ram = new Random();
		
       setBackground(new Color(0,76,35));
	}
	
    public void setGanador(int valor) {
         switch(valor){
        case 0:bola=355;break; 	  case 1:bola=220;break;    case 2:bola=53.5;break;
        case 3:bola=335.5;break;  case 4:bola=34;break;     case 5:bola=180.5;break;
        case 6:bola=93;break;     case 7:bola=297;break;    case 8:bola=151.5;break;
        case 9:bola=258;break;    case 10:bola=171;break;   case 11:bola=131.5;break;
        case 12:bola=316.5;break; case 13:bola=112;break;   case 14:bola=239;break;
        case 15:bola=14;break;    case 16:bola=200;break;   case 17:bola=73;break;
        case 18:bola=278;break;   case 19:bola=24;break;    case 20:bola=229;break;
        case 21:bola=43.5;break;  case 22:bola=268;break;   case 23:bola=160.5;break;
        case 24:bola=190;break;   case 25:bola=63;break;    case 26:bola=345.5;break;
        case 27:bola=102;break;   case 28:bola=307;break;   case 29:bola=287.5;break;
        case 30:bola=141.5;break; case 31:bola=248.5;break; case 32:bola=4.5;break;
        case 33:bola=210;break;   case 34:bola=83;break;    case 35:bola=325.5;break;
        case 36:bola=121.5;break;
        }    
        }
 
    public void setGrados(double grados) {
        this.grados = grados;
        this.grados2 = bola-grados;
        repaint();
    }
    
 
    @Override
    public void paint(Graphics g) {
        super.paint(g); //se borra el contenido anterior
 
        double r = Math.toRadians(grados); //se convierte a radianes lo grados
        double r2 = Math.toRadians(grados2); //se convierte a radianes lo grados
 
        AffineTransform at = AffineTransform.getTranslateInstance(27, 27);
        AffineTransform at2 = AffineTransform.getTranslateInstance(27, 27);
        //AffineTransform at3 = AffineTransform.getTranslateInstance(25, 25);

        at.rotate(r,142.5,142.5); //se asigna el angulo y centro de rotacion
        at2.rotate(r2,142.5,142.5); //se asigna el angulo y centro de rotacion
       // at3.rotate(180,170,170); //se asigna el angulo y centro de rotacion
        //at3.rotate(0,142.5,142.5); //se asigna el angulo y centro de rotacion
        try {
        	//String Imagen = getClass().getResource("src/Imagenes/rul.png").toString();
			
				BufferedImage Apple = ImageIO.read(getClass().getResource("/Imagenes/rul.png"));
				//BufferedImage Apple2 = LoadImage("bala");
				//BufferedImage Apple3 = LoadImage("aro");
				// TODO Auto-generated catch block
					BufferedImage Apple2 = ImageIO.read(getClass().getResource("/Imagenes/bala.png"));
			BufferedImage Apple3 = ImageIO.read(getClass().getResource("/Imagenes/aro.png"));
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(Apple, at, null);
			g2d.drawImage(Apple2, at2, null);
			g2d.drawImage(Apple3,-1,0, null);
			//g2d.dr

		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ((Graphics2D) g).setTransform(at);
    }
    /*
    BufferedImage LoadImage(String FileName) throws IOException, URISyntaxException{
    	BufferedImage img2 = null;
    	//System.out.println(FileName);
    	img2 = ImageIO.read(new File(getClass().getResource("/Imagenes/"+FileName+".png").toURI()));
    	return img2;
    }*/

}
