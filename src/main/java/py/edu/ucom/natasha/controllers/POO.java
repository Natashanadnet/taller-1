package py.edu.ucom.natasha.controllers;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import py.edu.ucom.natasha.entities.Cuadrado;
import py.edu.ucom.natasha.entities.FiguraGeometrica;
import py.edu.ucom.natasha.entities.Rectangulo;

@Path("poo")
public class POO {

    @GET
    @Path("cuadrado")
    public double obtenerPerimetroCuadrado() {
        Cuadrado c = new Cuadrado(4.0);

        return c.calcularPerimetro();
    }

    @GET
    @Path("cuadrado-area")
    public double obtenerAreaCuadra() {
        Cuadrado c = new Cuadrado(2.0);

        return c.calcularArea();
    }

    @GET
    @Path("rectangulo-area")
    public double obtenerAreaRectangulo() {
        Rectangulo r = new Rectangulo(2.0, 4.0);
        FiguraGeometrica f = r;
        return f.calcularPerimetro();
    }

}