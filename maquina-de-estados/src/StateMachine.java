import java.util.ArrayList;

/**
 * Classe responsável por executar a máquina de estados:
 * guarda a lista de personagens e, a cada tick, chama update() de cada um.
 *
 * Uso: java -cp out StateMachine [ticks] [pausaMs]
 *      ticks = 0 roda para sempre.
 */
public class StateMachine {
    private final ArrayList<Character> characters = new ArrayList<>();

    public void run(int totalTicks, long pausaMs) {
        System.out.println("===== INICIO =====");
        Minerador minerador = new Minerador();
        Ferreiro ferreiro = new Ferreiro();
        minerador.setFerreiro(ferreiro);

        characters.add(minerador);
        characters.add(ferreiro);

        int tick = 0;
        while (totalTicks <= 0 || tick < totalTicks) {
            tick++;
            System.out.println("\n===== TICK " + tick + " =====");

            for (Character c : characters) {
                c.update();
            }

            try {
                Thread.sleep(pausaMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n===== FIM (" + totalTicks + " ticks) =====");
    }

    public static void main(String[] args) {
        int ticks = args.length > 0 ? Integer.parseInt(args[0]) : 30;
        long pausaMs = args.length > 1 ? Long.parseLong(args[1]) : 1000;
        new StateMachine().run(ticks, pausaMs);
    }
}
