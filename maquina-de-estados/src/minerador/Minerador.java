package minerador;

import core.Agente;
import core.GerenciadorDeAgentes;
import core.Log;
import core.MaquinaDeEstados;
import core.Mensagem;

/**
 * Agente A — Minerador.
 * Extrai minério, leva até o depósito quando a mochila enche,
 * descansa quando a energia baixa e pede conserto ao Ferreiro
 * quando a picareta quebra.
 */
public class Minerador extends Agente {

    // ---- Limiares (constantes) ----
    public static final int ENERGIA_MAX = 100;
    public static final int ENERGIA_MIN_PARA_TRABALHAR = 30;  // <= isso -> Descansando
    public static final int GASTO_ENERGIA_POR_TICK = 10;
    public static final int RECUPERACAO_ENERGIA_POR_TICK = 20;
    public static final int CAPACIDADE_MOCHILA = 5;           // >= isso -> EntregandoMinerio
    public static final int DURABILIDADE_MAX_PICARETA = 8;    // == 0 -> AguardandoPicareta
    public static final int TICKS_PARA_ENTREGAR = 2;          // caminho até o depósito

    // ---- Variáveis de estado do agente ----
    private int energia = ENERGIA_MAX;
    private int minerioNaMochila = 0;
    private int durabilidadePicareta = DURABILIDADE_MAX_PICARETA;
    private int minerioDepositado = 0;
    private int ticksNoCaminho = 0;
    private boolean picaretaConsertada = false;   // flag ligada pela mensagem do Ferreiro

    private final MaquinaDeEstados<Minerador> maquina;
    private final GerenciadorDeAgentes gerenciador;
    private Agente ferreiro;

    public Minerador(String nome, GerenciadorDeAgentes gerenciador) {
        super(nome);
        this.gerenciador = gerenciador;
        this.maquina = new MaquinaDeEstados<>(this, nome, Minerando.instancia());
    }

    public void setFerreiro(Agente ferreiro) {
        this.ferreiro = ferreiro;
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
        if (mensagem == Mensagem.PICARETA_CONSERTADA) {
            picaretaConsertada = true;
        }
    }

    // ---- Ações usadas pelos estados ----

    void minerar() {
        energia = Math.max(0, energia - GASTO_ENERGIA_POR_TICK);
        durabilidadePicareta = Math.max(0, durabilidadePicareta - 1);
        minerioNaMochila++;
    }

    void descansar() {
        energia = Math.min(ENERGIA_MAX, energia + RECUPERACAO_ENERGIA_POR_TICK);
    }

    void depositarMinerio() {
        minerioDepositado += minerioNaMochila;
        minerioNaMochila = 0;
    }

    void pedirConserto() {
        gerenciador.enviarMensagem(this, ferreiro, Mensagem.PICARETA_QUEBRADA);
    }

    void receberPicaretaNova() {
        durabilidadePicareta = DURABILIDADE_MAX_PICARETA;
        picaretaConsertada = false;
    }

    void log(String texto) {
        Log.acao(getNome(), texto);
    }

    String status() {
        return String.format("energia=%d mochila=%d/%d picareta=%d/%d depositado=%d",
                energia, minerioNaMochila, CAPACIDADE_MOCHILA,
                durabilidadePicareta, DURABILIDADE_MAX_PICARETA, minerioDepositado);
    }

    // ---- Consultas usadas nas regras de transição ----

    boolean picaretaQuebrada()   { return durabilidadePicareta == 0; }
    boolean mochilaCheia()       { return minerioNaMochila >= CAPACIDADE_MOCHILA; }
    boolean cansado()            { return energia <= ENERGIA_MIN_PARA_TRABALHAR; }
    boolean descansado()         { return energia >= ENERGIA_MAX; }
    boolean isPicaretaConsertada() { return picaretaConsertada; }

    int getTicksNoCaminho()           { return ticksNoCaminho; }
    void setTicksNoCaminho(int valor) { ticksNoCaminho = valor; }

    MaquinaDeEstados<Minerador> getMaquina() {
        return maquina;
    }
}
