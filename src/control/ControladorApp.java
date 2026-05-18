package control;

import javax.swing.SwingUtilities;
import view.VentanaPrincipal;

public class ControladorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}