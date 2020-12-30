/*==================================================
 Archivo: BaseDatos.java
 Fecha de creacion: 15-May-2017
 Fecha de la ultima modificacion: 22-May-2017
 Autor: Esneider Manzano
==================================================*/
/*
Clase: BaseDatos.
Responsabilidad: Se encarga de manejar la conexion con la base de datos "Jugadores", buscando
puntajes guardados, registrando nuevos usuarios y retornar esa informacion a la clase Servidor
Colaboracion: Depende de la libreria "sqlite-jdbc-3.7.2" y la base de datos "Jugadores" en 
el paquete "Base" para funcionar.
*/


package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDatos {
	private String direccion, controlador, cadenaConexion, sql1;
	private double puntaje;
	private Statement canal;
	private Connection conexion;
	
	public BaseDatos() { // Iniciar componentes y conexion
		direccion = "src/BaseDatos/Jugadores.db";  
		puntaje = 270;
		controlador = "org.sqlite.JDBC";  // Controladora
		cadenaConexion = "jdbc:sqlite:";  // Necesario para uso de SQLite
        try {
			Class.forName(controlador);  // Iniciar control
			conexion = DriverManager.getConnection(cadenaConexion + direccion); // Establecer conexion la base
	        canal =conexion.createStatement(); // Se abre conexion
	        sql1 ="SELECT * FROM jugadores"; 
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error en la conexion a la base: "+e.getMessage());
		}                    
	}
	
	// Busca en la tabla jugadores de la base la clave que dio el cliente y la retorna
	public double buscarEnBase(String usuario, String clave){
		try {           
            ResultSet buscar = canal.executeQuery(sql1); // Se ejecuta el comando
            boolean usuencontrado = false, claveEncontrada = false;
            while (buscar.next()) { // Hasta el ultimo dato
            	String dato = buscar.getString("usuario");
                if(dato.equals(usuario)) { // El usuario esta?
                	usuencontrado = true;
                	if(clave.equals(buscar.getString("clave"))){ //Es la clave?
                		claveEncontrada =true;
                		puntaje = Double.parseDouble(buscar.getString("puntaje")); // se carga el puntaje
                	}
                }               
            }            
            if(usuencontrado) {
            	if(!claveEncontrada){puntaje = -1;}//La clave es erronea           	
            }else {	 // Si no esta se crea y guarda puntaje por defecto
            	System.out.println("Creando usuario nuevo");
            	guardarDatos(usuario,clave,270);
            } 
            buscar.close(); // Se cierra canal
        } catch (SQLException e) {
            System.out.println("Error en la conexion a la base: "+e.getMessage());
        } 
		return puntaje;
	}
	
	// Si es nuevo se cre el usuario
	public void guardarDatos(String usuario, String clave, double puntos){		
    	String sql = "INSERT INTO jugadores VALUES(?,?,?)"; // comando
        PreparedStatement guardar; // canal
		try {
			guardar = conexion.prepareStatement(sql); // Se ejecuta el comando
			guardar.setString(1, usuario);
			guardar.setString(2, clave); // Guarda clave
	        guardar.setString(3, String.valueOf(puntos)); // Guarda puntaje
	        guardar.executeUpdate();
	        guardar.close();
	        puntaje = 2000; // Puntaje por defecto
		} catch (SQLException e) {
			System.out.println("Error al guardar datos: "+e.getMessage());
		}        
	}
	
	// Cuando se cierra la conexion con el cliente se guarda puntaje del usuario
	public void actualizarDatos(String usuario, double puntaje, String clave){
		String sql = "UPDATE jugadores SET clave = ?, puntaje = ? WHERE usuario = ?;";// Comando
		PreparedStatement actualizar;	// canal				
		try {
			System.out.println(puntaje);
			actualizar = conexion.prepareStatement(sql);	
			actualizar.setString(1, clave);
			actualizar.setString(2, String.valueOf(puntaje));						
			actualizar.setString(3, usuario); // Actualiza puntaje
			actualizar.executeUpdate();
			actualizar.close();
		} catch (SQLException e) {
			System.out.println("Error al actualizar puntaje: "+e.getMessage());
		}	
	}
}
