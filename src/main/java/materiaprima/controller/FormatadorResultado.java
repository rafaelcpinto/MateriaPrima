package materiaprima.controller;

import java.text.DecimalFormat;

public final class FormatadorResultado {

    private final DecimalFormat formato = new DecimalFormat("###0.000");

    public String polegadas(String valor) {
        return valor + "\" ";
    }

    public String diametroMilimetros(double valor) {
        return valor + " mm";
    }

    public String milimetros(double valor) {
        return formato.format(valor) + " mm";
    }

    public String quilogramas(double valor) {
        return formato.format(valor) + " kg";
    }
}
