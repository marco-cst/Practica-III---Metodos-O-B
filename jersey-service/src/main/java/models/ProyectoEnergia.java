package models;

public class ProyectoEnergia {
    private int idProyectoEnergia;
    private String nombre;
    private double inversion;
    private int tiempoVida;
    private String tiempoInicioConstruccion;
    private String tiempoFinConstruccion;
    private String inversionistas;
    private double capacidadGeneracionDiaria;
    private double montoInversion;
    private String ubicacion;

    public ProyectoEnergia() {
    }

    public ProyectoEnergia(int idProyectoEnergia, String nombre, double inversion, int tiempoVida, String tiempoInicioConstruccion, String tiempoFinConstruccion, String inversionistas, double capacidadGeneracionDiaria, double montoInversion, String ubicacion) {
        this.idProyectoEnergia = idProyectoEnergia;
        this.nombre = nombre;
        this.inversion = inversion;
        this.tiempoVida = tiempoVida;
        this.tiempoInicioConstruccion = tiempoInicioConstruccion;
        this.tiempoFinConstruccion = tiempoFinConstruccion;
        this.inversionistas = inversionistas;
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
        this.montoInversion = montoInversion;
        this.ubicacion = ubicacion;
    }

    public int getIdProyectoEnergia() {
        return idProyectoEnergia;
    }

    public void setIdProyectoEnergia(int idProyectoEnergia) {
        this.idProyectoEnergia = idProyectoEnergia;
    }

    public String getInversionistas() {
        return inversionistas;
    }

    public void setInversionistas(String inversionistas) {
        this.inversionistas = inversionistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getInversion() {
        return inversion;
    }

    public void setInversion(double inversion) {
        this.inversion = inversion;
    }

    public int getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public String getTiempoInicioConstruccion() {
        return tiempoInicioConstruccion;
    }

    public void setTiempoInicioConstruccion(String tiempoInicioConstruccion) {
        this.tiempoInicioConstruccion = tiempoInicioConstruccion;
    }

    public String getTiempoFinConstruccion() {
        return tiempoFinConstruccion;
    }

    public void setTiempoFinConstruccion(String tiempoFinConstruccion) {
        this.tiempoFinConstruccion = tiempoFinConstruccion;
    }

  
    public double getCapacidadGeneracionDiaria() {
        return capacidadGeneracionDiaria;
    }

    public void setCapacidadGeneracionDiaria(double capacidadGeneracionDiaria) {
        this.capacidadGeneracionDiaria = capacidadGeneracionDiaria;
    }

    public double getMontoInversion() {
        return montoInversion;
    }

    public void setMontoInversion(double montoInversion) {
        this.montoInversion = montoInversion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    @Override
    public String toString() {
        return  ", nombre='" + nombre + '\'' +
                ", inversionistas='" + inversionistas + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                '}';   
    }
}
