package control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import view.*;

public class ControlApp {

	
	public ControlApp() {
		
	}
	
	public void abrirVentana() {
		VentanaRegistro ventanaRegistro = new VentanaRegistro();
		ventanaRegistro.setVisible(true);

	}
}