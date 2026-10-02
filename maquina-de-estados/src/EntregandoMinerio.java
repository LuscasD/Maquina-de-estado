/** Leva o minério até o depósito; a caminhada leva TICKS_PARA_ENTREGAR ticks. */
public class EntregandoMinerio extends AbstractState<Minerador> {

    public EntregandoMinerio(Minerador minerador) {
        super(minerador);
    }

    @Override
    public void enter() {
        getCharacter().setTicksNoCaminho(0);
        System.out.println("[MINERADOR] Mochila cheia! Vai em direcao ao deposito.");
    }

    @Override
    public void execute() {
        getCharacter().setTicksNoCaminho(getCharacter().getTicksNoCaminho() + 1);
        getCharacter().printStats("Caminhando (" + getCharacter().getTicksNoCaminho()
                + "/" + Minerador.TICKS_PARA_ENTREGAR + ")...");

        if (getCharacter().getTicksNoCaminho() >= Minerador.TICKS_PARA_ENTREGAR) {
            getCharacter().depositarMinerio();
            getCharacter().printStats("Minerio depositado!");

            if (getCharacter().getEnergia() <= Minerador.ENERGIA_MIN) {
                getCharacter().setState(new Descansando(getCharacter()));
            } else {
                getCharacter().setState(new Minerando(getCharacter()));
            }
        }
    }

    @Override
    public void leave() {
        System.out.println("[MINERADOR] Sai do deposito com a mochila vazia.");
    }
}
