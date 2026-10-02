package ferreiro;

import core.Estado;

/** Estado inicial do Ferreiro: mantém a forja acesa e espera clientes. */
public final class Ocioso implements Estado<Ferreiro> {

    private static final Ocioso INSTANCIA = new Ocioso();

    private Ocioso() {
    }

    public static Ocioso instancia() {
        return INSTANCIA;
    }

    @Override
    public void enter(Ferreiro f) {
        f.log("Abre a ferraria e aguarda clientes.");
    }

    @Override
    public void execute(Ferreiro f) {
        if (f.temPedidoDeConserto()) {
            f.getMaquina().mudarEstado(ConsertandoPicareta.instancia());
        } else {
            f.log("Mantem a forja acesa. (picaretas consertadas: "
                    + f.getPicaretasConsertadas() + ")");
        }
    }

    @Override
    public void leave(Ferreiro f) {
        f.log("Um cliente chegou! Larga o que estava fazendo.");
    }

    @Override
    public String nome() {
        return "Ocioso";
    }
}
