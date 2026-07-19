# UML do MateriaPrima

Os diagramas abaixo representam a arquitetura atual da aplicação. Eles usam
[Mermaid](https://mermaid.js.org/) e podem ser visualizados diretamente em
renderizadores Markdown compatíveis, incluindo o GitHub.

## Diagrama de classes

```mermaid
classDiagram
    direction LR

    namespace Aplicacao {
        class Main {
            +main(String[] args)$ void
            -configurarAparencia()$ void
        }

        class VersaoAplicacao {
            +ATUAL$ String
        }
    }

    namespace View {
        class CalculoSobremetalView {
            -controller CalculoSobremetalController
            -material JComboBox~Material~
            -densidadePersonalizada JTextField
            -valor1 JTextField
            -valor2 JTextField
            -valor3 JTextField
            -resultado1 JTextField
            -resultado2 JTextField
            -resultado3 JTextField
            -resultadoMassa JTextField
            +getValor1() String
            +getValor2() String
            +getValor3() String
            +isPerfilCilindrico() boolean
            +isOtimizar() boolean
            +getMaterialSelecionado() Material
            +mostrarResultado(String, String, String, String) void
            +mostrarValorInvalido() void
            -calcular() void
            -atualizarPerfil() void
            -atualizarDensidade() void
            -abrirImagemAjuda(String, String) void
            -abrirSobre() void
            -copiarResultado(JTextField) void
        }
    }

    namespace Controller {
        class CalculoSobremetalController {
            -view CalculoSobremetalView
            -validador ValidadorCalculo
            -formatador FormatadorResultado
            +calcular() void
            -calcularCilindrico() void
            -calcularRetangular() void
        }

        class ValidadorCalculo {
            +lerValor(String) Double
            +valoresCilindricosValidos(Double, Double, Double) boolean
            +valoresRetangularesValidos(Double, Double, Double) boolean
            -diametroCilindricoValido(double) boolean
        }

        class FormatadorResultado {
            +polegadas(String) String
            +diametroMilimetros(double) String
            +milimetros(double) String
            +quilogramas(double) String
        }
    }

    namespace Modelo {
        class CalcMateriaPrima {
            -material Material
            +CalcMateriaPrima()
            +CalcMateriaPrima(int)
            +CalcMateriaPrima(Material)
            +calcularCilindrico(double, double, boolean) ResultadoCilindrico
            +calcularRetangular(double, double, double, boolean) ResultadoRetangular
            -calculosobremetal(double) double
            -selecionarIndiceDiametro(double, boolean) int
        }

        class Material {
            -nome String
            -densidade double
            +getNome() String
            +getDensidade() double
        }

        class FaixaSobremetal {
            -limiteMinimo double
            -limiteMaximo double
            -sobremetal double
            +contem(double) boolean
            +getLimiteMinimo() double
            +getLimiteMaximo() double
            +getSobremetal() double
        }

        class ResultadoCilindrico {
            -diametroMilimetros double
            -diametroPolegadas String
            -sobremetal double
            -massa double
        }

        class ResultadoRetangular {
            -lado1 double
            -lado2 double
            -lado3 double
            -massa double
        }
    }

    namespace Dados {
        class TabelasMateriaPrima {
            -MATERIAIS$ Material[]
            -FAIXAS_SOBREMETAL$ FaixaSobremetal[]
            -DIAMETROS_MM$ double[]
            -DIAMETROS_POLEGADA$ String[]
            +material(int)$ Material
            +materiais()$ Material[]
            +faixasSobremetal()$ FaixaSobremetal[]
            +diametrosMm()$ double[]
            +diametrosPolegada()$ String[]
        }
    }

    Main --> CalculoSobremetalView : cria
    CalculoSobremetalView --> VersaoAplicacao : exibe versão
    CalculoSobremetalView --> CalculoSobremetalController : delega cálculo
    CalculoSobremetalView ..> Material : cria material personalizado
    CalculoSobremetalView --> TabelasMateriaPrima : lista materiais
    CalculoSobremetalController --> ValidadorCalculo : valida entradas
    CalculoSobremetalController --> FormatadorResultado : formata saída
    CalculoSobremetalController --> CalcMateriaPrima : executa cálculo
    ValidadorCalculo --> TabelasMateriaPrima : deriva limite cilíndrico
    CalcMateriaPrima --> Material : usa densidade
    CalcMateriaPrima --> TabelasMateriaPrima : consulta tabelas
    CalcMateriaPrima ..> ResultadoCilindrico : cria
    CalcMateriaPrima ..> ResultadoRetangular : cria
    TabelasMateriaPrima o-- Material : mantém
    TabelasMateriaPrima o-- FaixaSobremetal : mantém
```

## Sequência do cálculo

```mermaid
sequenceDiagram
    actor Usuario as Usuário
    participant View as CalculoSobremetalView
    participant Controller as CalculoSobremetalController
    participant Validator as ValidadorCalculo
    participant Model as CalcMateriaPrima
    participant Tables as TabelasMateriaPrima
    participant Formatter as FormatadorResultado

    Usuario->>View: seleciona perfil e material
    opt Densidade personalizada
        Usuario->>View: informa densidade em g/cm³
        View->>View: valida e converte para kg/mm³
    end
    Usuario->>View: informa dimensões
    Usuario->>View: aciona Calcular ou Enter
    View->>Controller: calcular()
    Controller->>View: obtém entradas e opções
    Controller->>Validator: converte e valida dimensões

    alt Entrada inválida
        Validator-->>Controller: inválida
        Controller->>View: mostrarValorInvalido()
    else Entrada válida
        Validator->>Tables: consulta faixas e diâmetros
        Validator-->>Controller: válida
        Controller->>Model: calcular perfil selecionado
        Model->>Tables: consulta sobremetal e diâmetro comercial
        Tables-->>Model: dados técnicos
        Model-->>Controller: resultado imutável
        Controller->>Formatter: formata valores e unidades
        Formatter-->>Controller: textos formatados
        Controller->>View: mostrarResultado(...)
        View-->>Usuario: exibe resultados selecionáveis
    end
```

## Componentes de distribuição

```mermaid
flowchart LR
    SRC[Java Swing e regras] --> MVN[Maven]
    IMG[Imagens em src/main/resources] --> MVN
    MVN --> JAR[MateriaPrima.jar]
    JAR --> CLS[Classes Java 8]
    JAR --> RES[Imagens de ajuda]
    JAR --> MAN[Manifesto com Main-Class]
```
