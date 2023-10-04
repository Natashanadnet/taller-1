package py.edu.ucom.natasha.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import py.edu.ucom.natasha.entities.Moneda;

public class DataSourceJSON {
    public String SRC = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\monedas.json";

    public void guardarmonedas(Moneda moneda) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Moneda> lista = ObtenerMonedas();
            mapper.writeValue(new File(this.SRC), lista);
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public List<Moneda> ObtenerMonedas() {
        ObjectMapper mapper = new ObjectMapper();
        List<Moneda> lista = new ArrayList<>();
        try {
            lista = mapper.readValue(new File(this.SRC), new TypeReference<List<Moneda>>());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return lista;
    }

}
