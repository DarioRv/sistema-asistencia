package ar.edu.unju.fi.registroasistencia.util;

import ar.edu.unju.fi.registroasistencia.clases.Estudiante;
import ar.edu.unju.fi.registroasistencia.clases.RegistroAsistencia;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class CsvReader {
    public static final String SAVE_FOLDER = "src/main/resources/static/saves/";
    private String lastReportSave;

    public List<Estudiante> procesarCsv(String filename) {
        List<Estudiante> datosCsv = new ArrayList<Estudiante>();

        String csvFile = filename;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(cvsSplitBy);
                Estudiante estudiante = new Estudiante(datos[0].strip().replaceAll("\"",""), datos[1].strip().replaceAll("\"",""), datos[2].strip().replaceAll("\"",""));
                datosCsv.add(estudiante);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return datosCsv;
    }

    public void escribirCsv(List<RegistroAsistencia> registroAsistencias) {
        Date fechaActual = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
        String fechaFormateada = formatoFecha.format(fechaActual);

        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("es", "ES"));
        try (FileWriter writer = new FileWriter(SAVE_FOLDER + "reporte_" + fechaFormateada + ".csv")) {
            for (RegistroAsistencia registro : registroAsistencias) {
                String fechaHoraFormateada = formatoFechaHora.format(registro.getFecha());
                String linea = registro.getPersona().getLegajo() + ", " + registro.getPersona().getApellido() + ", " + registro.getPersona().getNombres() + ", " + fechaHoraFormateada + ", " + registro.getAsistio() + "\n";
                writer.write(linea);
            }
            setLastReportSave("reporte_" + fechaFormateada + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastReportSave() {
        return lastReportSave;
    }

    public void setLastReportSave(String lastReportSave) {
        this.lastReportSave = lastReportSave;
    }
}
