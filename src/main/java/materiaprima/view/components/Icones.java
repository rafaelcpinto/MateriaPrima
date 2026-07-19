package materiaprima.view.components;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.net.URL;
import java.util.logging.Logger;
import javax.swing.Icon;

public final class Icones {

    private static final Logger LOGGER = Logger.getLogger(Icones.class.getName());

    private Icones() {
    }

    public static Icon carregar(String nome, int tamanho) {
        String caminho = "/icons/" + nome + ".svg";
        URL recurso = Icones.class.getResource(caminho);
        if (recurso == null) {
            LOGGER.warning("Ícone não encontrado: " + caminho);
            return null;
        }
        try {
            return new FlatSVGIcon("icons/" + nome + ".svg", tamanho, tamanho);
        } catch (RuntimeException excecao) {
            LOGGER.warning("Não foi possível carregar o ícone: " + caminho);
            return null;
        }
    }
}
