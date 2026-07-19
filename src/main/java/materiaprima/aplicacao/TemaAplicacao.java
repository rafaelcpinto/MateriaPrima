package materiaprima.aplicacao;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

public final class TemaAplicacao {

    private static final Logger LOGGER = Logger.getLogger(TemaAplicacao.class.getName());

    private TemaAplicacao() {
    }

    public static boolean instalar() {
        try {
            boolean instalado = FlatLightLaf.setup();
            if (instalado) {
                configurarPadroes();
            }
            return instalado;
        } catch (RuntimeException excecao) {
            LOGGER.log(Level.WARNING,
                    "Não foi possível inicializar o tema FlatLaf.", excecao);
            return false;
        }
    }

    private static void configurarPadroes() {
        Font fonte = new Font("Segoe UI", Font.PLAIN, 13);
        if (!"Segoe UI".equalsIgnoreCase(fonte.getFamily())) {
            fonte = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
        }
        UIManager.put("defaultFont", fonte);
        UIManager.put("Component.arc", 8);
        UIManager.put("Button.arc", 8);
        UIManager.put("Button.default.background", new Color(37, 99, 235));
        UIManager.put("Button.default.foreground", Color.WHITE);
        UIManager.put("Button.default.focusedBackground", new Color(37, 99, 235));
        UIManager.put("Button.default.hoverBackground", new Color(29, 78, 216));
        UIManager.put("Button.default.pressedBackground", new Color(30, 64, 175));
        UIManager.put("Button.default.borderColor", new Color(37, 99, 235));
        UIManager.put("Button.default.focusColor", new Color(147, 197, 253));
        UIManager.put("TextComponent.arc", 8);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);
        UIManager.put("Component.minimumWidth", 80);
        UIManager.put("Component.minimumHeight", 34);
    }
}
