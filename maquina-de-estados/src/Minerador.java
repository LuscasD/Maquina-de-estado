/**
 * Agente A — Minerador.
 * Extrai minério, entrega no depósito quando a mochila enche,
 * descansa quando cansa e pede conserto ao Ferreiro quando a picareta quebra.
 */
public class Minerador implements Character {
    // Limiares
    public static final int ENERGIA_MAX = 100;
    public static final int ENERGIA_MIN = 30;           // energia <= 30 -> Descansando
    public static final int CAPACIDADE_MOCHILA = 5;     // mochila >= 5 -> EntregandoMinerio
    public static final int DURABILIDADE_MAX = 8;       // durabilidade == 0 -> AguardandoPicareta
    public static final int TICKS_PARA_ENTREGAR = 2;    // tempo de caminhada até o depósito

    private int energia = ENERGIA_MAX;
    private int minerioNaMochila = 0;
    private int durabilidadePicareta = DURABILIDADE_MAX;
    private int minerioDepositado = 0;
    private int ticksNoCaminho = 0;
    private boolean picaretaConsertada = false;   // ligada pela mensagem do Ferreiro

    private Character ferreiro;
    private State<Minerador> state = new Minerando(this);

    public Minerador() {
        state.enter();
    }

    public void setFerreiro(Character ferreiro) {
        this.ferreiro = ferreiro;
    }

    // ---- gets & adds ----

    public int getEnergia() {
        return energia;
    }

    public void addEnergia(int energia) {
        this.energia += energia;
        this.energia = Math.max(0, Math.min(this.energia, ENERGIA_MAX));
    }

    public int getMinerioNaMochila() {
        return minerioNaMochila;
    }

    public void addMinerioNaMochila(int quantidade) {
        this.minerioNaMochila += quantidade;
    }

    public void depositarMinerio() {
        minerioDepositado += minerioNaMochila;
        minerioNaMochila = 0;
    }

    public int getDurabilidadePicareta() {
        return durabilidadePicareta;
    }

    public void addDurabilidadePicareta(int valor) {
        this.durabilidadePicareta += valor;
        this.durabilidadePicareta = Math.max(0, Math.min(this.durabilidadePicareta, DURABILIDADE_MAX));
    }

    public int getTicksNoCaminho() {
        return ticksNoCaminho;
    }

    public void setTicksNoCaminho(int ticksNoCaminho) {
        this.ticksNoCaminho = ticksNoCaminho;
    }

    public boolean isPicaretaConsertada() {
        return picaretaConsertada;
    }

    public void setPicaretaConsertada(boolean picaretaConsertada) {
        this.picaretaConsertada = picaretaConsertada;
    }

    // ---- comunicação ----

    public void pedirConserto() {
        System.out.println("[MINERADOR] *** MENSAGEM para o Ferreiro: " + Mensagem.PICARETA_QUEBRADA);
        ferreiro.receiveMessage(Mensagem.PICARETA_QUEBRADA, this);
    }

    @Override
    public void receiveMessage(Mensagem mensagem, Character remetente) {
        if (mensagem == Mensagem.PICARETA_CONSERTADA) {
            picaretaConsertada = true;
        }
    }

    // ---- Character ----

    @Override
    public void update() {
        state.execute();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setState(State state) {
        this.state.leave();
        System.out.println("[MINERADOR] >>> TRANSICAO: " + this.state.getClass().getSimpleName()
                + " -> " + state.getClass().getSimpleName());
        this.state = state;
        state.enter();
    }

    @Override
    public void printStats(String state) {
        System.out.printf("[MINERADOR] %-24s | Energia: %3d | Mochila: %d/%d | Picareta: %d/%d | Depositado: %d%n",
                state, energia, minerioNaMochila, CAPACIDADE_MOCHILA,
                durabilidadePicareta, DURABILIDADE_MAX, minerioDepositado);
    }
}
