package py.edu.ucom.natasha.entities;

public class Peon extends Pieza {
    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {

        return inicioX == finalX && inicioY + 1 == finalY;
    }

    public static String prueba() {
        return "";
    }

}
