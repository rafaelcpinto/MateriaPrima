package materiaprima.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import materiaprima.aplicacao.VersaoAplicacao;
import materiaprima.dados.TabelasMateriaPrima;
import materiaprima.modelo.DimensaoComercial;
import materiaprima.modelo.FaixaSobremetal;
import materiaprima.modelo.Material;

/** Informações históricas e referência técnica da aplicação. */
public final class SobreDialog extends JDialog {

    private static final Locale PT_BR = new Locale("pt", "BR");
    private final JTabbedPane abas = new JTabbedPane();
    private final JTable tabelaMateriais;
    private final JTable tabelaFaixas;
    private final JTable tabelaDimensoes;
    private final JButton botaoFechar = new JButton("Fechar");

    public SobreDialog(Frame proprietario) {
        super(proprietario, "Sobre — Cálculo de Matéria-Prima", true);
        tabelaMateriais = criarTabelaMateriais();
        tabelaFaixas = criarTabelaFaixas();
        tabelaDimensoes = criarTabelaDimensoes();
        montarInterface();
    }

    private void montarInterface() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));
        abas.addTab("Sobre", criarAbaSobre());
        abas.addTab("Materiais", criarAbaMateriais());
        abas.addTab("Tabelas", criarAbaTabelas());
        abas.addTab("Cálculo", criarAbaCalculo());
        abas.setName("abasSobre");
        add(abas, BorderLayout.CENTER);

        botaoFechar.setName("botaoFechar");
        botaoFechar.addActionListener(evento -> dispose());
        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setBorder(BorderFactory.createEmptyBorder(0, 10, 8, 10));
        rodape.add(botaoFechar);
        add(rodape, BorderLayout.SOUTH);

        setSize(new Dimension(820, 620));
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private JPanel criarAbaSobre() {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createEmptyBorder(22, 26, 8, 26));
        painel.setLayout(new javax.swing.BoxLayout(painel, javax.swing.BoxLayout.Y_AXIS));
        JLabel nome = new JLabel("Cálculo de Matéria-Prima", SwingConstants.CENTER);
        nome.setFont(nome.getFont().deriveFont(Font.BOLD, 19f));
        nome.setAlignmentX(CENTER_ALIGNMENT);
        JLabel versao = new JLabel("Versão " + VersaoAplicacao.ATUAL);
        versao.setAlignmentX(CENTER_ALIGNMENT);
        JLabel descricao = new JLabel("<html><div style='width: 650px; color: #374151;'>"
                + "<p>Desenvolvido por Rafael C. Pinto.</p>"
                + "<p>Aplicação criada originalmente em 2015 para atender a uma necessidade real de cálculo de matéria-prima.</p>"
                + "<p>O programa permanece em uso desde sua implantação e recebeu atualizações para inclusão de novas funcionalidades.</p>"
                + "<p>A versão atual reorganiza a interface, melhora as validações, adiciona testes e facilita a distribuição, preservando as regras de cálculo utilizadas no contexto original.</p>"
                + "<p>Os resultados são estimativas para apoio ao processo de fabricação e devem ser conferidos conforme o material, o processo e a norma aplicável.</p>"
                + "</div></html>");
        descricao.setAlignmentX(CENTER_ALIGNMENT);
        painel.add(nome);
        painel.add(javax.swing.Box.createVerticalStrut(4));
        painel.add(versao);
        painel.add(javax.swing.Box.createVerticalStrut(12));
        painel.add(descricao);
        return painel;
    }

    private JPanel criarAbaMateriais() {
        JPanel painel = painelComMargem();
        painel.add(new JScrollPane(tabelaMateriais), BorderLayout.CENTER);
        painel.add(criarTexto("Para utilizar outro material, selecione ‘Densidade personalizada’ no campo Material e informe a densidade em g/cm³. São aceitos ponto ou vírgula como separador decimal. O valor deve ser maior que zero e será utilizado somente no cálculo atual.\n\nA interface recebe a densidade em g/cm³; internamente ela é usada em kg/mm³, pela conversão: densidadeInterna = densidadeEmGcm3 / 1.000.000. O material personalizado não é salvo permanentemente nem adicionado à lista. Confira o valor correto na especificação do material."), BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarAbaTabelas() {
        JTabbedPane tabelas = new JTabbedPane();
        tabelas.setName("abasTabelas");
        tabelas.addTab("Faixas de sobremetal", painelTabela(tabelaFaixas,
                "O limite inicial é inclusivo e o final é exclusivo. A faixa é determinada pela dimensão acabada e seu sobremetal é um valor total, não diretamente por lado. No padrão Milímetro, a opção marcada usa 50%; no retangular em Polegada também usa 50%; no cilíndrico em Polegada permanece integral.\n\nExemplo: de 40 mm até menos de 63 mm, o recomendado é 4 mm e 50% corresponde a 2 mm."));
        tabelas.addTab("Dimensões comerciais", painelTabela(tabelaDimensoes,
                "A tabela imperial é usada no padrão Polegada. No perfil cilíndrico representa o diâmetro comercial; no retangular é aplicada separadamente à largura, altura e comprimento. Sem ‘Permitir dimensão abaixo da recomendada’, seleciona-se a dimensão superior do intervalo; com a opção marcada, pode-se selecionar a inferior. No retangular em Polegada também é aplicado 50% do sobremetal; no cilíndrico em Polegada ele permanece integral.\n\nO padrão Milímetro não utiliza uma tabela comercial cadastrada. A dimensão necessária é arredondada para cima com Math.ceil()."));
        JPanel painel = painelComMargem();
        painel.add(tabelas, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarAbaCalculo() {
        JEditorPane texto = new JEditorPane("text/html", htmlCalculo());
        texto.setName("textoCalculo");
        texto.setEditable(false);
        texto.setCaretPosition(0);
        return envolver(new JScrollPane(texto));
    }

    private JTable criarTabelaMateriais() {
        Material[] materiais = TabelasMateriaPrima.materiais();
        Object[][] dados = new Object[materiais.length][2];
        NumberFormat formato = formato(2, 2);
        for (int i = 0; i < materiais.length; i++) {
            dados[i][0] = materiais[i].getNome();
            dados[i][1] = formato.format(materiais[i].getDensidade() * 1_000_000);
        }
        return criarTabela("tabelaMateriais", dados, new String[]{"Material", "Densidade (g/cm³)"});
    }

    private JTable criarTabelaFaixas() {
        FaixaSobremetal[] faixas = TabelasMateriaPrima.faixasSobremetal();
        Object[][] dados = new Object[faixas.length][4];
        NumberFormat formato = formato(0, 2);
        for (int i = 0; i < faixas.length; i++) {
            dados[i][0] = formato.format(faixas[i].getLimiteMinimo());
            dados[i][1] = formato.format(faixas[i].getLimiteMaximo());
            dados[i][2] = formato.format(faixas[i].getSobremetal());
            dados[i][3] = formato.format(faixas[i].getSobremetal() * 0.5);
        }
        return criarTabela("tabelaFaixas", dados, new String[]{"De (mm)", "Até (mm)", "Sobremetal recomendado (mm)", "50% do sobremetal (mm)"});
    }

    private JTable criarTabelaDimensoes() {
        DimensaoComercial[] dimensoes = TabelasMateriaPrima.dimensoesPolegada();
        Object[][] dados = new Object[dimensoes.length][2];
        NumberFormat formato = formato(0, 4);
        for (int i = 0; i < dimensoes.length; i++) {
            dados[i][0] = dimensoes[i].getDescricao().replaceAll("\\s+", " ") + "\"";
            dados[i][1] = formato.format(dimensoes[i].getMilimetros());
        }
        return criarTabela("tabelaDimensoes", dados, new String[]{"Polegada", "Equivalente em milímetros"});
    }

    private JTable criarTabela(String nome, Object[][] dados, String[] colunas) {
        JTable tabela = new JTable(new DefaultTableModel(dados, colunas) {
            @Override public boolean isCellEditable(int linha, int coluna) { return false; }
        });
        tabela.setName(nome);
        tabela.setRowHeight(24);
        tabela.setFillsViewportHeight(true);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        return tabela;
    }

    private JPanel painelTabela(JTable tabela, String explicacao) {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painel.add(criarTexto(explicacao), BorderLayout.SOUTH);
        return painel;
    }

    private JTextArea criarTexto(String conteudo) {
        JTextArea texto = new JTextArea(conteudo, 6, 20);
        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setOpaque(false);
        texto.setBorder(BorderFactory.createEmptyBorder(6, 2, 2, 2));
        return texto;
    }

    private JPanel painelComMargem() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return painel;
    }

    private JPanel envolver(JScrollPane rolagem) {
        JPanel painel = painelComMargem();
        painel.add(rolagem, BorderLayout.CENTER);
        return painel;
    }

    private NumberFormat formato(int minimo, int maximo) {
        NumberFormat formato = NumberFormat.getNumberInstance(PT_BR);
        formato.setMinimumFractionDigits(minimo);
        formato.setMaximumFractionDigits(maximo);
        formato.setGroupingUsed(false);
        return formato;
    }

    private String htmlCalculo() {
        return "<html><head><style>body{font-family:sans-serif;font-size:12px;color:#374151;margin:14px}h2{color:#172033}code{font-family:monospace}</style></head><body>"
                + "<h2>1. Faixas de sobremetal</h2><p>O programa localiza a faixa correspondente à dimensão acabada e obtém o sobremetal recomendado. O mínimo é inclusivo e o máximo exclusivo. O sobremetal é total e não representa diretamente o valor por lado.</p>"
                + "<h2>2. Dimensão abaixo da recomendada</h2><p>A opção ‘Permitir dimensão abaixo da recomendada’ preserva as regras históricas de cada perfil e padrão dimensional.</p><ul><li>Cilíndrico — Milímetro: 50% do sobremetal e Math.ceil().</li><li>Cilíndrico — Polegada: 100%; desmarcada escolhe a dimensão superior e marcada a inferior.</li><li>Retangular — Milímetro: 50%, acréscimo de 1 mm e Math.ceil().</li><li>Retangular — Polegada: 50%, acréscimo de 1 mm e dimensão inferior.</li></ul>"
                + "<h2>3. Perfil cilíndrico</h2><p><code>sobremetalTabela = valor localizado na faixa</code></p><p>Métrico: <code>fator = marcada ? 0,5 : 1,0</code><br><code>diâmetro necessário = diâmetro acabado + sobremetalTabela × fator</code><br><code>diâmetro comercial = Math.ceil(diâmetro necessário)</code></p><p>Imperial: <code>diâmetro necessário = diâmetro acabado + sobremetalTabela</code>. Sem otimização seleciona o limite superior; com otimização, o inferior.</p><p><code>sobremetalReal = diâmetro comercial - diâmetro acabado</code><br><code>sobremetalPorLado = sobremetalReal / 2</code><br><code>comprimentoTotal = comprimento da peça + adicional para fixação</code><br><code>raio = diâmetro comercial / 2</code><br><code>volume = π × raio² × comprimentoTotal</code><br><code>massa = volume × densidade</code></p><p>Exemplo: 40 mm, faixa de 4 mm. Sem otimização: 40 + 4 = 44 mm. Com: 40 + 4 × 0,5 = 42 mm; Math.ceil(42) = 42 mm.</p>"
                + "<h2>4. Perfil retangular</h2><p>Para largura, altura e comprimento: <code>fator = marcada ? 0,5 : 1,0</code>; <code>dimensão necessária = 1 mm + dimensão acabada + sobremetalTabela × fator</code>. O 1 mm pertence à regra histórica. No métrico: Math.ceil(). No imperial, sem otimização seleciona a superior; com otimização aplica 50% e seleciona a inferior.</p><p><code>volume = largura comercial × altura comercial × comprimento comercial</code><br><code>massa = volume × densidade</code></p>"
                + "<h2>5. Seleção em Milímetro</h2><p>Não existe tabela métrica. Todas as entradas permanecem em milímetros e Math.ceil() seleciona o próximo número inteiro em milímetros.</p>"
                + "<h2>6. Seleção em Polegada</h2><p>O programa consulta TabelasMateriaPrima.dimensoesPolegada(), localiza o intervalo da dimensão necessária e identifica os itens inferior e superior. Sem otimização seleciona o superior; com otimização, o inferior. A seleção usa os valores comerciais cadastrados, não uma conversão simples para fração.</p>"
                + "<h2>7. Cálculo da massa</h2><p>Dimensões internas em milímetros, volume em mm³, densidade interna em kg/mm³ e massa final em quilogramas.</p>"
                + "<h2>8. Densidade personalizada</h2><p><code>densidade em kg/mm³ = densidade em g/cm³ / 1.000.000</code>. O valor vale somente para o cálculo atual.</p>"
                + "<h2>9. Aviso técnico</h2><p>Os resultados são estimativas para apoio ao processo de fabricação. A utilização de dimensão abaixo da recomendação deve ser conferida conforme o material, o processo de usinagem, a fixação e a norma aplicável.</p>"
                + "</body></html>";
    }
}
