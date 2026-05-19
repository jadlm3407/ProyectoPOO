package view;

import model.Jugador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Pantalla de selección de dificultad para Pasapalabra.
 * Se abre entre VistaMenu y VistaPasapalabra.
 *
 * Flujo: VistaMenu → VistaDificultad → VistaPasapalabra
 */
public class VentanaDificultadPasapalabras extends JFrame {

    // ── Colores ──────────────────────────────────────────────────────────────
    private static final Color FONDO = new Color(12, 12, 40);

    // ── Definición de dificultades ───────────────────────────────────────────
    // { etiqueta, descripción, emoji, color, ruta del fichero }
    private static final Object[][] DIFICULTADES = {
        {
            "FÁCIL",
            "Preguntas sencillas para\nempezar a jugar",
            "😊",
            new Color(30, 180, 60),
            ".\\data\\RoscoFacil.txt"
        },
        {
            "MEDIO",
            "Un reto equilibrado\npara jugadores habituales",
            "🤔",
            new Color(220, 160, 0),
            ".\\data\\RoscoMedio.txt"
        },
        {
            "DIFÍCIL",
            "Preguntas complicadas\npara los más expertos",
            "🔥",
            new Color(200, 40, 40),
            ".\\data\\RoscoDifícil.txt"
        },
        {
            "DISNEY",
            "Solo películas y personajes\ndel universo Disney",
            "✨",
            new Color(80, 60, 220),
            ".\\data\\RoscoDisney.txt"
        }
    };

    private Jugador jugador;

    // ────────────────────────────────────────────────────────────────────────
    private VentanaDificultadPasapalabras(Jugador jugador) {
        this.jugador = jugador;

        setTitle("Elige dificultad — Pasapalabra");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout());

        add(panelTitulo(),      BorderLayout.NORTH);
        add(panelOpciones(),    BorderLayout.CENTER);
        add(panelVolver(),      BorderLayout.SOUTH);

        setSize(860, 480);
        setMinimumSize(new Dimension(680, 380));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Panel de título ──────────────────────────────────────────────────────
    private JPanel panelTitulo() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(24, 40, 8, 40));

        JLabel titulo = new JLabel("PASAPALABRA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.YELLOW);

        JLabel sub = new JLabel("Selecciona la dificultad", SwingConstants.CENTER);
        sub.setFont(new Font("Arial", Font.PLAIN, 14));
        sub.setForeground(new Color(160, 160, 200));

        p.add(titulo, BorderLayout.CENTER);
        p.add(sub,    BorderLayout.SOUTH);
        return p;
    }

    // ── Panel con las tarjetas de dificultad ─────────────────────────────────
    private JPanel panelOpciones() {
        JPanel p = new JPanel(new GridLayout(1, DIFICULTADES.length, 18, 0));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(20, 30, 10, 30));

        for (Object[] d : DIFICULTADES) {
            p.add(crearTarjeta(
                (String) d[0],
                (String) d[1],
                (String) d[2],
                (Color)  d[3],
                (String) d[4]
            ));
        }
        return p;
    }

    // ── Cada tarjeta de dificultad ───────────────────────────────────────────
    private JPanel crearTarjeta(String nombre, String descripcion,
                                String emoji, Color acento, String ruta) {

        JPanel carta = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(25, 25, 70));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 18, 18));
                g2.setColor(acento);
                g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 18, 18));
            }
        };
        carta.setOpaque(false);
        carta.setBorder(new EmptyBorder(18, 16, 16, 16));
        carta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Emoji grande
        JLabel lblEmoji = new JLabel(emoji, SwingConstants.CENTER);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblEmoji.setAlignmentX(CENTER_ALIGNMENT);

        // Nombre de la dificultad
        JLabel lblNombre = new JLabel(nombre, SwingConstants.CENTER);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
        lblNombre.setForeground(acento);
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        // Descripción
        JLabel lblDesc = new JLabel(
            "<html><center>" + descripcion.replace("\n", "<br>") + "</center></html>",
            SwingConstants.CENTER);
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(160, 160, 200));
        lblDesc.setAlignmentX(CENTER_ALIGNMENT);

        // Botón Jugar
        JButton btn = new JButton("▶  JUGAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? acento.brighter() : acento);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> iniciarJuego(ruta));

        JPanel wrapBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapBtn.setOpaque(false);
        wrapBtn.add(btn);

        // Montar contenido
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        contenido.add(lblEmoji);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(lblNombre);
        contenido.add(Box.createVerticalStrut(6));
        contenido.add(lblDesc);
        contenido.add(Box.createVerticalGlue());
        contenido.add(wrapBtn);

        carta.add(contenido, BorderLayout.CENTER);

        // Hover: encoge/crece el borde
        carta.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                carta.setBorder(new EmptyBorder(16, 14, 14, 14));
                carta.repaint();
            }
            @Override public void mouseExited(MouseEvent e) {
                carta.setBorder(new EmptyBorder(18, 16, 16, 16));
                carta.repaint();
            }
            @Override public void mouseClicked(MouseEvent e) { iniciarJuego(ruta); }
        });

        return carta;
    }

    // ── Botón volver ─────────────────────────────────────────────────────────
    private JPanel panelVolver() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(FONDO);
        p.setBorder(new EmptyBorder(0, 20, 10, 0));

        JButton btnVolver = new JButton("← Volver al menú");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 12));
        btnVolver.setForeground(new Color(160, 160, 200));
        btnVolver.setBackground(new Color(30, 30, 70));
        btnVolver.setBorderPainted(false);
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.addActionListener(e -> dispose());

        p.add(btnVolver);
        return p;
    }

    // ── Lanza el juego con la ruta seleccionada ──────────────────────────────
    private void iniciarJuego(String ruta) {
        dispose(); // cierra esta ventana
        VentanaPasapalabra.abrir(jugador, ruta);
    }

    // ── Método estático llamado desde VentanaMenu ──────────────────────────────
    /**
     * Abre la pantalla de selección de dificultad.
     * Llamado desde VentanaMenu.abrirJuego("pasapalabra").
     */
    public static void abrir(Jugador jugador) {
        SwingUtilities.invokeLater(() -> new VentanaDificultadPasapalabras(jugador));
    }
}
