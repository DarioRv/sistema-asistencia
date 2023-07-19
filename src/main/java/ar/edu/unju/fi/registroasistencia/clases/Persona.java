package ar.edu.unju.fi.registroasistencia.clases;

public abstract class Persona {
    protected int dni;
    protected String nombres;
    protected String apellido;
    protected String direccion;
    protected int telefono;

    protected Persona() {
    }

    protected Persona(int dni, String nombres, String apellido, String direccion, int telefono) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Persona(String nombres, String apellido) {
        this.nombres = nombres;
        this.apellido = apellido;
    }

    protected abstract void presentarse();

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
