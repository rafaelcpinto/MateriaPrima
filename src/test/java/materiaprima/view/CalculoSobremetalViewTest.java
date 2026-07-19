package materiaprima.view;

import java.awt.GraphicsEnvironment;
import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import materiaprima.modelo.PadraoDimensional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        javax.swing.JPanel painelPadrao = campo("painelPadraoDiametro");
        JTextField valor1 = campo("valor1");
        JTextField valor2 = campo("valor2");
        JTextField valor3 = campo("valor3");
        JTextField resultado1 = campo("resultado1");
        JTextField resultado2 = campo("resultado2");
        JTextField resultadoMassa = campo("resultadoMassa");
        JButton calcular = campo("botaoCalcular");
        JRadioButton perfilCilindrico = campo("perfilCilindrico");
        JRadioButton perfilRetangular = campo("perfilRetangular");

        SwingUtilities.invokeAndWait(() -> {
            assertEquals(PadraoDimensional.POLEGADA,
                    view.getPadraoDimensionalCilindrico());
            assertTrue(padraoPolegada.isSelected());
            assertFalse(padraoMetrico.isSelected());
            assertEquals(
                    padraoMetrico.getToolTipText(),
                    padraoPolegada.getToolTipText()
            );
            valor1.setText("46.01");
            valor2.setText("100");
            valor3.setText("0");
            calcular.doClick();
        });

        assertEquals("50,80 mm", resultado1.getText());
        assertEquals("2\" ", resultado2.getText());
        String massaPolegada = resultadoMassa.getText();

        SwingUtilities.invokeAndWait(() -> {
            padraoMetrico.doClick();
            assertEquals("46.01", valor1.getText());
            assertEquals("100", valor2.getText());
            assertEquals("0", valor3.getText());
            calcular.doClick();
        });

        assertEquals(PadraoDimensional.METRICO,
                view.getPadraoDimensionalCilindrico());
        assertEquals("51,00 mm", resultado1.getText());
        assertEquals("51 mm", resultado2.getText());
        assertNotEquals(massaPolegada, resultadoMassa.getText());

        SwingUtilities.invokeAndWait(perfilRetangular::doClick);
        assertFalse(painelPadrao.isVisible());
        assertEquals("46.01", valor1.getText());

        SwingUtilities.invokeAndWait(perfilCilindrico::doClick);
        assertTrue(painelPadrao.isVisible());
        assertTrue(padraoMetrico.isSelected());
        assertFalse(padraoPolegada.isSelected());
        assertEquals(PadraoDimensional.METRICO,
                view.getPadraoDimensionalCilindrico());
    }

    @SuppressWarnings("unchecked")
    private <T> T campo(String nome) throws Exception {
        Field campo = CalculoSobremetalView.class.getDeclaredField(nome);
        campo.setAccessible(true);
        return (T) campo.get(view);
    }
}
