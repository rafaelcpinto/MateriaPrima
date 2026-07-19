package materiaprima.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;
import javax.swing.UIManager;

final class PainelVisualizacao extends JPanel {

    private static final Color AZUL = new Color(37, 99, 235);
    private static final Color AZUL_SUAVE = new Color(234, 242, 255);
    private static final Color CINZA_PECA = new Color(229, 231, 235);
    private static final Color CONTORNO = new Color(71, 84, 103);
    private static final Color TEXTO = new Color(52, 64, 84);
    private static final Color SECUNDARIO = new Color(102, 112, 133);
    private static final Pattern NUMERO = Pattern.compile("-?\\d+(?:[.,]\\d+)?");
    private static final int MARGEM_VISUAL = 8;
    static final String TEXTO_OBSERVACAO =
            "Esquema proporcional com diferenças ampliadas para visualização";

    private boolean cilindrico = true;
    private String bruto1 = "—";
    private String bruto2 = "—";
    private String bruto3 = "—";
    private String final1 = "—";
    private String final2 = "—";
    private String final3 = "—";
    private String sobremetal = "—";

    PainelVisualizacao() {
        setOpaque(false);
        setPreferredSize(new Dimension(520, 330));
        setMinimumSize(new Dimension(390, 260));
    }

    void mostrarCilindrico(
            String diametroBruto,
            String diametroFinal,
            String sobremetalPorBanda,
            String comprimentoFinal,
            String comprimentoTotal
    ) {
        cilindrico = true;
        bruto1 = diametroBruto;
        bruto2 = comprimentoTotal;
        final1 = diametroFinal;
        final2 = comprimentoFinal;
        sobremetal = sobremetalPorBanda;
        repaint();
    }

    void mostrarRetangular(
            String larguraBruta,
            String alturaBruta,
            String comprimentoBruto,
            String larguraFinal,
            String alturaFinal,
            String comprimentoFinal
    ) {
        cilindrico = false;
        bruto1 = larguraBruta;
        bruto2 = alturaBruta;
        bruto3 = comprimentoBruto;
        final1 = larguraFinal;
        final2 = alturaFinal;
        final3 = comprimentoFinal;
        sobremetal = calcularSobremetalRetangular();
        repaint();
    }

    void limpar(boolean perfilCilindrico) {
        cilindrico = perfilCilindrico;
        bruto1 = "—";
        bruto2 = "—";
        bruto3 = "—";
        final1 = "—";
        final2 = "—";
        final3 = "—";
        sobremetal = "—";
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font fonteBase = UIManager.getFont("Label.font");
        if (fonteBase == null) {
            fonteBase = getFont();
        }
        g2.setFont(fonteBase.deriveFont(Font.PLAIN, 12f));

        if (cilindrico) {
            desenharCilindrico(g2);
        } else {
            desenharRetangular(g2);
        }
        desenharLegenda(g2);
        g2.dispose();
    }

    private void desenharCilindrico(Graphics2D g2) {
        int divisorY = (int) Math.round(getHeight() * 0.43);
        int areaSuperior = divisorY - 8;
        int centroX = Math.max(78, (int) Math.round(getWidth() * 0.22));
        int centroY = Math.max(54, areaSuperior / 2);
        int diametroMaximo = Math.max(72, Math.min(
                areaSuperior - 20,
                (int) Math.round(getWidth() * 0.25)));
        int diametroBruto = diametroMaximo;
        int diametroFinal = tamanhoProporcional(
                valor(final1), valor(bruto1), diametroBruto, 42);

        g2.setColor(AZUL_SUAVE);
        g2.fillOval(centroX - diametroBruto / 2, centroY - diametroBruto / 2,
                diametroBruto, diametroBruto);
        g2.setColor(AZUL);
        g2.setStroke(new BasicStroke(2f));
        g2.drawOval(centroX - diametroBruto / 2, centroY - diametroBruto / 2,
                diametroBruto, diametroBruto);
        g2.setColor(CINZA_PECA);
        g2.fillOval(centroX - diametroFinal / 2, centroY - diametroFinal / 2,
                diametroFinal, diametroFinal);
        g2.setColor(CONTORNO);
        g2.drawOval(centroX - diametroFinal / 2, centroY - diametroFinal / 2,
                diametroFinal, diametroFinal);
        desenharEixos(g2, centroX, centroY,
                diametroBruto / 2 + 7, diametroBruto / 2 + 7);

        int textoX = Math.max(205, (int) Math.round(getWidth() * 0.47));
        desenharChamada(g2, centroX + diametroBruto / 2 + 7,
                centroY - diametroBruto / 3, textoX, centroY - 45,
                "Bruto: Ø " + bruto1, AZUL, true);
        desenharChamada(g2, centroX + diametroFinal / 2 + 7,
                centroY - 10, textoX, centroY - 6,
                "Final: Ø " + final1, TEXTO, false);
        desenharChamada(g2, centroX + diametroBruto / 2 + 7,
                centroY + diametroFinal / 3, textoX, centroY + 43,
                "Sobremetal por banda: " + sobremetal, SECUNDARIO, false);

        g2.setColor(new Color(217, 225, 234));
        g2.drawLine(16, divisorY, getWidth() - 16, divisorY);
        desenharVistaComprimento(g2, divisorY + 8,
                getHeight() - 30, bruto2, final2);
    }

    private void desenharRetangular(Graphics2D g2) {
        int divisorY = (int) Math.round(getHeight() * 0.43);
        int brutoLargura = Math.max(120, Math.min(
                (int) Math.round(getWidth() * 0.36), getWidth() / 2 - 30));
        int brutoAltura = Math.max(62, Math.min(
                (int) Math.round(divisorY * 0.70), divisorY - 30));
        int finalLargura = tamanhoProporcional(
                valor(final1), valor(bruto1), brutoLargura, 55);
        int finalAltura = tamanhoProporcional(
                valor(final2), valor(bruto2), brutoAltura, 42);
        int x = Math.max(24, (int) Math.round(getWidth() * 0.22) - brutoLargura / 2);
        int y = Math.max(14, divisorY / 2 - brutoAltura / 2);

        g2.setColor(AZUL_SUAVE);
        g2.fillRect(x, y, brutoLargura, brutoAltura);
        g2.setColor(AZUL);
        g2.setStroke(new BasicStroke(2f));
        g2.drawRect(x, y, brutoLargura, brutoAltura);
        int finalX = x + (brutoLargura - finalLargura) / 2;
        int finalY = y + (brutoAltura - finalAltura) / 2;
        g2.setColor(CINZA_PECA);
        g2.fillRect(finalX, finalY, finalLargura, finalAltura);
        g2.setColor(CONTORNO);
        g2.drawRect(finalX, finalY, finalLargura, finalAltura);
        desenharEixos(g2, x + brutoLargura / 2, y + brutoAltura / 2,
                brutoLargura / 2 + 8, brutoAltura / 2 + 8);

        int textoX = Math.max(235, (int) Math.round(getWidth() * 0.52));
        int textoY = Math.max(9, (int) Math.round(divisorY * 0.07));
        g2.setColor(AZUL);
        g2.setFont(getFont().deriveFont(Font.BOLD, 10.5f));
        g2.drawString("Bruto", textoX, textoY);
        g2.setFont(getFont().deriveFont(Font.PLAIN, 10.5f));
        g2.drawString("Largura: " + bruto1, textoX, textoY + 14);
        g2.drawString("Altura: " + bruto2, textoX, textoY + 28);
        g2.setColor(TEXTO);
        g2.setFont(getFont().deriveFont(Font.BOLD, 10.5f));
        g2.drawString("Final", textoX, textoY + 43);
        g2.setFont(getFont().deriveFont(Font.PLAIN, 10.5f));
        g2.drawString("Largura: " + final1, textoX, textoY + 57);
        g2.drawString("Altura: " + final2, textoX, textoY + 71);
        g2.setColor(SECUNDARIO);
        g2.drawString("Sobremetal: " + sobremetal, textoX, textoY + 85);

        g2.setColor(new Color(217, 225, 234));
        g2.drawLine(16, divisorY, getWidth() - 16, divisorY);
        desenharVistaComprimento(g2, divisorY + 8,
                getHeight() - 30, bruto3, final3);
    }

    private void desenharVistaComprimento(
            Graphics2D g2,
            int topo,
            int base,
            String comprimentoBruto,
            String comprimentoFinal
    ) {
        int alturaDisponivel = Math.max(105, base - topo);
        int x = Math.max(34, (int) Math.round(getWidth() * 0.09));
        int larguraBruta = Math.max(200, getWidth() - 2 * x);
        int larguraFinal = tamanhoProporcional(valor(comprimentoFinal),
                valor(comprimentoBruto), larguraBruta, 100);
        int alturaBruta = Math.max(30, Math.min(48,
                (int) Math.round(alturaDisponivel * 0.32)));
        int alturaFinal = Math.max(18, (int) Math.round(alturaBruta * 0.60));
        int y = topo + Math.max(5, (int) Math.round(alturaDisponivel * 0.08));
        int finalX = x + (larguraBruta - larguraFinal) / 2;

        g2.setColor(AZUL_SUAVE);
        g2.fillRoundRect(x, y, larguraBruta, alturaBruta, 6, 6);
        g2.setColor(AZUL);
        g2.drawRoundRect(x, y, larguraBruta, alturaBruta, 6, 6);
        g2.setColor(CINZA_PECA);
        g2.fillRect(finalX, y + 9, larguraFinal, alturaFinal);
        g2.setColor(CONTORNO);
        g2.drawRect(finalX, y + 9, larguraFinal, alturaFinal);
        desenharEixoHorizontal(g2, x - 8, x + larguraBruta + 8, y + 24);

        int cotaFinalY = Math.min(base - 34,
                y + alturaBruta + Math.max(16, alturaDisponivel / 8));
        desenharCotaHorizontal(g2, finalX, finalX + larguraFinal, cotaFinalY,
                "Final: " + comprimentoFinal, TEXTO);
        int cotaBrutaY = Math.min(base - 7,
                cotaFinalY + Math.max(27, alturaDisponivel / 6));
        desenharCotaHorizontal(g2, x, x + larguraBruta, cotaBrutaY,
                "Bruto: " + comprimentoBruto, AZUL);
    }

    private int tamanhoProporcional(
            double valorMenor,
            double valorMaior,
            int tamanhoMaximo,
            int minimo
    ) {
        if (valorMenor <= 0 || valorMaior <= 0) {
            return Math.max(minimo, tamanhoMaximo - 2 * MARGEM_VISUAL);
        }
        int calculado = (int) Math.round(tamanhoMaximo * valorMenor / valorMaior);
        return Math.max(minimo, Math.min(tamanhoMaximo - 2 * MARGEM_VISUAL, calculado));
    }

    private void desenharChamada(
            Graphics2D g2,
            int origemX,
            int origemY,
            int textoX,
            int textoY,
            String texto,
            Color cor,
            boolean negrito
    ) {
        g2.setColor(cor);
        g2.setStroke(new BasicStroke(1.4f));
        g2.drawLine(origemX, origemY, textoX - 12, textoY - 4);
        g2.drawLine(textoX - 12, textoY - 4, textoX - 4, textoY - 4);
        g2.setFont(getFont().deriveFont(negrito ? Font.BOLD : Font.PLAIN, 12f));
        g2.drawString(texto, textoX, textoY);
    }

    private void desenharCotaHorizontal(
            Graphics2D g2,
            int inicio,
            int fim,
            int y,
            String texto,
            Color cor
    ) {
        g2.setColor(cor);
        g2.drawLine(inicio, y, fim, y);
        g2.fillPolygon(new int[]{inicio, inicio + 8, inicio + 8},
                new int[]{y, y - 4, y + 4}, 3);
        g2.fillPolygon(new int[]{fim, fim - 8, fim - 8},
                new int[]{y, y - 4, y + 4}, 3);
        FontMetrics metricas = g2.getFontMetrics();
        int larguraTexto = metricas.stringWidth(texto) + 14;
        int centro = (inicio + fim) / 2;
        g2.setColor(Color.WHITE);
        g2.fillRect(centro - larguraTexto / 2, y - 9, larguraTexto, 18);
        g2.setColor(cor);
        g2.drawString(texto, centro - metricas.stringWidth(texto) / 2, y + 4);
    }

    private void desenharEixos(
            Graphics2D g2,
            int centroX,
            int centroY,
            int metadeLargura,
            int metadeAltura
    ) {
        Stroke anterior = g2.getStroke();
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10f, new float[]{5f, 4f}, 0f));
        g2.setColor(SECUNDARIO);
        g2.drawLine(centroX - metadeLargura, centroY,
                centroX + metadeLargura, centroY);
        g2.drawLine(centroX, centroY - metadeAltura,
                centroX, centroY + metadeAltura);
        g2.setStroke(anterior);
    }

    private void desenharEixoHorizontal(Graphics2D g2, int inicio, int fim, int y) {
        Stroke anterior = g2.getStroke();
        g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10f, new float[]{5f, 4f}, 0f));
        g2.setColor(SECUNDARIO);
        g2.drawLine(inicio, y, fim, y);
        g2.setStroke(anterior);
    }

    private void desenharLegenda(Graphics2D g2) {
        g2.setColor(SECUNDARIO);
        Font fonteBase = UIManager.getFont("Label.font");
        if (fonteBase == null) {
            fonteBase = getFont();
        }
        g2.setFont(fonteBase.deriveFont(Font.ITALIC, 11f));
        g2.drawString(TEXTO_OBSERVACAO, 18, getHeight() - 12);
    }

    private String calcularSobremetalRetangular() {
        double horizontal = Math.max(0, (valor(bruto1) - valor(final1)) / 2.0);
        double vertical = Math.max(0, (valor(bruto2) - valor(final2)) / 2.0);
        if (horizontal == 0 && vertical == 0) {
            return "—";
        }
        return String.format(java.util.Locale.forLanguageTag("pt-BR"),
                "%.2f / %.2f mm", horizontal, vertical);
    }

    private double valor(String texto) {
        if (texto == null) {
            return 0;
        }
        Matcher matcher = NUMERO.matcher(texto);
        if (!matcher.find()) {
            return 0;
        }
        try {
            return Double.parseDouble(matcher.group().replace(',', '.'));
        } catch (NumberFormatException excecao) {
            return 0;
        }
    }
}
