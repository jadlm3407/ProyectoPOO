package model.pasapalabras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Rosco {
    private List<Preguntas> preguntas; // Las 26 preguntas finales del juego
    private int indiceActual;

    public Rosco() {
        this.preguntas = new ArrayList<>();
        this.indiceActual = 0;
        cargarYSeleccionarAleatorias("src/resources/preguntas.txt");
    }

    private void cargarYSeleccionarAleatorias(String ruta) {
        // Usamos un Map para agrupar todas las opciones por cada letra
        Map<Character, List<Preguntas>> todasLasOpciones = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    char letra = partes[0].trim().toUpperCase().charAt(0);
                    Preguntas p = new Preguntas(letra, partes[1].trim(), partes[2].trim());
                    
                    // Si la letra no existe en el mapa, creamos la lista
                    todasLasOpciones.putIfAbsent(letra, new ArrayList<>());
                    todasLasOpciones.get(letra).add(p);
                }
            }
            
            // Ahora, de cada letra en el mapa, elegimos una al azar
            Random rand = new Random();
            // Ordenamos por abecedario para que el rosco tenga sentido (A-Z)
            for (char c = 'A'; c <= 'Z'; c++) {
                if (c == 'Ñ') continue; // Opcional: manejar la Ñ si está en tu txt
                
                List<Preguntas> opcionesParaEstaLetra = todasLasOpciones.get(c);
                if (opcionesParaEstaLetra != null && !opcionesParaEstaLetra.isEmpty()) {
                    int indiceAleatorio = rand.nextInt(opcionesParaEstaLetra.size());
                    this.preguntas.add(opcionesParaEstaLetra.get(indiceAleatorio));
                }
            }

        } catch (IOException e) {
            System.err.println("Error al cargar el archivo de preguntas: " + e.getMessage());
        }
    }

    public Preguntas getSiguientePregunta() {
        int total = preguntas.size();
        for (int i = 0; i < total; i++) {
            int pos = (indiceActual + i) % total;
            Preguntas p = preguntas.get(pos);
            if (p.getEstado() == EstadoPreguntas.PENDIENTE || p.getEstado() == EstadoPreguntas.PASADA) {
                indiceActual = pos;
                return p;
            }
        }
        return null;
    }

    public void avanzarIndice() {
        indiceActual = (indiceActual + 1) % preguntas.size();
    }

    public List<Preguntas> getPreguntas() {
        return preguntas;
    }
}