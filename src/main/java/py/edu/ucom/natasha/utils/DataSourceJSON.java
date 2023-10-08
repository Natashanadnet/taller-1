package py.edu.ucom.natasha.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;

import py.edu.ucom.natasha.entities.Moneda;

@ApplicationScoped
public class DataSourceJSON {
    public String SRC_MONEDAS = "C:\\Users\\n" + //
            "atas\\OneDrive\\Documents\\Ucom Laptop\\2023\\java 2\\clase 1\\taller-1\\src\\main\\java\\py\\edu\\ucom\\n"
            + //
            "atasha\\utils\\monedas.json";

    public List<Moneda> obtenerMonedas() {
        ObjectMapper mapper = new ObjectMapper();
        List<Moneda> lista = new ArrayList();

        try {
            lista = mapper.readValue(
                    new File(this.SRC_MONEDAS),
                    new TypeReference<List<Moneda>>() {
                    });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return lista;
    }

    public Moneda buscarMonedas(String codigo) {
        Moneda data = null;
        List<Moneda> lista = obtenerMonedas();

        for (Moneda item : lista) {
            if (item.getCodigo().equals(codigo)) {
                data = item;
                break;
            }
        }
        return data;
    }

}