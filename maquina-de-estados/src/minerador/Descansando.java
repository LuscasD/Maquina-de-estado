package minerador;

import core.Estado;

/** Recupera RECUPERACAO_ENERGIA_POR_TICK de energia por tick até ficar com a energia cheia. */
public final class Descansando implements Estado<Minerador> {

    private static final Descansando INSTANCIA = new Descansando();

    private Descansando() {
    }

    public static Descansando instancia() {
        return INSTANCIA;
    }

    @Override
    public void enter(Minerador m) {
        m.log("Esta exausto. Senta num caixote para descansar.");
    }

    @Override
    public void execute(Minerador m) {
        m.descansar();
        m.log("Descansando... [" + m.status() + "]");

        if (m.descansado()) {
            m.getMaquina().mudarEstado(Minerando.instancia());
        }
    }

    @Override
    public void leave(Minerador m) {
        m.log("Levanta renovado, pronto para trabalhar.");
    }

    @Override
    public String nome() {
        return "Descansando";
    }
}
