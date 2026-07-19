package materiaprima.aplicacao;

import javax.swing.UIManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemaAplicacaoTest {

    @Test
    void instalaFlatLafSemImpedirInicializacao() {
        assertTrue(TemaAplicacao.instalar());
        assertNotNull(UIManager.getLookAndFeel());
        assertTrue(UIManager.getLookAndFeel().getName().contains("FlatLaf"));
        assertNotNull(UIManager.get("Button.default.background"));
        assertNotNull(UIManager.get("Button.default.foreground"));
    }
}
