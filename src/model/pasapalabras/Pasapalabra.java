package model.pasapalabras;

import java.util.ArrayList;
import java.util.List;
import model.*;

/**
 * Implementación de Pasapalabra heredando de Juegos.
 */
public class Pasapalabra extends Juegos {

    private Rosco rosco;
    private Jugador jugadorActual;
    private int aciertos;
    private int fallos;

    public Pasapalabra(Jugador jugador) {
        // Nombre, min jugadores, max jugadores
        super("Pasapalabra", 1, 1);
        this.jugadorActual = jugador;
        this.rosco = new Rosco();
        this.aciertos = 0;
        this.fallos = 0;
    }

    @Override
    public void inicializar() {
        // Reiniciamos el rosco y los contadores
        this.rosco = new Rosco(); 
        this.aciertos = 0;
        this.fallos = 0;
    }

    @Override
    public String procesarTurno(Jugador j, String entrada) {
        // 1. Obtenemos la pregunta que toca
        Preguntas actual = rosco.getSiguientePregunta();

        if (actual == null) {
            return "Fin del juego.";
        }

        // 2. Lógica de Pasapalabra
        if (entrada.equalsIgnoreCase("pasapalabra") || entrada.equals("")) {
            actual.setEstado(EstadoPreguntas.PASADA);
            rosco.avanzarIndice();
            return "Saltamos la letra " + actual.getLetra();
        }

        // 3. Comprobar acierto o fallo
        if (actual.comprobar(entrada)) {
            actual.setEstado(EstadoPreguntas.ACERTADA);
            this.aciertos++;
            rosco.avanzarIndice();
            return "¡Correcto!";
        } else {
            actual.setEstado(EstadoPreguntas.FALLADA);
            this.fallos++;
            rosco.avanzarIndice();
            return "Incorrecto. Era: " + actual.getRespuestaCorrecta();
        }
    }

    @Override
    public boolean isPartidaTerminada() {
        // Si ya no hay preguntas pendientes ni pasadas, el método devuelve null
        return rosco.getSiguientePregunta() == null;
    }

    @Override
    public int getPuntuacion(Jugador j) {
        return this.aciertos;
    }

    @Override
    public Jugador getGanador() {
        // En modo 1 jugador, si termina es que ha ganado él (o se evalúa por puntos)
        return jugadorActual;
    }

    @Override
    public String getEstadoVisible() {
        Preguntas p = rosco.getSiguientePregunta();
        if (p != null) {
            return p.getEnunciado();
        }
        return "Juego terminado.";
    }

    // --- Getters adicionales ---

    public Rosco getRosco() {
        return rosco;
    }

    public int getAciertos() {
        return aciertos;
    }

    public int getFallos() {
        return fallos;
    }
}