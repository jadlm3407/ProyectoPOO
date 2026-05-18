package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase Administrador, extiende Usuario.
 */
public class Administrador extends Usuario {

    // Usuario especial predefinido
    private  static String USERNAME_ADMIN = "admin";
    private static String PASSWORD_ADMIN = "admin1234";

public Administrador() {
    super(USERNAME_ADMIN, PASSWORD_ADMIN);
}    /**
     * Devuelve el ranking de jugadores para un juego concreto,
     * ordenado de mayor a menor puntuación.
     *
     * @param jugadores   Lista de todos los jugadores del sistema.
     * @param nombreJuego Nombre del juego para filtrar.
     * @return Lista de jugadores ordenados por puntuación máxima descendente.
     */
    public List<Jugador> getRankingPorJuego(List<Jugador> jugadores, String nombreJuego) {
        List<Jugador> ranking = new ArrayList<>(jugadores);
        ranking.sort(Comparator.comparingInt(
                (Jugador j) -> j.getPuntuacionMaxima(nombreJuego)
        ).reversed());
        return ranking;
    }

    /**
     * Devuelve las últimas N partidas de un jugador concreto.
     *
     * @param jugador     El jugador del que consultar las partidas.
     * @param maxPartidas Número máximo de partidas a devolver.
     * @return Lista con las últimas estadísticas del jugador.
     */
    public List<Estadistica> getUltimasPartidasDeJugador(Jugador jugador, int maxPartidas) {
        List<Estadistica> todas = jugador.getEstadisticas();
        int inicio = Math.max(0, todas.size() - maxPartidas);
        return new ArrayList<>(todas.subList(inicio, todas.size()));
    }

    @Override
    public String toString() {
        return "Administrador{username='" + getUsername() + "'}";
    }
}