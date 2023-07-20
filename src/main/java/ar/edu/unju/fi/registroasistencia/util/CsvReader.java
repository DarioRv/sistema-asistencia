package ar.edu.unju.fi.registroasistencia.util;

import ar.edu.unju.fi.registroasistencia.clases.Estudiante;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {
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
                System.out.println("estudiante = " + estudiante);
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
}
