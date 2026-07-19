package materiaprima.controller;

import materiaprima.dados.TabelasMateriaPrima;
import materiaprima.modelo.FaixaSobremetal;

public final class ValidadorCalculo {

    public Double lerValor(String texto) {
        try {
            double valor = Double.parseDouble(texto);
            return Double.isFinite(valor) ? valor : null;
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }
    }

    public boolean valoresCilindricosValidos(
            Double diametro, Double comprimento, Double sobremetalComprimento) {
        return diametro != null && comprimento != null && sobremetalComprimento != null
                && diametroCilindricoValido(diametro)
                && comprimento >= 0 && comprimento <= 3500
                && sobremetalComprimento >= 0;
    }

    private boolean diametroCilindricoValido(double diametro) {
        if (!Double.isFinite(diametro) || diametro < 0) {
            return false;
        }

        double[] diametrosComerciais = TabelasMateriaPrima.diametrosMm();
        double maiorDiametroComercial =
                diametrosComerciais[diametrosComerciais.length - 1];
        for (FaixaSobremetal faixa : TabelasMateriaPrima.faixasSobremetal()) {
            if (faixa.contem(diametro)) {
                return diametro + faixa.getSobremetal() < maiorDiametroComercial;
            }
        }
        return false;
    }

    public boolean valoresRetangularesValidos(Double lado1, Double lado2, Double lado3) {
        return ladoValido(lado1) && ladoValido(lado2) && ladoValido(lado3);
    }

    private boolean ladoValido(Double lado) {
        return lado != null && lado >= 0 && lado < 1000;
    }
}
