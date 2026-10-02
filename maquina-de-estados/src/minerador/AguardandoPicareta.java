package minerador;

import core.Estado;

/**
 * Comunicação entre agentes: ao entrar, o Minerador avisa o Ferreiro
 * que a picareta quebrou. Ele só volta a minerar quando o Ferreiro
 * responder com PICARETA_CONSERTADA (flag picaretaConsertada).
 */
public final class AguardandoPicareta implements Estado<Minerador> {

    private static final AguardandoPicareta INSTANCIA = new AguardandoPicareta();

    private AguardandoPicareta() {
    }

    public static AguardandoPicareta instancia() {
        return INSTANCIA;
    }

    @Override
    public void enter(Minerador m) {
        m.log("A picareta quebrou! Leva a picareta ao Ferreiro.");
        m.pedirConserto();
    }

    @Override
    public void execute(Minerador m) {
        if (m.isPicaretaConsertada()) {
            m.receberPicaretaNova();
            m.log("Recebe a picareta consertada. [" + m.status() + "]");
            m.getMaquina().mudarEstado(Minerando.instancia());
        } else {
            m.log("Esperando na porta da ferraria...");
        }
    }

    @Override
    public void leave(Minerador m) {
        m.log("Agradece ao Ferreiro e volta para a mina.");
    }

    @Override
    public String nome() {
        return "AguardandoPicareta";
    }
}
