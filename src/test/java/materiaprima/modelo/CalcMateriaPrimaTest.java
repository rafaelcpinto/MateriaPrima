package materiaprima.modelo;

import org.junit.jupiter.api.Test;

public class CalcMateriaPrimaTest {

    @Test
    void aceitaLimitesCilindricosValidos() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico resultado = calculo.calcularCilindrico(0.0, 100.0, false);
        assertTrue(resultado.getMassa() >= 0, "Diâmetro zero deveria ser aceito");

        resultado = calculo.calcularCilindrico(483.0, 100.0, false);
        assertTrue(resultado.getDiametroMilimetros() == 508.0,
                "483 mm deveria selecionar matéria-prima de 508 mm");
    }

    @Test
    void rejeitaLimitesCilindricosInvalidos() {
        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularCilindrico(484.0, 100.0, false);
            }
        }, "484 mm deveria ser rejeitado");

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
        CalcMateriaPrima polipropileno = new CalcMateriaPrima(3);

        ResultadoCilindrico resultadoAco = aco.calcularCilindrico(100.0, 200.0, false);
        ResultadoCilindrico resultadoPolipropileno =
                polipropileno.calcularCilindrico(100.0, 200.0, false);

        assertTrue(resultadoAco.getMassa() > resultadoPolipropileno.getMassa(),
                "Materiais com densidades diferentes deveriam produzir massas diferentes");

        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima(7);
            }
        }, "Posição de material inexistente deveria ser rejeitada");
    }

    @Test
    void respeitaLimitesDasFaixasDeSobremetal() {
        FaixaSobremetal faixa = new FaixaSobremetal(0, 25, 2.6);

        assertTrue(faixa.contem(0), "O limite mínimo deveria pertencer à faixa");
        assertTrue(faixa.contem(24.999), "Um valor abaixo do máximo deveria pertencer à faixa");
        assertTrue(!faixa.contem(25), "O limite máximo deveria pertencer à próxima faixa");
        assertTrue(faixa.getSobremetal() == 2.6, "A faixa deveria manter seu sobremetal");
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
