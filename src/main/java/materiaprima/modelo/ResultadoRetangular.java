package materiaprima.modelo;

public final class ResultadoRetangular {

    private final double larguraMilimetros;
    private final double alturaMilimetros;
    private final double comprimentoMilimetros;
    private final String larguraDescricao;
    private final String alturaDescricao;
    private final String comprimentoDescricao;
    private final double massa;

    public ResultadoRetangular(
            double larguraMilimetros,
            double alturaMilimetros,
            double comprimentoMilimetros,
            String larguraDescricao,
            String alturaDescricao,
            String comprimentoDescricao,
            double massa
    ) {
        this.larguraMilimetros = larguraMilimetros;
        this.alturaMilimetros = alturaMilimetros;
        this.comprimentoMilimetros = comprimentoMilimetros;
        this.larguraDescricao = larguraDescricao;
        this.alturaDescricao = alturaDescricao;
        this.comprimentoDescricao = comprimentoDescricao;
        this.massa = massa;
    }

    public double getLarguraMilimetros() {
        return larguraMilimetros;
    }

    public double getAlturaMilimetros() {
        return alturaMilimetros;
    }

    public double getComprimentoMilimetros() {
        return comprimentoMilimetros;
    }

    public String getLarguraDescricao() {
        return larguraDescricao;
    }

    public String getAlturaDescricao() {
        return alturaDescricao;
    }

    public String getComprimentoDescricao() {
        return comprimentoDescricao;
    }

    public double getLado1() {
        return getLarguraMilimetros();
    }

    public double getLado2() {
        return getAlturaMilimetros();
    }

    public double getLado3() {
        return getComprimentoMilimetros();
    }

    public double getMassa() {
        return massa;
    }
}
