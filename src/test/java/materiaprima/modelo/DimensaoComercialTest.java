package materiaprima.modelo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DimensaoComercialTest {

    @Test
    void criaObjetoImperialValido() {
        DimensaoComercial diametro = new DimensaoComercial(
                50.8, " 2 ", PadraoDimensional.POLEGADA);

        assertEquals(50.8, diametro.getMilimetros());
        assertEquals("2", diametro.getDescricao());
        assertEquals(PadraoDimensional.POLEGADA, diametro.getPadrao());
    }

    @Test
    void criaObjetoMetricoValido() {
        DimensaoComercial diametro = new DimensaoComercial(
                51.0, "51 mm", PadraoDimensional.METRICO);

        assertEquals(51.0, diametro.getMilimetros());
        assertEquals("51 mm", diametro.getDescricao());
        assertEquals(PadraoDimensional.METRICO, diametro.getPadrao());
    }

    @Test
    void rejeitaMilimetrosIgualAZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(0, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaMilimetrosNegativos() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(-1, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaNaN() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(
                        Double.NaN, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaInfinito() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(
                        Double.POSITIVE_INFINITY, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaDescricaoNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(25.4, null, PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaDescricaoVazia() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(25.4, "   ", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaPadraoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new DimensaoComercial(25.4, "1", null));
    }

    @Test
    void estruturaEhImutavel() {
        assertTrue(Modifier.isFinal(DimensaoComercial.class.getModifiers()));
        for (Field campo : DimensaoComercial.class.getDeclaredFields()) {
            assertTrue(Modifier.isPrivate(campo.getModifiers()));
            assertTrue(Modifier.isFinal(campo.getModifiers()));
        }
    }
}
