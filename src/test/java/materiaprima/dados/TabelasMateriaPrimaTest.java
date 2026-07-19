package materiaprima.dados;

import materiaprima.modelo.DimensaoComercial;
import materiaprima.modelo.PadraoDimensional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TabelasMateriaPrimaTest {

    @Test
    void dimensoesComerciaisSaoValidasEEstaoEmOrdemCrescente() {
        DimensaoComercial[] diametros = TabelasMateriaPrima.dimensoesPolegada();

        assertTrue(diametros.length > 0);
        assertEquals(100, diametros.length);
        for (int indice = 0; indice < diametros.length; indice++) {
            assertNotNull(diametros[indice]);
            assertFalse(diametros[indice].getDescricao().trim().isEmpty());
            assertEquals(PadraoDimensional.POLEGADA, diametros[indice].getPadrao());
            if (indice > 0) {
                assertFalse(diametros[indice].getMilimetros()
                        <= diametros[indice - 1].getMilimetros());
            }
        }
    }

    @Test
    void ultimaDimensaoComercialEhTrintaEOitoPolegadas() {
        DimensaoComercial[] diametros = TabelasMateriaPrima.dimensoesPolegada();

        DimensaoComercial ultimo = diametros[diametros.length - 1];

        assertEquals(965.2, ultimo.getMilimetros(), 0.0001);
        assertEquals("38", ultimo.getDescricao());
        assertEquals(PadraoDimensional.POLEGADA, ultimo.getPadrao());
    }

    @Test
    void retornaCopiaSemExporAReferenciaInterna() {
        DimensaoComercial[] primeiraCopia = TabelasMateriaPrima.dimensoesPolegada();
        DimensaoComercial primeiroOriginal = primeiraCopia[0];

        primeiraCopia[0] = new DimensaoComercial(
                1.0, "alterado", PadraoDimensional.POLEGADA);
        DimensaoComercial[] segundaCopia = TabelasMateriaPrima.dimensoesPolegada();

        assertNotSame(primeiraCopia, segundaCopia);
        assertEquals(primeiroOriginal.getMilimetros(), segundaCopia[0].getMilimetros());
        assertEquals(primeiroOriginal.getDescricao(), segundaCopia[0].getDescricao());
    }

    @Test
    void metodoCompativelRetornaOsMesmosDadosSemDuplicarAReferencia() {
        DimensaoComercial[] dimensoes = TabelasMateriaPrima.dimensoesPolegada();
        DimensaoComercial[] diametros = TabelasMateriaPrima.diametrosPolegada();

        assertNotSame(dimensoes, diametros);
        assertEquals(dimensoes.length, diametros.length);
        assertEquals(dimensoes[0].getMilimetros(), diametros[0].getMilimetros());
        assertEquals(dimensoes[dimensoes.length - 1].getDescricao(),
                diametros[diametros.length - 1].getDescricao());
    }
}
