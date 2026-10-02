/** Conserta a picareta durante TICKS_PARA_CONSERTAR ticks e avisa o Minerador ao terminar. */
public class ConsertandoPicareta extends AbstractState<Ferreiro> {

    public ConsertandoPicareta(Ferreiro ferreiro) {
        super(ferreiro);
    }

    @Override
    public void enter() {
        getCharacter().setPedidoDeConserto(false);
        getCharacter().setTicksConsertando(0);
        System.out.println("[FERREIRO]  Recebe a picareta quebrada e aquece o metal na forja.");
    }

    @Override
    public void execute() {
        getCharacter().addTicksConsertando(1);
        getCharacter().printStats("Martelando a picareta...");

        if (getCharacter().getTicksConsertando() >= Ferreiro.TICKS_PARA_CONSERTAR) {
            getCharacter().setState(new Ocioso(getCharacter()));
        }
    }

    @Override
    public void leave() {
        System.out.println("[FERREIRO]  Resfria a picareta na agua e devolve ao Minerador.");
        getCharacter().entregarPicareta();
    }
}
