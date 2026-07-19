package materiaprima.modelo;

public final class Material {

    private final String nome;
    private final double densidade;

    public Material(String nome, double densidade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do material é obrigatório");
        }
        if (!Double.isFinite(densidade) || densidade <= 0) {
            throw new IllegalArgumentException("Densidade inválida: " + densidade);
        }
        this.nome = nome;
        this.densidade = densidade;
    }

    public String getNome() {
        return nome;
    }

    public double getDensidade() {
        return densidade;
    }

    @Override
    public String toString() {
        return nome;
    }
}
