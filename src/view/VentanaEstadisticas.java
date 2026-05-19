package view;

import model.Jugador;
import model.Estadistica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VentanaEstadisticas extends JFrame {

    private Jugador jugador;

    public VentanaEstadisticas(Jugador jugador) {
        this.jugador = jugador;

        setTitle("Estadísticas de " + jugador.getUsername());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        Font fuenteTitulo  = new Font("Arial", Font.BOLD, 18);
        Font fuenteNormal  = new Font("Arial", Font.PLAIN, 14);
        Font fuenteNegrita = new Font("Arial", Font.BOLD, 14);

        // Panel principal con margen
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(panelPrincipal);

        // ── Título arriba ──────────────────────────────────────────────────────

        JLabel lblTitulo = new JLabel("Estadísticas de " + jugador.getUsername());
        lblTitulo.setFont(fuenteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── Panel central (resumen + historial) ────────────────────────────────

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // -- Resumen general --

        List<Estadistica> todas = jugador.getEstadisticas();

        int totalPartidas  = todas.size();
        int totalVictorias = 0;
        int totalPuntos    = 0;

        for (int i = 0; i < todas.size(); i++) {
            totalPuntos += todas.get(i).getPuntuacion();
            if (todas.get(i).isGanada()) {
                totalVictorias++;
            }
        }

        JPanel panelResumen = new JPanel(new GridLayout(3, 2, 10, 5));
        panelResumen.setBorder(BorderFactory.createTitledBorder("Resumen general"));

        panelResumen.add(new JLabel("Total de partidas:", SwingConstants.RIGHT));
        JLabel lblPartidas = new JLabel(String.valueOf(totalPartidas));
        lblPartidas.setFont(fuenteNegrita);
        panelResumen.add(lblPartidas);

        panelResumen.add(new JLabel("Victorias:", SwingConstants.RIGHT));
        JLabel lblVictorias = new JLabel(totalVictorias + " de " + totalPartidas);
        lblVictorias.setFont(fuenteNegrita);
        panelResumen.add(lblVictorias);

        panelResumen.add(new JLabel("Puntos totales:", SwingConstants.RIGHT));
        JLabel lblPuntos = new JLabel(String.valueOf(totalPuntos));
        lblPuntos.setFont(fuenteNegrita);
        panelResumen.add(lblPuntos);

        panelCentro.add(panelResumen);
        panelCentro.add(Box.createVerticalStrut(10)); // Espacio entre secciones

        // -- Historial de partidas --

        String[] columnas = { "Juego", "Puntuación", "Resultado" };

        // Rellenamos los datos de la tabla
        String[][] datos = new String[todas.size()][3];
        for (int i = 0; i < todas.size(); i++) {
            Estadistica e = todas.get(i);
            datos[i][0] = e.getNombreJuego();
            datos[i][1] = String.valueOf(e.getPuntuacion());
            datos[i][2] = e.isGanada() ? "Victoria" : "Derrota";
        }

        JTable tabla = new JTable(datos, columnas) {
            // Hacemos que las celdas no se puedan editar
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla.setFont(fuenteNormal);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setFont(fuenteNegrita);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de partidas"));
        panelCentro.add(scroll);

        // ── Botón cerrar abajo ─────────────────────────────────────────────────

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(fuenteNormal);
        btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra esta ventana
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
    }
}