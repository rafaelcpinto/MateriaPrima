package materiaprima.aplicacao;

import java.awt.EventQueue;
import materiaprima.view.CalculoSobremetalView;

public class Main {

    public static void main(String[] args) {
        TemaAplicacao.instalar();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculoSobremetalView().setVisible(true);
            }
        });
    }

}
