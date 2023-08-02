package ar.edu.unju.fi.registroasistencia.service;

import ar.edu.unju.fi.registroasistencia.clases.Estudiante;
import ar.edu.unju.fi.registroasistencia.clases.RegistroAsistencia;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegistrarAsistencia implements IRegistrarAsistencia {
    List<Estudiante> listaEstudiantes = new ArrayList<Estudiante>();
    List<RegistroAsistencia> registroAsistencias = new ArrayList<RegistroAsistencia>();

    private int horaInicio = 01;
    private int horaFin = 22;

    @Override
    public int getHoraInicio() {
        return horaInicio;
    }

    @Override
    public int getHoraFin() {
        return horaFin;
    }

    @Override
    public void setHorario(int inicio,int fin){
        this.horaInicio= inicio;
        this.horaFin= fin;
    }

    @Override
    public void registrarAsistencia(Estudiante estudiante) {
        RegistroAsistencia registroAsistencia = new RegistroAsistencia(estudiante, new Date(), true);
        registroAsistencias.add(registroAsistencia);
    }

    @Override
    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    @Override
    public void setListaEstudiantes(List<Estudiante> lista) {
        for (Estudiante estudiante : lista) {
            listaEstudiantes.add(estudiante);
        }
    }

    @Override
    public List<RegistroAsistencia> getRegistroAsistencia() {
        return registroAsistencias;
    }

    @Override
    public Estudiante getEstudiantePorDni(int dni) {
        for (Estudiante estudiante : listaEstudiantes) {
            if (dni == estudiante.getDni()) {
                return estudiante;
            }
        }
        return null;
    }

    @Override
    public Estudiante getEstudiantePorLegajo(String legajo) {
        for (Estudiante estudiante : listaEstudiantes) {
            if (legajo.equalsIgnoreCase(estudiante.getLegajo())) {
                return estudiante;
            }
        }
        return null;
    }
}
