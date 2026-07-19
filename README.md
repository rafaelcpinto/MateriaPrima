# Cálculo de Matéria-Prima

Aplicação desktop desenvolvida em 2015 para uma necessidade real de cálculo de matéria-prima e mantida em uso desde sua implantação.

## Versão atual

**2026.07.1**

## Sobre o projeto

O projeto automatiza o dimensionamento de matéria-prima cilíndrica ou retangular e estima sua massa conforme o material selecionado. Antes de sua implantação, esses cálculos eram realizados manualmente.

A refatoração de 2026 reorganizou o código, adicionou validações e testes automatizados e melhorou o processo de distribuição, preservando as regras de negócio utilizadas pela aplicação em produção.

> A versão publicada não representa exatamente a estrutura original de 2015; ela é uma refatoração destinada à manutenção, preservação e compartilhamento do projeto.

Principais características:

- interface desktop Java Swing;
- perfis cilíndrico e retangular;
- onze materiais cadastrados e opção de densidade personalizada;
- seleção automática de diâmetros comerciais;
- cálculo de sobremetal por faixa dimensional;
- estimativa de massa em quilogramas;
- opção de otimização abaixo da norma;
- validação de entradas e limites;
- ajuda contextual com diagramas incorporados ao JAR;
- resultados selecionáveis e cópia individual para a área de transferência;
- janela Sobre com histórico e identificação da aplicação;
- testes automatizados com JUnit 5;
- build reproduzível com Maven.

## Imagens

<img width="508" height="624" alt="Interface do MateriaPrima" src="https://github.com/user-attachments/assets/2a29628d-be5d-49c5-b386-bd331cbe6799" />

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
- formatação das unidades e resultados.

## Regras de entrada

### Perfil cilíndrico

- diâmetro acabado: deve pertencer às faixas técnicas e permitir a seleção
  de um diâmetro comercial após a aplicação do sobremetal (na tabela atual,
  valores menores que `919,2 mm` são aceitos);
- comprimento: de `0` a `3500 mm`;
- sobremetal no comprimento: maior ou igual a `0`.

### Perfil retangular

- lado 1, lado 2 e lado 3: de `0` até um valor menor que `1000 mm`.

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

Os materiais, faixas de sobremetal e diâmetros comerciais estão centralizados em
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
└── view/         interface construída com Java Swing

src/test/java/materiaprima/
├── controller/   testes de validação e formatação
└── modelo/       testes dos cálculos e regras

src/main/resources/images/
├── sobremetal-dimensoes.png
└── sobremetal-comprimento.png
```

Consulte [docs/arquitetura.md](docs/arquitetura.md) para as decisões arquiteturais e
[docs/uml.md](docs/uml.md) para os diagramas de classes, sequência e distribuição.

## Dados técnicos

- `Material` associa nome e densidade.
- `FaixaSobremetal` associa limite mínimo inclusivo, limite máximo exclusivo e sobremetal.
- Os diâmetros em milímetros e polegadas permanecem em tabelas paralelas, preparados para futura persistência em arquivo.
- `TABELA_MATERIA_PRIMA.csv` foi preservado como referência para essa evolução.

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
