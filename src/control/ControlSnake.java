package control;

import javax.swing.JFrame;
import model.Snake.*;

public class ControlSnake {
    public void AbrirVentana() {
        JFrame ventana = new JFrame("Snake");
        ventana.setSize(616, 700);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        ventana.add(new MovimientoSnake());

        ventana.setVisible(true);
    }
}