# Cálculo de Matéria-Prima

Aplicativo desktop para dimensionar matéria-prima cilíndrica ou retangular e estimar sua massa conforme o material selecionado.

## Versão atual

**07/2026-01**

A versão exibida pela aplicação é definida em `VersaoAplicacao.ATUAL`. O artefato Maven correspondente usa a versão `2026.07.1`.

## Sobre o projeto

O projeto foi criado em 2015 para automatizar cálculos que antes eram realizados manualmente. A aplicação continua preservando as regras e tabelas utilizadas no contexto original, mas sua estrutura foi modernizada para facilitar manutenção, testes e distribuição.

Principais características:

- interface desktop Java Swing;
- perfis cilíndrico e retangular;
- sete materiais com densidades próprias;
- seleção automática de diâmetros comerciais;
- cálculo de sobremetal por faixa dimensional;
- estimativa de massa em quilogramas;
- opção de otimização abaixo da norma;
- validação de entradas e limites;
- testes automatizados com JUnit 5;
- build reproduzível com Maven.

## Imagens:

<img width="508" height="625" alt="image" src="https://github.com/user-attachments/assets/21d23ec4-2bb6-4360-af13-5131ecfd3842" />

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

- diâmetro acabado: de `0` a `483 mm`;
- comprimento: de `0` a `3500 mm`;
- sobremetal no comprimento: maior ou igual a `0`.

### Perfil retangular

- lado 1, lado 2 e lado 3: de `0` até um valor menor que `1000 mm`.

Campos vazios, números malformados, negativos, infinitos ou `NaN` são rejeitados pela aplicação.

## Materiais

| Material | Densidade usada no cálculo (kg/mm³) |
|---|---:|
| Aço | 7,85 × 10⁻⁶ |
| Latão | 8,50 × 10⁻⁶ |
| Cobre | 8,93 × 10⁻⁶ |
| Polipropileno | 0,93 × 10⁻⁶ |
| Nylon | 1,14 × 10⁻⁶ |
| Alumínio | 2,70 × 10⁻⁶ |
| Bronze Fosfórico | 8,80 × 10⁻⁶ |

Os materiais, faixas de sobremetal e diâmetros comerciais estão centralizados em `TabelasMateriaPrima`.

## Estrutura

```text
src/main/java/materiaprima/
├── aplicacao/    ponto de entrada e versão
├── controller/   coordenação, validação e formatação
├── dados/        tabelas técnicas
├── modelo/       regras, entidades e resultados
└── view/         interface Swing e formulário visual

src/test/java/materiaprima/
├── controller/   testes de validação e formatação
└── modelo/       testes dos cálculos e regras
```

Consulte [docs/arquitetura.md](docs/arquitetura.md) para os diagramas UML e detalhes do fluxo.

## Dados técnicos

- `Material` associa nome e densidade.
- `FaixaSobremetal` associa limite mínimo inclusivo, limite máximo exclusivo e sobremetal.
- Os diâmetros em milímetros e polegadas permanecem em tabelas paralelas, preparados para futura persistência em arquivo.
- `TABELA_MATERIA_PRIMA.csv` foi preservado como referência para essa evolução.

> A opção **Otimizar** pode selecionar valores abaixo da norma, conforme indicado na própria interface. Resultados destinados à fabricação devem ser conferidos conforme o processo e a norma aplicável.

## Histórico

- **2015:** criação e implantação da primeira versão.
- **Atualizações intermediárias:** inclusão de materiais, perfil retangular e otimização conforme necessidades dos usuários.
- **07/2026-01:** migração para Maven, reorganização em camadas, validações, objetos de domínio, testes automatizados e preparação para distribuição em JAR/EXE.

## Arquivos gerados

Maven grava classes, relatórios e o JAR em `target/`. O diretório não é versionado e pode ser removido com:

```bash
mvn clean
```
