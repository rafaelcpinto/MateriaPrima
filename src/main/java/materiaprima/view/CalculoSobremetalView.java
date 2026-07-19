package materiaprima.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import materiaprima.aplicacao.VersaoAplicacao;
import materiaprima.controller.CalculoSobremetalController;
import materiaprima.dados.TabelasMateriaPrima;
import materiaprima.modelo.DiametroComercial;
import materiaprima.modelo.Material;

public class CalculoSobremetalView extends JFrame {

    private static final Color COR_FUNDO = new Color(244, 246, 248);
    private static final Color COR_PAINEL = Color.WHITE;
    private static final Color COR_TITULO = new Color(31, 41, 55);
    private static final Color COR_TEXTO = new Color(55, 65, 81);
    private static final Color COR_TEXTO_SECUNDARIO = new Color(107, 114, 128);
    private static final Color COR_PRINCIPAL = new Color(37, 99, 235);
    private static final Color COR_RESULTADO_DESTAQUE = new Color(30, 58, 95);
    private static final Color COR_BORDA = new Color(203, 213, 225);
    private static final Color COR_ERRO = new Color(220, 38, 38);
    private static final int LARGURA_MAXIMA_IMAGEM_AJUDA = 820;
    private static final int ALTURA_MAXIMA_IMAGEM_AJUDA = 550;
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

    private final JLabel rotuloResultado1 = new JLabel();
    private final JLabel rotuloResultado2 = new JLabel();
    private final JLabel rotuloResultado3 = new JLabel();
    private final JTextField resultado1 = criarValorResultado();
    private final JTextField resultado2 = criarValorResultado();
    private final JTextField resultado3 = criarValorResultado();
    private final JTextField resultadoMassa = criarValorResultado();

    public CalculoSobremetalView() {
        super("Cálculo de sobremetal");
        controller = new CalculoSobremetalController(this);
        configurarJanela();
        montarInterface();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void montarInterface() {
        JPanel conteudo = new JPanel(new BorderLayout(0, 12));
        conteudo.setBackground(COR_FUNDO);
        conteudo.setBorder(BorderFactory.createEmptyBorder(16, 22, 10, 22));
        conteudo.add(criarCabecalho(), BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setBackground(COR_FUNDO);
        centro.setLayout(new javax.swing.BoxLayout(centro, javax.swing.BoxLayout.Y_AXIS));
        centro.add(criarPainelEntrada());
        centro.add(javax.swing.Box.createVerticalStrut(12));
        centro.add(criarPainelResultado());
        conteudo.add(centro, BorderLayout.CENTER);
        conteudo.add(criarRodape(), BorderLayout.SOUTH);

        setContentPane(conteudo);
        getRootPane().setDefaultButton(botaoCalcular);
        atualizarPerfil();
        pack();
        setSize(new Dimension(getWidth() + 70, getHeight()));
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
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 23f));
        titulo.setForeground(COR_TITULO);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Baseado na norma DIN 7527");
        subtitulo.setFont(subtitulo.getFont().deriveFont(Font.PLAIN, 13f));
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

        dialogo.setSize(new Dimension(920, 700));
        dialogo.setMinimumSize(new Dimension(600, 450));
        dialogo.setResizable(true);
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
        return new JScrollPane(conteudo);
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
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COR_BORDA), "Dados de entrada"));
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

        JLabel rotuloPerfil = new JLabel("Perfil");
        JLabel rotuloMaterial = new JLabel("Material");
        JLabel rotuloDensidade = new JLabel("Densidade (g/cm³)");
        rotuloPerfil.setLabelFor(perfilCilindrico);
        rotuloMaterial.setLabelFor(material);
        rotuloDensidade.setLabelFor(densidadePersonalizada);
        configurarDensidade();
        adicionarLinha(painel, 0, rotuloPerfil, opcoesPerfil);
        adicionarLinha(painel, 1, rotuloMaterial, material);
        adicionarLinha(painel, 2, rotuloDensidade, painelDensidade);
        adicionarLinha(painel, 3, criarRotuloComAjuda(rotuloValor1, ajudaValor1), valor1);
        adicionarLinha(painel, 4, criarRotuloComAjuda(rotuloValor2, ajudaValor2), valor2);
        adicionarLinha(painel, 5, criarRotuloComAjuda(rotuloValor3, ajudaValor3), valor3);

        otimizar.setToolTipText(
                "Pode selecionar uma dimensão comercial abaixo da recomendação padrão. "
                        + "Confirme a adequação ao processo de fabricação e à norma aplicável.");
        otimizar.setBackground(COR_PAINEL);
        otimizar.setForeground(COR_TEXTO);
        GridBagConstraints opcao = restricoes(0, 6);
        opcao.gridwidth = 2;
        opcao.insets = new Insets(12, 4, 4, 4);
        painel.add(otimizar, opcao);

        botaoCalcular.setFont(botaoCalcular.getFont().deriveFont(Font.BOLD));
        botaoCalcular.setBackground(COR_PRINCIPAL);
        botaoCalcular.setForeground(Color.WHITE);
        botaoCalcular.addActionListener(evento -> calcular());
        botaoLimpar.addActionListener(evento -> limpar());

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        botoes.setBackground(COR_PAINEL);
        botoes.add(botaoCalcular);
        botoes.add(botaoLimpar);

        GridBagConstraints areaBotoes = restricoes(0, 7);
        areaBotoes.gridwidth = 2;
        areaBotoes.fill = GridBagConstraints.HORIZONTAL;
        areaBotoes.insets = new Insets(12, 4, 2, 4);
        painel.add(botoes, areaBotoes);

        mensagem.setForeground(COR_ERRO);
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints areaMensagem = restricoes(0, 8);
        areaMensagem.gridwidth = 2;
        areaMensagem.fill = GridBagConstraints.HORIZONTAL;
        painel.add(mensagem, areaMensagem);

        rotuloValor1.setLabelFor(valor1);
        rotuloValor2.setLabelFor(valor2);
        rotuloValor3.setLabelFor(valor3);
        return painel;
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
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosPolegada();
        DiametroComercial maior = diametros[diametros.length - 1];
        String milimetros = String.format(
                new Locale("pt", "BR"), "%.1f", maior.getMilimetros());
        return "Informe o maior diâmetro final da peça após a usinagem. A aplicação "
                + "utilizará esse valor para selecionar o diâmetro comercial da "
                + "matéria-prima e calcular o sobremetal."
                + "<br><br>Limite da tabela = " + maior.getDescricao() + "\" ("
                + milimetros + " mm)";
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
        painel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COR_BORDA), "Resultado"));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        adicionarLinhaResultado(painel, 0, rotuloResultado1, resultado1);
        adicionarLinhaResultado(painel, 1, rotuloResultado2, resultado2);
        adicionarLinhaResultado(painel, 2, rotuloResultado3, resultado3);
        adicionarLinhaResultado(painel, 3, new JLabel("Massa estimada"), resultadoMassa);
        return painel;
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
        final JLabel ajuda = new JLabel("?");
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
        GridBagConstraints esquerda = restricoes(0, linha);
        esquerda.weightx = 0.0;
        esquerda.anchor = GridBagConstraints.LINE_START;
        painel.add(rotulo, esquerda);

        GridBagConstraints direita = restricoes(1, linha);
        direita.weightx = 1.0;
        direita.fill = GridBagConstraints.HORIZONTAL;
        painel.add(componente, direita);
    }

    private void adicionarLinhaResultado(
            JPanel painel, int linha, JLabel rotulo, JTextField resultado) {
        adicionarLinha(painel, linha, rotulo, criarResultadoComCopia(resultado));
    }

    private JPanel criarResultadoComCopia(final JTextField resultado) {
        JPanel painel = new JPanel(new BorderLayout(5, 0));
        painel.setBackground(COR_PAINEL);
        painel.add(resultado, BorderLayout.CENTER);
        painel.add(criarIconeCopiar(resultado), BorderLayout.EAST);
        return painel;
    }

    private JLabel criarIconeCopiar(final JTextField resultado) {
        final JLabel copiar = new JLabel("⧉");
        copiar.setFont(copiar.getFont().deriveFont(Font.BOLD, 13f));
        copiar.setForeground(COR_PRINCIPAL);
        copiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copiar.setToolTipText("Copiar este valor");
        copiar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evento) {
                copiarResultado(resultado);
            }
        });
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
        restricoes.insets = new Insets(4, 8, 4, 8);
        restricoes.anchor = GridBagConstraints.LINE_START;
        return restricoes;
    }

    private JTextField criarValorResultado() {
        JTextField valor = new JTextField("—", 14);
        valor.setEditable(false);
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
            ajudaValor1.putClientProperty("textoClique", AJUDA_DIAMETRO);
            atualizarAjuda(ajudaValor2, AJUDA_COMPRIMENTO);
            atualizarAjuda(ajudaValor3, AJUDA_ADICIONAL);
            atualizarImagemAjuda(ajudaValor1, "/images/sobremetal-dimensoes.png",
                    "Ajuda — Diâmetro e sobremetal");
            atualizarImagemAjuda(ajudaValor2, "/images/sobremetal-comprimento.png",
                    "Ajuda — Comprimento e fixação");
            atualizarImagemAjuda(ajudaValor3, "/images/sobremetal-comprimento.png",
                    "Ajuda — Comprimento e fixação");
            rotuloResultado1.setText("Diâmetro comercial");
            rotuloResultado2.setText("Equivalente");
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
        resultado.setFont(resultado.getFont().deriveFont(Font.BOLD, 15f));
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
        if (isPerfilCilindrico()) {
            destacarResultado(resultado1);
        }
        destacarResultado(resultadoMassa);
        mensagem.setText(" ");
    }

    public void mostrarValorInvalido() {
        limparResultados();
        mensagem.setText("Revise os valores informados e tente novamente.");
    }
}
