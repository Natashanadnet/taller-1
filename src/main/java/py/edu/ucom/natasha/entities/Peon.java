package py.edu.ucom.natasha.entities;

public class Peon extends Pieza {
    public Peon(String color, int x, int y) {
        super(color, x, y);
    }

    public boolean primerMovimiento(int inicioX, int inicioY, int finalX, int finalY) {
        return inicioX == finalX && (inicioY + 1 == finalY || inicioY + 2 == finalY);
    }

    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {

        return inicioX == finalX && inicioY + 1 == finalY;
    }

}
