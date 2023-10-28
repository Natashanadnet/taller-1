package py.edu.ucom.natasha.util;

public class CaracteresUtil {
    public static String limpiarYCapitalizar(String texto) {
        String textoLimpio = texto.replaceAll("[^a-zA-Z]", "");
        textoLimpio = textoLimpio.substring(0, 1).toUpperCase() + textoLimpio.substring(1).toLowerCase();
        return textoLimpio;

    }

}
