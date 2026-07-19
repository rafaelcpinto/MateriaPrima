package materiaprima.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiametroComercialTest {

    @Test
    void criaObjetoImperialValido() {
        DiametroComercial diametro = new DiametroComercial(
                50.8, " 2 ", PadraoDimensional.POLEGADA);

        assertEquals(50.8, diametro.getMilimetros());
        assertEquals("2", diametro.getDescricao());
        assertEquals(PadraoDimensional.POLEGADA, diametro.getPadrao());
    }

    @Test
    void criaObjetoMetricoValido() {
        DiametroComercial diametro = new DiametroComercial(
                51.0, "51 mm", PadraoDimensional.METRICO);

        assertEquals(51.0, diametro.getMilimetros());
        assertEquals("51 mm", diametro.getDescricao());
        assertEquals(PadraoDimensional.METRICO, diametro.getPadrao());
    }

    @Test
    void rejeitaMilimetrosIgualAZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(0, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaMilimetrosNegativos() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(-1, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaNaN() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(
                        Double.NaN, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaInfinito() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(
                        Double.POSITIVE_INFINITY, "1", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaDescricaoNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(25.4, null, PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaDescricaoVazia() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(25.4, "   ", PadraoDimensional.POLEGADA));
    }

    @Test
    void rejeitaPadraoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(25.4, "1", null));
    }
}
