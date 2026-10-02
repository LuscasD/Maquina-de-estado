package core;

/**
 * Máquina de estados genérica. Guarda o estado atual de um agente,
 * repassa as atualizações para ele e realiza as trocas de estado
 * chamando leave() do estado antigo e enter() do novo.
 *
 * @param <T> tipo do agente dono da máquina
 */
public class MaquinaDeEstados<T> {

    private final T dono;
    private final String nomeDono;
    private Estado<T> estadoAtual;
    private Estado<T> estadoAnterior;

    public MaquinaDeEstados(T dono, String nomeDono, Estado<T> estadoInicial) {
        this.dono = dono;
        this.nomeDono = nomeDono;
        this.estadoAtual = estadoInicial;
    }

    /** Chama enter() do estado inicial. Deve ser chamado uma vez antes do primeiro tick. */
    public void iniciar() {
        Log.transicao(nomeDono, "(inicio)", estadoAtual.nome());
        estadoAtual.enter(dono);
    }

    /** Um tick: executa o estado atual. */
    public void atualizar() {
        estadoAtual.execute(dono);
    }

    /** Troca de estado: leave() do atual, troca a referência, enter() do novo. */
    public void mudarEstado(Estado<T> novoEstado) {
        estadoAnterior = estadoAtual;
        estadoAtual.leave(dono);
        Log.transicao(nomeDono, estadoAnterior.nome(), novoEstado.nome());
        estadoAtual = novoEstado;
        estadoAtual.enter(dono);
    }

    public Estado<T> getEstadoAtual() {
        return estadoAtual;
    }

    public Estado<T> getEstadoAnterior() {
        return estadoAnterior;
    }
}
