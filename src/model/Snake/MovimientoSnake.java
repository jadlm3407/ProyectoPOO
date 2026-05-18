package model.Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MovimientoSnake extends JPanel implements ActionListener, KeyListener {

    static int CELL_SIZE = 60;  // Tamaño por celda
    static final int MAX_LENGTH = 100; // Tamaño maximo de serpiente

    Mapa mapa = new Mapa();
    int ptos = 0;
    boolean crash = false;

    // snakeRow[0], snakeCol[0] = cabeza
    // snakeRow[i], snakeCol[i] = segmentos
    int[] snakeRow = new int[MAX_LENGTH];
    int[] snakeCol = new int[MAX_LENGTH];
    int snakeLength = 1;
    int tiempoZoom = 0;

    int dirRow = 0, dirCol = 0;
    int nextDirRow = 0, nextDirCol = 0;

    Timer timer;

    public MovimientoSnake() {
        this.setFocusable(true);
        this.addKeyListener(this);

        //Posicion inicial
        snakeRow[0] = 6;
        snakeCol[0] = 5;

        timer = new Timer(150, this); //Se mueve cada 150ms
        timer.start();
    }

    public void SincronizarSerpiente() {
        int n = 10; // El tamaño de tu mapa

        // Actualizo todas las posiciones del cuerpo
        for (int i = 0; i < snakeLength; i++) {
            int viejaFila = snakeRow[i];
            int viejaCol = snakeCol[i];
            
            snakeRow[i] = viejaCol;          // nueva fila
            snakeCol[i] = n - 1 - viejaFila; // nueva columna
        }
        // Giro la dirección para que no se choque sola
        int viejaDirRow = dirRow;
        dirRow = dirCol;
        dirCol = -viejaDirRow;

        int viejaNextDirRow = nextDirRow;
        nextDirRow = nextDirCol;
        nextDirCol = -viejaNextDirRow;
    }

    public void reiniciar() {
        CELL_SIZE = 60;
        ptos = 0;
        crash = false;
        snakeLength = 1;
        dirRow = 0;   dirCol = 0;
        nextDirRow = 0; nextDirCol = 0;

        mapa = new Mapa(); // Reinicio el mapa

        snakeRow[0] = 6;
        snakeCol[0] = 5;

        timer.start();
        repaint();
    }

    // Logica de movimiento
    @Override
    public void actionPerformed(ActionEvent e) {

        // Para que no se mueva hasta que piques alguna flecha
        if (nextDirRow == 0 && nextDirCol == 0) {
            repaint();
            return;
        }

        // Cambio la direccion
        dirRow = nextDirRow;
        dirCol = nextDirCol;

        // Para poner donde va la cabeza
        int newRow = snakeRow[0] + dirRow;
        int newCol = snakeCol[0] + dirCol;

        // Choque
        int cell = mapa.mapa[newRow][newCol];
        if (cell == 1 || cell == 2 || cell == 3) { //Pared o el mismo
            crash = true;

            
            repaint();
            return;
        }

        // Para comer la manzana
        boolean ateApple = (cell == 4 || cell == 5 || cell == 6);

        if (ateApple) {
            ptos++;
            snakeLength = Math.min(snakeLength + 1, MAX_LENGTH); // Crece
            mapa.nuevaManzana(); // spawnea una manzana nueva
        } else {
            // Si no comemos la manzana quitamos la ultima parte de la cola para que se vea como si se mueve
            mapa.mapa[snakeRow[snakeLength - 1]][snakeCol[snakeLength - 1]] = 0;
        }

        if (cell == 6){
            Random rand = new Random();
            CELL_SIZE = rand.nextInt(30,60);
            tiempoZoom = 33; // Definí que se actualize el mapa cada 150ms 
            // y quiero hacer que el zoom dure 5 seg --> 5000ms/150ms = 33,3 --> 33
        }

        // Movemos las partes para que cada uno tomen la siguiente
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeRow[i] = snakeRow[i - 1];
            snakeCol[i] = snakeCol[i - 1];
        }

        // Mover la cabeza
        snakeRow[0] = newRow;
        snakeCol[0] = newCol;

        // Actualizamos el mapa
        if (snakeLength > 1) {
            mapa.mapa[snakeRow[1]][snakeCol[1]] = 3;
        }
        mapa.mapa[snakeRow[0]][snakeCol[0]] = 2;

        if(cell ==5){ // Si come una manzana especial el mapa se gira
            mapa.giroMapa();
            SincronizarSerpiente();
        }

        if (tiempoZoom == 0){
            CELL_SIZE = 60;
        }

        tiempoZoom--;
        repaint(); // Vuelvo a pintar el panel
    }

    // Dibujamos el mapa segun el mapa de matrices que tenemos
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int px = col * CELL_SIZE;
                int py = row * CELL_SIZE + 60; //Dejamos un hueco extra para poner los puntos

                switch (mapa.mapa[row][col]) {
                    case 1 -> { // Pared
                        g.setColor(new Color(40, 40, 40));
                        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                    }
                    case 2 -> { // Cabeza
                        g.setColor(new Color(50, 200, 50));
                        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                        g.setColor(new Color(20, 140, 20));
                        g.fillRect(px + 5, py + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    }
                    case 3 -> { // Cuerpo
                        g.setColor(new Color(80, 170, 80));
                        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                    }
                    case 4 -> { // Manzana
                        g.setColor(new Color(220, 50, 50));
                        g.fillOval(px + 4, py + 4, CELL_SIZE - 8, CELL_SIZE - 8);
                    }
                    case 5 -> { // Manzana especial
                        g.setColor(new Color(253, 191, 0));
                        g.fillOval(px + 4, py + 4, CELL_SIZE - 8, CELL_SIZE - 8);
                    }
                    case 6 -> { // Manzana zoom
                        g.setColor(new Color(34, 139, 34));
                        g.fillOval(px + 4, py + 4, CELL_SIZE - 8, CELL_SIZE - 8);
                    }
                    default -> { // Vacio
                        g.setColor(new Color(230, 230, 210));
                        g.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                        g.setColor(new Color(200, 200, 180));
                        g.drawRect(px, py, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }

        // Pongo los puntos hasta arriba
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, 600, 60);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 30));
        g.drawString("Puntos: " + ptos, 20, 42);

        if (nextDirRow == 0 && nextDirCol == 0 && !crash) {
            g.setColor(new Color(255, 255, 255, 180));
            g.setFont(new Font("Consolas", Font.PLAIN, 20));
            g.drawString("Pulsa una tecla para empezar", 140, 42);
        }

        // Para cuando pierdes
        if (crash) {
            timer.stop();
            g.setColor(new Color(0, 0, 0, 160));
            g.fillRect(0, 60, 600, 600);
            g.setColor(Color.RED);
            g.setFont(new Font("Consolas", Font.BOLD, 60));
            g.drawString("GAME OVER", 90, 350);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Consolas", Font.PLAIN, 22));
            g.drawString("Puntos: " + ptos, 240, 410);
            g.drawString("Pulsa R para reiniciar", 175, 450);
        }
    }

    // Para leer las teclas puedes usar flechas o el wasd y esc para pausa
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if ((tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) && dirCol != 1) {
            nextDirRow = 0;  nextDirCol = -1;
        }
        if ((tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) && dirCol != -1) {
            nextDirRow = 0;  nextDirCol = 1;
        }
        if ((tecla == KeyEvent.VK_UP || tecla == KeyEvent.VK_W) && dirRow != 1) {
            nextDirRow = -1; nextDirCol = 0;
        }
        if ((tecla == KeyEvent.VK_DOWN || tecla == KeyEvent.VK_S) && dirRow != -1) {
            nextDirRow = 1;  nextDirCol = 0;
        }
        if (tecla == KeyEvent.VK_ESCAPE) {
            if (timer.isRunning()) timer.stop();
            else timer.start();
        }
        if (tecla == KeyEvent.VK_R && !timer.isRunning()) {
            reiniciar();
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}

