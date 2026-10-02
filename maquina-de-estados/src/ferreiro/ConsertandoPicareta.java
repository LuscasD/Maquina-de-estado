package ferreiro;

import core.Estado;

/ Conserta a picareta durante TICKS_PARA_CONSERTAR ticks e avisa o Minerador ao terminar. */
public final class ConsertandoPicareta implements Estado<Ferreiro> {

    private static final ConsertandoPicareta INSTANCIA = new ConsertandoPicareta();

    private ConsertandoPicareta() {
    }

    public static ConsertandoPicareta instancia() {
        return INSTANCIA;
    }

    @Override
    public void enter(Ferreiro f) {
        f.aceitarPedido();
        f.log("Recebe a picareta quebrada e aquece o metal na forja.");
    }

    @Override
    public void execute(Ferreiro f) {
        f.martelar();
        f.log("Martelando a picareta (" + f.getTicksConsertando() + "/"
                + Ferreiro.TICKS_PARA_CONSERTAR + ").");

        if (f.consertoConcluido()) {
            f.getMaquina().mudarEstado(Ocioso.instancia());
        }
    }

    @Override
    public void leave(Ferreiro f) {
        f.log("Resfria a picareta na agua e devolve ao Minerador.");
        f.entregarPicareta();
    }

    @Override
    public String nome() {
        return "ConsertandoPicareta";
    }
}
