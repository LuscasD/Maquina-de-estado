package minerador;

import core.Estado;

/** Estado inicial do Minerador: extrai 1 minério por tick. */
public final class Minerando implements Estado<Minerador> {

    private static final Minerando INSTANCIA = new Minerando();

    private Minerando() {
    }

    public static Minerando instancia() {
        return INSTANCIA;
    }

    @Override
    public void enter(Minerador m) {
        m.log("Desce para a galeria e empunha a picareta.");
    }

    @Override
    public void execute(Minerador m) {
        m.minerar();
        m.log("Quebra a rocha e guarda 1 minerio. [" + m.status() + "]");

        // Regras de transição em ordem de prioridade
        if (m.picaretaQuebrada()) {
            m.getMaquina().mudarEstado(AguardandoPicareta.instancia());
        } else if (m.mochilaCheia()) {
            m.getMaquina().mudarEstado(EntregandoMinerio.instancia());
        } else if (m.cansado()) {
            m.getMaquina().mudarEstado(Descansando.instancia());
        }
    }

    @Override
    public void leave(Minerador m) {
        m.log("Para de minerar e guarda a picareta no cinto.");
    }

    @Override
    public String nome() {
        return "Minerando";
    }
}
