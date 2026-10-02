/** Estado inicial do Ferreiro: mantém a forja acesa e espera clientes. */
public class Ocioso extends AbstractState<Ferreiro> {

    public Ocioso(Ferreiro ferreiro) {
        super(ferreiro);
    }

    @Override
    public void enter() {
        getCharacter().setTicksConsertando(0);
        System.out.println("[FERREIRO]  Abre a ferraria e aguarda clientes.");
    }

    @Override
    public void execute() {
        if (getCharacter().temPedidoDeConserto()) {
            getCharacter().setState(new ConsertandoPicareta(getCharacter()));
        } else {
            getCharacter().printStats("Ocioso...");
        }
    }

    @Override
    public void leave() {
        System.out.println("[FERREIRO]  Um cliente chegou! Larga o que estava fazendo.");
    }
}
