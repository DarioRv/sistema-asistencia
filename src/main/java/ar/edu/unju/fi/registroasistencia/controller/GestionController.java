package ar.edu.unju.fi.registroasistencia.controller;

import ar.edu.unju.fi.registroasistencia.clases.Estudiante;
import ar.edu.unju.fi.registroasistencia.service.IRegistrarAsistencia;
import ar.edu.unju.fi.registroasistencia.util.CsvReader;
import ar.edu.unju.fi.registroasistencia.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/gestion")
public class GestionController {
    public static final String PASSWD = "admin";
    @Autowired
    private IRegistrarAsistencia registrarAsistencia;
    @Autowired
    UploadFile uploadFile;
    @Autowired
    CsvReader csvReader;

    @GetMapping("/login")
    public String getDashboardPage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String passwd) {
        ModelAndView modelAndView;
        if (passwd.equals(PASSWD)) {
            modelAndView  = new ModelAndView("dashboard");
            List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
            if (listaAntigua.size() != 0) {
                modelAndView.addObject("estudiantes", listaAntigua);
                registrarAsistencia.setListaEstudiantes(listaAntigua);
            }
            else {
                modelAndView.addObject("notfound", "No hay archivo cargado");
            }
        }
        else {
            modelAndView  = new ModelAndView("login");
            modelAndView.addObject("autherror", "No se pudo autenticar");
        }
        return modelAndView;
    }

    @GetMapping("/cargar-alumnos")
    public String getPageCargarDatosAlumnos(Model model) {
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        if (listaAntigua.size() != 0) {
            model.addAttribute("estudiantes", listaAntigua);
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }
        else {
            model.addAttribute("notfound", "No hay archivo cargado");
        }
        return "carga-datos";
    }

    @PostMapping("/procesar-csv")
    public ModelAndView cargarDatosAlumnos(@RequestParam MultipartFile file) throws Exception {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("success", "Datos cargados");
        try {
            uploadFile.copy(file);
        }
        catch (Exception e) {
            uploadFile.delete(file.getOriginalFilename());
            uploadFile.copy(file);
        }

        registrarAsistencia.setListaEstudiantes(csvReader.procesarCsv(uploadFile.getPath(file.getOriginalFilename()).toString()));

        modelAndView.addObject("estudiantes", registrarAsistencia.getListaEstudiantes());

        return modelAndView;
    }

    @GetMapping("/registro-asistencia")
    public ModelAndView getRegistroAsistencia() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        modelAndView.addObject("registroAsistencia", registrarAsistencia.getRegistroAsistencia());
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        modelAndView.addObject("estudiantes", listaAntigua);
        return modelAndView;
    }
}
