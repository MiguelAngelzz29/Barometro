
package es.miguel.barometro;

import java.time.LocalDate;

public class Medicion {

    private double temperatura;
    private double presion;
    private double presionRef;
    private double velocidad;
    private LocalDate fecha;
    private int hora;
    private double altitud;

    public Medicion() {
    }

    public Medicion(double temperatura, double presion, double presionRef, double velocidad, LocalDate fecha, int hora, double altitud) {
        this.temperatura = temperatura;
        this.presion = presion;
        this.presionRef = presionRef;
        this.velocidad = velocidad;
        this.fecha = fecha;
        this.hora = hora;
        this.altitud = altitud;
    }

   


    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getPresion() {
        return presion;
    }

    public void setPresion(double presion) {
        this.presion = presion;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public double getPresionRef() {
        return presionRef;
    }

    public void setPresionRef(double presionRef) {
        this.presionRef = presionRef;
    }
    
    

    @Override
    public String toString() {
        return "Fecha: " +fecha + " Hora: " + hora+":00" + " Temperatura: " + temperatura + " ºC " + 
                " Presión: " + presion + " hPa" + " Velocidad viento " +
                velocidad + " Altitud: " + altitud;
    }
}
