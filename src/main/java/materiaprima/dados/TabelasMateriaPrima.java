package materiaprima.dados;

import materiaprima.modelo.FaixaSobremetal;
import materiaprima.modelo.DimensaoComercial;
import materiaprima.modelo.Material;
import materiaprima.modelo.PadraoDimensional;

public final class TabelasMateriaPrima {

    private static final Material[] MATERIAIS = {
            new Material("Aço", 7.85 / 1_000_000),
            new Material("Aço inoxidável 304L", 7.90 / 1_000_000),
            new Material("Latão", 8.50 / 1_000_000),
            new Material("Cobre", 8.93 / 1_000_000),
            new Material("Bronze Fosfórico", 8.80 / 1_000_000),
            new Material("Alumínio", 2.70 / 1_000_000),
            new Material("Titânio Grau 2", 4.51 / 1_000_000),

            new Material("Polipropileno", 0.93 / 1_000_000),
            new Material("Nylon", 1.14 / 1_000_000),
            new Material("PEAD", 0.95 / 1_000_000),
            new Material("Acrílico (PMMA)", 1.18 / 1_000_000)
    };

    private static final FaixaSobremetal[] FAIXAS_SOBREMETAL = {
        new FaixaSobremetal(0, 25, 2.6),
        new FaixaSobremetal(25, 40, 3),
        new FaixaSobremetal(40, 63, 4),
        new FaixaSobremetal(63, 80, 5),
        new FaixaSobremetal(80, 100, 6),
        new FaixaSobremetal(100, 125, 7),
        new FaixaSobremetal(125, 160, 9),
        new FaixaSobremetal(160, 200, 11),
        new FaixaSobremetal(200, 250, 13),
        new FaixaSobremetal(250, 315, 16),
        new FaixaSobremetal(315, 400, 19),
        new FaixaSobremetal(400, 500, 24),
        new FaixaSobremetal(500, 630, 30),
        new FaixaSobremetal(630, 800, 37),
        new FaixaSobremetal(800, 1000, 46)
    };

    private static final DimensaoComercial[] DIMENSOES_POLEGADA = {
            new DimensaoComercial(2.38125, "3/32", PadraoDimensional.POLEGADA),
            new DimensaoComercial(3.175, "1/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(3.96875, "5/32", PadraoDimensional.POLEGADA),
            new DimensaoComercial(4.7625, "3/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(5.55625, "7/32", PadraoDimensional.POLEGADA),
            new DimensaoComercial(6.35, "1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(7.14375, "9/32", PadraoDimensional.POLEGADA),
            new DimensaoComercial(7.9375, "5/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(9.525, "3/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(11.1125, "7/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(12.7, "1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(14.2875, "9/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(15.875, "5/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(17.4625, "11/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(19.05, "3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(20.6375, "13/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(22.225, "7/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(23.8125, "15/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(25.4, "1", PadraoDimensional.POLEGADA),
            new DimensaoComercial(26.9875, "1   1/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(28.575, "1   1/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(30.1625, "1   3/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(31.75, "1   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(33.3375, "1   5/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(34.925, "1   3/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(36.5125, "1   7/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(38.1, "1   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(39.6875, "1   9/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(41.275, "1   5/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(44.45, "1   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(47.625, "1   7/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(50.8, "2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(52.3875, "2   1/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(53.975, "2   1/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(55.5625, "2   3/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(57.15, "2   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(58.7375, "2   5/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(60.325, "2   3/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(61.9125, "2   7/16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(63.5, "2   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(66.675, "2   5/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(69.85, "2   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(73.025, "2   7/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(76.2, "3", PadraoDimensional.POLEGADA),
            new DimensaoComercial(79.375, "3   1/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(82.55, "3   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(85.725, "3   3/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(88.9, "3   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(92.075, "3   5/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(95.25, "3   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(98.425, "3   7/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(101.6, "4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(104.775, "4   1/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(107.95, "4   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(111.125, "4   3/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(114.3, "4   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(117.475, "4   5/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(120.65, "4   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(123.825, "4   7/8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(127, "5", PadraoDimensional.POLEGADA),
            new DimensaoComercial(133.35, "5   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(139.7, "5   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(146.05, "5   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(152.4, "6", PadraoDimensional.POLEGADA),
            new DimensaoComercial(158.75, "6   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(165.1, "6   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(171.45, "6   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(177.8, "7", PadraoDimensional.POLEGADA),
            new DimensaoComercial(184.15, "7   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(190.5, "7   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(196.85, "7   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(203.2, "8", PadraoDimensional.POLEGADA),
            new DimensaoComercial(209.55, "8   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(215.9, "8   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(222.25, "8   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(228.6, "9", PadraoDimensional.POLEGADA),
            new DimensaoComercial(234.95, "9   1/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(241.3, "9   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(247.65, "9   3/4", PadraoDimensional.POLEGADA),
            new DimensaoComercial(254, "10", PadraoDimensional.POLEGADA),
            new DimensaoComercial(266.7, "10   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(279.4, "11", PadraoDimensional.POLEGADA),
            new DimensaoComercial(292.1, "11   1/2", PadraoDimensional.POLEGADA),
            new DimensaoComercial(304.8, "12", PadraoDimensional.POLEGADA),
            new DimensaoComercial(330.2, "13", PadraoDimensional.POLEGADA),
            new DimensaoComercial(355.6, "14", PadraoDimensional.POLEGADA),
            new DimensaoComercial(381, "15", PadraoDimensional.POLEGADA),
            new DimensaoComercial(406.4, "16", PadraoDimensional.POLEGADA),
            new DimensaoComercial(431.8, "17", PadraoDimensional.POLEGADA),
            new DimensaoComercial(457.2, "18", PadraoDimensional.POLEGADA),
            new DimensaoComercial(508, "20", PadraoDimensional.POLEGADA),
            new DimensaoComercial(558.8, "22", PadraoDimensional.POLEGADA),
            new DimensaoComercial(609.6, "24", PadraoDimensional.POLEGADA),
            new DimensaoComercial(660.4, "26", PadraoDimensional.POLEGADA),
            new DimensaoComercial(711.2, "28", PadraoDimensional.POLEGADA),
            new DimensaoComercial(762.0, "30", PadraoDimensional.POLEGADA),
            new DimensaoComercial(812.8, "32", PadraoDimensional.POLEGADA),
            new DimensaoComercial(863.6, "34", PadraoDimensional.POLEGADA),
            new DimensaoComercial(914.4, "36", PadraoDimensional.POLEGADA),
            new DimensaoComercial(965.2, "38", PadraoDimensional.POLEGADA)
    };

    static {
        validarFaixasSobremetal();
        validarOrdemCrescente(DIMENSOES_POLEGADA);
    }

    private TabelasMateriaPrima() {
    }

    public static Material material(int indice) {
        if (indice < 0 || indice >= MATERIAIS.length) {
            throw new IllegalArgumentException("Material inválido: " + indice);
        }
        return MATERIAIS[indice];
    }

    public static Material[] materiais() {
        return MATERIAIS.clone();
    }

    public static FaixaSobremetal[] faixasSobremetal() {
        return FAIXAS_SOBREMETAL.clone();
    }

    public static DimensaoComercial[] dimensoesPolegada() {
        return DIMENSOES_POLEGADA.clone();
    }

    // Mantido temporariamente para compatibilidade; futuramente usar dimensoesPolegada().
    public static DimensaoComercial[] diametrosPolegada() {
        return dimensoesPolegada();
    }

    // Mantido temporariamente para compatibilidade; futuramente usar dimensoesPolegada().
    public static DimensaoComercial[] diametrosComerciais() {
        return dimensoesPolegada();
    }

    private static void validarOrdemCrescente(DimensaoComercial[] dimensoes) {
        for (int indice = 1; indice < dimensoes.length; indice++) {
            double anterior = dimensoes[indice - 1].getMilimetros();
            double atual = dimensoes[indice].getMilimetros();
            if (atual <= anterior) {
                throw new IllegalStateException("Tabela de dimensões fora de ordem.");
            }
        }
    }

    private static void validarFaixasSobremetal() {
        if (FAIXAS_SOBREMETAL.length == 0) {
            throw new IllegalStateException("Tabela de sobremetal vazia");
        }
        for (int indice = 1; indice < FAIXAS_SOBREMETAL.length; indice++) {
            double fimAnterior = FAIXAS_SOBREMETAL[indice - 1].getLimiteMaximo();
            double inicioAtual = FAIXAS_SOBREMETAL[indice].getLimiteMinimo();
            if (Double.compare(fimAnterior, inicioAtual) != 0) {
                throw new IllegalStateException("Faixas de sobremetal descontínuas");
            }
        }
    }
}
