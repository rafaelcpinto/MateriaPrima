package materiaprima.dados;

import materiaprima.modelo.FaixaSobremetal;
import materiaprima.modelo.Material;

public final class TabelasMateriaPrima {

    private static final Material[] MATERIAIS = {
        new Material("Aço", 7.85/1000000),
        new Material("Latão", 8.50/1000000),
        new Material("Cobre", 8.93/1000000),
        new Material("Polipropileno", 0.93/1000000),
        new Material("Nylon", 1.14/1000000),
        new Material("Alumínio", 2.70/1000000),
        new Material("Bronze Fosfórico", 8.80/1000000)
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

    private static final double[] DIAMETROS_MM = {
        2.38125, 3.175, 3.96875, 4.7625, 5.55625, 6.35, 7.14375, 7.9375, 9.525,
        11.1125, 12.7, 14.2875, 15.875, 17.4625, 19.05, 20.6375, 22.225, 23.8125,
        25.4, 26.9875, 28.575, 30.1625, 31.75, 33.3375, 34.925, 36.5125, 38.1,
        39.6875, 41.275, 44.45, 47.625, 50.8, 52.3875, 53.975, 55.5625, 57.15,
        58.7375, 60.325, 61.9125, 63.5, 66.675, 69.85, 73.025, 76.2, 79.375,
        82.55, 85.725, 88.9, 92.075, 95.25, 98.425, 101.6, 104.775, 107.95,
        111.125, 114.3, 117.475, 120.65, 123.825, 127, 133.35, 139.7, 146.05,
        152.4, 158.75, 165.1, 171.45, 177.8, 184.15, 190.5, 196.85, 203.2,
        209.55, 215.9, 222.25, 228.6, 234.95, 241.3, 247.65, 254, 266.7, 279.4,
        292.1, 304.8, 330.2, 355.6, 381, 406.4, 431.8, 457.2, 508
    };

    private static final String[] DIAMETROS_POLEGADA = {
        "3/32", "1/8", "5/32", "3/16", "7/32", "1/4", "9/32", "5/16", "3/8",
        "7/16", "1/2", "9/16", "5/8", "11/16", "3/4", "13/16", "7/8", "15/16",
        "1", "1   1/16", "1   1/8", "1   3/16", "1   1/4", "1   5/16", "1   3/8",
        "1   7/16", "1   1/2", "1   9/16", "1   5/8", "1   3/4", "1   7/8", "2",
        "2   1/16", "2   1/8", "2   3/16", "2   1/4", "2   5/16", "2   3/8",
        "2   7/16", "2   1/2", "2   5/8", "2   3/4", "2   7/8", "3", "3   1/8",
        "3   1/4", "3   3/8", "3   1/2", "3   5/8", "3   3/4", "3   7/8", "4",
        "4   1/8", "4   1/4", "4   3/8", "4   1/2", "4   5/8", "4   3/4",
        "4   7/8", "5", "5   1/4", "5   1/2", "5   3/4", "6", "6   1/4",
        "6   1/2", "6   3/4", "7", "7   1/4", "7   1/2", "7   3/4", "8",
        "8   1/4", "8   1/2", "8   3/4", "9", "9   1/4", "9   1/2", "9   3/4",
        "10", "10   1/2", "11", "11   1/2", "12", "13", "14", "15", "16", "17",
        "18", "20"
    };

    static {
        if (DIAMETROS_MM.length != DIAMETROS_POLEGADA.length) {
            throw new IllegalStateException("Tabela de diâmetros inconsistente");
        }
        validarFaixasSobremetal();
        validarOrdemCrescente(DIAMETROS_MM, "diâmetros");
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

    public static double[] diametrosMm() {
        return DIAMETROS_MM.clone();
    }

    public static String[] diametrosPolegada() {
        return DIAMETROS_POLEGADA.clone();
    }

    private static void validarOrdemCrescente(double[] valores, String nome) {
        for (int indice = 1; indice < valores.length; indice++) {
            if (valores[indice] <= valores[indice - 1]) {
                throw new IllegalStateException("Tabela fora de ordem: " + nome);
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
