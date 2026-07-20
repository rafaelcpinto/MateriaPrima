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
    void selecionaDimensoesRetangularesMetricas() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoRetangular metrico = calculo.calcularRetangular(
                45.1, 56.0, 94.7, false, PadraoDimensional.METRICO);
        ResultadoRetangular compativel = calculo.calcularRetangular(
                45.1, 56.0, 94.7, false);

        assertTrue(metrico.getLarguraMilimetros() == 51.0,
                "A largura métrica deveria ser arredondada para 51 mm");
        assertTrue(metrico.getAlturaMilimetros() == 61.0,
                "A altura métrica deveria ser arredondada para 61 mm");
        assertTrue(metrico.getComprimentoMilimetros() == 102.0,
                "O comprimento métrico deveria ser arredondado para 102 mm");
        assertTrue(metrico.getLarguraDescricao().endsWith("mm")
                        && metrico.getAlturaDescricao().endsWith("mm")
                        && metrico.getComprimentoDescricao().endsWith("mm"),
                "As descrições métricas deveriam terminar com mm");
        assertTrue(compativel.getLado1() == metrico.getLarguraMilimetros()
                        && compativel.getLado2() == metrico.getAlturaMilimetros()
                        && compativel.getLado3() == metrico.getComprimentoMilimetros(),
                "A sobrecarga antiga deveria delegar para o padrão métrico");

        double massaEsperada = 51.0 * 61.0 * 102.0
                * TabelasMateriaPrima.material(0).getDensidade();
        assertTrue(metrico.getMassa() == massaEsperada,
                "A massa deveria usar as dimensões métricas selecionadas");
    }

    @Test
    void selecionaDimensoesRetangularesEmPolegada() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoRetangular resultado = calculo.calcularRetangular(
                45.1, 56.0, 94.7, false, PadraoDimensional.POLEGADA);

        assertTrue(resultado.getLarguraMilimetros() == 50.8,
                "A largura deveria selecionar 2 polegadas");
        assertTrue(resultado.getAlturaMilimetros() == 61.9125,
                "A altura deveria selecionar 2 7/16 polegadas");
        assertTrue(resultado.getComprimentoMilimetros() == 104.775,
                "O comprimento deveria selecionar 4 1/8 polegadas");
        assertTrue("2".equals(resultado.getLarguraDescricao()),
                "A descrição da largura deveria ser imperial");
        assertTrue("2   7/16".equals(resultado.getAlturaDescricao()),
                "A descrição da altura deveria ser imperial");
        assertTrue("4   1/8".equals(resultado.getComprimentoDescricao()),
                "A descrição do comprimento deveria ser imperial");
    }

    @Test
    void reduzirSelecionaDimensaoImperialInferior() {
        ResultadoRetangular resultado = new CalcMateriaPrima().calcularRetangular(
                45.1, 56.0, 94.7, true, PadraoDimensional.POLEGADA);

        assertTrue(resultado.getLarguraMilimetros() == 47.625,
                "Reduzir deveria escolher a largura inferior");
        assertTrue(resultado.getAlturaMilimetros() == 58.7375,
                "Reduzir deveria escolher a altura inferior");
        assertTrue(resultado.getComprimentoMilimetros() == 98.425,
                "Reduzir deveria escolher o comprimento inferior");
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
    void reduzSobremetalCilindricoMetricoAntesDoArredondamento() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico normal = calculo.calcularCilindrico(
                40.0, 120.0, false, PadraoDimensional.METRICO);
        ResultadoCilindrico reduzido = calculo.calcularCilindrico(
                40.0, 120.0, true, PadraoDimensional.METRICO);

        assertTrue(normal.getDiametroMilimetros() == 44.0,
                "Sobremetal métrico integral deveria resultar em 44 mm");
        assertTrue(reduzido.getDiametroMilimetros() == 42.0,
                "Metade do sobremetal métrico deveria resultar em 42 mm");
        assertTrue(reduzido.getSobremetal() / 2.0 == 1.0,
                "Sobremetal por lado deveria derivar do diâmetro selecionado");
        assertTrue(reduzido.getComprimentoTotal() == 120.0,
                "O comprimento total deveria permanecer inalterado");
        double massa = Math.pow(42.0 / 2.0, 2) * Math.PI * 120.0
                * TabelasMateriaPrima.material(0).getDensidade();
        assertTrue(reduzido.getMassa() == massa,
                "A massa deveria usar o diâmetro métrico selecionado");

        ResultadoCilindrico arredondado = calculo.calcularCilindrico(
                40.1, 120.0, true, PadraoDimensional.METRICO);
        assertTrue(arredondado.getDiametroMilimetros() == 43.0,
                "Math.ceil deveria permanecer após a redução");
    }

    @Test
    void cilindricoImperialMantemSobremetalIntegralEAlternaLimite() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico superior = calculo.calcularCilindrico(
                40.0, 100.0, false, PadraoDimensional.POLEGADA);
        ResultadoCilindrico inferior = calculo.calcularCilindrico(
                40.0, 100.0, true, PadraoDimensional.POLEGADA);

        assertTrue(superior.getDiametroMilimetros() == 44.45,
                "Sem opção deveria selecionar o limite superior imperial");
        assertTrue(inferior.getDiametroMilimetros() == 41.275,
                "Com opção deveria selecionar o limite inferior do intervalo de 44 mm");
        double massa = Math.pow(41.275 / 2.0, 2) * Math.PI * 100.0
                * TabelasMateriaPrima.material(0).getDensidade();
        assertTrue(inferior.getMassa() == massa,
                "A massa deveria usar o diâmetro imperial selecionado");
    }

    @Test
    void retangularPreservaFatoresAcrescimoSelecaoIndependenteEMassa() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoRetangular metrico = calculo.calcularRetangular(
                40.1, 25.1, 63.1, true, PadraoDimensional.METRICO);
        assertTrue(metrico.getLarguraMilimetros() == 44.0,
                "Largura deveria usar 1 mm, metade do sobremetal e ceil");
        assertTrue(metrico.getAlturaMilimetros() == 28.0,
                "Altura deveria ser calculada independentemente");
        assertTrue(metrico.getComprimentoMilimetros() == 67.0,
                "Comprimento deveria ser calculado independentemente");
        assertTrue(metrico.getMassa() == 44.0 * 28.0 * 67.0
                        * TabelasMateriaPrima.material(0).getDensidade(),
                "Massa retangular deveria usar dimensões comerciais");

        ResultadoRetangular imperial = calculo.calcularRetangular(
                40.1, 25.1, 63.1, true, PadraoDimensional.POLEGADA);
        assertTrue(imperial.getLarguraMilimetros() == 41.275
                        && imperial.getAlturaMilimetros() == 26.9875
                        && imperial.getComprimentoMilimetros() == 63.5,
                "Cada dimensão imperial deveria usar fator 0,5 e seu intervalo inferior");
    }

    @Test
    void preservaCompatibilidadeDaSelecaoImperial() {
        CalcMateriaPrima calculo = new CalcMateriaPrima();
        ResultadoCilindrico antigo = calculo.calcularCilindrico(46.01, 100.0, false);
        ResultadoCilindrico novo = calculo.calcularCilindrico(
                46.01, 100.0, false, PadraoDimensional.POLEGADA);

        assertTrue(antigo.getDiametroMilimetros() == novo.getDiametroMilimetros(),
                "A sobrecarga antiga deve preservar a seleção imperial");
        assertTrue(antigo.getDescricaoDiametro().equals(novo.getDescricaoDiametro()),
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
        assertTrue(polegada.getComprimentoTotal() == 100.0,
                "O resultado deveria armazenar o comprimento usado na massa");
        double massaEsperada = Math.pow(polegada.getDiametroMilimetros() / 2.0, 2)
                * Math.PI
                * polegada.getComprimentoTotal()
                * TabelasMateriaPrima.material(0).getDensidade();
        assertTrue(polegada.getMassa() == massaEsperada,
                "A massa cilíndrica deveria usar o comprimento total do resultado");
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

        assertThrows(new Acao() {
            public void executar() {
                new CalcMateriaPrima().calcularRetangular(
                        50.0, 50.0, 50.0, false, null);
            }
        }, "Padrão dimensional retangular nulo deveria ser rejeitado");
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
        assertTrue(resultado.getDescricaoDiametro().endsWith("mm"),
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
