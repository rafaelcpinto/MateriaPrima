package materiaprima.modelo;

public final class DiametroComercial {

    private final double milimetros;
    private final String descricao;
    private final PadraoDimensional padrao;

    public DiametroComercial(
            double milimetros,
            String descricao,
            PadraoDimensional padrao
    ) {
        if (!Double.isFinite(milimetros) || milimetros <= 0) {
            throw new IllegalArgumentException(
                    "O diâmetro em milímetros deve ser positivo e finito."
            );
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A descrição do diâmetro é obrigatória."
            );
        }

        if (padrao == null) {
            throw new IllegalArgumentException(
                    "O padrão dimensional é obrigatório."
            );
        }

        this.milimetros = milimetros;
        this.descricao = descricao.trim();
        this.padrao = padrao;
    }

    public double getMilimetros() {
        return milimetros;
    }

    public String getDescricao() {
        return descricao;
    }

    public PadraoDimensional getPadrao() {
        return padrao;
    }
}
