package model.pasapalabras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Rosco {

    private static final int NUM_LETRAS   = 26;
    private static final int MAX_POR_LETRA = 5;

    private Preguntas[] preguntas;
    private int indiceActual;

    // Constructor por defecto: usa difícil
    public Rosco() {
        this(".\\data\\RoscoDifícil.txt");
    }

    // Constructor con ruta: usado cuando el jugador elige dificultad
    public Rosco(String ruta) {
        this.preguntas    = new Preguntas[NUM_LETRAS];
        this.indiceActual = 0;
        cargarYSeleccionarAleatorias(ruta);
    }

    private void cargarYSeleccionarAleatorias(String ruta) {
        Preguntas[][] opciones  = new Preguntas[NUM_LETRAS][MAX_POR_LETRA];
        int[]         contadores = new int[NUM_LETRAS];

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    char letra = partes[0].trim().toUpperCase().charAt(0);
                    int idx = letra - 'A';
                    if (idx >= 0 && idx < NUM_LETRAS && contadores[idx] < MAX_POR_LETRA) {
                        opciones[idx][contadores[idx]] = new Preguntas(
                            letra, partes[1].trim(), partes[2].trim());
                        contadores[idx]++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar preguntas: " + e.getMessage());
        }

        Random rand = new Random();
        for (int i = 0; i < NUM_LETRAS; i++) {
            if (contadores[i] > 0) {
                preguntas[i] = opciones[i][rand.nextInt(contadores[i])];
            }
        }
    }

    public Preguntas getSiguientePregunta() {
        int total = preguntas.length;
        for (int i = 0; i < total; i++) {
            int pos = (indiceActual + i) % total;
            Preguntas p = preguntas[pos];
            if (p != null &&
               (p.getEstado() == EstadoPreguntas.PENDIENTE ||
                p.getEstado() == EstadoPreguntas.PASADA)) {
                indiceActual = pos;
                return p;
            }
        }
        return null;
    }

    public void avanzarIndice() {
        indiceActual = (indiceActual + 1) % preguntas.length;
    }

    public Preguntas[] getPreguntas() { return preguntas; }

    // Devuelve cuántas preguntas quedan por responder (PENDIENTE o PASADA)
    public int contarActivas() {
        int count = 0;
        for (int i = 0; i < preguntas.length; i++) {
            if (preguntas[i] != null &&
               (preguntas[i].getEstado() == EstadoPreguntas.PENDIENTE ||
                preguntas[i].getEstado() == EstadoPreguntas.PASADA)) {
                count++;
            }
        }
        return count;
    }
}
