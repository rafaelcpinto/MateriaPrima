package materiaprima.aplicacao;

import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import materiaprima.view.CalculoSobremetalView;

public class Main {

    public static void main(String[] args) {
        configurarAparencia();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculoSobremetalView().setVisible(true);
            }
        });
    }

    private static void configurarAparencia() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
