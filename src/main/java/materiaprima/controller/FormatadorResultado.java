package materiaprima.controller;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class FormatadorResultado {

    private final DecimalFormat formato =
            new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));

    public String polegadas(String valor) {
        return valor + "\" ";
    }

    public String diametroMilimetros(double valor) {
        return formato.format(valor) + " mm";
    }

    public String milimetros(double valor) {
        return formato.format(valor) + " mm";
    }

    public String quilogramas(double valor) {
        return formato.format(valor) + " kg";
    }
}
