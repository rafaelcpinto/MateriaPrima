package materiaprima.controller;

import org.junit.jupiter.api.Test;

public class ValidadorEFormatadorTest {

    @Test
    void testaLeituraEValidacao() {
        ValidadorCalculo validador = new ValidadorCalculo();

        assertTrue(validador.lerValor("12.5") == 12.5, "Número decimal deveria ser lido");
        assertTrue(validador.lerValor("") == null, "Campo vazio deveria ser rejeitado");
        assertTrue(validador.lerValor("NaN") == null, "NaN deveria ser rejeitado");
        assertTrue(validador.valoresCilindricosValidos(919.1, 3500.0, 0.0),
                "Diâmetro atendido pela nova tabela deveria ser aceito");
        assertTrue(!validador.valoresCilindricosValidos(919.2, 100.0, 0.0),
                "Diâmetro sem margem para o próximo comercial deveria ser rejeitado");
        assertTrue(validador.limiteDiametroCilindrico() == 919.2,
                "A ajuda deve usar o mesmo limite da validação cilíndrica");
        assertTrue(validador.valoresRetangularesValidos(0.0, 500.0, 999.999),
                "Lados dentro dos limites deveriam ser aceitos");
        assertTrue(!validador.valoresRetangularesValidos(0.0, 500.0, 1000.0),
                "Lado no limite máximo deveria ser rejeitado");
    }

    @Test
    void testaFormatacao() {
        FormatadorResultado formatador = new FormatadorResultado();

        assertTrue("10,00 mm".equals(formatador.milimetros(10)),
                "Milímetros deveriam ter duas casas e unidade");
        assertTrue("10,00 kg".equals(formatador.quilogramas(10)),
                "Massa deveria ter duas casas e unidade");
        assertTrue("508,00 mm".equals(formatador.diametroMilimetros(508)),
                "Diâmetro deveria ter duas casas e unidade");
        assertTrue("1/2\" ".equals(formatador.polegadas("1/2")),
                "Polegadas deveriam receber aspas");
    }

    private static void assertTrue(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
