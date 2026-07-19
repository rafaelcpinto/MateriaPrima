package materiaprima.dados;

import materiaprima.modelo.DiametroComercial;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TabelasMateriaPrimaTest {

    @Test
    void diametrosComerciaisSaoValidosEEstaoEmOrdemCrescente() {
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosComerciais();

        assertTrue(diametros.length > 0);
        for (int indice = 0; indice < diametros.length; indice++) {
            assertNotNull(diametros[indice]);
            assertFalse(diametros[indice].getPolegadas().trim().isEmpty());
            if (indice > 0) {
                assertFalse(diametros[indice].getMilimetros()
                        <= diametros[indice - 1].getMilimetros());
            }
        }
    }

    @Test
    void ultimoDiametroComercialEhTrintaEOitoPolegadas() {
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosComerciais();

        DiametroComercial ultimo = diametros[diametros.length - 1];

        assertEquals(965.2, ultimo.getMilimetros(), 0.0001);
        assertEquals("38", ultimo.getPolegadas());
    }

    @Test
    void retornaCopiaSemExporAReferenciaInterna() {
        DiametroComercial[] primeiraCopia = TabelasMateriaPrima.diametrosComerciais();
        DiametroComercial primeiroOriginal = primeiraCopia[0];

        primeiraCopia[0] = new DiametroComercial(1.0, "alterado");
        DiametroComercial[] segundaCopia = TabelasMateriaPrima.diametrosComerciais();

        assertNotSame(primeiraCopia, segundaCopia);
        assertEquals(primeiroOriginal.getMilimetros(), segundaCopia[0].getMilimetros());
        assertEquals(primeiroOriginal.getPolegadas(), segundaCopia[0].getPolegadas());
    }
}
