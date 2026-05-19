package view;

import model.Jugador;
import model.Estadistica;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.time.format.DateTimeFormatter;

/**
 * VentanaEstadisticas
 * Muestra el historial de partidas y un resumen por juego de un Jugador.
 */
public class VentanaEstadisticas extends JFrame {

    private static final Color COLOR_FONDO      = new Color(20, 20, 30);
    private static final Color COLOR_PANEL       = new Color(30, 30, 45);
    private static final Color COLOR_CABECERA    = new Color(40, 40, 60);
    private static final Color COLOR_ACENTO      = new Color(80, 160, 255);
    private static final Color COLOR_VERDE       = new Color(80, 200, 120);
    private static final Color COLOR_ROJO        = new Color(220, 80, 80);
    private static final Color COLOR_TEXTO       = new Color(230, 230, 240);
    private static final Color COLOR_TEXTO_TENUE = new Color(140, 140, 160);
    private static final Font  FUENTE_TITULO     = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font  FUENTE_SUBTITULO  = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font  FUENTE_NORMAL     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FUENTE_STAT       = new Font("Segoe UI", Font.BOLD, 28);

    private final Jugador jugador;

    public VentanaEstadisticas(Jugador jugador) {
        this.jugador = jugador;

        setTitle("Estadísticas – " + jugador.getUsername());
        setSize(900, 640);
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new BorderLayout(0, 0));

        add(crearPanelTitulo(),   BorderLayout.NORTH);
        add(crearPanelCentral(),  BorderLayout.CENTER);
        add(crearPanelBoton(),    BorderLayout.SOUTH);
    }

    // ── Título ────────────────────────────────────────────────────────────────

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_CABECERA);
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel lblTitulo = new JLabel("📊  Estadísticas de " + jugador.getUsername());
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_ACENTO);

        List<Estadistica> todas = jugador.getEstadisticas();
        long ganadas = todas.stream().filter(Estadistica::isGanada).count();
        JLabel lblResumen = new JLabel(
            todas.size() + " partidas  •  " + ganadas + " victorias"
        );
        lblResumen.setFont(FUENTE_NORMAL);
        lblResumen.setForeground(COLOR_TEXTO_TENUE);

        panel.add(lblTitulo,  BorderLayout.WEST);
        panel.add(lblResumen, BorderLayout.EAST);
        return panel;
    }

    // ── Panel central con tarjetas + tabla ───────────────────────────────────

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));

        panel.add(crearPanelTarjetas(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(),    BorderLayout.CENTER);
        return panel;
    }

    // ── Tarjetas de resumen por juego ─────────────────────────────────────────

    private JPanel crearPanelTarjetas() {
        List<Estadistica> todas = jugador.getEstadisticas();

        // Juegos únicos con estadísticas
        Set<String> juegosSet = new LinkedHashSet<>();
        for (Estadistica e : todas) juegosSet.add(e.getNombreJuego());

        JPanel panel = new JPanel(new GridLayout(1, Math.max(juegosSet.size(), 1), 12, 0));
        panel.setBackground(COLOR_FONDO);

        if (juegosSet.isEmpty()) {
            JLabel lbl = new JLabel("Aún no hay partidas registradas.", SwingConstants.CENTER);
            lbl.setFont(FUENTE_NORMAL);
            lbl.setForeground(COLOR_TEXTO_TENUE);
            panel.add(lbl);
            return panel;
        }

        for (String juego : juegosSet) {
            List<Estadistica> deJuego = jugador.getEstadisticasPorJuego(juego);
            int maxPts   = jugador.getPuntuacionMaxima(juego);
            long ganadas = deJuego.stream().filter(Estadistica::isGanada).count();
            int totalPts = deJuego.stream().mapToInt(Estadistica::getPuntuacion).sum();
            double media = deJuego.isEmpty() ? 0 : (double) totalPts / deJuego.size();

            panel.add(crearTarjeta(juego, deJuego.size(), maxPts, ganadas, media));
        }
        return panel;
    }

    private JPanel crearTarjeta(String juego, int partidas, int maxPts, long ganadas, double media) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_CABECERA, 1),
            BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));

        JLabel lblJuego = new JLabel(juego.toUpperCase());
        lblJuego.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblJuego.setForeground(COLOR_ACENTO);
        lblJuego.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMax = new JLabel(String.valueOf(maxPts));
        lblMax.setFont(FUENTE_STAT);
        lblMax.setForeground(COLOR_TEXTO);
        lblMax.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMaxDesc = new JLabel("Puntuación máxima");
        lblMaxDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblMaxDesc.setForeground(COLOR_TEXTO_TENUE);
        lblMaxDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_CABECERA);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel filas = new JPanel(new GridLayout(3, 2, 4, 4));
        filas.setBackground(COLOR_PANEL);
        filas.setAlignmentX(Component.LEFT_ALIGNMENT);
        filas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        agregarFila(filas, "Partidas",  String.valueOf(partidas));
        agregarFila(filas, "Victorias", String.valueOf(ganadas));
        agregarFila(filas, "Media pts", String.format("%.1f", media));

        card.add(lblJuego);
        card.add(Box.createVerticalStrut(6));
        card.add(lblMax);
        card.add(lblMaxDesc);
        card.add(Box.createVerticalStrut(10));
        card.add(sep);
        card.add(Box.createVerticalStrut(8));
        card.add(filas);
        return card;
    }

    private void agregarFila(JPanel panel, String clave, String valor) {
        JLabel lClave = new JLabel(clave);
        lClave.setFont(FUENTE_NORMAL);
        lClave.setForeground(COLOR_TEXTO_TENUE);

        JLabel lValor = new JLabel(valor);
        lValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lValor.setForeground(COLOR_TEXTO);

        panel.add(lClave);
        panel.add(lValor);
    }

    // ── Tabla de historial ────────────────────────────────────────────────────

    private JScrollPane crearPanelTabla() {
        String[] columnas = { "Fecha", "Juego", "Puntuación", "Resultado" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
        List<Estadistica> todas = new ArrayList<>(jugador.getEstadisticas());
        // Más recientes primero
        todas.sort(Comparator.comparing(Estadistica::getFecha).reversed());

        for (Estadistica e : todas) {
            modelo.addRow(new Object[]{
                e.getFecha().format(fmt),
                e.getNombreJuego(),
                e.getPuntuacion(),
                e.isGanada() ? "Victoria" : "Derrota"
            });
        }

        JTable tabla = new JTable(modelo);
        tabla.setBackground(COLOR_PANEL);
        tabla.setForeground(COLOR_TEXTO);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(32);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(COLOR_CABECERA);
        tabla.setSelectionForeground(COLOR_ACENTO);

        // Cabecera
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(COLOR_CABECERA);
        header.setForeground(COLOR_TEXTO_TENUE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBorder(BorderFactory.createEmptyBorder());
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.LEFT);

        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Renderer para colorear "Resultado"
        tabla.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(sel ? COLOR_CABECERA : COLOR_PANEL);
                String txt = val == null ? "" : val.toString();
                setForeground(txt.equals("Victoria") ? COLOR_VERDE : COLOR_ROJO);
                setFont(new Font("Segoe UI", Font.BOLD, 13));
                return this;
            }
        });

        // Renderer genérico para el resto (alternado)
        DefaultTableCellRenderer baseRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                setBackground(sel ? COLOR_CABECERA
                    : (row % 2 == 0 ? COLOR_PANEL : new Color(35, 35, 52)));
                setForeground(COLOR_TEXTO);
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return this;
            }
        };
        tabla.setDefaultRenderer(Object.class, baseRenderer);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(COLOR_FONDO);
        scroll.getViewport().setBackground(COLOR_PANEL);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_CABECERA, 1));
        return scroll;
    }

    // ── Botón cerrar ──────────────────────────────────────────────────────────

    private JPanel crearPanelBoton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(COLOR_CABECERA);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(FUENTE_SUBTITULO);
        btnCerrar.setBackground(COLOR_ACENTO);
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(110, 34));
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnCerrar);
        return panel;
    }

    // ── Para abrir la ventana desde cualquier sitio ───────────────────────────

    /**
     * Abre la ventana de estadísticas de un jugador.
     * Ejemplo de uso:
     *   VentanaEstadisticas.abrir(jugador);
     */
    public static void abrir(Jugador jugador) {
        SwingUtilities.invokeLater(() -> {
            VentanaEstadisticas v = new VentanaEstadisticas(jugador);
            v.setVisible(true);
        });
    }
}