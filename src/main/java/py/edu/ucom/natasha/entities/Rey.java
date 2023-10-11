package py.edu.ucom.natasha.entities;

public class Rey extends Pieza {
    public Rey(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {
        int difY = Math.abs(inicioY - finalY);
        int difX = Math.abs(inicioX - finalX);
        return difX == 1 && difY == 1 || difX == 1 && inicioY == finalY || difY == 1 && inicioX == finalX;
    }

}
