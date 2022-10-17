import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int opcion = -1;
		do {
			System.out.println("--- Selección de agencias --- \n 1. Visita Mundo \n 2. GuiaExperience \n 3. VIPGuies \n 4. Salir");
			System.out.println("Opción: ");
			try {
				opcion = Integer.parseInt(br.readLine());
				switch(opcion) {
					case 1:
						System.out.println("Entrando a Visita Mundo...");
						break;
					case 2:
						System.out.println("Entrando a GuiaExperience...");
						break;
					case 3:
						System.out.println("Entrando a VIPGuide...");
						break;
					case 4:
						System.out.println("Saliendo del sistema...");
						break;
					default:
						System.out.println("Valor incporrecto, inténtelo de nuevo");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("ERROR en el formato del entrada");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR");
			}
		} while(opcion != 4);
	}
}
