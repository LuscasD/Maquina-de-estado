package core;

/**
 * Contrato de um estado no padrão State.
 * Cada estado define o que acontece ao entrar, a cada atualização e ao sair.
 *
 * @param <T> tipo do agente que possui este estado
 */
public interface Estado<T> {

    /** Executado uma única vez, quando o agente entra no estado. */
    void enter(T agente);

    /** Executado a cada tick enquanto o agente permanece no estado. Aqui ficam as regras de transição. */
    void execute(T agente);

    /** Executado uma única vez, quando o agente sai do estado. */
    void leave(T agente);

    /** Nome usado nos logs. */
    String nome();
}
