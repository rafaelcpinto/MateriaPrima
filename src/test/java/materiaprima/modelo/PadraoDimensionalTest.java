package materiaprima.modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PadraoDimensionalTest {

    @Test
    void apresentaDescricoesDosPadroes() {
        assertEquals("Milímetro", PadraoDimensional.METRICO.getDescricao());
        assertEquals("Polegada", PadraoDimensional.POLEGADA.getDescricao());
        assertEquals("Milímetro", PadraoDimensional.METRICO.toString());
        assertEquals("Polegada", PadraoDimensional.POLEGADA.toString());
    }
}
