package model.pasapalabras;

public enum EstadoPreguntas {
    PENDIENTE, // Color azul (no se ha respondido)
    ACERTADA,  // Color verde
    FALLADA,   // Color rojo
    PASADA     // Se mantiene azul/amarillo (para volver a ella luego)
}