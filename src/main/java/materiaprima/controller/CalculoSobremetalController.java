package materiaprima.controller;

import materiaprima.modelo.CalcMateriaPrima;
import materiaprima.modelo.PadraoDimensional;
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
        PadraoDimensional padrao = view.getPadraoDimensional();

        if (!validador.valoresCilindricosValidos(
                diametro, comprimento, sobremetalComprimento, padrao)) {
            view.mostrarValorInvalido();
            return;
        }

        CalcMateriaPrima materia = new CalcMateriaPrima(view.getMaterialSelecionado());
        double comprimentoTotal = comprimento + sobremetalComprimento;
        ResultadoCilindrico resultado = materia.calcularCilindrico(
                diametro,
                comprimentoTotal,
                view.isOtimizar(),
                padrao
        );
        String descricaoDiametro = padrao == PadraoDimensional.METRICO
                ? resultado.getDescricaoDiametro()
                : formatador.polegadas(resultado.getDescricaoDiametro()).trim();
        String equivalente = padrao == PadraoDimensional.POLEGADA
                ? "(" + formatador.diametroMilimetros(
                        resultado.getDiametroMilimetros()) + ")"
                : null;

        view.mostrarResultadoCilindrico(
                descricaoDiametro,
                equivalente,
                formatador.milimetros(resultado.getComprimentoTotal()),
                formatador.milimetros(resultado.getSobremetal() / 2),
                formatador.quilogramas(resultado.getMassa()));
        view.mostrarVisualizacaoCilindrica(
                formatador.diametroMilimetros(resultado.getDiametroMilimetros()),
                formatador.diametroMilimetros(diametro),
                formatador.milimetros(resultado.getSobremetal() / 2),
                formatador.milimetros(comprimento),
                formatador.milimetros(resultado.getComprimentoTotal())
        );
    }

    private void calcularRetangular() {
        Double lado1 = validador.lerValor(view.getValor1());
        Double lado2 = validador.lerValor(view.getValor2());
        Double lado3 = validador.lerValor(view.getValor3());
        boolean reduzir = view.isOtimizar();
        PadraoDimensional padrao = view.getPadraoDimensional();

        if (!validador.valoresRetangularesValidos(
                lado1, lado2, lado3, reduzir, padrao)) {
            view.mostrarValorInvalido();
            return;
        }

        CalcMateriaPrima materia = new CalcMateriaPrima(view.getMaterialSelecionado());
        ResultadoRetangular resultado =
                materia.calcularRetangular(lado1, lado2, lado3, reduzir, padrao);

        view.mostrarResultadoRetangular(
                formatarDimensaoRetangular(resultado.getLarguraDescricao(), padrao),
                formatarEquivalenteRetangular(resultado.getLarguraMilimetros(), padrao),
                formatarDimensaoRetangular(resultado.getAlturaDescricao(), padrao),
                formatarEquivalenteRetangular(resultado.getAlturaMilimetros(), padrao),
                formatarDimensaoRetangular(resultado.getComprimentoDescricao(), padrao),
                formatarEquivalenteRetangular(resultado.getComprimentoMilimetros(), padrao),
                formatador.quilogramas(resultado.getMassa()));
        view.mostrarVisualizacaoRetangular(
                formatador.milimetros(resultado.getLarguraMilimetros()),
                formatador.milimetros(resultado.getAlturaMilimetros()),
                formatador.milimetros(resultado.getComprimentoMilimetros()),
                formatador.milimetros(lado1),
                formatador.milimetros(lado2),
                formatador.milimetros(lado3)
        );
    }

    private String formatarDimensaoRetangular(
            String descricao,
            PadraoDimensional padrao
    ) {
        return padrao == PadraoDimensional.POLEGADA
                ? formatador.polegadas(descricao).trim()
                : descricao;
    }

    private String formatarEquivalenteRetangular(
            double milimetros,
            PadraoDimensional padrao
    ) {
        return padrao == PadraoDimensional.POLEGADA
                ? "(" + formatador.milimetros(milimetros) + ")"
                : null;
    }
}
