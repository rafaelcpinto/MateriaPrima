package materiaprima.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiametroComercialTest {

    @Test
    void criaObjetoValido() {
        DiametroComercial diametro = new DiametroComercial(50.8, "2");

        assertEquals(50.8, diametro.getMilimetros());
        assertEquals("2", diametro.getPolegadas());
    }

    @Test
    void rejeitaMilimetrosIgualAZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(0, "1"));
    }

    @Test
    void rejeitaMilimetrosNegativos() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(-1, "1"));
    }

    @Test
    void rejeitaNaN() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(Double.NaN, "1"));
    }

    @Test
    void rejeitaInfinito() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(Double.POSITIVE_INFINITY, "1"));
    }

    @Test
    void rejeitaDescricaoNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(25.4, null));
    }

    @Test
    void rejeitaDescricaoVazia() {
        assertThrows(IllegalArgumentException.class,
                () -> new DiametroComercial(25.4, "   "));
    }
}
