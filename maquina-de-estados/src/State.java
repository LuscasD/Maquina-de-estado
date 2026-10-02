/**
 * Contrato de um estado (padrão State visto em aula).
 * C é o tipo do personagem dono do estado (Minerador, Ferreiro...).
 */
public interface State<C> {
    C getCharacter();

    /** Executado uma vez, quando o personagem entra no estado. */
    void enter();

    /** Executado a cada tick. Aqui ficam as ações e as regras de transição. */
    void execute();

    /** Executado uma vez, quando o personagem sai do estado. */
    void leave();
}
