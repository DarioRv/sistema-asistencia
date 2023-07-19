package ar.edu.unju.fi.registroasistencia.clases;

public class Estudiante extends Persona {
    private String legajo;
    private String carrera;
    private String universidad;

    public Estudiante() {
    }

    public Estudiante(int dni, String nombres, String apellido, String direccion, int telefono, String legajo, String carrera, String universidad) {
        super(dni, nombres, apellido, direccion, telefono);
        this.legajo = legajo;
        this.carrera = carrera;
        this.universidad = universidad;
    }

    public Estudiante(String legajo, String apellido, String nombres) {
        super(nombres, apellido);
        this.legajo = legajo;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    @Override
    protected void presentarse() {
        System.out.println("Hola, soy" + this.apellido + " " + this.nombres + " con número de legajo " + this.legajo);
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "legajo='" + legajo + '\'' +
                ", carrera='" + carrera + '\'' +
                ", universidad='" + universidad + '\'' +
                ", dni=" + dni +
                ", nombres='" + nombres + '\'' +
                ", apellido='" + apellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
