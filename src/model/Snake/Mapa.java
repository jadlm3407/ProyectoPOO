package model.Snake;

import java.util.Random;

public class Mapa {

    // 0 = vacio, 1 = pared, 2 = cabeza, 3 = cuerpo, 4 = manzana, 5 = manzana especial, 6 = manzana zoom
    int[][] mapa = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 2, 0, 0, 4, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    int[] coordM = {3, 5}; //Coordenadas iniciales de la manzana
    

    Random rand = new Random();

    // Para poner una manzana nueva en el mapa
    public int[] nuevaManzana() {
        mapa[coordM[0]][coordM[1]] = 0; // Quito la manzana anterior
        int tipoM = rand.nextInt(1,6); // Saco un numero aleatorio para escoger si toca manzana especial o de zoom
        // Lo intenta hasta que encuentre una casilla vacía
        do {
            coordM[0] = rand.nextInt(1, 9); // fila 1-9
            coordM[1] = rand.nextInt(1, 9); // columna 1-9
        } while (mapa[coordM[0]][coordM[1]] != 0);
        if(tipoM!=3 && tipoM!=4){
            mapa[coordM[0]][coordM[1]] = 4; // Pongo la nueva manzana
        } else if(tipoM==3){
            mapa[coordM[0]][coordM[1]] = 5; // Pongo la nueva manzana especial
        } else {
            mapa[coordM[0]][coordM[1]] = 6; // Pongo la nueva manzana zoom
        }
        return coordM;
    }

    public int[][] giroMapa(){
        int n = mapa.length; // Como tu mapa es de 10x10, n será 10
        int filaViejaM = coordM[0]; // Guardo coordenadas viejas de la manzana
        int columViejaM = coordM[1];
        int[][] nuevoMapa = new int[n][n]; // Hago otro mapa nuevo que este invertido
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // El elemento de la fila 'i' y columna 'j' pasa a:
                // Nueva fila: j
                // Nueva columna: la inversa de i (n - 1 - i)
                nuevoMapa[j][n - 1 - i] = mapa[i][j];
            }
        }
        coordM[0] = columViejaM; // Cambio coordenadas de la manzana
        coordM[1] = n - 1 - filaViejaM;
        mapa = nuevoMapa; // Cambio el mapa principal
        return mapa;
    }
}
