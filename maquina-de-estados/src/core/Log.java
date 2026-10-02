package core;

/** Saída padronizada no console, sempre com o número do tick atual. */
public final class Log {

    private static int tickAtual = 0;

    private Log() {
    }

    static void setTick(int tick) {
        tickAtual = tick;
    }

    private static String prefixo() {
        return String.format("[Tick %02d]", tickAtual);
    }

    /** Ação comum de um agente (enter/execute/leave). */
    public static void acao(String agente, String texto) {
        System.out.printf("%s %-10s | %s%n", prefixo(), agente, texto);
    }

    /** Troca de estado — destacada para ser fácil de achar no console. */
    public static void transicao(String agente, String de, String para) {
        System.out.printf("%s %-10s | >>> TRANSICAO: %s -> %s%n", prefixo(), agente, de, para);
    }

    /** Mensagem enviada de um agente para outro. */
    public static void mensagem(String de, String para, Mensagem msg) {
        System.out.printf("%s %-10s | *** MENSAGEM %s -> %s: %s%n", prefixo(), de, de, para, msg);
    }

    public static void separador(int tick) {
        System.out.printf("%n----------------------------- TICK %02d -----------------------------%n", tick);
    }
}
