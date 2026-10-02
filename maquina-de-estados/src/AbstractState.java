/**
 * Classe base dos estados: guarda a referência ao personagem
 * e oferece enter() e leave() vazios, para que cada estado
 * só sobrescreva o que precisar.
 */
public abstract class AbstractState<C> implements State<C> {
    private final C character;

    public AbstractState(C character) {
        this.character = character;
    }

    @Override
    public C getCharacter() {
        return character;
    }

    @Override
    public void enter() {
    }

    @Override
    public void leave() {
    }
}
