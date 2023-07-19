package ar.edu.unju.fi.registroasistencia.clases;

public class Docente extends Persona {
    private String grado;
    private String area;
    private String licencia;

    public Docente() {
    }

    public Docente(int dni, String nombres, String apellido, String direccion, int telefono, String grado, String area, String licencia) {
        super(dni, nombres, apellido, direccion, telefono);
        this.grado = grado;
        this.area = area;
        this.licencia = licencia;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    @Override
    public void presentarse() {
        System.out.println("Hola, soy " + this.grado + " " + this.apellido + " " + this.nombres);
    }

    @Override
    public String toString() {
        return "Docente{" +
                "grado='" + grado + '\'' +
                ", area='" + area + '\'' +
                ", licencia='" + licencia + '\'' +
                ", dni=" + dni +
                ", nombres='" + nombres + '\'' +
                ", apellido='" + apellido + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}
