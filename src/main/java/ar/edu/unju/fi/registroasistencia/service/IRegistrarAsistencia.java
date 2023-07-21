package ar.edu.unju.fi.registroasistencia.service;

import ar.edu.unju.fi.registroasistencia.clases.Estudiante;
import ar.edu.unju.fi.registroasistencia.clases.Persona;
import ar.edu.unju.fi.registroasistencia.clases.RegistroAsistencia;

import java.util.List;

public interface IRegistrarAsistencia {
    public int getHoraInicio();
    public int getHoraFin();
    public void registrarAsistencia(Estudiante estudiante);
    public List<Estudiante> getListaEstudiantes();
    public void setListaEstudiantes(List<Estudiante> lista);
    public List<RegistroAsistencia> getRegistroAsistencia();
    public Estudiante getEstudiantePorDni(int dni);
    public Estudiante getEstudiantePorLegajo(String legajo);
}
