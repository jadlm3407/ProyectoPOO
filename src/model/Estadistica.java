package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Estadistica.
 * Almacena el resultado de una partida para un jugador concreto.
 */
public class Estadistica {

    private String nombreJuego;
    private LocalDateTime fecha;
    private int puntuacion;
    private boolean ganada;
    private String usernameJugador;

    public Estadistica(String usernameJugador, String nombreJuego, int puntuacion, boolean ganada) {
        this.usernameJugador = usernameJugador;
        this.nombreJuego = nombreJuego;
        this.puntuacion = puntuacion;
        this.ganada = ganada;
        this.fecha = LocalDateTime.now();
    }

    // --- Getters ---

    public String getNombreJuego() {
        return nombreJuego;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public boolean isGanada() {
        return ganada;
    }

    public String getUsernameJugador() {
        return usernameJugador;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("[%s] Juego: %-15s | Puntuación: %4d | %s | %s",
                fecha.format(fmt),
                nombreJuego,
                puntuacion,
                ganada ? "GANADA" : "PERDIDA",
                usernameJugador);
    }
}