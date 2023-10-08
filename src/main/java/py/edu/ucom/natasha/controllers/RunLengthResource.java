package py.edu.ucom.natasha.controllers;

import jakarta.ws.rs.Path;

import jakarta.ws.rs.GET;

@Path("/run-length")
public class RunLengthResource {
    @GET
    public String comprimir() {
        String prueba = "AAABBCCCCDAA";
        int contador = 0;
        char letraActual = prueba.charAt(0);
        StringBuilder concat = new StringBuilder();

        for (int i = 0; i < prueba.length(); i++) {
            if (prueba.charAt(i) == letraActual) {
                contador++;
            } else {
                concat.append(contador).append(letraActual);
                contador = 1;
                letraActual = prueba.charAt(i);
            }

        }
        concat.append(contador).append(letraActual);

        return concat.toString();
    }

}
