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
            
         
     //O ponto de entrada da aplicação está definido na classe MateriaPrima.
         
    // --- FUNÇÃO QUE É EXECUTADA AO APERTAR O BOTÃO ----- //
         
    //é utilizada se a opção cilindrica for escolhida
    public ResultadoCilindrico calcularCilindrico(double a,double b,boolean REDUZ){
        //a = valor do diametro digitado
        //b = valor do comprimento
        //c = valor do sobremetal
        //d = valor do diametro em polegada escolhido
        double c = calculosobremetal(a);
        int indice = selecionarIndiceDiametro(a+c, REDUZ);
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosComerciais();
        DiametroComercial selecionado = diametros[indice];
        double d = selecionado.getMilimetros();
        double sobremetalCalculado = (float)d-(float)a;
        double massa = calcularMassaCilindro(d,b);
        return new ResultadoCilindrico(d, selecionado.getPolegadas(), sobremetalCalculado, massa);
    }
    //é utilizada se a opção retangular for escolhida
    public ResultadoRetangular calcularRetangular(double a,double b,double c,boolean REDUZ){
        //a = lado1
        //b = lado2
        //c = lado3
        double factor;
        if(REDUZ){
            factor =0.5;
        }
        else{
            factor =1.0;
        }
        double lado1 = (int)(1+a+calculosobremetal(a)*factor);
        double lado2 = (int)(1+b+calculosobremetal(b)*factor);
        double lado3 = (int)(1+c+calculosobremetal(c)*factor);
        
        double massa = lado1*lado2*lado3*material.getDensidade();
        return new ResultadoRetangular(lado1, lado2, lado3, massa);
    }
    //função que define o valor do sobremetal
    private double calculosobremetal(double a) {
        if (!Double.isFinite(a)) {
            throw new IllegalArgumentException("Valor inválido: " + a);
        }
        for (FaixaSobremetal faixa : TabelasMateriaPrima.faixasSobremetal()) {
            if (faixa.contem(a)) {
                return faixa.getSobremetal();
            }
        }
        throw new IllegalArgumentException("Valor fora dos limites da tabela: " + a);
   }
   //Escolhe a posição do diâmetro comercial mais próximo.
   private int selecionarIndiceDiametro(double a,boolean REDUZ) {
        DiametroComercial[] diametros = TabelasMateriaPrima.diametrosComerciais();
        int indice = localizarIntervalo(a, diametros);
        return REDUZ ? indice : indice + 1;
    }
   //METODO DE COMPARAÇÃO DE VALORES UTILIZADO PARA ESCOLHA DOS VALORES DE SOBREMETAL E DIAMETRO, é chamada pelos metodos calculosobremetal() e calculodiametro()
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
    
    //METODO UTILIZADO PARA REALIZAR OS CALCULOS NECESSARIOS
    
    private double calcularMassaCilindro(double diametro,double comprimento){
        double raio = diametro/2;
        double volume;
        volume = raio*raio*Math.PI*comprimento;
        return volume * material.getDensidade();
        //percentual = (diametroDigitado/diametroPoelegada)*100; //nao esta sendo utilizada
        
    }
}
