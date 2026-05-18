package control;
import model.pasapalabra.*;

public class main {
    public static void main(String[] args) {
        Jugador jugador = new Jugador("jugador1", "1234");
        Pasapalabra juego = new Pasapalabra(jugador);
        juego.inicializar();
        juego.empezarJuego();
    }
}
