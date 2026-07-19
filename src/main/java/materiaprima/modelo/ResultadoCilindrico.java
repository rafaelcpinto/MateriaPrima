package materiaprima.modelo;

public final class ResultadoCilindrico {

    private final double diametroMilimetros;
    private final String descricaoDiametro;
    private final double comprimentoTotal;
    private final double sobremetal;
    private final double massa;

    public ResultadoCilindrico(
            double diametroMilimetros,
            String descricaoDiametro,
            double comprimentoTotal,
            double sobremetal,
            double massa
    ) {
        this.diametroMilimetros = diametroMilimetros;
        this.descricaoDiametro = descricaoDiametro;
        this.comprimentoTotal = comprimentoTotal;
        this.sobremetal = sobremetal;
        this.massa = massa;
    }

    @Deprecated
    public ResultadoCilindrico(
            double diametroMilimetros,
            String descricaoDiametro,
            double sobremetal,
            double massa
    ) {
        this(diametroMilimetros, descricaoDiametro, 0.0, sobremetal, massa);
    }

    public double getDiametroMilimetros() {
        return diametroMilimetros;
    }

    public String getDescricaoDiametro() {
        return descricaoDiametro;
    }

    @Deprecated
    public String getDiametroPolegadas() {
        return getDescricaoDiametro();
    }

    public double getComprimentoTotal() {
        return comprimentoTotal;
    }

    public double getSobremetal() {
        return sobremetal;
    }

    public double getMassa() {
        return massa;
    }
}
