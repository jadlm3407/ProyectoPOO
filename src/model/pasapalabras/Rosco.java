package model.pasapalabras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Rosco {

    private static final int NUM_LETRAS = 26;
    private static final int MAX_POR_LETRA = 5; // máximo de preguntas por letra en el fichero

    private Preguntas[] preguntas;   // las 26 preguntas finales del rosco
    private int indiceActual;

    public Rosco() {
        this.preguntas = new Preguntas[NUM_LETRAS];
        this.indiceActual = 0;
        cargarYSeleccionarAleatorias(".\\data\\RoscoDifícil.txt");
    }

    private void cargarYSeleccionarAleatorias(String ruta) {

        // Matriz 26 x MAX_POR_LETRA para guardar las opciones de cada letra
        Preguntas[][] opciones = new Preguntas[NUM_LETRAS][MAX_POR_LETRA];
        int[] contadores = new int[NUM_LETRAS]; // cuántas preguntas hay por cada letra

        // --- Lectura del fichero ---
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    char letra = partes[0].trim().toUpperCase().charAt(0);
                    int idx = letra - 'A'; // 'A'=0, 'B'=1, ... 'Z'=25

                    // Solo letras A-Z y que no hayamos llenado el hueco
                    if (idx >= 0 && idx < NUM_LETRAS && contadores[idx] < MAX_POR_LETRA) {
                        opciones[idx][contadores[idx]] = new Preguntas(
                            letra,
                            partes[1].trim(),
                            partes[2].trim()
                        );
                        contadores[idx]++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar preguntas: " + e.getMessage());
        }

        // --- Selección aleatoria: una pregunta por letra ---
        Random rand = new Random();
        for (int i = 0; i < NUM_LETRAS; i++) {
            if (contadores[i] > 0) {
                int elegida = rand.nextInt(contadores[i]);
                preguntas[i] = opciones[i][elegida];
            }
            // Si no hay pregunta para esa letra, preguntas[i] queda null
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
        return null; // rosco terminado
    }

    public void avanzarIndice() {
        indiceActual = (indiceActual + 1) % preguntas.length;
    }

    public Preguntas[] getPreguntas() {
        return preguntas;
    }
}
