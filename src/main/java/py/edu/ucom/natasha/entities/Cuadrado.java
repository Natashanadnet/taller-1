package py.edu.ucom.natasha.entities;

public class Cuadrado extends FiguraGeometrica {
    public double lado;

    public Cuadrado(double lado) {
        this.lado = lado;
    }

    @Override
    public double calcularArea() {
        return (lado * lado);
    }

    @Override
    public double calcularPerimetro() {
        return 4 * lado;
    }

}
