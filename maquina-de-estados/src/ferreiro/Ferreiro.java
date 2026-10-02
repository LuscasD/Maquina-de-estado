package ferreiro;

import core.Agente;
import core.GerenciadorDeAgentes;
import core.Log;
import core.MaquinaDeEstados;
import core.Mensagem;

/**
 * Agente B — Ferreiro.
 * Fica ocioso (cuidando da forja) até receber um pedido de conserto
 * do Minerador; então conserta a picareta e avisa quando terminar.
 */
public class Ferreiro extends Agente {

    // ---- Limiares (constantes) ----
    public static final int TICKS_PARA_CONSERTAR = 3;

    // ---- Variáveis de estado do agente ----
    private boolean pedidoDeConserto = false;   // flag ligada pela mensagem do Minerador
    private int ticksConsertando = 0;
    private int picaretasConsertadas = 0;
    private Agente clienteAtual;

    private final MaquinaDeEstados<Ferreiro> maquina;
    private final GerenciadorDeAgentes gerenciador;

    public Ferreiro(String nome, GerenciadorDeAgentes gerenciador) {
        super(nome);
        this.gerenciador = gerenciador;
        this.maquina = new MaquinaDeEstados<>(this, nome, Ocioso.instancia());
    }

    @Override
    public void iniciar() {
        maquina.iniciar();
    }

    @Override
    public void atualizar() {
        maquina.atualizar();
    }

    @Override
    public void receberMensagem(Mensagem mensagem, Agente remetente) {
        if (mensagem == Mensagem.PICARETA_QUEBRADA) {
            pedidoDeConserto = true;
            clienteAtual = remetente;
        }
    }

    // ---- Ações usadas pelos estados ----

    void aceitarPedido() {
        pedidoDeConserto = false;
        ticksConsertando = 0;
    }

    void martelar() {
        ticksConsertando++;
    }

    void entregarPicareta() {
        picaretasConsertadas++;
        gerenciador.enviarMensagem(this, clienteAtual, Mensagem.PICARETA_CONSERTADA);
        clienteAtual = null;
    }

    void log(String texto) {
        Log.acao(getNome(), texto);
    }

    // ---- Consultas usadas nas regras de transição ----

    boolean temPedidoDeConserto() { return pedidoDeConserto; }
    boolean consertoConcluido()    { return ticksConsertando >= TICKS_PARA_CONSERTAR; }
    int getTicksConsertando()      { return ticksConsertando; }
    int getPicaretasConsertadas()  { return picaretasConsertadas; }

    MaquinaDeEstados<Ferreiro> getMaquina() {
        return maquina;
    }
}
