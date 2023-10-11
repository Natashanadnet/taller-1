package py.edu.ucom.natasha.entities;

public class Caballo extends Pieza {
    public Caballo(String color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {
        int comparacionY = Math.abs(inicioY - finalY);
        int comparacionX = Math.abs(inicioX - finalX);
        return comparacionY == 2 && comparacionX == 1 || comparacionY == 1 && comparacionX == 2;
    }

}
