package materiaprima.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import materiaprima.dados.TabelasMateriaPrima;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SobreDialogTest {
    private SobreDialog dialogo;

    @AfterEach
    void fechar() throws Exception {
        if (dialogo != null) SwingUtilities.invokeAndWait(dialogo::dispose);
    }

    @Test
    void apresentaAbasTabelasDinamicasEConteudoTecnico() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> dialogo = new SobreDialog(null));

        JTabbedPane abas = nome(dialogo, JTabbedPane.class, "abasSobre");
        assertEquals(4, abas.getTabCount());
        assertEquals("Sobre", abas.getTitleAt(0));
        assertEquals("Materiais", abas.getTitleAt(1));
        assertEquals("Tabelas", abas.getTitleAt(2));
        assertEquals("Cálculo", abas.getTitleAt(3));

        JTable materiais = nome(dialogo, JTable.class, "tabelaMateriais");
        JTable faixas = nome(dialogo, JTable.class, "tabelaFaixas");
        JTable dimensoes = nome(dialogo, JTable.class, "tabelaDimensoes");
        assertEquals(TabelasMateriaPrima.materiais().length, materiais.getRowCount());
        assertEquals(TabelasMateriaPrima.faixasSobremetal().length, faixas.getRowCount());
        assertEquals(TabelasMateriaPrima.dimensoesPolegada().length, dimensoes.getRowCount());
        assertFalse(materiais.isCellEditable(0, 0));
        assertFalse(faixas.isCellEditable(0, 0));
        assertFalse(dimensoes.isCellEditable(0, 0));

        String sobre = textoComponentes((Container) abas.getComponentAt(0));
        assertTrue(sobre.contains("Cálculo de Matéria-Prima"));
        assertTrue(sobre.contains("Desenvolvido por Rafael C. Pinto"));
        JEditorPane calculo = nome(dialogo, JEditorPane.class, "textoCalculo");
        String tecnico = calculo.getDocument().getText(
                0, calculo.getDocument().getLength());
        assertTrue(tecnico.contains("Faixas de sobremetal"));
        assertTrue(tecnico.contains("Perfil cilíndrico"));
        assertTrue(tecnico.contains("Cálculo da massa"));
    }

    @Test
    void botaoFecharDescartaSomenteODialogo() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> {
            dialogo = new SobreDialog(null);
            dialogo.addNotify();
            assertTrue(dialogo.isDisplayable());
            nome(dialogo, JButton.class, "botaoFechar").doClick();
            assertFalse(dialogo.isDisplayable());
        });
    }

    private static String textoComponentes(Container raiz) {
        StringBuilder texto = new StringBuilder();
        for (Component componente : raiz.getComponents()) {
            if (componente instanceof javax.swing.JLabel) {
                texto.append(((javax.swing.JLabel) componente).getText());
            }
            if (componente instanceof Container) texto.append(textoComponentes((Container) componente));
        }
        return texto.toString();
    }

    private static <T extends Component> T nome(Container raiz, Class<T> tipo, String nome) {
        for (T componente : componentes(raiz, tipo)) {
            if (nome.equals(componente.getName())) return componente;
        }
        throw new AssertionError("Componente não encontrado: " + nome);
    }

    private static <T extends Component> List<T> componentes(Container raiz, Class<T> tipo) {
        List<T> encontrados = new ArrayList<T>();
        for (Component componente : raiz.getComponents()) {
            if (tipo.isInstance(componente)) encontrados.add(tipo.cast(componente));
            if (componente instanceof Container) encontrados.addAll(componentes((Container) componente, tipo));
        }
        return encontrados;
    }
}
