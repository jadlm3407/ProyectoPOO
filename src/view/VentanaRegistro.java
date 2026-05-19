package view;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistro extends JFrame {

	public VentanaRegistro() {

		setTitle("Patata Caliente");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra la app al cerrar la ventana
		setLocationRelativeTo(null); // Centra la ventana al centro

		setLayout(new BorderLayout());

		inicializarComponentes();
	}

	private void inicializarComponentes() {
		
		Font fuente = new Font("Limelight", Font.BOLD, 14);
		
	    JPanel panelCentral = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10);

	    // Caja de texto usuario
	    JTextField CajaUsuario = new JTextField("Nombre de usuario");
	    CajaUsuario.setFont(fuente);
	    CajaUsuario.setForeground(Color.GRAY);
	    CajaUsuario.setPreferredSize(new Dimension(200, 30));
	    CajaUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(java.awt.event.FocusEvent e) {
	            if (CajaUsuario.getText().equals("Nombre de usuario")) {
	            	CajaUsuario.setText("");
	            	CajaUsuario.setForeground(Color.BLACK);
	            }
	        }
	        public void focusLost(java.awt.event.FocusEvent e) {
	            if (CajaUsuario.getText().isEmpty()) {
	            	CajaUsuario.setText("Nombre de usuario");
	            	CajaUsuario.setForeground(Color.GRAY);
	            }
	        }
	    });
	    gbc.gridx = 0; gbc.gridy = 0;
	    panelCentral.add(CajaUsuario, gbc);

	 // Caja de contraseña
	    JPasswordField CajaContraseña = new JPasswordField("Contraseña");
	    CajaContraseña.setFont(fuente);
	    CajaContraseña.setForeground(Color.GRAY);
	    CajaContraseña.setEchoChar((char) 0); // ← añade esto aquí
	    CajaContraseña.setPreferredSize(new Dimension(200, 30));
	    CajaContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
	        public void focusGained(java.awt.event.FocusEvent e) {
	            if (String.valueOf(CajaContraseña.getPassword()).equals("Contraseña")) {
	                CajaContraseña.setText("");
	                CajaContraseña.setForeground(Color.BLACK);
	                CajaContraseña.setEchoChar('•'); // ← activa puntos al escribir
	            }
	        }
	        public void focusLost(java.awt.event.FocusEvent e) {
	            if (String.valueOf(CajaContraseña.getPassword()).isEmpty()) {
	                CajaContraseña.setText("Contraseña");
	                CajaContraseña.setForeground(Color.GRAY);
	                CajaContraseña.setEchoChar((char) 0); // ← desactiva puntos al volver placeholder
	            }
	        }
	    });
	    gbc.gridx = 0; gbc.gridy = 1;
	    panelCentral.add(CajaContraseña, gbc);


	    // Botón registrarse
	    JButton btnRegistro = new JButton("Registrarme");
	    btnRegistro.setFont(fuente);
	    btnRegistro.setPreferredSize(new Dimension(200, 30));
	    gbc.gridx = 0; gbc.gridy = 3;
	    panelCentral.add(btnRegistro, gbc);

	    add(panelCentral, BorderLayout.CENTER);
	}
}

