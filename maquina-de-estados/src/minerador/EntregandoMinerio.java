package minerador;

import core.Estado;

/** Leva o minério da mochila até o depósito (demora TICKS_PARA_ENTREGAR ticks). */
public final class EntregandoMinerio implements Estado<Minerador> {

    private static final EntregandoMinerio INSTANCIA = new EntregandoMinerio();

    private EntregandoMinerio() {
    }

    public static EntregandoMinerio instancia() {
        return INSTANCIA;
    }

    @Override
    public void enter(Minerador m) {
        m.setTicksNoCaminho(0);
        m.log("Mochila cheia! Vai em direcao ao deposito.");
    }

    @Override
    public void execute(Minerador m) {
        m.setTicksNoCaminho(m.getTicksNoCaminho() + 1);
        m.log("Caminhando ate o deposito (" + m.getTicksNoCaminho() + "/"
                + Minerador.TICKS_PARA_ENTREGAR + ").");

        if (m.getTicksNoCaminho() >= Minerador.TICKS_PARA_ENTREGAR) {
            m.depositarMinerio();
            m.log("Despeja o minerio no deposito. [" + m.status() + "]");
            if (m.cansado()) {
                m.getMaquina().mudarEstado(Descansando.instancia());
            } else {
                m.getMaquina().mudarEstado(Minerando.instancia());
            }
        }
    }

    @Override
    public void leave(Minerador m) {
        m.log("Sai do deposito com a mochila vazia.");
    }

    @Override
    public String nome() {
        return "EntregandoMinerio";
    }
}
