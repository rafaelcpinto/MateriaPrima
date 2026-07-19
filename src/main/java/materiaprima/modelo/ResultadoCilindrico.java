package materiaprima.modelo;

public final class ResultadoCilindrico {

    private final double diametroMilimetros;
    private final String diametroPolegadas;
    private final double sobremetal;
    private final double massa;

    public ResultadoCilindrico(double diametroMilimetros, String diametroPolegadas,
            double sobremetal, double massa) {
        this.diametroMilimetros = diametroMilimetros;
        this.diametroPolegadas = diametroPolegadas;
        this.sobremetal = sobremetal;
        this.massa = massa;
    }

    public double getDiametroMilimetros() {
        return diametroMilimetros;
    }

    public String getDiametroPolegadas() {
        return diametroPolegadas;
    }

    public double getSobremetal() {
        return sobremetal;
    }

    public double getMassa() {
        return massa;
    }
}
