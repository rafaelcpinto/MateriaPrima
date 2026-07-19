package materiaprima.modelo;

public enum PadraoDimensional {

    METRICO("Milímetro"),
    POLEGADA("Polegada");

    private final String descricao;

    PadraoDimensional(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
