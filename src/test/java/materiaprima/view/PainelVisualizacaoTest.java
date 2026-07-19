package materiaprima.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PainelVisualizacaoTest {

    @Test
    void atualizaLimpaERenderizaOsDoisPerfis() {
        assertEquals(
                "Esquema proporcional com diferenças ampliadas para visualização",
                PainelVisualizacao.TEXTO_OBSERVACAO);
        PainelVisualizacao painel = new PainelVisualizacao();
        assertDoesNotThrow(() -> {
            painel.mostrarCilindrico(
                    "50,80 mm", "48,00 mm", "1,40 mm", "100,00 mm", "120,00 mm");
            renderizar(painel, 390, 260);
            renderizar(painel, 520, 330);
            renderizar(painel, 800, 500);
            painel.mostrarRetangular(
                    "52,00 mm", "108,00 mm", "24,00 mm",
                    "46,01 mm", "100,00 mm", "20,00 mm");
            renderizar(painel, 390, 260);
            renderizar(painel, 800, 500);
            painel.limpar(true);
            renderizar(painel, 520, 330);
        });
    }

    private void renderizar(PainelVisualizacao painel, int largura, int altura) {
        painel.setSize(largura, altura);
        BufferedImage imagem = new BufferedImage(
                largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = imagem.createGraphics();
        painel.paint(graphics);
        graphics.dispose();
    }
}
