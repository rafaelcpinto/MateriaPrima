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
        double sobremetalTabela = calcularSobremetal(diametroAcabado);
        int indice = selecionarIndiceDiametro(
                diametroAcabado + sobremetalTabela,
                reduzir
        );
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosComerciais();
        DiametroComercial selecionado = diametros[indice];
        double diametroSelecionado = selecionado.getMilimetros();
        double sobremetalCalculado = diametroSelecionado - diametroAcabado;
        double massa = calcularMassaCilindro(diametroSelecionado, comprimento);
        return new ResultadoCilindrico(
                diametroSelecionado,
                selecionado.getPolegadas(),
                sobremetalCalculado,
                massa
        );
    }
    public ResultadoRetangular calcularRetangular(double a,double b,double c,boolean REDUZ){
        double factor;
        if(REDUZ){
            factor =0.5;
        }
        else{
            factor =1.0;
        }
        double lado1 = (int)(1+a+calcularSobremetal(a)*factor);
        double lado2 = (int)(1+b+calcularSobremetal(b)*factor);
        double lado3 = (int)(1+c+calcularSobremetal(c)*factor);
        
        double massa = lado1*lado2*lado3*material.getDensidade();
        return new ResultadoRetangular(lado1, lado2, lado3, massa);
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
   private int selecionarIndiceDiametro(double diametroNecessario, boolean reduzir) {
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosComerciais();
        int indice = localizarIntervalo(diametroNecessario, diametros);
        return reduzir ? indice : indice + 1;
    }
    private int localizarIntervalo(double valor, DiametroComercial[] diametros) {
        if (!Double.isFinite(valor) || diametros == null || diametros.length < 2) {
            throw new IllegalArgumentException("Dados inválidos para localizar o intervalo.");
        }

        double primeiro = diametros[0].getMilimetros();
        double ultimo = diametros[diametros.length - 1].getMilimetros();
        if (valor < primeiro || valor >= ultimo) {
            throw new IllegalArgumentException("Valor fora dos limites da tabela: " + valor);
        }

        for (int indice = 0; indice < diametros.length - 1; indice++) {
            double atual = diametros[indice].getMilimetros();
            double proximo = diametros[indice + 1].getMilimetros();
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
