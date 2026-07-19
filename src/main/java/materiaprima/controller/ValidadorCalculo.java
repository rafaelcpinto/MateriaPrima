package materiaprima.controller;

import materiaprima.dados.TabelasMateriaPrima;
import materiaprima.modelo.DimensaoComercial;
import materiaprima.modelo.FaixaSobremetal;
import materiaprima.modelo.PadraoDimensional;

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
        return valoresCilindricosValidos(
                diametro,
                comprimento,
                sobremetalComprimento,
                PadraoDimensional.POLEGADA
        );
    }

    public boolean valoresCilindricosValidos(
            Double diametro,
            Double comprimento,
            Double sobremetalComprimento,
            PadraoDimensional padrao
    ) {
        return diametro != null && comprimento != null && sobremetalComprimento != null
                && padrao != null
                && diametroCilindricoValido(diametro, padrao)
                && comprimento >= 0 && comprimento <= 3500
                && sobremetalComprimento >= 0;
    }

    private boolean diametroCilindricoValido(
            double diametro,
            PadraoDimensional padrao
    ) {
        if (!Double.isFinite(diametro) || diametro < 0) {
            return false;
        }

        if (padrao == PadraoDimensional.POLEGADA) {
            return diametro < limiteDiametroCilindrico();
        }

        for (FaixaSobremetal faixa : TabelasMateriaPrima.faixasSobremetal()) {
            if (faixa.contem(diametro)) {
                return true;
            }
        }
        return false;
    }

    public double limiteDiametroCilindrico() {
        DimensaoComercial[] diametrosComerciais = TabelasMateriaPrima.dimensoesPolegada();
        double maiorDimensaoComercial =
                diametrosComerciais[diametrosComerciais.length - 1].getMilimetros();
        for (FaixaSobremetal faixa : TabelasMateriaPrima.faixasSobremetal()) {
            double limite = maiorDimensaoComercial - faixa.getSobremetal();
            if (faixa.contem(limite)) {
                return limite;
            }
        }
        throw new IllegalStateException("Limite cilíndrico não encontrado.");
    }

    public boolean valoresRetangularesValidos(Double lado1, Double lado2, Double lado3) {
        return valoresRetangularesValidos(
                lado1, lado2, lado3, false, PadraoDimensional.METRICO);
    }

    public boolean valoresRetangularesValidos(
            Double largura,
            Double altura,
            Double comprimento,
            PadraoDimensional padrao
    ) {
        return valoresRetangularesValidos(
                largura, altura, comprimento, false, padrao);
    }

    public boolean valoresRetangularesValidos(
            Double largura,
            Double altura,
            Double comprimento,
            boolean reduzir,
            PadraoDimensional padrao
    ) {
        return padrao != null
                && dimensaoRetangularValida(largura, reduzir, padrao)
                && dimensaoRetangularValida(altura, reduzir, padrao)
                && dimensaoRetangularValida(comprimento, reduzir, padrao);
    }

    private boolean dimensaoRetangularValida(
            Double dimensao,
            boolean reduzir,
            PadraoDimensional padrao
    ) {
        if (dimensao == null || !Double.isFinite(dimensao) || dimensao < 0) {
            return false;
        }

        for (FaixaSobremetal faixa : TabelasMateriaPrima.faixasSobremetal()) {
            if (faixa.contem(dimensao)) {
                if (padrao == PadraoDimensional.METRICO) {
                    return true;
                }
                double fator = reduzir ? 0.5 : 1.0;
                double dimensaoNecessaria =
                        1.0 + dimensao + faixa.getSobremetal() * fator;
                DimensaoComercial[] dimensoes = TabelasMateriaPrima.dimensoesPolegada();
                double primeira = dimensoes[0].getMilimetros();
                double ultima = dimensoes[dimensoes.length - 1].getMilimetros();
                return dimensaoNecessaria >= primeira && dimensaoNecessaria < ultima;
            }
        }
        return false;
    }
}
