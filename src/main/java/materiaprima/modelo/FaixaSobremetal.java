package materiaprima.modelo;

public final class FaixaSobremetal {

    private final double limiteMinimo;
    private final double limiteMaximo;
    private final double sobremetal;

    public FaixaSobremetal(double limiteMinimo, double limiteMaximo, double sobremetal) {
        if (!Double.isFinite(limiteMinimo) || !Double.isFinite(limiteMaximo)
                || limiteMinimo < 0 || limiteMaximo <= limiteMinimo) {
            throw new IllegalArgumentException("Limites inválidos para a faixa");
        }
        if (!Double.isFinite(sobremetal) || sobremetal < 0) {
            throw new IllegalArgumentException("Sobremetal inválido: " + sobremetal);
        }
        this.limiteMinimo = limiteMinimo;
        this.limiteMaximo = limiteMaximo;
        this.sobremetal = sobremetal;
    }

    public boolean contem(double valor) {
        return valor >= limiteMinimo && valor < limiteMaximo;
    }

    public double getLimiteMinimo() {
        return limiteMinimo;
    }

    public double getLimiteMaximo() {
        return limiteMaximo;
    }

    public double getSobremetal() {
        return sobremetal;
    }
}
