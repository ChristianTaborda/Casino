/*****************************************************
 * -Christian Camilo Taborda Campiño    1632081-3743 *
 * -Cristian Camilo Vallecilla Cuellar  1628790-3743 *
 * -Esneider Arbey Manzano Arango       1628373-3743 *
 * -Fecha de creación:                  15/06/2017   *
 * -Fecha de última modificación:       15/06/2017   *
 *****************************************************/ 

package Clases;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class DJ{
	
	//Reproduce una pista de audio:
	public void reproducirAudio(String pista){
		
		try{	
			
			//Importación y reproducción del sonido:
			URL url = getClass().getResource("/audio/" + pista);
			AudioInputStream entrada = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(entrada);
			clip.start();
			
		}catch(IOException E){
			E.printStackTrace();
		}
		catch(LineUnavailableException E){
			E.printStackTrace();
		}
		catch(UnsupportedAudioFileException E){
			E.printStackTrace();
		}	
		
	}
	
}
