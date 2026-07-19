package materiaprima.controller;

import org.junit.jupiter.api.Test;

public class ValidadorEFormatadorTest {

    @Test
    void testaLeituraEValidacao() {
        ValidadorCalculo validador = new ValidadorCalculo();

        assertTrue(validador.lerValor("12.5") == 12.5, "Número decimal deveria ser lido");
        assertTrue(validador.lerValor("") == null, "Campo vazio deveria ser rejeitado");
        assertTrue(validador.lerValor("NaN") == null, "NaN deveria ser rejeitado");
        assertTrue(validador.valoresCilindricosValidos(483.0, 3500.0, 0.0),
                "Limites cilíndricos deveriam ser aceitos");
        assertTrue(!validador.valoresCilindricosValidos(484.0, 100.0, 0.0),
                "Diâmetro acima do limite deveria ser rejeitado");
        assertTrue(validador.valoresRetangularesValidos(0.0, 500.0, 999.999),
                "Lados dentro dos limites deveriam ser aceitos");
        assertTrue(!validador.valoresRetangularesValidos(0.0, 500.0, 1000.0),
                "Lado no limite máximo deveria ser rejeitado");
    }

    @Test
    void testaFormatacao() {
        FormatadorResultado formatador = new FormatadorResultado();

        assertTrue(formatador.milimetros(10).endsWith(" mm"), "Milímetros deveriam ter unidade");
        assertTrue(formatador.quilogramas(10).endsWith(" kg"), "Massa deveria ter unidade");
        assertTrue("1/2\" ".equals(formatador.polegadas("1/2")),
                "Polegadas deveriam receber aspas");
    }

    private static void assertTrue(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }
}
