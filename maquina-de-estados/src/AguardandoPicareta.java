/**
 * Comunicação entre agentes: ao entrar, avisa o Ferreiro que a picareta quebrou.
 * Só volta a minerar quando o Ferreiro responde com PICARETA_CONSERTADA.
 */
public class AguardandoPicareta extends AbstractState<Minerador> {

    public AguardandoPicareta(Minerador minerador) {
        super(minerador);
    }

    @Override
    public void enter() {
        System.out.println("[MINERADOR] A picareta quebrou! Leva a picareta ao Ferreiro.");
        getCharacter().pedirConserto();
    }

    @Override
    public void execute() {
        if (getCharacter().isPicaretaConsertada()) {
            getCharacter().setPicaretaConsertada(false);
            getCharacter().addDurabilidadePicareta(Minerador.DURABILIDADE_MAX);
            getCharacter().printStats("Recebeu a picareta!");
            getCharacter().setState(new Minerando(getCharacter()));
        } else {
            getCharacter().printStats("Esperando o Ferreiro...");
        }
    }

    @Override
    public void leave() {
        System.out.println("[MINERADOR] Agradece ao Ferreiro e volta para a mina.");
    }
}
