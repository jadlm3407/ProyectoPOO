package model.pasapalabras;

import java.util.Scanner;


public class Pasapalabra extends Juegos {

    static Scanner sc = new Scanner(System.in);
    private Rosco rosco;
    private Jugador jugadorActual;
    private int aciertos;
    private int fallos;

    public Pasapalabra(Jugador jugador) {
        super("Pasapalabra", 1, 1);
        this.jugadorActual = jugador;
        this.rosco = new Rosco();
        this.aciertos = 0;
        this.fallos = 0;
    }

    @Override
    public void inicializar() {
        this.rosco = new Rosco();
        this.aciertos = 0;
        this.fallos = 0;
    }

    
    public void empezarJuego() {
        System.out.println("Tenga en cuenta las tildes y pulse enter para comenzar.");
        sc.nextLine();

        int contRondas = 1;
        boolean seguirJugando;
        int contP;

        Preguntas[] preguntas = rosco.getPreguntas();

        do {
            seguirJugando = false;
            contP = 0;
            System.out.println("----- RONDA " + contRondas + " -----");

            for (int i = 0; i < preguntas.length; i++) {
                Preguntas p = preguntas[i];

                if (p != null && (p.getEstado() == EstadoPreguntas.PENDIENTE
                               || p.getEstado() == EstadoPreguntas.PASADA)) {

                    System.out.println("Con la letra " + p.getLetra());
                    System.out.println(p.getEnunciado());
                    System.out.print("Su respuesta: ");
                    String respuesta = sc.nextLine();

                    if (respuesta.equalsIgnoreCase("pasapalabra")) {
                        p.setEstado(EstadoPreguntas.PASADA);
                        contP++;
                    } else if (p.comprobar(respuesta)) {
                        p.setEstado(EstadoPreguntas.ACERTADA);
                        System.out.println("¡Respuesta correcta!");
                        this.aciertos++;
                    } else {
                        p.setEstado(EstadoPreguntas.FALLADA);
                        System.out.println("¡Respuesta incorrecta!");
                        this.fallos++;
                    }
                }

                System.out.println("------------------------");
                System.out.println("Correctas: "    + this.aciertos);
                System.out.println("Incorrectas: "  + this.fallos);
                System.out.println("Pasapalabras: " + contP);
                System.out.println("------------------------");
            }

            if (contP > 0) {
                System.out.println("¿Quiere seguir jugando con los pasapalabra? (si/enter para terminar)");
                String respuesta2 = sc.nextLine();

                if (respuesta2.equalsIgnoreCase("si")) {
                    seguirJugando = true;
                    contRondas++;
                }
            }

        } while (seguirJugando);

        System.out.println("Has terminado en la ronda " + contRondas);
        System.out.println("Correctas: "    + this.aciertos);
        System.out.println("Incorrectas: "  + this.fallos);
        System.out.println("Pasapalabras: " + contP);
    }
    @Override
    public boolean isPartidaTerminada() {
        // La partida termina cuando no quedan PENDIENTES ni PASADAS
        Preguntas[] preguntas = rosco.getPreguntas();
        for (int i = 0; i < preguntas.length; i++) {
            if (preguntas[i] != null &&
            (preguntas[i].getEstado() == EstadoPreguntas.PENDIENTE ||
                preguntas[i].getEstado() == EstadoPreguntas.PASADA)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Jugador getGanador() {
        return jugadorActual;
    }

    @Override
    public String getEstadoVisible() {
        return "Aciertos: " + aciertos + " | Fallos: " + fallos;
    }

    @Override
    public int getPuntuacion(Jugador jugador) {
        return aciertos;
    }

    @Override
    public String procesarTurno(Jugador jugador, String respuesta) {
        Preguntas p = rosco.getSiguientePregunta();
        if (p == null) return "No quedan preguntas.";

        if (respuesta.equalsIgnoreCase("pasapalabra")) {
            p.setEstado(EstadoPreguntas.PASADA);
            rosco.avanzarIndice();
            return "Pasapalabra.";
        } else if (p.comprobar(respuesta)) {
            p.setEstado(EstadoPreguntas.ACERTADA);
            this.aciertos++;
            rosco.avanzarIndice();
            return "¡Respuesta correcta!";
        } else {
            p.setEstado(EstadoPreguntas.FALLADA);
            this.fallos++;
            rosco.avanzarIndice();
            return "¡Respuesta incorrecta!";
        }
    }
}
