package materiaprima.view;

import java.awt.GraphicsEnvironment;
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import materiaprima.modelo.PadraoDimensional;
import materiaprima.view.components.CardPanel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculoSobremetalViewTest {

    private CalculoSobremetalView view;

    @AfterEach
    void fecharJanela() throws Exception {
        if (view != null) {
            SwingUtilities.invokeAndWait(() -> view.dispose());
        }
    }

    @Test
    void enviaPadraoSelecionadoSemAlterarEntradas() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());

        SwingUtilities.invokeAndWait(() -> view = new CalculoSobremetalView());

        JRadioButton padraoMetrico = campo("padraoMetrico");
        JRadioButton padraoPolegada = campo("padraoPolegada");
        javax.swing.JPanel opcoesPadrao = campo("opcoesPadraoDiametro");
        JTextField valor1 = campo("valor1");
        JTextField valor2 = campo("valor2");
        JTextField valor3 = campo("valor3");
        JTextField resultado1 = campo("resultado1");
        JTextField resultado2 = campo("resultado2");
        JTextField resultado3 = campo("resultado3");
        JTextField resultadoMassa = campo("resultadoMassa");
        JLabel equivalenteDiametro = campo("equivalenteDiametro");
        JLabel equivalenteResultado2 = campo("equivalenteResultado2");
        JLabel equivalenteResultado3 = campo("equivalenteResultado3");
        JButton calcular = campo("botaoCalcular");
        JButton limpar = campo("botaoLimpar");
        JRadioButton perfilCilindrico = campo("perfilCilindrico");
        JRadioButton perfilRetangular = campo("perfilRetangular");
        JLabel ajudaValor1 = campo("ajudaValor1");

        SwingUtilities.invokeAndWait(() -> {
            assertEquals(PadraoDimensional.POLEGADA,
                    view.getPadraoDimensional());
            assertEquals(view.getPadraoDimensional(),
                    view.getPadraoDimensionalCilindrico());
            assertTrue(padraoPolegada.isSelected());
            assertFalse(padraoMetrico.isSelected());
            assertEquals(
                    padraoMetrico.getToolTipText(),
                    padraoPolegada.getToolTipText()
            );
            assertTrue(ajudaValor1.getToolTipText().contains("919,2 mm"));
            assertFalse(ajudaValor1.getToolTipText().contains("38\""));
            valor1.setText("46.01");
            valor2.setText("100");
            valor3.setText("20");
            calcular.doClick();
        });

        assertEquals("2\"", resultado1.getText());
        assertFalse(resultado1.getText().contains("50,80 mm"));
        assertEquals("(50,80 mm)", equivalenteDiametro.getText());
        assertTrue(equivalenteDiametro.isVisible());
        assertEquals("120,00 mm", resultado2.getText());
        String massaPolegada = resultadoMassa.getText();

        SwingUtilities.invokeAndWait(() -> {
            limpar.doClick();
            assertFalse(equivalenteDiametro.isVisible());
            valor1.setText("46.01");
            valor2.setText("100");
            valor3.setText("20");
            padraoMetrico.doClick();
            assertEquals("46.01", valor1.getText());
            assertEquals("100", valor2.getText());
            assertEquals("20", valor3.getText());
            calcular.doClick();
        });

        assertEquals(PadraoDimensional.METRICO,
                view.getPadraoDimensional());
        assertEquals("51 mm", resultado1.getText());
        assertFalse(equivalenteDiametro.isVisible());
        assertEquals("120,00 mm", resultado2.getText());
        assertNotEquals(massaPolegada, resultadoMassa.getText());

        SwingUtilities.invokeAndWait(perfilRetangular::doClick);
        assertTrue(opcoesPadrao.isVisible());
        assertFalse(equivalenteDiametro.isVisible());
        assertEquals("46.01", valor1.getText());

        SwingUtilities.invokeAndWait(calcular::doClick);
        assertEquals("52 mm", resultado1.getText());
        assertEquals("108 mm", resultado2.getText());
        assertEquals("24 mm", resultado3.getText());
        assertFalse(equivalenteDiametro.isVisible());
        assertFalse(equivalenteResultado2.isVisible());
        assertFalse(equivalenteResultado3.isVisible());

        SwingUtilities.invokeAndWait(() -> {
            padraoPolegada.doClick();
            calcular.doClick();
        });
        assertEquals("2   1/16\"", resultado1.getText());
        assertEquals("4   3/8\"", resultado2.getText());
        assertEquals("15/16\"", resultado3.getText());
        assertEquals("(52,39 mm)", equivalenteDiametro.getText());
        assertEquals("(111,12 mm)", equivalenteResultado2.getText());
        assertEquals("(23,81 mm)", equivalenteResultado3.getText());
        assertTrue(equivalenteDiametro.isVisible());
        assertTrue(equivalenteResultado2.isVisible());
        assertTrue(equivalenteResultado3.isVisible());

        SwingUtilities.invokeAndWait(perfilCilindrico::doClick);
        assertTrue(opcoesPadrao.isVisible());
        assertFalse(padraoMetrico.isSelected());
        assertTrue(padraoPolegada.isSelected());
        assertEquals(PadraoDimensional.POLEGADA, view.getPadraoDimensional());
    }

    @Test
    void possuiSomenteTresCardsPrincipaisEQuatroLinhasDeResultado() throws Exception {
        Assumptions.assumeFalse(GraphicsEnvironment.isHeadless());
        SwingUtilities.invokeAndWait(() -> view = new CalculoSobremetalView());

        List<CardPanel> cards = componentes(view.getContentPane(), CardPanel.class);
        assertEquals(3, cards.size());
        Set<String> titulos = new HashSet<String>();
        for (CardPanel card : cards) {
            titulos.add((String) card.getClientProperty("card.titulo"));
        }
        assertEquals(new HashSet<String>(Arrays.asList(
                "Dados de entrada", "Resultado", "Visualização dinâmica")), titulos);

        CardPanel resultado = card(cards, "card.result");
        assertTrue(componentes(resultado, CardPanel.class).isEmpty());
        assertEquals(4, componentesComNome(resultado, "linhaResultado").size());
        List<JButton> botoesResultado = componentes(resultado, JButton.class);
        assertEquals(4, botoesResultado.size());
        for (JButton copiar : botoesResultado) {
            assertEquals("Copiar", copiar.getToolTipText());
            assertTrue(copiar.isFocusable());
            assertNotNull(copiar.getIcon());
        }

        CardPanel entrada = card(cards, "card.input");
        JTextField valor1 = campo("valor1");
        assertTrue(javax.swing.SwingUtilities.isDescendingFrom(valor1, entrada));

        JButton calcular = campo("botaoCalcular");
        assertEquals("Calcular", calcular.getText());
        assertNotNull(calcular.getIcon());
        assertTrue(calcular.getFont().isBold());
        assertEquals("roundRect", calcular.getClientProperty("JButton.buttonType"));
        assertSame(calcular, view.getRootPane().getDefaultButton());

        javax.swing.JComboBox<?> material = campo("material");
        JTextField valor2 = campo("valor2");
        JTextField valor3 = campo("valor3");
        assertEquals(valor1.getWidth(), material.getWidth());
        assertEquals(valor1.getWidth(), valor2.getWidth());
        assertEquals(valor1.getWidth(), valor3.getWidth());

        int larguraCardEntrada = entrada.getWidth();
        int larguraCampoEntrada = valor1.getWidth();
        JRadioButton perfilRetangular = campo("perfilRetangular");
        SwingUtilities.invokeAndWait(perfilRetangular::doClick);
        assertEquals(larguraCardEntrada, entrada.getWidth());
        assertEquals(larguraCampoEntrada, valor1.getWidth());
        assertEquals(valor1.getWidth(), material.getWidth());
    }

    private CardPanel card(List<CardPanel> cards, String nome) {
        for (CardPanel card : cards) {
            if (nome.equals(card.getName())) {
                return card;
            }
        }
        throw new AssertionError("Card não encontrado: " + nome);
    }

    private List<JPanel> componentesComNome(Container raiz, String nome) {
        List<JPanel> encontrados = new ArrayList<JPanel>();
        for (JPanel painel : componentes(raiz, JPanel.class)) {
            if (nome.equals(painel.getName())) {
                encontrados.add(painel);
            }
        }
        return encontrados;
    }

    private <T extends Component> List<T> componentes(Container raiz, Class<T> tipo) {
        List<T> encontrados = new ArrayList<T>();
        for (Component componente : raiz.getComponents()) {
            if (tipo.isInstance(componente)) {
                encontrados.add(tipo.cast(componente));
            }
            if (componente instanceof Container) {
                encontrados.addAll(componentes((Container) componente, tipo));
            }
        }
        return encontrados;
    }

    @SuppressWarnings("unchecked")
    private <T> T campo(String nome) throws Exception {
        Field campo = CalculoSobremetalView.class.getDeclaredField(nome);
        campo.setAccessible(true);
        return (T) campo.get(view);
    }
}
