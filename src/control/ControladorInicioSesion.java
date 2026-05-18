package control;

import java.util.Scanner;
import java.io.*;
import model.Usuario;

public class ControladorInicioSesion{
    static int MAX = 100;

    public void nuevoUsuario(){
        try{
            File userF = new File(".\\Data\\users.txt"); // Falta poner direccion del archivo
            Scanner teclado = new Scanner(System.in);
            FileWriter fw = new FileWriter(userF);
            PrintWriter outF = new PrintWriter(fw);

            String correo; // Ya veremos si lo dejamos o no
            do {
                System.out.println("Escribe tu correo: ");
                correo = teclado.nextLine().toLowerCase();
                if (!correo.contains("@") || !correo.contains(".")){
                    System.out.println("Correo Incorrecto");
                }
            } while (!correo.contains("@") || !correo.contains("."));

                System.out.println("Escribe tu usuario: ");
                String username = teclado.nextLine();

                System.out.println("Escribe tu contraseña: ");
                String contraseña = teclado.nextLine();

            Usuario user = new Usuario(username, contraseña);
            outF.println(user.getUsername() +";"+ user.getPassword());
            outF.close();
            fw.close();
            teclado.close();
        }   catch (Exception e){
            System.out.println(e);
        }
    }

    public void login(){
        try {

            Scanner teclado = new Scanner(System.in);
			File userF = new File(".\\Data\\users.txt"); // falta poner direccion de 

			Usuario[] usuarios = new Usuario[MAX];
			Scanner sc = new Scanner(userF);
			int x = 0, u = -1, intentos = 3;

			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String campos[] = linea.split(";");
				if (campos[0] == null) {
					usuarios[x] = new Usuario("0", "0");
				} else {
					usuarios[x] = new Usuario(campos[0], campos[1]);
				}
				x++;
			}
			sc.close();

            while (u == -1 && intentos > 0) {
				System.out.println("Escribe tu username: ");
				String username = teclado.nextLine().toLowerCase();

				for (int i = 0; i < x; i++) {
					if (usuarios[i].getUsername().equals(username)) {
						u = i;
					}
				}
				if (u == -1) {
					System.out.println("Username incorrecto");
				}
				intentos--;

				if (intentos == 0) {
					System.out.println("¿Quieres crear nuevo usuario? \n1:Si \n2:no");
					int nuevo = teclado.nextInt();
					teclado.nextLine();
					if (nuevo == 1)
						nuevoUsuario();
					else
						intentos = 3;
				}
			}

			if (intentos == 0) {
				nuevoUsuario();
			} else {
				int datos = 0;
				String contraseña;
				intentos = 3;
				do {
					System.out.println("Escribe tu contraseña (te quedan " + intentos + " intentos");
					contraseña = teclado.nextLine();

					if (contraseña.equals(usuarios[u].getPassword())) {
						System.out.println("Contraseña correcta");
					} else {
						System.out.println("Contraseña incorrecta");
						intentos--;
					}

				} while (intentos > 0 && !contraseña.equals(usuarios[u].getPassword()));

				if (intentos == 0) {
					System.out.println("Crea un nuevo usuario");
					nuevoUsuario();
				}
            }

        } catch(Exception e){
            System.out.println(e);
        }

    }
}