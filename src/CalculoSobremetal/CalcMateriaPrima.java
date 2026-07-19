package CalculoSobremetal;

import java.util.Scanner;
import java.text.DecimalFormat;

public class CalcMateriaPrima{
        
//variaveis globais, utilizadas para passar os valores para o Jframe
         public static double diametroPoelegada;
         public static String diametroPOrdinaria;
         public static double diametroDigitado;
         public static double massaCilindro;
         //public static double percentual; //não esta sendo utilizada
         public static double sobremetal;
         public static double densidade[]={7.85/1000000,8.50/1000000,8.93/1000000,0.93/1000000,1.14/1000000,2.70/1000000,8.80/1000000};//densidades
         private static int i;
         public static double lado1,lado2,lado3;
         public static double massaRetangular;
         public static int posicaoDensidade=0;
            
         
     //OBS: O METODO public static void main(String[] args) ESTA AO FINAL DA CLASSE POIS NÃO ESTA SENDO UTILIZADA, E METODO PRINCIPAL FOI DEFINDO NA CALSSE CalculoSobremetal;
         
    // --- FUNÇÃO QUE É EXECUTADA AO APERTAR O BOTÃO ----- //
         
    //é utilizada se a opção cilindrica for escolhida
    public void CalculoCilindrico(double a,double b,boolean REDUZ){
        //a = valor do diametro digitado
        //b = valor do comprimento
        //c = valor do sobremetal
        //d = valor do diametro em polegada escolhido
        double c;
        c = calculosobremetal(a);
        calculodiametro(a+c,REDUZ);
        double d = diametroPoelegada;  //diametroPoelegada teve seu valor definido no metodo void calculodiametro
        sobremetal = (float)d-(float)a;
        calculos(d,b);
    }
    //é utilizada se a opção retangular for escolhida
    public void CalculoRetangular(double a,double b,double c,boolean REDUZ){
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
        lado1 = (int)(1+a+calculosobremetal(a)*factor);
        lado2 = (int)(1+b+calculosobremetal(b)*factor);
        lado3 = (int)(1+c+calculosobremetal(c)*factor);
        
        massaRetangular=lado1*lado2*lado3*densidade[posicaoDensidade];
    }
    //função que define o valor do sobremetal
    private static double calculosobremetal(double a) {
        double tabLimitesSobremetal[] = {0, 25, 40, 63, 80, 100, 125, 160, 200, 250, 315, 400, 500, 630, 800, 1000};
        double tabSobremetal[] = {2.6, 3, 4, 5, 6, 7, 9, 11, 13, 16, 19, 24, 30, 37, 46};     
        repetição(a,tabLimitesSobremetal);
        //sobremetal = tabSobremetal[i];
        return(tabSobremetal[i]);
   }
   //função que escolhe o valor em polegada mais proximo, considerando o sobremetal, é utilizada pelo metodo CalculoCilindrico()
   private static void calculodiametro(double a,boolean REDUZ) {
        double[] diametroMM = {
            2.38125,    3.175,    3.96875,    4.7625,    5.55625,    6.35,    7.14375,    7.9375,    9.525,    11.1125,    12.7,    14.2875,    15.875,
            17.4625,    19.05,    20.6375,    22.225,    23.8125,    25.4,    26.9875,    28.575,    30.1625,    31.75,    33.3375,    34.925,    36.5125,
            38.1,    39.6875,    41.275,    44.45,    47.625,    50.8,    52.3875,    53.975,    55.5625,    57.15,    58.7375,    60.325,    61.9125,    63.5,
            66.675,    69.85,    73.025,    76.2,    79.375,    82.55,    85.725,    88.9,    92.075,    95.25,    98.425,    101.6,    104.775,    107.95,
            111.125,    114.3,    117.475,    120.65,    123.825,    127,    133.35,    139.7,    146.05,    152.4,    158.75,    165.1,    171.45,    177.8,
            184.15,    190.5,    196.85,    203.2,    209.55,    215.9,    222.25,    228.6,    234.95,    241.3,    247.65,    254,    266.7,    279.4,
            292.1,    304.8,    330.2,    355.6,    381,    406.4,    431.8,    457.2,    508	        
        };
        String diametropolegada[] = {
"3/3"	,
"1/8"	,
"5/32"	,
"3/16"	,
"7/32"	,
"1/4"	,
"9/32"	,
"5/16"	,
"3/8"	,
"7/16"  ,
"1/2"	,
"9/16"	,
"5/8"	,
"11/16"	,
"3/4"	,
"13/16"	,
"7/8"	,
"15/16"	,
"1"	,
"1   1/16"	,
"1   1/8"	,
"1   3/16"	,
"1   1/4"	,
"1   5/16"	,
"1   3/8"	,
"1   7/16"	,
"1   1/2"	,
"1   9/16"	,
"1   5/8"	,
"1   3/4"	,
"1   7/8"	,
"2"	,
"2   1/16"	,
"2   1/8"	,
"2   3/16 "	,
"2   1/4"	,
"2   5/16"	,
"2   3/8"	,
"2   7/16"	,
"2   1/2"	,
"2   5/8"	,
"2   3/4"	,
"2   7/8"	,
"3"	,
"3   1/8"	,
"3   1/4"	,
"3   3/8"	,
"3   1/2"	,
"3   5/8"	,
"3   3/4"	,
"3   7/8"	,
"4"	,
"4   1/8"	,
"4   1/4"	,
"4   3/8"	,
"4   1/2"	,
"4   5/8"	,
"4   3/4"	,
"4   7/8"	,
"5"	,
"5   1/4"	,
"5   1/2"	,
"5   3/4"	,
"6"	,
"6   1/4"	,
"6   1/2"	,
"6   3/4"	,
"7"	,
"7   1/4"	,
"7   1/2"	,
"7   3/4"	,
"8"	,
"8   1/4"	,
"8   1/2"	,
"8   3/4"	,
"9"	,
"9   1/4"	,
"9   1/2"	,
"9   3/4"	,
"10"	,
"10   1/2"	,
"11"	,
"11   1/2"	,
"12"	,
"13"	,
"14"	,
"15"	,
"16"	,
"17 "	,
"18"	,
"20"	

        }; 
        repetição(a,diametroMM);
        if(REDUZ){
            diametroPoelegada=diametroMM[i];
            diametroPOrdinaria=diametropolegada[i];
        }
        else{
            diametroPoelegada=diametroMM[i+1];
            diametroPOrdinaria=diametropolegada[i+1];
        }
        
    }
   //METODO DE COMPARAÇÃO DE VALORES UTILIZADO PARA ESCOLHA DOS VALORES DE SOBREMETAL E DIAMETRO, é chamada pelos metodos calculosobremetal() e calculodiametro()
    private static void repetição(double a, double b[]){
        i=0;
        while(true){
            if ((b[i]<=a)&&(a<b[i+1])){
                break;
            }
            i++;
        }
    }
    
    //METODO UTILIZADO PARA REALIZAR OS CALCULOS NECESSARIOS
    
    private static void calculos(double diametro,double comprimento){
        double raio = diametro/2;
        double volume;
        volume = raio*raio*3.14159*comprimento; 
        massaCilindro = volume * densidade[posicaoDensidade];
        //percentual = (diametroDigitado/diametroPoelegada)*100; //nao esta sendo utilizada
        
    }
    //Metodo removido, não necessario
    
    /*public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in); 
        
        double diametro, comprimento;//valores fornecidos por usuario
         //constante
        //double sobremetal;
        
        while (true) {
            System.out.print("Digite o valor do diametro maior da peça acabada: ");
            diametro = input.nextDouble();
            if (diametro >= 483) {
                System.out.println("\nvalor invalido, diametro maximo de 483");
                continue;
            }
            break;
        }
        System.out.print("Digite o valor do comprimento, considerar sobremetal: ");
        comprimento = input.nextDouble();
        
        sobremetal = calculosobremetal(diametro);
        calculodiametro(diametro+sobremetal);
        
        System.out.println(sobremetal);
        System.out.println(diametro);
        System.out.println(diametroPoelegada);
        System.out.println(diametroPOrdinaria); 
        
    }*/
}