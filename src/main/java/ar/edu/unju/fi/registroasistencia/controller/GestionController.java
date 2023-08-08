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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        ModelAndView modelAndView = new ModelAndView("inicio");
        if (!passwd.equals(PASSWD)) {
            modelAndView.setViewName("login");
            modelAndView.addObject("autherror", "No se pudo autenticar");
        }
        return modelAndView;
    }

    @GetMapping("/alumnos")
    public ModelAndView getAlumnos(){
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        ModelAndView modelAndView = new ModelAndView("alumnos");
        if (listaAntigua.size() != 0) {
            modelAndView.addObject("estudiantes", listaAntigua);
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }
        else {
            modelAndView.addObject("csvnotfound", "No hay archivo cargado");
        }
        return modelAndView;
    }

    @GetMapping("/test")
    public ModelAndView gestionClase(){
        ModelAndView modelAndView = new ModelAndView("evento");
        modelAndView.addObject("registroAsistencia", registrarAsistencia.getRegistroAsistencia());
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        if (listaAntigua.size() != 0) {
            modelAndView.addObject("estudiantes", listaAntigua);
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }
        else {
            modelAndView.addObject("csvnotfound", "No hay archivo cargado");
        }
        return modelAndView;
    }
    
    @GetMapping("/horarios")
    public ModelAndView gestionHorarios(){
        ModelAndView modelAndView = new ModelAndView("horarios");
        modelAndView.addObject("horaIni", registrarAsistencia.getHoraInicio());
        modelAndView.addObject("horaFin", registrarAsistencia.getHoraFin());
        return modelAndView;
    }

    @GetMapping("/guardar-horarios")
    public ModelAndView guardarHorarios(@RequestParam("ini") String ini, @RequestParam("fin") String fin){
        ModelAndView modelAndView = new ModelAndView("horarios");
        if(ini.isEmpty() || fin.isEmpty() ){
            modelAndView.addObject("error", true);
        }else{
            registrarAsistencia.setHorario(Integer.parseInt(ini), Integer.parseInt(fin));
        }
        modelAndView.addObject("horaIni", registrarAsistencia.getHoraInicio());
        modelAndView.addObject("horaFin", registrarAsistencia.getHoraFin());
        return modelAndView;
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
        ModelAndView modelAndView = new ModelAndView("inicio");
        modelAndView.addObject("registroAsistencia", registrarAsistencia.getRegistroAsistencia());
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        if (listaAntigua.size() != 0) {
            modelAndView.addObject("estudiantes", listaAntigua);
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }
        else {
            modelAndView.addObject("csvnotfound", "No hay archivo cargado");
        }
        return modelAndView;
    }

    @GetMapping("/generarar-registro-csv")
    public ModelAndView generarRegistroAsistenciaCsv() {
        ModelAndView modelAndView = new ModelAndView("inicio");
        modelAndView.addObject("registroAsistencia", registrarAsistencia.getRegistroAsistencia());
        List<Estudiante> listaAntigua = csvReader.procesarCsv(uploadFile.getPath("alumnos.csv").toString());
        if (listaAntigua.size() != 0) {
            modelAndView.addObject("estudiantes", listaAntigua);
            registrarAsistencia.setListaEstudiantes(listaAntigua);
        }
        else {
            modelAndView.addObject("csvnotfound", "No hay archivo cargado");
        }

        Date fecha = new Date();
        System.out.println(fecha.getHours() + ">=" + registrarAsistencia.getHoraFin());
        System.out.println(fecha.getHours() >= registrarAsistencia.getHoraFin());
        if (fecha.getHours() >= registrarAsistencia.getHoraFin()) {
            csvReader.escribirCsv(registrarAsistencia.getRegistroAsistencia());
            modelAndView.addObject("csvGenerated", true);
            String csvName = csvReader.getLastReportSave();
            modelAndView.addObject("csvName", csvName);
        }
        else {
            modelAndView.addObject("csvGenerated", false);
            modelAndView.addObject("horaFin", registrarAsistencia.getHoraFin());
        }
        return modelAndView;
    }
}
