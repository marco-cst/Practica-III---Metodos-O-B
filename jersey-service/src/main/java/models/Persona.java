package models;

public class Persona {
    private int idPersona;
    private String nombre;
    private String apellido;
    private String DNI;
    private String celular;

    public Persona() {
    }
    
    public Persona(int idPersona, String nombre, String apellido, String DNI, String celular) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.celular = celular;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
