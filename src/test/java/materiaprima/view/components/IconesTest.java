package materiaprima.view.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class IconesTest {

    @Test
    void carregaIconeExistenteDosRecursos() {
        assertNotNull(Icones.carregar("copy", 16));
    }

    @Test
    void retornaNuloQuandoIconeNaoExiste() {
        assertNull(Icones.carregar("inexistente", 16));
    }
}
