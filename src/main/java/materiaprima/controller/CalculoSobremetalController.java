package materiaprima.controller;

import materiaprima.modelo.CalcMateriaPrima;
import materiaprima.modelo.ResultadoCilindrico;
import materiaprima.modelo.ResultadoRetangular;
import materiaprima.view.CalculoSobremetalView;

public class CalculoSobremetalController {

    private final CalculoSobremetalView view;
    private final ValidadorCalculo validador = new ValidadorCalculo();
    private final FormatadorResultado formatador = new FormatadorResultado();

    public CalculoSobremetalController(CalculoSobremetalView view) {
        this.view = view;
    }

    public void calcular() {
        if (view.isPerfilCilindrico()) {
            calcularCilindrico();
        } else {
            calcularRetangular();
        }
    }

    private void calcularCilindrico() {
        Double diametro = validador.lerValor(view.getValor1());
        Double comprimento = validador.lerValor(view.getValor2());
        Double sobremetalComprimento = validador.lerValor(view.getValor3());

        if (!validador.valoresCilindricosValidos(
                diametro, comprimento, sobremetalComprimento)) {
            view.mostrarValorInvalido();
            return;
        }

        CalcMateriaPrima materia = new CalcMateriaPrima(view.getMaterialSelecionado());
        ResultadoCilindrico resultado = materia.calcularCilindrico(
                diametro, comprimento + sobremetalComprimento, view.isOtimizar());

        view.mostrarResultado(
                formatador.polegadas(resultado.getDiametroPolegadas()),
                formatador.diametroMilimetros(resultado.getDiametroMilimetros()),
                formatador.milimetros(resultado.getSobremetal() / 2),
                formatador.quilogramas(resultado.getMassa()));
    }

    private void calcularRetangular() {
        Double lado1 = validador.lerValor(view.getValor1());
        Double lado2 = validador.lerValor(view.getValor2());
        Double lado3 = validador.lerValor(view.getValor3());

        if (!validador.valoresRetangularesValidos(lado1, lado2, lado3)) {
            view.mostrarValorInvalido();
            return;
        }

        CalcMateriaPrima materia = new CalcMateriaPrima(view.getMaterialSelecionado());
        ResultadoRetangular resultado =
                materia.calcularRetangular(lado1, lado2, lado3, view.isOtimizar());

        view.mostrarResultado(
                formatador.milimetros(resultado.getLado1()),
                formatador.milimetros(resultado.getLado2()),
                formatador.milimetros(resultado.getLado3()),
                formatador.quilogramas(resultado.getMassa()));
    }
}
