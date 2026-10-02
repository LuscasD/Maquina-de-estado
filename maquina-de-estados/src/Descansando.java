/** Recupera 20 de energia por tick até ficar com a energia cheia. */
public class Descansando extends AbstractState<Minerador> {

    public Descansando(Minerador minerador) {
        super(minerador);
    }

    @Override
    public void enter() {
        System.out.println("[MINERADOR] Esta exausto. Senta num caixote para descansar.");
    }

    @Override
    public void execute() {
        getCharacter().addEnergia(20);
        getCharacter().printStats("Descansando...");

        if (getCharacter().getEnergia() >= Minerador.ENERGIA_MAX) {
            getCharacter().setState(new Minerando(getCharacter()));
        }
    }

    @Override
    public void leave() {
        System.out.println("[MINERADOR] Levanta renovado, pronto para trabalhar.");
    }
}
