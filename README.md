# Cálculo de Matéria-Prima

Aplicação desktop desenvolvida em 2015 para uma necessidade real de cálculo de matéria-prima e mantida em uso desde sua implantação.

## Versão atual

**2026.07.1**

## Sobre o projeto

O projeto automatiza o dimensionamento de matéria-prima cilíndrica ou retangular e estima sua massa conforme o material selecionado. Antes de sua implantação, esses cálculos eram realizados manualmente.

A refatoração de 2026 reorganizou o código, adicionou validações e testes automatizados e melhorou o processo de distribuição, preservando as regras de negócio utilizadas pela aplicação em produção.

> A versão publicada não representa exatamente a estrutura original de 2015; ela é uma refatoração destinada à manutenção, preservação e compartilhamento do projeto.

Principais características:

- interface desktop Java Swing com tema FlatLaf;
- perfis cilíndrico e retangular;
- seleção dimensional em milímetro ou polegada para os dois perfis;
- onze materiais cadastrados e opção de densidade personalizada;
- seleção automática de dimensões comerciais;
- cálculo de sobremetal por faixa dimensional;
- estimativa de massa em quilogramas;
- opção de otimização abaixo da norma;
- validação de entradas e limites;
- ajuda contextual com diagramas incorporados ao JAR;
- resultados selecionáveis e cópia individual para a área de transferência;
- visualização dinâmica e proporcional da matéria-prima e da peça acabada;
- janela Sobre com histórico e identificação da aplicação;
- testes automatizados com JUnit 5;
- build reproduzível com Maven.

## Requisitos

### Para desenvolver

- JDK 21 recomendado;
- Apache Maven 3.9 ou superior.

O Maven compila com `release 8`, portanto o JAR gerado permanece compatível com Java 8.

### Para executar o JAR

- Java 8 ou superior.

Uma distribuição futura com `jpackage` poderá incluir o próprio runtime e eliminar essa exigência para o usuário final.

## Executar

Pelo Maven:

```bash
mvn exec:java
```

Pelo JAR:

```bash
mvn clean package
java -jar target/MateriaPrima.jar
```

O empacotamento gera um JAR executável autocontido com FlatLaf e o suporte a
ícones SVG.

## Testes

Execute:

```bash
mvn test
```

Para limpar, testar e validar o pacote completo:

```bash
mvn clean verify
```

Os testes cobrem:

- limites dos cálculos cilíndricos e retangulares;
- entradas negativas, `NaN` e infinito;
- independência entre materiais e densidades;
- fronteiras das faixas de sobremetal;
- validação dos campos;
- formatação das unidades e resultados;
- inicialização do tema e carregamento seguro dos ícones;
- estrutura dos cards e atualização da visualização dinâmica.

## Regras de entrada

### Perfil cilíndrico

- todas as entradas são informadas em milímetros;
- no padrão **Milímetro**, o diâmetro necessário é selecionado com `Math.ceil()`;
- no padrão **Polegada**, o próximo valor é obtido da tabela imperial;
- no padrão Polegada, o diâmetro acabado deve ser menor que `919,2 mm`, limite
  derivado do maior valor comercial e do sobremetal necessário;
- comprimento: de `0` a `3500 mm`;
- adicional para fixação: maior ou igual a `0`.

### Perfil retangular

- largura, altura e comprimento são informados em milímetros;
- no padrão **Milímetro**, cada dimensão necessária é selecionada com `Math.ceil()`;
- no padrão **Polegada**, cada dimensão é selecionada separadamente na mesma
  tabela imperial usada pelo perfil cilíndrico;
- cada dimensão deve pertencer às faixas de sobremetal e permitir uma seleção
  comercial válida no padrão escolhido.

Campos vazios, números malformados, negativos, infinitos ou `NaN` são rejeitados pela aplicação.

## Materiais

| Material | Densidade usada no cálculo (kg/mm³) |
|---|---:|
| Aço | 7,85 × 10⁻⁶ |
| Aço inoxidável 304L | 7,90 × 10⁻⁶ |
| Latão | 8,50 × 10⁻⁶ |
| Cobre | 8,93 × 10⁻⁶ |
| Bronze Fosfórico | 8,80 × 10⁻⁶ |
| Alumínio | 2,70 × 10⁻⁶ |
| Titânio Grau 2 | 4,51 × 10⁻⁶ |
| Polipropileno | 0,93 × 10⁻⁶ |
| Nylon | 1,14 × 10⁻⁶ |
| PEAD | 0,95 × 10⁻⁶ |
| Acrílico (PMMA) | 1,18 × 10⁻⁶ |

Os materiais, faixas de sobremetal e dimensões comerciais estão centralizados em
`TabelasMateriaPrima`. A interface mostra a densidade do material selecionado em
`g/cm³` e permite informar uma densidade personalizada positiva, usando ponto ou
vírgula decimal.

## Estrutura

```text
src/main/java/materiaprima/
├── aplicacao/    ponto de entrada e versão
├── controller/   coordenação, validação e formatação
├── dados/        tabelas técnicas
├── modelo/       regras, entidades e resultados
└── view/         interface Swing, cards, ícones e visualização dinâmica

src/test/java/materiaprima/
├── aplicacao/    teste de inicialização do tema
├── controller/   testes de validação e formatação
├── dados/        testes das tabelas técnicas
├── modelo/       testes dos cálculos e regras
└── view/         testes estruturais e de renderização

src/main/resources/
├── icons/        ícones SVG da interface
└── images/       imagens da ajuda contextual
```

Consulte [docs/arquitetura.md](docs/arquitetura.md) para as decisões arquiteturais e
[docs/uml.md](docs/uml.md) para os diagramas de classes, sequência e distribuição.

## Dados técnicos

- `Material` associa nome e densidade.
- `FaixaSobremetal` associa limite mínimo inclusivo, limite máximo exclusivo e sobremetal.
- `PadraoDimensional` representa `METRICO` e `POLEGADA`.
- `DimensaoComercial` associa valor em milímetros, descrição comercial e padrão dimensional.
- A tabela imperial é única; o padrão métrico é calculado com `Math.ceil()` e não
  possui uma tabela cadastrada.
- `TABELA_MATERIA_PRIMA.csv` foi preservado como referência para essa evolução.
- FlatLaf e os ícones de terceiros estão documentados em
  [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md).

### Identificação da versão

- versão pública e artefato Maven: `2026.07.1`;
- identificação exibida na interface: `2026.07.1`;
- a identificação da interface é centralizada em `VersaoAplicacao.ATUAL`.

> A opção **Otimizar** pode selecionar valores abaixo da norma, conforme indicado na própria interface. Resultados destinados à fabricação devem ser conferidos conforme o processo e a norma aplicável.

## Histórico

- **2015:** criação e implantação da primeira versão.
- **Atualizações intermediárias:** inclusão de materiais, perfil retangular e otimização conforme necessidades dos usuários.
- **2026.07.1:** migração para Maven, reorganização em camadas, interface Swing modernizada, novos materiais e diâmetros, densidade personalizada, ajuda contextual, testes automatizados e distribuição em JAR.

## Arquivos gerados

Maven grava classes, relatórios e o JAR em `target/`. O diretório não é versionado e pode ser removido com:

```bash
mvn clean
```
