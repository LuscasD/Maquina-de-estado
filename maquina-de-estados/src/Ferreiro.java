/**
 * Agente B — Ferreiro.
 * Fica ocioso até receber um pedido de conserto do Minerador;
 * então conserta a picareta e avisa quando terminar.
 */
public class Ferreiro implements Character {
    // Limiar
    public static final int TICKS_PARA_CONSERTAR = 3;

    private boolean pedidoDeConserto = false;   // ligada pela mensagem do Minerador
    private int ticksConsertando = 0;
    private int picaretasConsertadas = 0;
    private Character clienteAtual;

    private State<Ferreiro> state = new Ocioso(this);

    public Ferreiro() {
        state.enter();
    }

    // ---- gets & adds ----

    public boolean temPedidoDeConserto() {
        return pedidoDeConserto;
    }

    public void setPedidoDeConserto(boolean pedidoDeConserto) {
        this.pedidoDeConserto = pedidoDeConserto;
    }

    public int getTicksConsertando() {
        return ticksConsertando;
    }

    public void addTicksConsertando(int ticks) {
        this.ticksConsertando += ticks;
    }

    public void setTicksConsertando(int ticksConsertando) {
        this.ticksConsertando = ticksConsertando;
    }

    // ---- comunicação ----

    public void entregarPicareta() {
        picaretasConsertadas++;
        System.out.println("[FERREIRO]  *** MENSAGEM para o Minerador: " + Mensagem.PICARETA_CONSERTADA);
        clienteAtual.receiveMessage(Mensagem.PICARETA_CONSERTADA, this);
        clienteAtual = null;
    }

    @Override
    public void receiveMessage(Mensagem mensagem, Character remetente) {
        if (mensagem == Mensagem.PICARETA_QUEBRADA) {
            pedidoDeConserto = true;
            clienteAtual = remetente;
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
        System.out.println("[FERREIRO]  >>> TRANSICAO: " + this.state.getClass().getSimpleName()
                + " -> " + state.getClass().getSimpleName());
        this.state = state;
        state.enter();
    }

    @Override
    public void printStats(String state) {
        System.out.printf("[FERREIRO]  %-24s | Conserto: %d/%d | Picaretas consertadas: %d%n",
                state, ticksConsertando, TICKS_PARA_CONSERTAR, picaretasConsertadas);
    }
}
