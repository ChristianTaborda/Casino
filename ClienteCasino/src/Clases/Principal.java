package Clases;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cliente control;
		//VentanaGUI vent = new VentanaGUI();
		//vent.elegriJuego();
		if(args.length==0){
			control = new Cliente("localhost"); // Se le asigna parametro por defecto
		}else{
			control = new Cliente(args[0]); // Se le asigna parametros de entrada
		}
	}

}
