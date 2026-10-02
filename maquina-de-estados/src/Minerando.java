/** Estado inicial do Minerador: extrai 1 minério por tick. */
public class Minerando extends AbstractState<Minerador> {

    public Minerando(Minerador minerador) {
        super(minerador);
    }

    @Override
    public void enter() {
        System.out.println("[MINERADOR] Desce para a galeria e empunha a picareta.");
    }

    @Override
    public void execute() {
        getCharacter().addEnergia(-10);
        getCharacter().addDurabilidadePicareta(-1);
        getCharacter().addMinerioNaMochila(1);
        getCharacter().printStats("Minerando...");

        // Regras de transição, em ordem de prioridade
        if (getCharacter().getDurabilidadePicareta() == 0) {
            getCharacter().setState(new AguardandoPicareta(getCharacter()));
        } else if (getCharacter().getMinerioNaMochila() >= Minerador.CAPACIDADE_MOCHILA) {
            getCharacter().setState(new EntregandoMinerio(getCharacter()));
        } else if (getCharacter().getEnergia() <= Minerador.ENERGIA_MIN) {
            getCharacter().setState(new Descansando(getCharacter()));
        }
    }

    @Override
    public void leave() {
        System.out.println("[MINERADOR] Para de minerar e guarda a picareta no cinto.");
    }
}
