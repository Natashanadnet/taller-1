package py.edu.ucom.natasha.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/clase-3")
public class ListasYMapsResource {

    @GET
    public List<Integer> getLista() {
        List<Integer> listaInteger = new ArrayList<>();
        // int[8] lista = null;
        listaInteger.add(1);
        listaInteger.add(2);
        listaInteger.add(3);
        listaInteger.add(4);

        return listaInteger;

    }

    @GET
    @Path("maps")
    public Map<String, Object> getMaps() {
        List<Integer> listaInteger = getLista();

        Map<String, Object> mapsItem = new HashMap<>();
        mapsItem.put("clase-3", "25/09/2023");
        mapsItem.put("clase-4", 1000000);

        Map<String, Object> maps = new HashMap<>();
        maps.put("clase-3", "25/09/2023");
        maps.put("clase-4", 1000000);
        maps.put("clase-5", new Date());
        maps.put("clase-6", listaInteger);
        maps.put("clase-7", mapsItem);

        return maps;
    }

    @GET
    @Path("ejercicio-1")

    public Map<String, Integer> getFrecuencias() {
        String textoAContar = "Este es un ejemplo de texto. En este texto, queremos contar cuántas veces aparece cada palabra";
        return contadorDePalabras(textoAContar);

    }

    public Map<String, Integer> contadorDePalabras(String texto) {
        String[] palabras = texto.split("\\s+");
        List<String> listaPalabras = new ArrayList<>();

        for (String palabra : palabras) {
            palabra = palabra.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!palabra.isEmpty()) {
                listaPalabras.add(palabra);
            }
        }

        Map<String, Integer> contadorPalabras = new HashMap<>();

        for (String palabra : listaPalabras) {

            if (contadorPalabras.containsKey(palabra)) {
                int frecuencia = contadorPalabras.get(palabra);
                contadorPalabras.put(palabra, frecuencia + 1);
            } else {
                contadorPalabras.put(palabra, 1);
            }
        }

        return contadorPalabras;

    }

}
