package model;

/**
 * Clase abstracta Juego.
 * Define el contrato común que debe cumplir cualquier juego del sistema.
 * Cada juego concreto (Pasapalabra, Ahorcado, etc.) extiende esta clase.
 */
public abstract class Juegos {

    /** Nombre identificativo del juego (ej: "Pasapalabra", "Ahorcado"). */
    private String nombre;

    /** Número mínimo de jugadores requeridos. */
    private int minJugadores;

    /** Número máximo de jugadores permitidos. */
    private int maxJugadores;

    public Juegos(String nombre, int minJugadores, int maxJugadores) {
        this.nombre = nombre;
        this.minJugadores = minJugadores;
        this.maxJugadores = maxJugadores;
    }

    // --- Métodos abstractos que cada juego debe implementar ---

    /**
     * Inicializa o reinicia el estado interno del juego para una nueva partida.
     */
    public abstract void inicializar();

    /**
     * Procesa la acción/respuesta del jugador en su turno.
     *
     * @param jugador  El jugador que realiza la acción.
     * @param entrada  La entrada del jugador (respuesta, movimiento, etc.).
     * @return Mensaje de resultado de la acción.
     */
    public abstract String procesarTurno(Jugador jugador, String entrada);

    /**
     * Indica si la partida ha terminado.
     */
    public abstract boolean isPartidaTerminada();

    /**
     * Devuelve la puntuación actual de un jugador concreto.
     */
    public abstract int getPuntuacion(Jugador jugador);

    /**
     * Devuelve el jugador ganador, o null si no hay ganador (empate o partida no terminada).
     */
    public abstract Jugador getGanador();

    /**
     * Devuelve una representación del estado actual del juego para mostrar en pantalla.
     */
    public abstract String getEstadoVisible();

    // --- Getters ---

    public String getNombre() {
        return nombre;
    }

    public int getMinJugadores() {
        return minJugadores;
    }

    public int getMaxJugadores() {
        return maxJugadores;
    }

    @Override
    public String toString() {
        return "Juego{nombre='" + nombre + "'}";
    }
}