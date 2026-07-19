package materiaprima.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Icon;
import com.formdev.flatlaf.FlatClientProperties;
import materiaprima.aplicacao.VersaoAplicacao;
import materiaprima.controller.CalculoSobremetalController;
import materiaprima.controller.ValidadorCalculo;
import materiaprima.dados.TabelasMateriaPrima;
import materiaprima.modelo.Material;
import materiaprima.modelo.PadraoDimensional;
import materiaprima.view.components.CardPanel;
import materiaprima.view.components.Icones;

public class CalculoSobremetalView extends JFrame {

    private static final Color COR_FUNDO = new Color(244, 246, 249);
    private static final Color COR_PAINEL = Color.WHITE;
    private static final Color COR_TITULO = new Color(23, 32, 51);
    private static final Color COR_TEXTO = new Color(52, 64, 84);
    private static final Color COR_TEXTO_SECUNDARIO = new Color(102, 112, 133);
    private static final Color COR_PRINCIPAL = new Color(37, 99, 235);
    private static final Color COR_RESULTADO_DESTAQUE = new Color(30, 58, 95);
    private static final Color COR_BORDA = new Color(217, 225, 234);
    private static final Color COR_ERRO = new Color(220, 38, 38);
    private static final int LARGURA_MAXIMA_IMAGEM_AJUDA = 820;
    private static final int ALTURA_MAXIMA_IMAGEM_AJUDA = 550;
    private static final int LARGURA_CAMPO_RESULTADO = 100;
    private static final int LARGURA_EQUIVALENTE_RESULTADO = 95;
    private static final int LARGURA_ROTULO_RESULTADO = 165;
    private static final int LARGURA_CAMPO_ENTRADA = 150;
    private static final int LARGURA_ROTULO_ENTRADA = 175;
    private static final int LARGURA_CARD_ENTRADA = 425;
    private static final int ALTURA_CAMPO_ENTRADA = 38;
    private static final String NOME_MATERIAL_PERSONALIZADO = "Densidade personalizada";
    private static final String AJUDA_DIAMETRO = criarTextoAjudaDiametro();
    private static final String AJUDA_COMPRIMENTO =
            "Informe o comprimento final da peça acabada, sem incluir o material adicional "
                    + "necessário para fixação ou faceamento.";
    private static final String AJUDA_ADICIONAL =
            "Informe somente o material excedente além do comprimento final da peça. Esse "
                    + "adicional é usado para permitir a fixação da matéria-prima no torno "
                    + "e as operações de faceamento. Como referência prática, normalmente "
                    + "são utilizados de 10 mm a 20 mm, conforme o processo e a forma de fixação.";
    private static final String AJUDA_LARGURA =
            "Informe a largura final da peça retangular após a usinagem.";
    private static final String AJUDA_ALTURA =
            "Informe a altura final da peça retangular após a usinagem.";

    private final CalculoSobremetalController controller;

    private final JRadioButton perfilCilindrico = new JRadioButton("Cilíndrico", true);
    private final JRadioButton perfilRetangular = new JRadioButton("Retangular");
    private final JComboBox<Material> material =
            new JComboBox<Material>(criarListaMateriais());
    private final JLabel rotuloPadraoDiametro = new JLabel("Padrão dimensional");
    private final JRadioButton padraoMetrico = new JRadioButton("Milímetro");
    private final JRadioButton padraoPolegada = new JRadioButton("Polegada", true);
    private final JPanel opcoesPadraoDiametro =
            new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    private final JLabel densidadeMaterial = new JLabel();
    private final JTextField densidadePersonalizada = new JTextField(14);
    private final JPanel painelDensidade = new JPanel(new CardLayout());
    private final JTextField valor1 = new JTextField(14);
    private final JTextField valor2 = new JTextField(14);
    private final JTextField valor3 = new JTextField(14);
    private final JCheckBox otimizar =
            new JCheckBox("Permitir dimensão abaixo da recomendada");
    private final JButton botaoCalcular = new JButton("Calcular");
    private final JButton botaoLimpar = new JButton("Limpar");

    private final JLabel rotuloValor1 = new JLabel();
    private final JLabel rotuloValor2 = new JLabel();
    private final JLabel rotuloValor3 = new JLabel();
    private final JLabel ajudaValor1 = criarAjuda(AJUDA_DIAMETRO);
    private final JLabel ajudaValor2 = criarAjuda(AJUDA_COMPRIMENTO);
    private final JLabel ajudaValor3 = criarAjuda(AJUDA_ADICIONAL);
    private final JLabel mensagem = new JLabel(" ");
    private final PainelVisualizacao painelVisualizacao = new PainelVisualizacao();

    private final JLabel rotuloResultado1 = new JLabel();
    private final JLabel rotuloResultado2 = new JLabel();
    private final JLabel rotuloResultado3 = new JLabel();
    private final JTextField resultado1 = criarValorResultado();
    private final JTextField resultado2 = criarValorResultado();
    private final JTextField resultado3 = criarValorResultado();
    private final JTextField resultadoMassa = criarValorResultado();
    private final JLabel equivalenteDiametro = new JLabel();
    private final JLabel equivalenteResultado2 = new JLabel();
    private final JLabel equivalenteResultado3 = new JLabel();

    public CalculoSobremetalView() {
        super("Cálculo de sobremetal");
        controller = new CalculoSobremetalController(this);
        configurarJanela();
        configurarComponentes();
        montarInterface();
    }

    private void configurarComponentes() {
        Dimension tamanhoEntrada = new Dimension(
                LARGURA_CAMPO_ENTRADA, ALTURA_CAMPO_ENTRADA);
        JTextField[] campos = {valor1, valor2, valor3, densidadePersonalizada};
        for (JTextField campo : campos) {
            campo.putClientProperty(FlatClientProperties.STYLE,
                    "arc: 8; borderColor: #D0D5DD; focusedBorderColor: #2563EB");
            campo.setPreferredSize(tamanhoEntrada);
            campo.setMinimumSize(tamanhoEntrada);
        }
        material.putClientProperty(FlatClientProperties.STYLE,
                "arc: 8; borderColor: #D0D5DD; focusedBorderColor: #2563EB");
        material.setPreferredSize(tamanhoEntrada);
        material.setMinimumSize(tamanhoEntrada);
        painelDensidade.setPreferredSize(tamanhoEntrada);
        painelDensidade.setMinimumSize(tamanhoEntrada);
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void montarInterface() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 10));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(18, 24, 10, 24));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(COR_FUNDO);
        JPanel painelEntrada = criarPainelEntrada();
        painelEntrada.setPreferredSize(new Dimension(
                LARGURA_CARD_ENTRADA,
                painelEntrada.getPreferredSize().height
        ));
        painelEntrada.setMinimumSize(new Dimension(LARGURA_CARD_ENTRADA, 0));
        JPanel colunaDireita = new JPanel(new GridBagLayout());
        colunaDireita.setBackground(COR_FUNDO);
        GridBagConstraints resultadoDireita = new GridBagConstraints();
        resultadoDireita.gridx = 0;
        resultadoDireita.gridy = 0;
        resultadoDireita.weightx = 1.0;
        resultadoDireita.fill = GridBagConstraints.HORIZONTAL;
        resultadoDireita.insets = new Insets(0, 0, 12, 0);
        colunaDireita.add(criarPainelResultado(), resultadoDireita);
        GridBagConstraints visualizacaoDireita = new GridBagConstraints();
        visualizacaoDireita.gridx = 0;
        visualizacaoDireita.gridy = 1;
        visualizacaoDireita.weightx = 1.0;
        visualizacaoDireita.weighty = 1.0;
        visualizacaoDireita.fill = GridBagConstraints.BOTH;
        colunaDireita.add(criarPainelVisualizacao(), visualizacaoDireita);

        GridBagConstraints colunaEntrada = new GridBagConstraints();
        colunaEntrada.gridx = 0;
        colunaEntrada.gridy = 0;
        colunaEntrada.weightx = 0.0;
        colunaEntrada.weighty = 1.0;
        colunaEntrada.fill = GridBagConstraints.BOTH;
        colunaEntrada.insets = new Insets(0, 0, 0, 8);
        centro.add(painelEntrada, colunaEntrada);

        GridBagConstraints colunaResultado = new GridBagConstraints();
        colunaResultado.gridx = 1;
        colunaResultado.gridy = 0;
        colunaResultado.weightx = 0.58;
        colunaResultado.weighty = 1.0;
        colunaResultado.fill = GridBagConstraints.BOTH;
        colunaResultado.insets = new Insets(0, 8, 0, 0);
        centro.add(colunaDireita, colunaResultado);
        conteudo.add(centro, BorderLayout.CENTER);
        conteudo.add(criarRodape(), BorderLayout.SOUTH);

        setContentPane(conteudo);
        getRootPane().setDefaultButton(botaoCalcular);
        atualizarPerfil();
        pack();
        setSize(new Dimension(950, 700));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent evento) {
                valor1.requestFocusInWindow();
            }
        });
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel();
        painel.setBackground(COR_FUNDO);
        painel.setLayout(new javax.swing.BoxLayout(painel, javax.swing.BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Cálculo de sobremetal");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 27f));
        titulo.setForeground(COR_TITULO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Baseado na norma DIN 7527");
        subtitulo.setFont(subtitulo.getFont().deriveFont(Font.PLAIN, 14f));
        subtitulo.setForeground(COR_TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(titulo);
        painel.add(javax.swing.Box.createVerticalStrut(3));
        painel.add(subtitulo);
        return painel;
    }

    private void abrirImagemAjuda(String caminho, String titulo, String texto) {
        final JDialog dialogo = new JDialog(this, titulo, true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setLayout(new BorderLayout(0, 8));
        if (texto != null) {
            JLabel explicacao = new JLabel(formatarTextoAjuda(texto));
            explicacao.setBorder(BorderFactory.createEmptyBorder(8, 12, 0, 12));
            dialogo.add(explicacao, BorderLayout.NORTH);
        }
        dialogo.add(criarPainelImagem(caminho), BorderLayout.CENTER);

        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(evento -> dialogo.dispose());
        JPanel areaFechar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        areaFechar.add(fechar);
        dialogo.add(areaFechar, BorderLayout.SOUTH);

        dialogo.pack();
        dialogo.setResizable(false);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private JScrollPane criarPainelImagem(String caminho) {
        ImageIcon imagem = carregarImagem(caminho);
        JLabel conteudo;
        if (imagem == null) {
            conteudo = new JLabel("Imagem de ajuda não encontrada.", SwingConstants.CENTER);
            conteudo.setForeground(COR_ERRO);
        } else {
            conteudo = new JLabel(redimensionarParaCaber(
                    imagem, LARGURA_MAXIMA_IMAGEM_AJUDA, ALTURA_MAXIMA_IMAGEM_AJUDA));
            conteudo.setHorizontalAlignment(SwingConstants.CENTER);
            conteudo.setVerticalAlignment(SwingConstants.TOP);
        }
        conteudo.setOpaque(true);
        conteudo.setBackground(COR_PAINEL);
        JScrollPane painel = new JScrollPane(conteudo);
        painel.setBorder(BorderFactory.createEmptyBorder());
        painel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        painel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        painel.setPreferredSize(new Dimension(
                conteudo.getPreferredSize().width + 4,
                conteudo.getPreferredSize().height + 4
        ));
        return painel;
    }

    private ImageIcon carregarImagem(String caminho) {
        URL recurso = getClass().getResource(caminho);
        return recurso == null ? null : new ImageIcon(recurso);
    }

    private ImageIcon redimensionarParaCaber(
            ImageIcon original, int larguraMaxima, int alturaMaxima) {
        int larguraOriginal = original.getIconWidth();
        int alturaOriginal = original.getIconHeight();
        if (larguraOriginal <= 0 || alturaOriginal <= 0) {
            return original;
        }

        double escalaLargura = (double) larguraMaxima / larguraOriginal;
        double escalaAltura = (double) alturaMaxima / alturaOriginal;
        double escala = Math.min(1.0, Math.min(escalaLargura, escalaAltura));
        int novaLargura = (int) Math.round(larguraOriginal * escala);
        int novaAltura = (int) Math.round(alturaOriginal * escala);

        Image imagemRedimensionada = original.getImage().getScaledInstance(
                novaLargura, novaAltura, Image.SCALE_SMOOTH);
        return new ImageIcon(imagemRedimensionada);
    }

    private JPanel criarPainelEntrada() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_PAINEL);
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        ButtonGroup grupoPerfil = new ButtonGroup();
        grupoPerfil.add(perfilCilindrico);
        grupoPerfil.add(perfilRetangular);
        perfilCilindrico.setBackground(COR_PAINEL);
        perfilRetangular.setBackground(COR_PAINEL);
        perfilCilindrico.setForeground(COR_TEXTO);
        perfilRetangular.setForeground(COR_TEXTO);
        perfilCilindrico.addActionListener(evento -> atualizarPerfil());
        perfilRetangular.addActionListener(evento -> atualizarPerfil());

        JPanel opcoesPerfil = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        opcoesPerfil.setBackground(COR_PAINEL);
        opcoesPerfil.add(perfilCilindrico);
        opcoesPerfil.add(javax.swing.Box.createHorizontalStrut(18));
        opcoesPerfil.add(perfilRetangular);

        ButtonGroup grupoPadraoDiametro = new ButtonGroup();
        grupoPadraoDiametro.add(padraoMetrico);
        grupoPadraoDiametro.add(padraoPolegada);
        padraoMetrico.setBackground(COR_PAINEL);
        padraoPolegada.setBackground(COR_PAINEL);
        padraoMetrico.setForeground(COR_TEXTO);
        padraoPolegada.setForeground(COR_TEXTO);
        opcoesPadraoDiametro.setBackground(COR_PAINEL);
        opcoesPadraoDiametro.add(padraoPolegada);
        opcoesPadraoDiametro.add(javax.swing.Box.createHorizontalStrut(18));
        opcoesPadraoDiametro.add(padraoMetrico);

        JLabel rotuloPerfil = new JLabel("Perfil");
        JLabel rotuloMaterial = new JLabel("Material");
        JLabel rotuloDensidade = new JLabel("Densidade (g/cm³)");
        rotuloPerfil.setLabelFor(perfilCilindrico);
        rotuloMaterial.setLabelFor(material);
        rotuloDensidade.setLabelFor(densidadePersonalizada);
        rotuloPadraoDiametro.setLabelFor(padraoPolegada);
        String ajudaPadrao = "<html>Define como as dimensões comerciais da matéria-prima "
                + "serão selecionadas.<br><br><b>Milímetro:</b> arredonda cada dimensão "
                + "necessária para o próximo milímetro inteiro.<br><b>Polegada:</b> "
                + "seleciona, para cada dimensão, o próximo valor disponível na tabela "
                + "imperial.<br><br>As entradas continuam sendo informadas em "
                + "milímetros.</html>";
        rotuloPadraoDiametro.setToolTipText(ajudaPadrao);
        padraoMetrico.setToolTipText(ajudaPadrao);
        padraoPolegada.setToolTipText(ajudaPadrao);
        configurarDensidade();
        adicionarLinha(painel, 0, rotuloPerfil, opcoesPerfil);
        adicionarLinha(painel, 1, rotuloPadraoDiametro, opcoesPadraoDiametro);
        adicionarLinha(painel, 2, rotuloMaterial, material);
        adicionarLinha(painel, 3, rotuloDensidade, painelDensidade);
        adicionarLinha(painel, 4, criarRotuloComAjuda(rotuloValor1, ajudaValor1), valor1);
        adicionarLinha(painel, 5, criarRotuloComAjuda(rotuloValor2, ajudaValor2), valor2);
        adicionarLinha(painel, 6, criarRotuloComAjuda(rotuloValor3, ajudaValor3), valor3);

        otimizar.setToolTipText(
                "Pode selecionar uma dimensão comercial abaixo da recomendação padrão. "
                        + "Confirme a adequação ao processo de fabricação e à norma aplicável.");
        otimizar.setBackground(COR_PAINEL);
        otimizar.setForeground(COR_TEXTO);
        GridBagConstraints opcao = restricoes(0, 7);
        opcao.gridwidth = 2;
        opcao.insets = new Insets(8, 4, 2, 4);
        painel.add(otimizar, opcao);

        botaoCalcular.setFont(botaoCalcular.getFont().deriveFont(Font.BOLD));
        botaoCalcular.setBackground(COR_PRINCIPAL);
        botaoCalcular.setForeground(Color.WHITE);
        botaoCalcular.setIcon(Icones.carregar("calculator", 17));
        botaoCalcular.setIconTextGap(8);
        botaoCalcular.putClientProperty(FlatClientProperties.STYLE,
                "borderWidth: 0; focusWidth: 1; innerFocusWidth: 0; arc: 10");
        botaoCalcular.putClientProperty("JButton.buttonType", "roundRect");
        botaoCalcular.setOpaque(true);
        botaoCalcular.setContentAreaFilled(true);
        botaoCalcular.setBorderPainted(true);
        botaoCalcular.setFocusPainted(true);
        botaoCalcular.addActionListener(evento -> calcular());
        botaoLimpar.setIcon(Icones.carregar("clear", 17));
        botaoLimpar.setIconTextGap(8);
        botaoLimpar.putClientProperty(FlatClientProperties.STYLE,
                "background: #FFFFFF; foreground: #344054; borderColor: #D0D5DD; arc: 10");
        botaoLimpar.putClientProperty("JButton.buttonType", "roundRect");
        botaoLimpar.addActionListener(evento -> limpar());
        Dimension tamanhoBotao = new Dimension(135, 42);
        botaoCalcular.setPreferredSize(tamanhoBotao);
        botaoLimpar.setPreferredSize(tamanhoBotao);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        botoes.setBackground(COR_PAINEL);
        botoes.add(botaoCalcular);
        botoes.add(botaoLimpar);

        GridBagConstraints areaBotoes = restricoes(0, 8);
        areaBotoes.gridwidth = 2;
        areaBotoes.fill = GridBagConstraints.HORIZONTAL;
        areaBotoes.insets = new Insets(8, 4, 0, 4);
        painel.add(botoes, areaBotoes);

        mensagem.setForeground(COR_ERRO);
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints areaMensagem = restricoes(0, 9);
        areaMensagem.gridwidth = 2;
        areaMensagem.fill = GridBagConstraints.HORIZONTAL;
        areaMensagem.insets = new Insets(2, 4, 0, 4);
        painel.add(mensagem, areaMensagem);

        rotuloValor1.setLabelFor(valor1);
        rotuloValor2.setLabelFor(valor2);
        rotuloValor3.setLabelFor(valor3);
        aplicarFonteCompacta(painel);
        JPanel alinhadoAoTopo = new JPanel(new BorderLayout());
        alinhadoAoTopo.setOpaque(false);
        alinhadoAoTopo.add(painel, BorderLayout.NORTH);
        return criarCard("Dados de entrada", "input", alinhadoAoTopo);
    }

    private void aplicarFonteCompacta(Component componente) {
        if (componente instanceof JLabel
                || componente instanceof JRadioButton
                || componente instanceof JCheckBox) {
            componente.setFont(componente.getFont().deriveFont(12f));
        }
        if (componente instanceof Container) {
            for (Component filho : ((Container) componente).getComponents()) {
                aplicarFonteCompacta(filho);
            }
        }
    }

    private static Material[] criarListaMateriais() {
        Material[] cadastrados = TabelasMateriaPrima.materiais();
        Material[] opcoes = new Material[cadastrados.length + 1];
        System.arraycopy(cadastrados, 0, opcoes, 0, cadastrados.length);
        opcoes[cadastrados.length] =
                new Material(NOME_MATERIAL_PERSONALIZADO, 1.0 / 1_000_000);
        return opcoes;
    }

    private static String criarTextoAjudaDiametro() {
        double limite = new ValidadorCalculo().limiteDiametroCilindrico();
        String limiteMilimetros = String.format(
                new Locale("pt", "BR"), "%.1f", limite);
        return "Informe o maior diâmetro final da peça após a usinagem. A aplicação "
                + "utilizará esse valor para selecionar o diâmetro comercial da "
                + "matéria-prima e calcular o sobremetal."
                + "<br><br>O diâmetro acabado deve ser menor que "
                + limiteMilimetros + " mm.";
    }

    private void configurarDensidade() {
        painelDensidade.setBackground(COR_PAINEL);
        densidadeMaterial.setForeground(COR_TEXTO_SECUNDARIO);
        densidadePersonalizada.setToolTipText(
                "Informe a densidade do material em gramas por centímetro cúbico (g/cm³).");
        painelDensidade.add(densidadeMaterial, "cadastrada");
        painelDensidade.add(densidadePersonalizada, "personalizada");
        material.addActionListener(evento -> atualizarDensidade());
        atualizarDensidade();
    }

    private void atualizarDensidade() {
        Material selecionado = (Material) material.getSelectedItem();
        boolean personalizada = selecionado != null
                && NOME_MATERIAL_PERSONALIZADO.equals(selecionado.getNome());
        CardLayout layout = (CardLayout) painelDensidade.getLayout();
        if (personalizada) {
            layout.show(painelDensidade, "personalizada");
        } else if (selecionado != null) {
            densidadeMaterial.setText(String.format(
                    new Locale("pt", "BR"), "%.2f g/cm³",
                    selecionado.getDensidade() * 1_000_000));
            layout.show(painelDensidade, "cadastrada");
        }
        painelDensidade.revalidate();
        painelDensidade.repaint();
    }

    private void calcular() {
        if (isDensidadePersonalizada() && lerDensidadePersonalizada() == null) {
            limparResultados();
            mensagem.setText("Informe uma densidade personalizada válida e maior que zero.");
            densidadePersonalizada.requestFocusInWindow();
            return;
        }
        controller.calcular();
    }

    private boolean isDensidadePersonalizada() {
        Material selecionado = (Material) material.getSelectedItem();
        return selecionado != null
                && NOME_MATERIAL_PERSONALIZADO.equals(selecionado.getNome());
    }

    private Double lerDensidadePersonalizada() {
        try {
            double densidade = Double.parseDouble(
                    densidadePersonalizada.getText().trim().replace(',', '.'));
            return Double.isFinite(densidade) && densidade > 0 ? densidade : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private JPanel criarPainelResultado() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_PAINEL);
        painel.setOpaque(false);
        painel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        configurarEquivalente(equivalenteDiametro);
        configurarEquivalente(equivalenteResultado2);
        configurarEquivalente(equivalenteResultado3);
        adicionarLinhaResultadoCompacta(painel, 0, rotuloResultado1, resultado1,
                equivalenteDiametro, true);
        adicionarLinhaResultadoCompacta(painel, 1, rotuloResultado2, resultado2,
                equivalenteResultado2, true);
        adicionarLinhaResultadoCompacta(painel, 2, rotuloResultado3, resultado3,
                equivalenteResultado3, true);
        adicionarLinhaResultadoCompacta(painel, 3, new JLabel("Massa estimada"),
                resultadoMassa, null, false);
        return criarCard(
                "Resultado",
                "result",
                painel,
                new Insets(6, 12, 7, 12)
        );
    }

    private void adicionarLinhaResultadoCompacta(
            JPanel painel,
            int linha,
            JLabel rotulo,
            JTextField valor,
            JLabel equivalente,
            boolean separador
    ) {
        JPanel conteudoLinha = new JPanel(new GridBagLayout());
        conteudoLinha.setName("linhaResultado");
        conteudoLinha.setOpaque(false);
        conteudoLinha.setBorder(BorderFactory.createCompoundBorder(
                separador
                        ? BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDA)
                        : BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(3, 6, 3, 6)));

        rotulo.setForeground(COR_TEXTO);
        JPanel areaRotulo = new JPanel(new BorderLayout());
        areaRotulo.setOpaque(false);
        Dimension tamanhoRotulo = new Dimension(
                LARGURA_ROTULO_RESULTADO,
                Math.max(28, rotulo.getPreferredSize().height)
        );
        areaRotulo.setPreferredSize(tamanhoRotulo);
        areaRotulo.setMinimumSize(tamanhoRotulo);
        areaRotulo.add(rotulo, BorderLayout.CENTER);

        GridBagConstraints colunaRotulo = restricoes(0, 0);
        colunaRotulo.weightx = 0.0;
        colunaRotulo.fill = GridBagConstraints.NONE;
        colunaRotulo.insets = new Insets(0, 0, 0, 8);
        conteudoLinha.add(areaRotulo, colunaRotulo);

        JPanel areaValor = equivalente == null
                ? criarResultadoComCopia(valor)
                : criarResultadoComCopia(valor, criarEspacoEquivalente(equivalente));
        GridBagConstraints colunaValor = restricoes(1, 0);
        colunaValor.weightx = 1.0;
        colunaValor.fill = GridBagConstraints.HORIZONTAL;
        colunaValor.insets = new Insets(0, 0, 0, 0);
        conteudoLinha.add(areaValor, colunaValor);

        GridBagConstraints restricaoLinha = new GridBagConstraints();
        restricaoLinha.gridx = 0;
        restricaoLinha.gridy = linha;
        restricaoLinha.weightx = 1.0;
        restricaoLinha.fill = GridBagConstraints.HORIZONTAL;
        painel.add(conteudoLinha, restricaoLinha);
    }

    private JPanel criarRodape() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_FUNDO);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, COR_BORDA),
                BorderFactory.createEmptyBorder(7, 0, 0, 0)));

        JLabel autor = new JLabel("Criado por Rafael C. Pinto");
        JLabel versao = new JLabel("Versão " + VersaoAplicacao.ATUAL);
        JLabel sobre = criarLinkSobre();
        JLabel separador = new JLabel("|");
        autor.setFont(autor.getFont().deriveFont(11f));
        versao.setFont(versao.getFont().deriveFont(11f));
        separador.setFont(separador.getFont().deriveFont(11f));
        autor.setForeground(COR_TEXTO_SECUNDARIO);
        versao.setForeground(COR_TEXTO_SECUNDARIO);
        separador.setForeground(COR_BORDA);

        JPanel informacoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        informacoes.setBackground(COR_FUNDO);
        informacoes.add(sobre);
        informacoes.add(separador);
        informacoes.add(versao);
        painel.add(autor, BorderLayout.WEST);
        painel.add(informacoes, BorderLayout.EAST);
        return painel;
    }

    private JLabel criarLinkSobre() {
        final JLabel sobre = new JLabel("Sobre");
        sobre.setFont(sobre.getFont().deriveFont(11f));
        sobre.setForeground(COR_PRINCIPAL);
        sobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sobre.setToolTipText("Informações sobre o aplicativo");
        sobre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                abrirSobre();
            }
        });
        return sobre;
    }

    private void abrirSobre() {
        final JDialog dialogo = new JDialog(
                this, "Sobre — Cálculo de Matéria-Prima", true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setLayout(new BorderLayout(0, 16));

        JPanel conteudo = new JPanel();
        conteudo.setBackground(COR_PAINEL);
        conteudo.setBorder(BorderFactory.createEmptyBorder(22, 26, 8, 26));
        conteudo.setLayout(new javax.swing.BoxLayout(conteudo, javax.swing.BoxLayout.Y_AXIS));

        JLabel nome = new JLabel("Cálculo de Matéria-Prima");
        nome.setFont(nome.getFont().deriveFont(Font.BOLD, 19f));
        nome.setForeground(COR_TITULO);
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel versao = new JLabel("Versão " + VersaoAplicacao.ATUAL);
        versao.setForeground(COR_TEXTO_SECUNDARIO);
        versao.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descricao = new JLabel(
                "<html><div style='width: 500px; color: #374151;'>"
                        + "<p>Desenvolvido por Rafael C. Pinto.</p>"
                        + "<p>Aplicação criada originalmente em 2015 para atender a uma "
                        + "necessidade real de cálculo de matéria-prima.</p>"
                        + "<p>O programa permanece em uso desde sua implantação e recebeu "
                        + "atualizações para inclusão de novas funcionalidades.</p>"
                        + "<p>A versão atual reorganiza a interface, melhora as validações, "
                        + "adiciona testes e facilita a distribuição, preservando as regras de "
                        + "cálculo utilizadas no contexto original.</p>"
                        + "<p>Os resultados são estimativas para apoio ao processo de fabricação "
                        + "e devem ser conferidos conforme o material, o processo e a norma "
                        + "aplicável.</p></div></html>");
        descricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        conteudo.add(nome);
        conteudo.add(javax.swing.Box.createVerticalStrut(4));
        conteudo.add(versao);
        conteudo.add(javax.swing.Box.createVerticalStrut(12));
        conteudo.add(descricao);
        dialogo.add(conteudo, BorderLayout.CENTER);

        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(evento -> dialogo.dispose());
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBackground(COR_PAINEL);
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 18, 12, 18));
        rodape.add(fechar);
        dialogo.add(rodape, BorderLayout.SOUTH);

        dialogo.pack();
        dialogo.setResizable(false);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private JPanel criarRotuloComAjuda(JLabel rotulo, JLabel ajuda) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        painel.setBackground(COR_PAINEL);
        rotulo.setForeground(COR_TEXTO);
        painel.add(rotulo);
        painel.add(ajuda);
        return painel;
    }

    private JLabel criarAjuda(String texto) {
        Icon iconeAjuda = Icones.carregar("help", 14);
        final JLabel ajuda = new JLabel(iconeAjuda);
        if (iconeAjuda == null) {
            ajuda.setText("?");
        }
        ajuda.setToolTipText(formatarTooltip(texto));
        ajuda.setHorizontalAlignment(SwingConstants.CENTER);
        ajuda.setFont(ajuda.getFont().deriveFont(Font.BOLD));
        ajuda.setForeground(COR_PRINCIPAL);
        ajuda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ajuda.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                Object caminho = ajuda.getClientProperty("caminhoImagem");
                Object titulo = ajuda.getClientProperty("tituloAjuda");
                if (caminho instanceof String && titulo instanceof String) {
                    abrirImagemAjuda(
                            (String) caminho,
                            (String) titulo,
                            (String) ajuda.getClientProperty("textoClique")
                    );
                }
            }
        });
        return ajuda;
    }

    private String formatarTooltip(String texto) {
        return "<html><div style='width: 320px;'>" + texto + "</div></html>";
    }

    private String formatarTextoAjuda(String texto) {
        return "<html><div style='width: 860px;'>" + texto + "</div></html>";
    }

    private void atualizarAjuda(JLabel ajuda, String texto) {
        ajuda.setToolTipText(formatarTooltip(texto));
    }

    private void atualizarImagemAjuda(JLabel ajuda, String caminho, String titulo) {
        ajuda.putClientProperty("caminhoImagem", caminho);
        ajuda.putClientProperty("tituloAjuda", titulo);
    }

    private void adicionarLinha(JPanel painel, int linha, Component rotulo, Component componente) {
        rotulo.setForeground(COR_TEXTO);
        JPanel areaRotulo = new JPanel(new BorderLayout());
        areaRotulo.setOpaque(false);
        Dimension tamanhoRotulo = new Dimension(
                LARGURA_ROTULO_ENTRADA,
                Math.max(ALTURA_CAMPO_ENTRADA, rotulo.getPreferredSize().height)
        );
        areaRotulo.setPreferredSize(tamanhoRotulo);
        areaRotulo.setMinimumSize(tamanhoRotulo);
        areaRotulo.add(rotulo, BorderLayout.CENTER);

        GridBagConstraints esquerda = restricoes(0, linha);
        esquerda.weightx = 0.0;
        esquerda.anchor = GridBagConstraints.LINE_START;
        esquerda.insets = new Insets(5, 6, 5, 6);
        painel.add(areaRotulo, esquerda);

        GridBagConstraints direita = restricoes(1, linha);
        direita.weightx = 1.0;
        direita.fill = GridBagConstraints.HORIZONTAL;
        direita.insets = new Insets(5, 6, 5, 6);
        painel.add(componente, direita);
    }

    private void adicionarLinhaResultado(
            JPanel painel, int linha, JLabel rotulo, JTextField resultado) {
        adicionarLinha(painel, linha, rotulo, criarResultadoComCopia(resultado));
    }

    private JPanel criarResultadoComCopia(final JTextField resultado) {
        return criarResultadoComCopia(resultado, criarEspacoEquivalente(null));
    }

    private JPanel criarResultadoComCopia(
            final JTextField resultado,
            JPanel espacoEquivalente
    ) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(COR_PAINEL);

        GridBagConstraints campo = restricoes(0, 0);
        campo.insets = new Insets(0, 0, 0, 0);
        campo.anchor = GridBagConstraints.BASELINE_LEADING;
        painel.add(resultado, campo);

        GridBagConstraints equivalente = restricoes(1, 0);
        equivalente.insets = new Insets(0, 6, 0, 0);
        equivalente.anchor = GridBagConstraints.BASELINE_LEADING;
        painel.add(espacoEquivalente, equivalente);

        GridBagConstraints preenchimento = restricoes(2, 0);
        preenchimento.weightx = 1.0;
        preenchimento.fill = GridBagConstraints.HORIZONTAL;
        painel.add(javax.swing.Box.createHorizontalGlue(), preenchimento);

        GridBagConstraints copia = restricoes(3, 0);
        copia.anchor = GridBagConstraints.LINE_END;
        copia.insets = new Insets(0, 8, 0, 2);
        painel.add(criarIconeCopiar(resultado), copia);
        return painel;
    }

    private JPanel criarPainelVisualizacao() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_PAINEL);
        painel.setOpaque(false);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.add(painelVisualizacao, BorderLayout.CENTER);
        return criarCard("Visualização dinâmica", "visualization", painel);
    }

    private JPanel criarCard(String titulo, String nomeIcone, JPanel conteudo) {
        return criarCard(
                titulo,
                nomeIcone,
                conteudo,
                new Insets(12, 14, 14, 14)
        );
    }

    private JPanel criarCard(
            String titulo,
            String nomeIcone,
            JPanel conteudo,
            Insets margensCorpo
    ) {
        CardPanel card = new CardPanel();
        card.setName("card." + nomeIcone);
        card.putClientProperty("card.titulo", titulo);
        card.setLayout(new BorderLayout());

        JPanel cabecalho = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        cabecalho.setOpaque(false);
        cabecalho.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDA),
                BorderFactory.createEmptyBorder(14, 16, 13, 16)));
        JLabel rotulo = new JLabel(titulo, Icones.carregar(nomeIcone, 17),
                SwingConstants.LEFT);
        rotulo.setIconTextGap(9);
        rotulo.setFont(rotulo.getFont().deriveFont(Font.BOLD, 15f));
        rotulo.setForeground(COR_RESULTADO_DESTAQUE);
        cabecalho.add(rotulo);

        JPanel corpo = new JPanel(new BorderLayout());
        corpo.setOpaque(false);
        corpo.setBorder(BorderFactory.createEmptyBorder(
                margensCorpo.top,
                margensCorpo.left,
                margensCorpo.bottom,
                margensCorpo.right
        ));
        corpo.add(conteudo, BorderLayout.CENTER);
        card.add(cabecalho, BorderLayout.NORTH);
        card.add(corpo, BorderLayout.CENTER);
        return card;
    }

    private JPanel criarCaixaResultado(JTextField resultado) {
        JPanel caixa = new JPanel(new BorderLayout());
        caixa.setBackground(COR_PAINEL);
        Dimension tamanho = new Dimension(
                LARGURA_CAMPO_RESULTADO,
                resultado.getPreferredSize().height
        );
        caixa.setPreferredSize(tamanho);
        caixa.setMinimumSize(tamanho);
        caixa.setMaximumSize(tamanho);
        caixa.add(resultado, BorderLayout.CENTER);
        return caixa;
    }

    private JPanel criarResultadoDiametro() {
        configurarEquivalente(equivalenteDiametro);
        return criarResultadoComCopia(
                resultado1,
                criarEspacoEquivalente(equivalenteDiametro)
        );
    }

    private JPanel criarResultadoComEquivalente(
            JTextField resultado,
            JLabel equivalente
    ) {
        configurarEquivalente(equivalente);
        return criarResultadoComCopia(
                resultado,
                criarEspacoEquivalente(equivalente)
        );
    }

    private void configurarEquivalente(JLabel equivalente) {
        equivalente.setForeground(new Color(71, 84, 103));
        equivalente.setFont(equivalente.getFont().deriveFont(Font.BOLD, 11.5f));
        equivalente.setVerticalAlignment(SwingConstants.CENTER);
        equivalente.setToolTipText("Equivalente do valor comercial em milímetros.");
        equivalente.setVisible(false);
    }

    private JPanel criarEspacoEquivalente(Component conteudo) {
        JPanel espaco = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
            @Override
            public int getBaseline(int largura, int altura) {
                if (getComponentCount() == 0) {
                    return super.getBaseline(largura, altura);
                }
                Component componente = getComponent(0);
                Dimension tamanho = componente.getPreferredSize();
                int linhaBase = componente.getBaseline(tamanho.width, tamanho.height);
                return linhaBase < 0
                        ? super.getBaseline(largura, altura)
                        : (altura - tamanho.height) / 2 + linhaBase;
            }
        };
        espaco.setBackground(COR_PAINEL);
        Dimension tamanho = new Dimension(
                LARGURA_EQUIVALENTE_RESULTADO,
                resultado1.getPreferredSize().height
        );
        espaco.setPreferredSize(tamanho);
        espaco.setMinimumSize(tamanho);
        if (conteudo != null) {
            espaco.add(conteudo);
        }
        return espaco;
    }

    private JButton criarIconeCopiar(final JTextField resultado) {
        Icon icone = Icones.carregar("copy", 15);
        final JButton copiar = new JButton(icone);
        if (icone == null) {
            copiar.setText("Copiar");
        }
        copiar.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension tamanho = new Dimension(28, 28);
        copiar.setPreferredSize(tamanho);
        copiar.setMinimumSize(tamanho);
        copiar.setMaximumSize(tamanho);
        copiar.setMargin(new Insets(0, 0, 0, 0));
        copiar.setForeground(COR_PRINCIPAL);
        copiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copiar.setToolTipText("Copiar");
        copiar.setFocusable(true);
        copiar.putClientProperty("JButton.buttonType", "toolBarButton");
        copiar.putClientProperty(FlatClientProperties.STYLE,
                "borderWidth: 0; focusWidth: 1; arc: 8");
        copiar.addActionListener(evento -> copiarResultado(resultado));
        return copiar;
    }

    private void copiarResultado(JTextField resultado) {
        String valor = resultado.getText();
        if (valor == null || valor.trim().isEmpty() || "—".equals(valor)) {
            return;
        }
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new StringSelection(valor), null);
        } catch (IllegalStateException ex) {
            mensagem.setText("Não foi possível copiar o valor. Tente novamente.");
        }
    }

    private GridBagConstraints restricoes(int coluna, int linha) {
        GridBagConstraints restricoes = new GridBagConstraints();
        restricoes.gridx = coluna;
        restricoes.gridy = linha;
        restricoes.insets = new Insets(7, 8, 7, 8);
        restricoes.anchor = GridBagConstraints.LINE_START;
        return restricoes;
    }

    private JTextField criarValorResultado() {
        JTextField valor = new JTextField("—", 14);
        Dimension tamanho = new Dimension(
                LARGURA_CAMPO_RESULTADO,
                valor.getPreferredSize().height
        );
        valor.setPreferredSize(tamanho);
        valor.setMinimumSize(tamanho);
        valor.setEditable(false);
        valor.setFocusable(true);
        valor.setHorizontalAlignment(SwingConstants.LEFT);
        valor.setBorder(null);
        valor.setOpaque(false);
        valor.setForeground(COR_TEXTO);
        return valor;
    }

    private void atualizarPerfil() {
        if (isPerfilCilindrico()) {
            rotuloValor1.setText("Diâmetro acabado (mm)");
            rotuloValor2.setText("Comprimento da peça (mm)");
            rotuloValor3.setText("Adicional para fixação (mm)");
            atualizarAjuda(ajudaValor1, AJUDA_DIAMETRO);
            ajudaValor1.putClientProperty("textoClique", null);
            atualizarAjuda(ajudaValor2, AJUDA_COMPRIMENTO);
            atualizarAjuda(ajudaValor3, AJUDA_ADICIONAL);
            atualizarImagemAjuda(ajudaValor1, "/images/sobremetal-dimensoes.png",
                    "Ajuda — Diâmetro e sobremetal");
            atualizarImagemAjuda(ajudaValor2, "/images/sobremetal-comprimento.png",
                    "Ajuda — Comprimento e fixação");
            atualizarImagemAjuda(ajudaValor3, "/images/sobremetal-comprimento.png",
                    "Ajuda — Comprimento e fixação");
            rotuloResultado1.setText("Diâmetro comercial");
            rotuloResultado2.setText("Comprimento total");
            rotuloResultado3.setText("Sobremetal por lado");
        } else {
            rotuloValor1.setText("Largura (mm)");
            rotuloValor2.setText("Altura (mm)");
            rotuloValor3.setText("Comprimento (mm)");
            atualizarAjuda(ajudaValor1, AJUDA_LARGURA);
            ajudaValor1.putClientProperty("textoClique", null);
            atualizarAjuda(ajudaValor2, AJUDA_ALTURA);
            atualizarAjuda(ajudaValor3, AJUDA_COMPRIMENTO);
            atualizarImagemAjuda(ajudaValor1, "/images/sobremetal-dimensoes.png",
                    "Ajuda — Dimensões e sobremetal");
            atualizarImagemAjuda(ajudaValor2, "/images/sobremetal-dimensoes.png",
                    "Ajuda — Dimensões e sobremetal");
            atualizarImagemAjuda(ajudaValor3, "/images/sobremetal-comprimento.png",
                    "Ajuda — Comprimento e fixação");
            rotuloResultado1.setText("Largura comercial");
            rotuloResultado2.setText("Altura comercial");
            rotuloResultado3.setText("Comprimento comercial");
        }
        limparResultados();
    }

    private void destacarResultado(JTextField resultado) {
        resultado.setFont(resultado.getFont().deriveFont(Font.BOLD, 14f));
        resultado.setForeground(COR_RESULTADO_DESTAQUE);
    }

    private void normalizarResultado(JTextField resultado) {
        resultado.setFont(resultado.getFont().deriveFont(Font.PLAIN, 12f));
        resultado.setForeground(COR_TEXTO);
    }

    private void limpar() {
        valor1.setText("");
        valor2.setText("");
        valor3.setText("");
        densidadePersonalizada.setText("");
        otimizar.setSelected(false);
        limparResultados();
        valor1.requestFocusInWindow();
    }

    private void limparResultados() {
        resultado1.setText("—");
        resultado2.setText("—");
        resultado3.setText("—");
        resultadoMassa.setText("—");
        equivalenteDiametro.setText("");
        equivalenteDiametro.setVisible(false);
        limparEquivalente(equivalenteResultado2);
        limparEquivalente(equivalenteResultado3);
        normalizarResultado(resultado1);
        normalizarResultado(resultado2);
        normalizarResultado(resultado3);
        normalizarResultado(resultadoMassa);
        mensagem.setText(" ");
    }

    public String getValor1() {
        return valor1.getText();
    }

    public String getValor2() {
        return valor2.getText();
    }

    public String getValor3() {
        return valor3.getText();
    }

    public boolean isPerfilCilindrico() {
        return perfilCilindrico.isSelected();
    }

    public boolean isOtimizar() {
        return otimizar.isSelected();
    }

    public PadraoDimensional getPadraoDimensional() {
        if (padraoMetrico.isSelected()) {
            return PadraoDimensional.METRICO;
        }
        if (!padraoPolegada.isSelected()) {
            padraoPolegada.setSelected(true);
        }
        return PadraoDimensional.POLEGADA;
    }

    public PadraoDimensional getPadraoDimensionalCilindrico() {
        return getPadraoDimensional();
    }

    public Material getMaterialSelecionado() {
        if (isDensidadePersonalizada()) {
            Double densidade = lerDensidadePersonalizada();
            if (densidade != null) {
                return new Material("Personalizado", densidade / 1_000_000);
            }
        }
        return (Material) material.getSelectedItem();
    }

    public void mostrarResultado(
            String primeiro, String segundo, String terceiro, String massa) {
        resultado1.setText(primeiro);
        resultado2.setText(segundo);
        resultado3.setText(terceiro);
        resultadoMassa.setText(massa);
        equivalenteDiametro.setText("");
        equivalenteDiametro.setVisible(false);
        limparEquivalente(equivalenteResultado2);
        limparEquivalente(equivalenteResultado3);
        if (isPerfilCilindrico()) {
            destacarResultado(resultado1);
        }
        destacarResultado(resultado2);
        destacarResultado(resultado3);
        destacarResultado(resultadoMassa);
        mensagem.setText(" ");
        painelVisualizacao.limpar(isPerfilCilindrico());
    }

    public void mostrarResultadoCilindrico(
            String diametroPrincipal,
            String equivalenteMilimetros,
            String comprimentoTotal,
            String sobremetalPorBanda,
            String massa
    ) {
        resultado1.setText(diametroPrincipal);
        resultado2.setText(comprimentoTotal);
        resultado3.setText(sobremetalPorBanda);
        resultadoMassa.setText(massa);

        boolean mostrarEquivalente = equivalenteMilimetros != null
                && !equivalenteMilimetros.trim().isEmpty();
        equivalenteDiametro.setText(mostrarEquivalente ? equivalenteMilimetros : "");
        equivalenteDiametro.setVisible(mostrarEquivalente);

        destacarResultado(resultado1);
        destacarResultado(resultado2);
        destacarResultado(resultado3);
        destacarResultado(resultadoMassa);
        mensagem.setText(" ");
        revalidate();
        repaint();
    }

    public void mostrarResultadoRetangular(
            String larguraPrincipal,
            String larguraEquivalente,
            String alturaPrincipal,
            String alturaEquivalente,
            String comprimentoPrincipal,
            String comprimentoEquivalente,
            String massa
    ) {
        resultado1.setText(larguraPrincipal);
        resultado2.setText(alturaPrincipal);
        resultado3.setText(comprimentoPrincipal);
        resultadoMassa.setText(massa);
        atualizarEquivalente(equivalenteDiametro, larguraEquivalente);
        atualizarEquivalente(equivalenteResultado2, alturaEquivalente);
        atualizarEquivalente(equivalenteResultado3, comprimentoEquivalente);
        destacarResultado(resultado1);
        destacarResultado(resultado2);
        destacarResultado(resultado3);
        destacarResultado(resultadoMassa);
        mensagem.setText(" ");
        revalidate();
        repaint();
    }

    public void mostrarVisualizacaoCilindrica(
            String diametroBruto,
            String diametroFinal,
            String sobremetalPorBanda,
            String comprimentoFinal,
            String comprimentoTotal
    ) {
        painelVisualizacao.mostrarCilindrico(
                diametroBruto,
                diametroFinal,
                sobremetalPorBanda,
                comprimentoFinal,
                comprimentoTotal
        );
    }

    public void mostrarVisualizacaoRetangular(
            String larguraBruta,
            String alturaBruta,
            String comprimentoBruto,
            String larguraFinal,
            String alturaFinal,
            String comprimentoFinal
    ) {
        painelVisualizacao.mostrarRetangular(
                larguraBruta,
                alturaBruta,
                comprimentoBruto,
                larguraFinal,
                alturaFinal,
                comprimentoFinal
        );
    }

    private void atualizarEquivalente(JLabel equivalente, String texto) {
        boolean mostrar = texto != null && !texto.trim().isEmpty();
        equivalente.setText(mostrar ? texto : "");
        equivalente.setVisible(mostrar);
    }

    private void limparEquivalente(JLabel equivalente) {
        equivalente.setText("");
        equivalente.setVisible(false);
    }

    public void mostrarValorInvalido() {
        limparResultados();
        mensagem.setText("Revise os valores informados e tente novamente.");
    }
}
