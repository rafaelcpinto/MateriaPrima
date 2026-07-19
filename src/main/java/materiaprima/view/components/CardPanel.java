package materiaprima.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public final class CardPanel extends JPanel {

    private static final Color FUNDO = Color.WHITE;
    private static final Color BORDA = new Color(217, 225, 234);
    private static final int ARCO = 14;

    public CardPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(FUNDO);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARCO, ARCO);
        g2.dispose();
        super.paintComponent(graphics);
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(BORDA);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ARCO, ARCO);
        g2.dispose();
    }
}
