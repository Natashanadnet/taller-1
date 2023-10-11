package py.edu.ucom.natasha.entities;

public class Alfil extends Pieza {
    public Alfil(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {
        int difX = Math.abs(inicioX - finalX);
        int difY = Math.abs(inicioY - finalY);
        return difX == difY;
    }

}
