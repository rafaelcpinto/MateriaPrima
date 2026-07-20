package materiaprima.modelo;

import materiaprima.dados.TabelasMateriaPrima;

public class CalcMateriaPrima{
        
         private final Material material;

    public CalcMateriaPrima() {
        this(0);
    }

    public CalcMateriaPrima(int posicaoDensidade) {
        this(TabelasMateriaPrima.material(posicaoDensidade));
    }

    public CalcMateriaPrima(Material material) {
        if (material == null) {
            throw new IllegalArgumentException("Material é obrigatório");
        }
        this.material = material;
    }
            
         
    public ResultadoCilindrico calcularCilindrico(
            double diametroAcabado,
            double comprimento,
            boolean reduzir
    ) {
        return calcularCilindrico(
                diametroAcabado,
                comprimento,
                reduzir,
                PadraoDimensional.POLEGADA
        );
    }

    public ResultadoCilindrico calcularCilindrico(
            double diametroAcabado,
            double comprimento,
            boolean reduzir,
            PadraoDimensional padrao
    ) {
        if (padrao == null) {
            throw new IllegalArgumentException(
                    "O padrão dimensional é obrigatório."
            );
        }

        double sobremetalTabela = calcularSobremetal(diametroAcabado);
        double diametroNecessario = padrao == PadraoDimensional.METRICO
                ? diametroAcabado + sobremetalTabela * (reduzir ? 0.5 : 1.0)
                : diametroAcabado + sobremetalTabela;
        DimensaoComercial selecionado;
        if (padrao == PadraoDimensional.METRICO) {
            selecionado = selecionarDimensaoMetrica(diametroNecessario);
        } else {
            selecionado = selecionarDimensaoPolegada(diametroNecessario, reduzir);
        }
        double diametroSelecionado = selecionado.getMilimetros();
        double sobremetalCalculado = diametroSelecionado - diametroAcabado;
        double massa = calcularMassaCilindro(diametroSelecionado, comprimento);
        return new ResultadoCilindrico(
                diametroSelecionado,
                selecionado.getDescricao(),
                comprimento,
                sobremetalCalculado,
                massa
        );
    }
    public ResultadoRetangular calcularRetangular(
            double largura,
            double altura,
            double comprimento,
            boolean reduzir
    ) {
        return calcularRetangular(
                largura,
                altura,
                comprimento,
                reduzir,
                PadraoDimensional.METRICO
        );
    }

    public ResultadoRetangular calcularRetangular(
            double larguraAcabada,
            double alturaAcabada,
            double comprimentoAcabado,
            boolean reduzir,
            PadraoDimensional padrao
    ) {
        if (padrao == null) {
            throw new IllegalArgumentException(
                    "O padrão dimensional é obrigatório."
            );
        }

        double fator = reduzir ? 0.5 : 1.0;
        DimensaoComercial largura = selecionarDimensao(
                1.0 + larguraAcabada + calcularSobremetal(larguraAcabada) * fator,
                reduzir,
                padrao
        );
        DimensaoComercial altura = selecionarDimensao(
                1.0 + alturaAcabada + calcularSobremetal(alturaAcabada) * fator,
                reduzir,
                padrao
        );
        DimensaoComercial comprimento = selecionarDimensao(
                1.0 + comprimentoAcabado + calcularSobremetal(comprimentoAcabado) * fator,
                reduzir,
                padrao
        );

        double massa = largura.getMilimetros()
                * altura.getMilimetros()
                * comprimento.getMilimetros()
                * material.getDensidade();
        return new ResultadoRetangular(
                largura.getMilimetros(),
                altura.getMilimetros(),
                comprimento.getMilimetros(),
                largura.getDescricao(),
                altura.getDescricao(),
                comprimento.getDescricao(),
                massa
        );
    }
    private double calcularSobremetal(double dimensao) {
        if (!Double.isFinite(dimensao)) {
            throw new IllegalArgumentException("Valor inválido: " + dimensao);
        }
        for (FaixaSobremetal faixa : TabelasMateriaPrima.faixasSobremetal()) {
            if (faixa.contem(dimensao)) {
                return faixa.getSobremetal();
            }
        }
        throw new IllegalArgumentException("Valor fora dos limites da tabela: " + dimensao);
   }
    private DimensaoComercial selecionarDimensao(
            double dimensaoNecessariaMm,
            boolean reduzir,
            PadraoDimensional padrao
    ) {
        return padrao == PadraoDimensional.METRICO
                ? selecionarDimensaoMetrica(dimensaoNecessariaMm)
                : selecionarDimensaoPolegada(dimensaoNecessariaMm, reduzir);
    }

    private DimensaoComercial selecionarDimensaoMetrica(double dimensaoNecessariaMm) {
        if (!Double.isFinite(dimensaoNecessariaMm) || dimensaoNecessariaMm <= 0) {
            throw new IllegalArgumentException(
                    "Dimensão necessária inválida: " + dimensaoNecessariaMm
            );
        }

        double dimensaoSelecionadaMm = Math.ceil(dimensaoNecessariaMm);
        return new DimensaoComercial(
                dimensaoSelecionadaMm,
                formatarMilimetros(dimensaoSelecionadaMm),
                PadraoDimensional.METRICO
        );
    }

    private String formatarMilimetros(double valorMm) {
        return String.format(
                java.util.Locale.forLanguageTag("pt-BR"),
                "%.0f mm",
                valorMm
        );
    }

    private DimensaoComercial selecionarDimensaoPolegada(
            double dimensaoNecessariaMm,
            boolean reduzir
    ) {
        DimensaoComercial[] dimensoes = TabelasMateriaPrima.dimensoesPolegada();
        int indice = localizarIntervalo(dimensaoNecessariaMm, dimensoes);
        return dimensoes[reduzir ? indice : indice + 1];
    }
    private int localizarIntervalo(double valor, DimensaoComercial[] dimensoes) {
        if (!Double.isFinite(valor) || dimensoes == null || dimensoes.length < 2) {
            throw new IllegalArgumentException("Dados inválidos para localizar o intervalo.");
        }

        double primeiro = dimensoes[0].getMilimetros();
        double ultimo = dimensoes[dimensoes.length - 1].getMilimetros();
        if (valor < primeiro || valor >= ultimo) {
            throw new IllegalArgumentException("Valor fora dos limites da tabela: " + valor);
        }

        for (int indice = 0; indice < dimensoes.length - 1; indice++) {
            double atual = dimensoes[indice].getMilimetros();
            double proximo = dimensoes[indice + 1].getMilimetros();
            if (atual <= valor && valor < proximo) {
                return indice;
            }
        }

        throw new IllegalArgumentException(
                "Intervalo não encontrado para o valor: " + valor
        );
    }
    
    private double calcularMassaCilindro(double diametro, double comprimento) {
        double raio = diametro / 2.0;
        double volume = raio * raio * Math.PI * comprimento;
        return volume * material.getDensidade();
    }
}
