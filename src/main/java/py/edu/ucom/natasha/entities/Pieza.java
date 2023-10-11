package py.edu.ucom.natasha.entities;

public class Pieza {
    private String color;
    private int x;
    private int y;

    public Pieza(String color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean movimientoValido(int inicioX, int inicioY, int finalX, int finalY) {
        return false;
    }

}
