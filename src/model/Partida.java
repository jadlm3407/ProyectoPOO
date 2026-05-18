package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Partida.
 * Representa una sesión de juego concreta: qué juego se juega,
 * quiénes participan, cuándo empezó y el estado actual (en curso, pausada, terminada).
 * La persistencia se delega en GestorFicheros.
 */
public class Partida {

    public enum Estado { EN_CURSO, PAUSADA, TERMINADA }

    private Juegos juego;
    private List<Jugador> jugadores;
    private int turnoActual;           // índice del jugador que tiene el turno
    private Estado estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaUltimaModificacion;

    // ID único para poder guardar/cargar partidas concretas
    private String idPartida;

    public Partida(Juegos juego, List<Jugador> jugadores) {
        this.juego = juego;
        this.jugadores = new ArrayList<>(jugadores);
        this.turnoActual = 0;
        this.estado = Estado.EN_CURSO;
        this.fechaInicio = LocalDateTime.now();
        this.fechaUltimaModificacion = fechaInicio;
        this.idPartida = generarId();
        juego.inicializar();
    }

    private String generarId() {
        // ID simple basado en timestamp
        return juego.getNombre().replaceAll("\\s+", "_") + "_" + System.currentTimeMillis();
    }

    /**
     * Juega el turno del jugador actual con la entrada dada.
     * Avanza el turno si procede.
     *
     * @param entrada Respuesta o movimiento del jugador.
     * @return Resultado textual del turno.
     */
    public String jugarTurno(String entrada) {
        if (estado != Estado.EN_CURSO) {
            return "La partida no está en curso.";
        }

        Jugador jugadorActual = jugadores.get(turnoActual);
        String resultado = juego.procesarTurno(jugadorActual, entrada);
        fechaUltimaModificacion = LocalDateTime.now();

        if (juego.isPartidaTerminada()) {
            estado = Estado.TERMINADA;
        } else {
            // Pasar al siguiente jugador (turnos circulares)
            turnoActual = (turnoActual + 1) % jugadores.size();
        }

        return resultado;
    }

    /**
     * Pausa la partida para poder reanudarla más tarde.
     */
    public void pausar() {
        if (estado == Estado.EN_CURSO) {
            estado = Estado.PAUSADA;
            fechaUltimaModificacion = LocalDateTime.now();
        }
    }

    /**
     * Reanuda una partida pausada.
     */
    public void reanudar() {
        if (estado == Estado.PAUSADA) {
            estado = Estado.EN_CURSO;
            fechaUltimaModificacion = LocalDateTime.now();
        }
    }

    /**
     * Genera las estadísticas de la partida para todos los jugadores.
     * Debe llamarse cuando la partida está TERMINADA.
     *
     * @return Lista de Estadistica, una por jugador.
     */
    public List<Estadistica> generarEstadisticas() {
        List<Estadistica> estadisticas = new ArrayList<>();
        Jugador ganador = juego.getGanador();

        for (Jugador j : jugadores) {
            boolean gano = (ganador != null && ganador.equals(j));
            int puntuacion = juego.getPuntuacion(j);
            estadisticas.add(new Estadistica(j.getUsername(), juego.getNombre(), puntuacion, gano));
        }

        return estadisticas;
    }

    // --- Getters ---

    public Juegos getJuego() { return juego; }

    public List<Jugador> getJugadores() { return jugadores; }

    public Jugador getJugadorActual() { return jugadores.get(turnoActual); }

    public Estado getEstado() { return estado; }

    public String getIdPartida() { return idPartida; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }

    public LocalDateTime getFechaUltimaModificacion() { return fechaUltimaModificacion; }

    @Override
    public String toString() {
        return String.format("Partida{id='%s', juego='%s', estado=%s, jugadores=%d}",
                idPartida, juego.getNombre(), estado, jugadores.size());
    }
}