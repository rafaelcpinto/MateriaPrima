package materiaprima.modelo;

public final class DiametroComercial {

    private final double milimetros;
    private final String polegadas;

    public DiametroComercial(double milimetros, String polegadas) {
        if (!Double.isFinite(milimetros) || milimetros <= 0) {
            throw new IllegalArgumentException(
                    "O diâmetro em milímetros deve ser positivo e finito."
            );
        }

        if (polegadas == null || polegadas.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A descrição em polegada é obrigatória."
            );
        }

        this.milimetros = milimetros;
        this.polegadas = polegadas;
    }

    public double getMilimetros() {
        return milimetros;
    }

    public String getPolegadas() {
        return polegadas;
    }
}
