package ar.edu.unju.fi.registroasistencia.controller;

import ar.edu.unju.fi.registroasistencia.clases.Estudiante;
import ar.edu.unju.fi.registroasistencia.service.IRegistrarAsistencia;
import ar.edu.unju.fi.registroasistencia.util.CsvReader;
import ar.edu.unju.fi.registroasistencia.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/asistencia")
public class AsistenciaController {
    @Autowired
    private IRegistrarAsistencia registrarAsistencia;
    @Autowired
    private CsvReader csvReader;
    @Autowired
    private UploadFile uploadFile;

    @GetMapping("/formulario-asistencia")
    public String getFormularioAsistenciaPage(Model model) {
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        if (listaAntigua.size() != 0) {
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }

        Date fechaActual = new Date();
        boolean isOutOfDate = !(fechaActual.getHours() >= registrarAsistencia.getHoraInicio() && fechaActual.getHours() < registrarAsistencia.getHoraFin());
        if (isOutOfDate) {
            model.addAttribute("outOfDate", true);
        }
        else {
            model.addAttribute("outOfDate", false);
        }
        model.addAttribute("horaInicio", registrarAsistencia.getHoraInicio());
        model.addAttribute("horaFin", registrarAsistencia.getHoraFin()-1);
        return "registrar-asistencia";
    }

    @GetMapping("/registrar")
    public ModelAndView registrarAsistencia(@RequestParam String legajo) {
        ModelAndView modelAndView = new ModelAndView("registrar-asistencia");
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        if (listaAntigua.size() != 0) {
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }

        Date fechaActual = new Date();
        boolean isOutOfDate = !(fechaActual.getHours() >= registrarAsistencia.getHoraInicio() && fechaActual.getHours() < registrarAsistencia.getHoraFin());
        if (isOutOfDate) {
            modelAndView.addObject("outOfDate", true);
        }
        else {
            modelAndView.addObject("outOfDate", false);
        }

        if (isOutOfDate) {
            modelAndView.addObject("accessDenied", true);
        }
        else {
            Estudiante estudiante = registrarAsistencia.getEstudiantePorLegajo(legajo);

            if (estudiante != null) {
                registrarAsistencia.registrarAsistencia(estudiante);
                modelAndView.addObject("success", "Tu asistencia se registro");
            }
            else {
                modelAndView.addObject("error", "El legajo no esta registrado");
            }
        }

        modelAndView.addObject("horaInicio", registrarAsistencia.getHoraInicio());
        modelAndView.addObject("horaFin", registrarAsistencia.getHoraFin()-1);
        return modelAndView;
    }
}
