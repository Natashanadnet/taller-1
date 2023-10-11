package py.edu.ucom.natasha.entities;

public class Torre extends Pieza {

    public Torre(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {
        return inicioX == finalX;
    }

}
