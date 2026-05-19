package view;

import model.pasapalabras.*;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class VentanaPasapalabra extends JFrame {

    // ── Colores ──────────────────────────────────────────────────────────────
    private static final Color FONDO        = new Color(15, 15, 50);
    private static final Color PENDIENTE    = new Color(30,  90, 200);
    private static final Color ACERTADA     = new Color(20, 170,  50);
    private static final Color FALLADA      = new Color(200,  30,  30);
    private static final Color PASADA       = new Color(200, 160,   0);
    private static final Color SIN_PREGUNTA = new Color(55,  55,  90);

    // ── Modelo ───────────────────────────────────────────────────────────────
    private Pasapalabra juego;
    private Jugador     jugador;

    // ── Control de ronda ─────────────────────────────────────────────────────
    private int preguntasEnRonda;   // cuántas había al empezar esta ronda
    private int respondidas;        // cuántas llevamos en esta ronda
    private int numeroRonda;        // contador de rondas

    // ── Componentes ──────────────────────────────────────────────────────────
    private PanelRosco panelRosco;
    private JLabel     lblLetra;
    private JLabel     lblPregunta;
    private JTextField txtRespuesta;
    private JButton    btnResponder;
    private JButton    btnPasapalabra;
    private JLabel     lblAciertos;
    private JLabel     lblFallos;
    private JLabel     lblMensaje;
    private JLabel     lblRonda;

    // ────────────────────────────────────────────────────────────────────────
    private VentanaPasapalabra(Jugador jugador, String rutaFichero) {
        this.jugador      = jugador;
        this.juego        = new Pasapalabra(jugador, rutaFichero);
        this.numeroRonda  = 1;

        setTitle("Pasapalabra");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(FONDO);
        setLayout(new BorderLayout(0, 0));

        construirUI();
        iniciarRonda();  // configura contadores y muestra la primera pregunta

        setSize(720, 820);
        setMinimumSize(new Dimension(600, 700));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Iniciar / reiniciar una ronda ────────────────────────────────────────
    private void iniciarRonda() {
        preguntasEnRonda = juego.getRosco().contarActivas();
        respondidas      = 0;
        lblRonda.setText("Ronda " + numeroRonda
                + "  •  Quedan " + preguntasEnRonda + " preguntas");
        mostrarSiguientePregunta();
    }

    // ── Construcción de la UI ────────────────────────────────────────────────
    private void construirUI() {

        // ── Norte ────────────────────────────────────────────────────────
        JPanel norte = new JPanel(new BorderLayout());
        norte.setBackground(FONDO);
        norte.setBorder(new EmptyBorder(14, 20, 4, 20));

        JLabel lblTitulo = new JLabel("PASAPALABRA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.YELLOW);

        lblRonda = new JLabel("Ronda 1", SwingConstants.CENTER);
        lblRonda.setFont(new Font("Arial", Font.PLAIN, 13));
        lblRonda.setForeground(new Color(160, 160, 210));

        JPanel marcador = new JPanel(new GridLayout(1, 2, 20, 0));
        marcador.setBackground(FONDO);
        lblAciertos = etiqueta("✔  Correctas: 0",   ACERTADA, 15);
        lblFallos   = etiqueta("✘  Incorrectas: 0", FALLADA,  15);
        marcador.add(lblAciertos);
        marcador.add(lblFallos);

        JPanel infoNorte = new JPanel(new GridLayout(2, 1));
        infoNorte.setBackground(FONDO);
        infoNorte.add(lblRonda);
        infoNorte.add(marcador);

        norte.add(lblTitulo,  BorderLayout.NORTH);
        norte.add(infoNorte,  BorderLayout.SOUTH);

        // ── Centro ───────────────────────────────────────────────────────
        panelRosco = new PanelRosco();

        // ── Sur ──────────────────────────────────────────────────────────
        JPanel sur = new JPanel();
        sur.setLayout(new BoxLayout(sur, BoxLayout.Y_AXIS));
        sur.setBackground(new Color(25, 25, 70));
        sur.setBorder(new EmptyBorder(14, 30, 20, 30));

        lblLetra = etiqueta("Letra: -", Color.YELLOW, 20);
        lblLetra.setAlignmentX(CENTER_ALIGNMENT);

        lblPregunta = new JLabel("<html><center>Cargando...</center></html>",
                                 SwingConstants.CENTER);
        lblPregunta.setFont(new Font("Arial", Font.PLAIN, 15));
        lblPregunta.setForeground(Color.WHITE);
        lblPregunta.setAlignmentX(CENTER_ALIGNMENT);
        lblPregunta.setMaximumSize(new Dimension(640, 60));

        txtRespuesta = new JTextField();
        txtRespuesta.setFont(new Font("Arial", Font.PLAIN, 17));
        txtRespuesta.setHorizontalAlignment(JTextField.CENTER);
        txtRespuesta.setMaximumSize(new Dimension(640, 36));

        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 14, 0));
        panelBotones.setBackground(new Color(25, 25, 70));
        panelBotones.setMaximumSize(new Dimension(640, 40));

        btnResponder   = boton("Responder",   new Color(20, 140, 20));
        btnPasapalabra = boton("Pasapalabra", new Color(20,  80, 190));
        panelBotones.add(btnResponder);
        panelBotones.add(btnPasapalabra);

        lblMensaje = etiqueta("", Color.YELLOW, 14);
        lblMensaje.setAlignmentX(CENTER_ALIGNMENT);

        sur.add(lblLetra);
        sur.add(Box.createVerticalStrut(6));
        sur.add(lblPregunta);
        sur.add(Box.createVerticalStrut(10));
        sur.add(txtRespuesta);
        sur.add(Box.createVerticalStrut(8));
        sur.add(panelBotones);
        sur.add(Box.createVerticalStrut(6));
        sur.add(lblMensaje);

        add(norte,      BorderLayout.NORTH);
        add(panelRosco, BorderLayout.CENTER);
        add(sur,        BorderLayout.SOUTH);

        btnResponder.addActionListener(e -> responder());
        btnPasapalabra.addActionListener(e -> pasapalabra());
        txtRespuesta.addActionListener(e -> responder());
    }

    // ── Lógica de respuesta ──────────────────────────────────────────────────
    private void responder() {
        String resp = txtRespuesta.getText().trim();
        if (resp.isEmpty()) return;

        String resultado = juego.procesarTurno(jugador, resp);
        mostrarMensaje(resultado);
        actualizarMarcador();
        panelRosco.repaint();
        txtRespuesta.setText("");

        respondidas++;
        comprobarFinRonda();
    }

    private void pasapalabra() {
        juego.procesarTurno(jugador, "pasapalabra");
        lblMensaje.setText("Pasapalabra…");
        lblMensaje.setForeground(PASADA);
        panelRosco.repaint();
        txtRespuesta.setText("");

        respondidas++;
        comprobarFinRonda();
    }

    // ── Comprueba si la ronda ha terminado ───────────────────────────────────
    private void comprobarFinRonda() {
        if (respondidas < preguntasEnRonda) {
            // Ronda no terminada: siguiente pregunta
            mostrarSiguientePregunta();
            return;
        }

        // Ronda terminada
        if (juego.isPartidaTerminada()) {
            mostrarFin();
        } else {
            mostrarDialogoEntreRondas();
        }
    }

    // ── Diálogo al terminar una vuelta con preguntas pendientes ─────────────
    private void mostrarDialogoEntreRondas() {
        int pasadas = juego.getRosco().contarActivas();

        // Panel del diálogo con tema oscuro
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 70));
        panel.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel lblTitulo = new JLabel("Vuelta " + numeroRonda + " completada",
                                      SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblInfo = new JLabel(
            "<html><center>Te quedan <b>" + pasadas
            + " pregunta" + (pasadas == 1 ? "" : "s") + "</b> por responder."
            + "<br>¿Quieres dar otra vuelta al rosco?</center></html>",
            SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblInfo);

        // Opciones del diálogo
        String[] opciones = { "▶  Seguir jugando", "⬅  Volver al menú" };

        UIManager.put("OptionPane.background",          new Color(25, 25, 70));
        UIManager.put("Panel.background",               new Color(25, 25, 70));
        UIManager.put("OptionPane.messageForeground",   Color.WHITE);
        UIManager.put("Button.background",              new Color(40, 40, 100));
        UIManager.put("Button.foreground",              Color.WHITE);

        int eleccion = JOptionPane.showOptionDialog(
            this,
            panel,
            "Vuelta completada",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (eleccion == 0) {
            // Continuar: iniciar nueva ronda
            numeroRonda++;
            iniciarRonda();
        } else {
            // Volver al menú
            dispose();
            SwingUtilities.invokeLater(() ->
                new VentanaMenu(jugador));
        }
    }

    // ── Pantalla de fin de partida ───────────────────────────────────────────
    private void mostrarFin() {
        btnResponder.setEnabled(false);
        btnPasapalabra.setEnabled(false);
        txtRespuesta.setEnabled(false);

        lblLetra.setText("¡Rosco completado!");
        lblPregunta.setText("<html><center>Puntuación final: <b>"
                + juego.getPuntuacion(jugador) + " aciertos</b>"
                + " en " + numeroRonda + " vuelta"
                + (numeroRonda == 1 ? "" : "s") + "</center></html>");
        lblMensaje.setText("Enhorabuena, " + jugador.getUsername() + "!");
        lblMensaje.setForeground(Color.YELLOW);
        panelRosco.setLetraActual(' ');
        panelRosco.repaint();
        lblRonda.setText("Partida terminada");
    }

    // ── Helpers de juego ─────────────────────────────────────────────────────
    private void mostrarSiguientePregunta() {
        Preguntas p = juego.getRosco().getSiguientePregunta();
        if (p == null) { mostrarFin(); return; }

        panelRosco.setLetraActual(p.getLetra());
        lblLetra.setText("Con la letra  " + p.getLetra());
        lblPregunta.setText("<html><center>" + p.getEnunciado() + "</center></html>");
        lblRonda.setText("Ronda " + numeroRonda
                + "  •  Pregunta " + (respondidas + 1) + " de " + preguntasEnRonda);
        txtRespuesta.requestFocusInWindow();
    }

    private void actualizarMarcador() {
        lblAciertos.setText("✔  Correctas: "   + juego.getPuntuacion(jugador));
        lblFallos.setText  ("✘  Incorrectas: " + juego.getFallos());
    }

    private void mostrarMensaje(String msg) {
        lblMensaje.setText(msg);
        if      (msg.contains("correcta"))   lblMensaje.setForeground(ACERTADA);
        else if (msg.contains("incorrecta")) lblMensaje.setForeground(FALLADA);
        else                                  lblMensaje.setForeground(PASADA);
    }

    // ── Helpers de UI ────────────────────────────────────────────────────────
    private JLabel etiqueta(String texto, Color color, int tam) {
        JLabel l = new JLabel(texto, SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.BOLD, tam));
        l.setForeground(color);
        return l;
    }

    private JButton boton(String texto, Color bg) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ════════════════════════════════════════════════════════════════════════
    // Panel del rosco circular
    // ════════════════════════════════════════════════════════════════════════
    class PanelRosco extends JPanel {

        private char letraActual = ' ';

        PanelRosco() {
            setBackground(FONDO);
            setPreferredSize(new Dimension(500, 460));
        }

        void setLetraActual(char c) { this.letraActual = c; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth(), h = getHeight();
            int cx = w / 2, cy = h / 2;
            int radioRosco = (int)(Math.min(w, h) * 0.42);
            int radioLetra = (int)(radioRosco * 0.18);

            Preguntas[] preguntas = juego.getRosco().getPreguntas();
            String abecedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int n = abecedario.length();

            for (int i = 0; i < n; i++) {
                char letra = abecedario.charAt(i);
                double angulo = (2.0 * Math.PI * i / n) - Math.PI / 2.0;
                int x = (int)(cx + radioRosco * Math.cos(angulo));
                int y = (int)(cy + radioRosco * Math.sin(angulo));

                Color fondo = SIN_PREGUNTA;
                if (preguntas[i] != null) {
                    switch (preguntas[i].getEstado()) {
                        case PENDIENTE: fondo = PENDIENTE; break;
                        case ACERTADA:  fondo = ACERTADA;  break;
                        case FALLADA:   fondo = FALLADA;   break;
                        case PASADA:    fondo = PASADA;    break;
                    }
                }

                boolean esActual = (letra == letraActual);
                if (esActual) {
                    g2.setColor(new Color(255, 230, 0, 70));
                    g2.fillOval(x - radioLetra - 7, y - radioLetra - 7,
                                (radioLetra + 7) * 2, (radioLetra + 7) * 2);
                }

                g2.setColor(fondo);
                g2.fillOval(x - radioLetra, y - radioLetra,
                            radioLetra * 2, radioLetra * 2);
                g2.setStroke(new BasicStroke(esActual ? 3f : 1.5f));
                g2.setColor(esActual ? Color.YELLOW : new Color(160, 160, 210));
                g2.drawOval(x - radioLetra, y - radioLetra,
                            radioLetra * 2, radioLetra * 2);

                int fontSize = Math.max(10, (int)(radioLetra * 0.85));
                g2.setFont(new Font("Arial", Font.BOLD, fontSize));
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                String s = String.valueOf(letra);
                g2.drawString(s, x - fm.stringWidth(s) / 2,
                                 y + fm.getAscent() / 2 - 1);
            }
            dibujarLeyenda(g2, cx, cy);
        }

        private void dibujarLeyenda(Graphics2D g2, int cx, int cy) {
            int r = 10;
            String[] textos  = {"Correcta","Incorrecta","Pasada","Pendiente"};
            Color[]  colores = {ACERTADA, FALLADA, PASADA, PENDIENTE};
            int totalAncho   = textos.length * 110;
            int x0 = cx - totalAncho / 2;
            int y0 = cy - 16;
            for (int i = 0; i < textos.length; i++) {
                int x = x0 + i * 110;
                g2.setColor(colores[i]);
                g2.fillOval(x, y0, r * 2, r * 2);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.PLAIN, 11));
                g2.drawString(textos[i], x + r * 2 + 4, y0 + r + 3);
            }
        }
    }

    // ── Métodos estáticos de apertura ─────────────────────────────────────────
    public static void abrir(Jugador jugador, String rutaFichero) {
        SwingUtilities.invokeLater(() -> new VentanaPasapalabra(jugador, rutaFichero));
    }

    public static void abrir(Jugador jugador) {
        SwingUtilities.invokeLater(() -> new VentanaPasapalabra(jugador,
                ".\\data\\RoscoDifícil.txt"));
    }
}
