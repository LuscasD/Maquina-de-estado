package core;

/**
 * Classe base de todo agente: tem um nome, sabe se atualizar a cada tick
 * e pode receber mensagens de outros agentes.
 */
public abstract class Agente {

    private final String nome;

    protected Agente(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /** Chamado uma vez antes do loop começar. */
    public abstract void iniciar();

    /** Chamado uma vez por tick pelo gerenciador. */
    public abstract void atualizar();

    /** Recebe uma mensagem enviada por outro agente (comunicação entre agentes). */
    public abstract void receberMensagem(Mensagem mensagem, Agente remetente);
}
