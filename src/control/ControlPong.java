package control;

import java.util.Scanner;
import javax.swing.JFrame;
import model.Pong.*;

public class ControlPong {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner teclado = new Scanner(System.in);
		int n;
		do {
			System.out.println("¿Qué modo? \n 1. 1 vs 1 \n 2. 1 vs CPU");
			n=teclado.nextInt();
		} while(n<1 && n>2);
		
		JFrame ventana = new JFrame("Pongs");
		ventana.setSize(550,380);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setLocationRelativeTo(null);
		if (n==1) {
		ventana.add(new MovimientoPong());
		} else ventana.add(new MovimientoPongCPU());
		
		ventana.setVisible(true);
		
		teclado.close();
	}
}