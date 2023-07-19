package ar.edu.unju.fi.registroasistencia.clases;

import java.util.Date;

public class RegistroAsistencia {
    private Persona persona;
    private Date fecha;
    private boolean asistio;

    public RegistroAsistencia() {
    }

    public RegistroAsistencia(Persona persona, Date fecha, boolean asistio) {
        this.persona = persona;
        this.fecha = fecha;
        this.asistio = asistio;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    @Override
    public String toString() {
        return "RegistroAsistencia{" +
                "persona=" + persona +
                ", fecha=" + fecha +
                ", asistio=" + asistio +
                '}';
    }
}
