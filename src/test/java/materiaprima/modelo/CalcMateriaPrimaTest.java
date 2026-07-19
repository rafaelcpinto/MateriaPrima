package materiaprima.modelo;

import materiaprima.dados.TabelasMateriaPrima;
import org.junit.jupiter.api.Test;

public class CalcMateriaPrimaTest {

    @Test
    void aceitaLimitesCilindricosValidos() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico resultado = calculo.calcularCilindrico(0.0, 100.0, false);
        assertTrue(resultado.getMassa() >= 0, "Diâmetro zero deveria ser aceito");

        resultado = calculo.calcularCilindrico(919.1, 100.0, false);
        assertTrue(resultado.getDiametroMilimetros() == 965.2,
                "919,1 mm deveria selecionar matéria-prima de 965,2 mm");
    }

    @Test
    void rejeitaLimitesCilindricosInvalidos() {
        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularCilindrico(919.2, 100.0, false);
            }
        }, "919,2 mm deveria ser rejeitado por não haver próximo diâmetro comercial");

        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularCilindrico(-1.0, 100.0, false);
            }
        }, "Diâmetro negativo deveria ser rejeitado");
    }

    @Test
    void aceitaLimitesRetangularesValidos() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoRetangular resultado = calculo.calcularRetangular(0.0, 500.0, 999.999, false);
        assertTrue(resultado.getMassa() >= 0, "Lados válidos deveriam ser aceitos");
    }

    @Test
    void rejeitaValoresInvalidos() {
        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularRetangular(-1.0, 10.0, 10.0, false);
            }
        }, "Lado negativo deveria ser rejeitado");

        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularRetangular(Double.NaN, 10.0, 10.0, false);
            }
        }, "NaN deveria ser rejeitado");

        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularRetangular(Double.POSITIVE_INFINITY, 10.0, 10.0, false);
            }
        }, "Infinito deveria ser rejeitado");
    }

    @Test
    void mantemCalculosIndependentesPorMaterial() {
        CalcMateriaPrima aco = new CalcMateriaPrima(0);
        CalcMateriaPrima polipropileno = new CalcMateriaPrima(7);

        ResultadoCilindrico resultadoAco = aco.calcularCilindrico(100.0, 200.0, false);
        ResultadoCilindrico resultadoPolipropileno =
                polipropileno.calcularCilindrico(100.0, 200.0, false);

        assertTrue(resultadoAco.getMassa() > resultadoPolipropileno.getMassa(),
                "Materiais com densidades diferentes deveriam produzir massas diferentes");

        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima(TabelasMateriaPrima.materiais().length);
            }
        }, "Posição de material inexistente deveria ser rejeitada");
    }

    @Test
    void selecionaProximoMilimetroInteiroNoPadraoMetrico() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();

        assertDiametroMetrico(calculo, 46.0, 50.0);
        assertDiametroMetrico(calculo, 46.01, 51.0);
        assertDiametroMetrico(calculo, 46.8, 51.0);
        assertDiametroMetrico(calculo, 47.0, 51.0);
    }

    @Test
    void preservaCompatibilidadeDaSelecaoImperial() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico antigo = calculo.calcularCilindrico(46.01, 100.0, false);
        ResultadoCilindrico novo = calculo.calcularCilindrico(
                46.01, 100.0, false, PadraoDimensional.POLEGADA);

        assertTrue(antigo.getDiametroMilimetros() == novo.getDiametroMilimetros(),
                "A sobrecarga antiga deve preservar a seleção imperial");
        assertTrue(antigo.getDiametroPolegadas().equals(novo.getDiametroPolegadas()),
                "A descrição imperial deve permanecer igual");
        assertTrue(antigo.getMassa() == novo.getMassa(),
                "A massa imperial deve permanecer igual");
    }

    @Test
    void usaDiametroSelecionadoNoCalculoDaMassa() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico metrico = calculo.calcularCilindrico(
                46.01, 100.0, false, PadraoDimensional.METRICO);
        ResultadoCilindrico polegada = calculo.calcularCilindrico(
                46.01, 100.0, false, PadraoDimensional.POLEGADA);

        assertTrue(metrico.getDiametroMilimetros() == 51.0,
                "O padrão métrico deveria selecionar 51 mm");
        assertTrue(polegada.getDiametroMilimetros() == 50.8,
                "O padrão imperial deveria selecionar 2 polegadas");
        assertTrue(metrico.getMassa() != polegada.getMassa(),
                "Diâmetros selecionados diferentes devem produzir massas diferentes");
    }

    @Test
    void rejeitaPadraoDimensionalNulo() {
        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularCilindrico(50.0, 100.0, false, null);
            }
        }, "Padrão dimensional nulo deveria ser rejeitado");
    }

    @Test
    void respeitaLimitesDasFaixasDeSobremetal() {
        FaixaSobremetal faixa = new FaixaSobremetal(0, 25, 2.6);

        assertTrue(faixa.contem(0), "O limite mínimo deveria pertencer à faixa");
        assertTrue(faixa.contem(24.999), "Um valor abaixo do máximo deveria pertencer à faixa");
        assertTrue(!faixa.contem(25), "O limite máximo deveria pertencer à próxima faixa");
        assertTrue(faixa.getSobremetal() == 2.6, "A faixa deveria manter seu sobremetal");
    }

    private static void assertDiametroMetrico(
            CalcMateriaPrima calculo,
            double diametroAcabado,
            double diametroEsperado
    ) {
        ResultadoCilindrico resultado = calculo.calcularCilindrico(
                diametroAcabado, 100.0, false, PadraoDimensional.METRICO);
        assertTrue(resultado.getDiametroMilimetros() == diametroEsperado,
                "O diâmetro métrico deveria ser arredondado para o próximo milímetro");
        assertTrue(resultado.getDiametroPolegadas().endsWith("mm"),
                "A descrição métrica deveria terminar com mm");
    }

    private static void assertTrue(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }

    private static void assertThrows(Acao acao, String mensagem) {
        try {
            acao.executar();
            throw new AssertionError(mensagem);
        } catch (IllegalArgumentException esperado) {
            // comportamento esperado
        }
    }

    private interface Acao {
        void executar();
    }
}
