package model.pasapalabras;

public class Preguntas {
    private char letra;
    private String enunciado;
    private String respuestaCorrecta;
    private EstadoPreguntas estado;

    public Preguntas(char letra, String enunciado, String respuestaCorrecta) {
        this.letra = letra;
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.estado = EstadoPreguntas.PENDIENTE;
    }

    public boolean comprobar(String respuestaUser) {
        return respuestaCorrecta.equalsIgnoreCase(respuestaUser.trim());
    }

    // Getters y Setters
    public char getLetra() { return letra; }
    public String getEnunciado() { return enunciado; }
    public EstadoPreguntas getEstado() { return estado; }
    public void setEstado(EstadoPreguntas estado) { this.estado = estado; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
}
