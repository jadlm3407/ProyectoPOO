package view;
 
import model.Jugador;
import model.Usuario;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
 
/**
 * Ventana de inicio de sesión y registro.
 */
public class VentanaAcceso extends JFrame {
 
    // Simulación de base de datos en memoria (sustituir por GestorFicheros)
    private Map<String, Jugador> usuarios = new HashMap<>();
 
    // Colores
    private static final Color COLOR_FONDO      = new Color(245, 245, 243);
    private static final Color COLOR_TARJETA    = Color.WHITE;
    private static final Color COLOR_BORDE      = new Color(220, 220, 215);
    private static final Color COLOR_BOTON      = new Color(30, 30, 28);
    private static final Color COLOR_BOTON_TEXT = Color.WHITE;
    private static final Color COLOR_TEXTO_SEC  = new Color(120, 120, 115);
    private static final Color COLOR_ERROR      = new Color(226, 75, 74);
    private static final Color COLOR_OK         = new Color(29, 158, 117);
 
    // Fuente
    private static final Font FUENTE_LABEL  = new Font("SansSerif", Font.BOLD, 11);
    private static final Font FUENTE_INPUT  = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font FUENTE_BTN    = new Font("SansSerif", Font.BOLD, 14);
    private static final Font FUENTE_TITULO = new Font("Monospaced", Font.BOLD, 22);
 
    // Pestañas
    private JPanel panelLogin;
    private JPanel panelRegistro;
    private JButton tabLogin;
    private JButton tabRegistro;
 
    // Campos login
    private JTextField loginUser;
    private JPasswordField loginPass;
    private JLabel msgLogin;
 
    // Campos registro
    private JTextField regUser;
    private JPasswordField regPass;
    private JPasswordField regPass2;
    private JLabel msgRegistro;
 
    public VentanaAcceso() {
        setTitle("Acceso");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(460, 560);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        setLayout(new GridBagLayout());
        add(buildContenido());
    }
 
    // -- UI --
 
    private JPanel buildContenido() {
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(30, 40, 30, 40));
 
        // Logo
        JLabel logo = new JLabel("{ juego }");
        logo.setFont(FUENTE_TITULO);
        logo.setForeground(new Color(30, 30, 28));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenido.add(logo);
        contenido.add(Box.createVerticalStrut(20));
 
        // Tarjeta
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(0, 0, 24, 0)
        ));
 
        // Barra de pestañas
        tarjeta.add(buildTabBar(), BorderLayout.NORTH);
 
        // Paneles
        JPanel stackPaneles = new JPanel(new CardLayout());
        stackPaneles.setBackground(COLOR_TARJETA);
 
        panelLogin    = buildPanelLogin();
        panelRegistro = buildPanelRegistro();
 
        stackPaneles.add(panelLogin,    "login");
        stackPaneles.add(panelRegistro, "registro");
        tarjeta.add(stackPaneles, BorderLayout.CENTER);
 
        // Lógica pestañas
        tabLogin.addActionListener(e -> {
            ((CardLayout) stackPaneles.getLayout()).show(stackPaneles, "login");
            activarTab(tabLogin, tabRegistro);
            limpiarMensajes();
        });
        tabRegistro.addActionListener(e -> {
            ((CardLayout) stackPaneles.getLayout()).show(stackPaneles, "registro");
            activarTab(tabRegistro, tabLogin);
            limpiarMensajes();
        });
 
        contenido.add(tarjeta);
        return contenido;
    }
 
    private JPanel buildTabBar() {
        JPanel bar = new JPanel(new GridLayout(1, 2));
        bar.setBackground(COLOR_TARJETA);
        bar.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDE));
 
        tabLogin    = crearTab("Iniciar sesión", true);
        tabRegistro = crearTab("Registrarse", false);
 
        bar.add(tabLogin);
        bar.add(tabRegistro);
        return bar;
    }
 
    private JButton crearTab(String texto, boolean activo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 46));
        if (activo) {
            btn.setBackground(COLOR_TARJETA);
            btn.setForeground(new Color(30, 30, 28));
            btn.setBorder(new MatteBorder(0, 0, 2, 0, new Color(30, 30, 28)));
        } else {
            btn.setBackground(COLOR_TARJETA);
            btn.setForeground(COLOR_TEXTO_SEC);
            btn.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0,0,0,0)));
        }
        return btn;
    }
 
    private void activarTab(JButton activo, JButton inactivo) {
        activo.setForeground(new Color(30, 30, 28));
        activo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(30, 30, 28)));
        inactivo.setForeground(COLOR_TEXTO_SEC);
        inactivo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0,0,0,0)));
    }
 
    private JPanel buildPanelLogin() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(COLOR_TARJETA);
        p.setBorder(new EmptyBorder(22, 28, 0, 28));
 
        msgLogin = crearMensaje();
        p.add(msgLogin);
        p.add(Box.createVerticalStrut(4));
 
        loginUser = new JTextField();
        p.add(crearCampo("USUARIO", loginUser));
        p.add(Box.createVerticalStrut(14));
 
        loginPass = new JPasswordField();
        p.add(crearCampo("CONTRASEÑA", loginPass));
        p.add(Box.createVerticalStrut(20));
 
        JButton btnEntrar = crearBoton("Entrar");
        btnEntrar.addActionListener(e -> handleLogin());
        p.add(btnEntrar);
        p.add(Box.createVerticalStrut(16));
 
        p.add(crearSeparador());
        p.add(Box.createVerticalStrut(12));
 
        JLabel hint = new JLabel("¿No tienes cuenta? Regístrate");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint.setForeground(COLOR_TEXTO_SEC);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        hint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hint.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { tabRegistro.doClick(); }
        });
        p.add(hint);
 
        return p;
    }
 
    private JPanel buildPanelRegistro() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(COLOR_TARJETA);
        p.setBorder(new EmptyBorder(22, 28, 0, 28));
 
        msgRegistro = crearMensaje();
        p.add(msgRegistro);
        p.add(Box.createVerticalStrut(4));
 
        regUser = new JTextField();
        p.add(crearCampo("USUARIO", regUser));
        p.add(Box.createVerticalStrut(14));
 
        regPass = new JPasswordField();
        p.add(crearCampo("CONTRASEÑA (mín. 6 caracteres)", regPass));
        p.add(Box.createVerticalStrut(14));
 
        regPass2 = new JPasswordField();
        p.add(crearCampo("CONFIRMAR CONTRASEÑA", regPass2));
        p.add(Box.createVerticalStrut(20));
 
        JButton btnCrear = crearBoton("Crear cuenta");
        btnCrear.addActionListener(e -> handleRegistro());
        p.add(btnCrear);
        p.add(Box.createVerticalStrut(16));
 
        p.add(crearSeparador());
        p.add(Box.createVerticalStrut(12));
 
        JLabel hint = new JLabel("¿Ya tienes cuenta? Inicia sesión");
        hint.setFont(new Font("SansSerif", Font.PLAIN, 12));
        hint.setForeground(COLOR_TEXTO_SEC);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        hint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        hint.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { tabLogin.doClick(); }
        });
        p.add(hint);
 
        return p;
    }
 
    // -- Helpers UI --
 
    private JPanel crearCampo(String etiqueta, JTextField input) {
        JPanel campo = new JPanel();
        campo.setLayout(new BoxLayout(campo, BoxLayout.Y_AXIS));
        campo.setBackground(COLOR_TARJETA);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(FUENTE_LABEL);
        lbl.setForeground(COLOR_TEXTO_SEC);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        input.setFont(FUENTE_INPUT);
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        input.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        input.setBackground(new Color(248, 248, 246));
        input.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        campo.add(lbl);
        campo.add(Box.createVerticalStrut(5));
        campo.add(input);
        return campo;
    }
 
    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_BTN);
        btn.setBackground(COLOR_BOTON);
        btn.setForeground(COLOR_BOTON_TEXT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }
 
    private JLabel crearMensaje() {
        JLabel lbl = new JLabel(" ");
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setVisible(false);
        return lbl;
    }
 
    private JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(COLOR_BORDE);
        return sep;
    }
 
    private void mostrarMensaje(JLabel lbl, boolean ok, String texto) {
        lbl.setText(texto);
        lbl.setForeground(ok ? COLOR_OK : COLOR_ERROR);
        lbl.setVisible(true);
    }
 
    private void limpiarMensajes() {
        msgLogin.setVisible(false);
        msgRegistro.setVisible(false);
    }
 
    // -- Lógica --
 
    private void handleLogin() {
        String user = loginUser.getText().trim();
        String pass = new String(loginPass.getPassword());
 
        if (user.isEmpty() || pass.isEmpty()) {
            mostrarMensaje(msgLogin, false, "Rellena todos los campos.");
            return;
        }
        if (!usuarios.containsKey(user)) {
            mostrarMensaje(msgLogin, false, "Usuario no encontrado.");
            return;
        }
        Jugador jugador = usuarios.get(user);
        if (!jugador.autenticar(user, pass)) {
            mostrarMensaje(msgLogin, false, "Contraseña incorrecta.");
            return;
        }
        mostrarMensaje(msgLogin, true, "¡Bienvenido, " + user + "!");
        // TODO: abrir ventana principal -> new VentanaPrincipal(jugador).setVisible(true);
    }
 
    private void handleRegistro() {
        String user  = regUser.getText().trim();
        String pass  = new String(regPass.getPassword());
        String pass2 = new String(regPass2.getPassword());
 
        if (user.isEmpty() || pass.isEmpty() || pass2.isEmpty()) {
            mostrarMensaje(msgRegistro, false, "Rellena todos los campos.");
            return;
        }
        if (pass.length() < 6) {
            mostrarMensaje(msgRegistro, false, "La contraseña debe tener al menos 6 caracteres.");
            return;
        }
        if (!pass.equals(pass2)) {
            mostrarMensaje(msgRegistro, false, "Las contraseñas no coinciden.");
            return;
        }
        if (usuarios.containsKey(user)) {
            mostrarMensaje(msgRegistro, false, "Ese nombre de usuario ya existe.");
            return;
        }
 
        Jugador nuevo = new Jugador(user, pass);
        usuarios.put(user, nuevo);
        mostrarMensaje(msgRegistro, true, "Cuenta creada. ¡Ya puedes iniciar sesión!");
 
        // Redirigir a login tras 1.5s
        Timer timer = new Timer(1500, e -> tabLogin.doClick());
        timer.setRepeats(false);
        timer.start();
    }
 
    // -- Main --
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new VentanaAcceso().setVisible(true);
        });
    }
}