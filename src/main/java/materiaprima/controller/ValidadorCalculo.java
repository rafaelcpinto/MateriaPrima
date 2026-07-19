package materiaprima.controller;

public final class ValidadorCalculo {

    public Double lerValor(String texto) {
        try {
            double valor = Double.parseDouble(texto);
            return Double.isFinite(valor) ? valor : null;
        } catch (NumberFormatException | NullPointerException ex) {
            return null;
        }
    }

    public boolean valoresCilindricosValidos(
            Double diametro, Double comprimento, Double sobremetalComprimento) {
        return diametro != null && comprimento != null && sobremetalComprimento != null
                && diametro >= 0 && diametro <= 483.0
                && comprimento >= 0 && comprimento <= 3500
                && sobremetalComprimento >= 0;
    }

    public boolean valoresRetangularesValidos(Double lado1, Double lado2, Double lado3) {
        return ladoValido(lado1) && ladoValido(lado2) && ladoValido(lado3);
    }

    private boolean ladoValido(Double lado) {
        return lado != null && lado >= 0 && lado < 1000;
    }
}
