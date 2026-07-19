package materiaprima.dados;

import materiaprima.modelo.FaixaSobremetal;
import materiaprima.modelo.DiametroComercial;
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

    private static final DiametroComercial[] DIAMETROS_POLEGADA = {
            new DiametroComercial(2.38125, "3/32", PadraoDimensional.POLEGADA),
            new DiametroComercial(3.175, "1/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(3.96875, "5/32", PadraoDimensional.POLEGADA),
            new DiametroComercial(4.7625, "3/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(5.55625, "7/32", PadraoDimensional.POLEGADA),
            new DiametroComercial(6.35, "1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(7.14375, "9/32", PadraoDimensional.POLEGADA),
            new DiametroComercial(7.9375, "5/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(9.525, "3/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(11.1125, "7/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(12.7, "1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(14.2875, "9/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(15.875, "5/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(17.4625, "11/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(19.05, "3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(20.6375, "13/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(22.225, "7/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(23.8125, "15/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(25.4, "1", PadraoDimensional.POLEGADA),
            new DiametroComercial(26.9875, "1   1/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(28.575, "1   1/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(30.1625, "1   3/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(31.75, "1   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(33.3375, "1   5/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(34.925, "1   3/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(36.5125, "1   7/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(38.1, "1   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(39.6875, "1   9/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(41.275, "1   5/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(44.45, "1   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(47.625, "1   7/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(50.8, "2", PadraoDimensional.POLEGADA),
            new DiametroComercial(52.3875, "2   1/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(53.975, "2   1/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(55.5625, "2   3/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(57.15, "2   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(58.7375, "2   5/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(60.325, "2   3/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(61.9125, "2   7/16", PadraoDimensional.POLEGADA),
            new DiametroComercial(63.5, "2   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(66.675, "2   5/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(69.85, "2   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(73.025, "2   7/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(76.2, "3", PadraoDimensional.POLEGADA),
            new DiametroComercial(79.375, "3   1/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(82.55, "3   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(85.725, "3   3/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(88.9, "3   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(92.075, "3   5/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(95.25, "3   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(98.425, "3   7/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(101.6, "4", PadraoDimensional.POLEGADA),
            new DiametroComercial(104.775, "4   1/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(107.95, "4   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(111.125, "4   3/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(114.3, "4   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(117.475, "4   5/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(120.65, "4   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(123.825, "4   7/8", PadraoDimensional.POLEGADA),
            new DiametroComercial(127, "5", PadraoDimensional.POLEGADA),
            new DiametroComercial(133.35, "5   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(139.7, "5   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(146.05, "5   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(152.4, "6", PadraoDimensional.POLEGADA),
            new DiametroComercial(158.75, "6   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(165.1, "6   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(171.45, "6   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(177.8, "7", PadraoDimensional.POLEGADA),
            new DiametroComercial(184.15, "7   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(190.5, "7   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(196.85, "7   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(203.2, "8", PadraoDimensional.POLEGADA),
            new DiametroComercial(209.55, "8   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(215.9, "8   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(222.25, "8   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(228.6, "9", PadraoDimensional.POLEGADA),
            new DiametroComercial(234.95, "9   1/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(241.3, "9   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(247.65, "9   3/4", PadraoDimensional.POLEGADA),
            new DiametroComercial(254, "10", PadraoDimensional.POLEGADA),
            new DiametroComercial(266.7, "10   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(279.4, "11", PadraoDimensional.POLEGADA),
            new DiametroComercial(292.1, "11   1/2", PadraoDimensional.POLEGADA),
            new DiametroComercial(304.8, "12", PadraoDimensional.POLEGADA),
            new DiametroComercial(330.2, "13", PadraoDimensional.POLEGADA),
            new DiametroComercial(355.6, "14", PadraoDimensional.POLEGADA),
            new DiametroComercial(381, "15", PadraoDimensional.POLEGADA),
            new DiametroComercial(406.4, "16", PadraoDimensional.POLEGADA),
            new DiametroComercial(431.8, "17", PadraoDimensional.POLEGADA),
            new DiametroComercial(457.2, "18", PadraoDimensional.POLEGADA),
            new DiametroComercial(508, "20", PadraoDimensional.POLEGADA),
            new DiametroComercial(558.8, "22", PadraoDimensional.POLEGADA),
            new DiametroComercial(609.6, "24", PadraoDimensional.POLEGADA),
            new DiametroComercial(660.4, "26", PadraoDimensional.POLEGADA),
            new DiametroComercial(711.2, "28", PadraoDimensional.POLEGADA),
            new DiametroComercial(762.0, "30", PadraoDimensional.POLEGADA),
            new DiametroComercial(812.8, "32", PadraoDimensional.POLEGADA),
            new DiametroComercial(863.6, "34", PadraoDimensional.POLEGADA),
            new DiametroComercial(914.4, "36", PadraoDimensional.POLEGADA),
            new DiametroComercial(965.2, "38", PadraoDimensional.POLEGADA)
    };

    static {
        validarFaixasSobremetal();
        validarOrdemCrescente(DIAMETROS_POLEGADA);
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

    public static DiametroComercial[] diametrosPolegada() {
        return DIAMETROS_POLEGADA.clone();
    }

    // Mantido temporariamente para compatibilidade; futuramente usar diametrosPolegada().
    public static DiametroComercial[] diametrosComerciais() {
        return diametrosPolegada();
    }

    private static void validarOrdemCrescente(DiametroComercial[] diametros) {
        for (int indice = 1; indice < diametros.length; indice++) {
            double anterior = diametros[indice - 1].getMilimetros();
            double atual = diametros[indice].getMilimetros();
            if (atual <= anterior) {
                throw new IllegalStateException("Tabela de diâmetros fora de ordem.");
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
