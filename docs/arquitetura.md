# Arquitetura

## Visão geral

A aplicação segue uma separação simples entre inicialização, interface, coordenação, domínio e dados técnicos.

```mermaid
classDiagram
    direction LR

    class Main {
        +main(String[] args)
        -configurarAparencia()
    }

    class VersaoAplicacao {
        +String ATUAL
    }

    class CalculoSobremetalView {
        -CalculoSobremetalController controller
        +getValor1() String
        +getValor2() String
        +getValor3() String
        +isPerfilCilindrico() boolean
        +isOtimizar() boolean
        +getMaterialSelecionado() Material
        +mostrarResultado(String, String, String, String)
        +mostrarValorInvalido()
        -abrirImagemAjuda(String, String)
        -abrirSobre()
        -copiarResultado(JTextField)
    }

    class CalculoSobremetalController {
        -CalculoSobremetalView view
        -ValidadorCalculo validador
        -FormatadorResultado formatador
        +calcular()
        -calcularCilindrico()
        -calcularRetangular()
    }

    class ValidadorCalculo {
        +lerValor(String) Double
        +valoresCilindricosValidos(Double, Double, Double) boolean
        +valoresRetangularesValidos(Double, Double, Double) boolean
    }

    class FormatadorResultado {
        +polegadas(String) String
        +diametroMilimetros(double) String
        +milimetros(double) String
        +quilogramas(double) String
    }

    class CalcMateriaPrima {
        -Material material
        +calcularCilindrico(double, double, boolean) ResultadoCilindrico
        +calcularRetangular(double, double, double, boolean) ResultadoRetangular
    }

    class Material {
        -String nome
        -double densidade
        +getNome() String
        +getDensidade() double
    }

    class FaixaSobremetal {
        -double limiteMinimo
        -double limiteMaximo
        -double sobremetal
        +contem(double) boolean
        +getSobremetal() double
    }

    class ResultadoCilindrico {
        -double diametroMilimetros
        -String diametroPolegadas
        -double sobremetal
        -double massa
    }

    class ResultadoRetangular {
        -double lado1
        -double lado2
        -double lado3
        -double massa
    }

    class TabelasMateriaPrima {
        -Material[] MATERIAIS
        -FaixaSobremetal[] FAIXAS_SOBREMETAL
        -double[] DIAMETROS_MM
        -String[] DIAMETROS_POLEGADA
    }

    Main --> CalculoSobremetalView : abre
    CalculoSobremetalView --> VersaoAplicacao : exibe versão
    CalculoSobremetalView --> CalculoSobremetalController : delega ações
    CalculoSobremetalController --> ValidadorCalculo : valida
    CalculoSobremetalController --> FormatadorResultado : formata
    CalculoSobremetalController --> CalcMateriaPrima : solicita cálculo
    CalcMateriaPrima --> Material : usa densidade
    CalcMateriaPrima --> TabelasMateriaPrima : consulta dados
    TabelasMateriaPrima o-- Material
    TabelasMateriaPrima o-- FaixaSobremetal
    CalcMateriaPrima --> ResultadoCilindrico : cria
    CalcMateriaPrima --> ResultadoRetangular : cria
```

## Fluxo de cálculo

```mermaid
sequenceDiagram
    actor Usuario as Usuário
    participant View as CalculoSobremetalView
    participant Controller as CalculoSobremetalController
    participant Validator as ValidadorCalculo
    participant Model as CalcMateriaPrima
    participant Tables as TabelasMateriaPrima
    participant Formatter as FormatadorResultado

    Usuario->>View: informa dimensões, material ou densidade personalizada
    Usuario->>View: clica em Calcular
    View->>View: valida densidade personalizada, quando usada
    View->>Controller: calcular()
    Controller->>View: lê campos e opções
    Controller->>Validator: converte e valida valores

    alt entrada inválida
        Validator-->>Controller: inválida
        Controller->>View: mostrarValorInvalido()
    else entrada válida
        Validator-->>Controller: válida
        Controller->>Model: calcular perfil selecionado
        Model->>Tables: consulta material, faixa e diâmetro
        Tables-->>Model: dados técnicos
        Model-->>Controller: objeto de resultado
        Controller->>Formatter: formata unidades e casas decimais
        Formatter-->>Controller: textos formatados
        Controller->>View: mostrarResultado(...)
    end
```

## Responsabilidades

| Área | Responsabilidade |
|---|---|
| `aplicacao` | Iniciar o Swing, configurar aparência e declarar a versão apresentada. |
| `view` | Ler componentes visuais, validar a densidade personalizada e apresentar mensagens, resultados e ajudas contextuais. |
| `controller` | Coordenar a ação do usuário, validar e formatar. |
| `modelo` | Executar fórmulas e representar materiais, faixas e resultados. |
| `dados` | Manter e validar as tabelas técnicas em memória. |

## Decisões de projeto

- O modelo não guarda resultados temporários: cada operação retorna um objeto imutável.
- Materiais associam nome e densidade no mesmo objeto, evitando dependência de índices soltos.
- Faixas de sobremetal tornam explícita a relação entre limites e valor aplicado.
- As tabelas retornam cópias dos arrays para evitar alteração externa acidental.
- Diâmetros ainda permanecem em memória; a evolução prevista é introduzir um repositório baseado em arquivo.
- A View é construída diretamente com Swing, sem dependência de arquivos gerados por IDE.
- A densidade cadastrada é exibida em `g/cm³`; uma densidade personalizada é
  convertida pela View para `kg/mm³` antes da criação do `Material` temporário.
- A validação cilíndrica deriva o limite superior das faixas de sobremetal e da
  tabela de diâmetros comerciais, evitando um teto fixo desatualizado.
- As imagens de ajuda ficam em `src/main/resources/images` e são carregadas por
  `getResource`, permanecendo incorporadas ao JAR.
- Os resultados são campos não editáveis e selecionáveis; cada linha possui uma
  ação de cópia independente para a área de transferência.

## Evolução prevista

Quando os diâmetros forem persistidos, a dependência direta de `TabelasMateriaPrima` poderá ser substituída por uma abstração como:

```mermaid
classDiagram
    class RepositorioDiametros {
        <<interface>>
        +listar() List~DiametroComercial~
    }

    class RepositorioDiametrosArquivo
    class CalcMateriaPrima

    RepositorioDiametros <|.. RepositorioDiametrosArquivo
    CalcMateriaPrima --> RepositorioDiametros
```
