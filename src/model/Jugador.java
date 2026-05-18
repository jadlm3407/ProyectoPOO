package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Jugador, extiende Usuario.
 */
public class Jugador extends Usuario {

    private List<Estadistica> estadisticas;

    public Jugador(String username, String password) {
        super(username, password);
        this.estadisticas = new ArrayList<>();
    }

    /**
     * Añade una estadística (resultado de una partida) al historial del jugador.
     */
    public void agregarEstadistica(Estadistica estadistica) {
        estadisticas.add(estadistica);
    }

    /**
     * Devuelve todas las estadísticas del jugador.
     */
    public List<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    /**
     * Devuelve las estadísticas de un juego concreto.
     */
    public List<Estadistica> getEstadisticasPorJuego(String nombreJuego) {
        List<Estadistica> resultado = new ArrayList<>();
        for (Estadistica e : estadisticas) {
            if (e.getNombreJuego().equalsIgnoreCase(nombreJuego)) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    /**
     * Devuelve la puntuación máxima obtenida en un juego concreto.
     */
    public int getPuntuacionMaxima(String nombreJuego) {
        int max = 0;
        for (Estadistica e : getEstadisticasPorJuego(nombreJuego)) {
            if (e.getPuntuacion() > max) {
                max = e.getPuntuacion();
            }
        }
        return max;
    }

    @Override
    public String toString() {
        return "Jugador{username='" + getUsername() + "', partidas=" + estadisticas.size() + "}";
    }
}