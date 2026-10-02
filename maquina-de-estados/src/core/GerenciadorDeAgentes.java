package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsável pelo loop principal: guarda os agentes e,
 * a cada tick, pede que cada um atualize sua máquina de estados.
 * Também entrega as mensagens trocadas entre agentes.
 */
public class GerenciadorDeAgentes {

    private final List<Agente> agentes = new ArrayList<>();
    private final long pausaEntreTicksMs;

    public GerenciadorDeAgentes(long pausaEntreTicksMs) {
        this.pausaEntreTicksMs = pausaEntreTicksMs;
    }

    public void registrar(Agente agente) {
        agentes.add(agente);
    }

    /** Entrega uma mensagem imediatamente ao destinatário. */
    public void enviarMensagem(Agente remetente, Agente destinatario, Mensagem mensagem) {
        Log.mensagem(remetente.getNome(), destinatario.getNome(), mensagem);
        destinatario.receberMensagem(mensagem, remetente);
    }

    /** Loop principal da simulação. */
    public void executar(int totalDeTicks) {
        Log.setTick(0);
        for (Agente agente : agentes) {
            agente.iniciar();
        }

        for (int tick = 1; tick <= totalDeTicks; tick++) {
            Log.setTick(tick);
            Log.separador(tick);
            for (Agente agente : agentes) {
                agente.atualizar();
            }
            pausar();
        }
        System.out.println("\nSimulacao encerrada apos " + totalDeTicks + " ticks.");
    }

    private void pausar() {
        if (pausaEntreTicksMs <= 0) {
            return;
        }
        try {
            Thread.sleep(pausaEntreTicksMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
