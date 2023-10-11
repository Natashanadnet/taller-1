package py.edu.ucom.natasha.entities;

public class Reina extends Pieza {
    public Reina(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {
        int difY = Math.abs(inicioY - finalY);
        int difX = Math.abs(inicioX - finalX);
        return difX == difY || inicioX == finalX || inicioY == finalY;
    }

}
