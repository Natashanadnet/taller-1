package py.edu.ucom.natasha;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import py.edu.ucom.natasha.entities.Alumno;

@Path("/prueba")
public class clase1Resource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Double random() {
        return Math.random();
    }

    @GET
    @Path("obtener-alumno")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Alumno> obtenerAlumno() {
        List<Alumno> listaAlumnos = new ArrayList();
        Alumno data = new Alumno();
        data.setApellido("Netepczuk");
        data.setNombre("Mahiara");
        Alumno data2 = new Alumno();
        data2.setApellido("Netepczuk");
        data2.setNombre("Mahiara");

        listaAlumnos.add(data);
        listaAlumnos.add(data2);

        return listaAlumnos;
    }

}
