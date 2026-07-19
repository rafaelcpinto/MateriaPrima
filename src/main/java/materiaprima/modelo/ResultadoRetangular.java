package materiaprima.modelo;

public final class ResultadoRetangular {

    private final double lado1;
    private final double lado2;
    private final double lado3;
    private final double massa;

    public ResultadoRetangular(double lado1, double lado2, double lado3, double massa) {
        this.lado1 = lado1;
        this.lado2 = lado2;
        this.lado3 = lado3;
        this.massa = massa;
    }

    public double getLado1() {
        return lado1;
    }

    public double getLado2() {
        return lado2;
    }

    public double getLado3() {
        return lado3;
    }

    public double getMassa() {
        return massa;
    }
}
