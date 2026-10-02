import core.GerenciadorDeAgentes;
import ferreiro.Ferreiro;
import minerador.Minerador;

/**
 * Ponto de entrada. Cria os agentes, liga um ao outro para a comunicação
 * e entrega o controle ao GerenciadorDeAgentes, que roda o loop principal.
 *
 * Uso: java -cp out Main [ticks] [pausaMs]
 */
public class Main {

    public static void main(String[] args) {
        int ticks = args.length > 0 ? Integer.parseInt(args[0]) : 30;
        long pausaMs = args.length > 1 ? Long.parseLong(args[1]) : 300;

        GerenciadorDeAgentes gerenciador = new GerenciadorDeAgentes(pausaMs);

        Minerador minerador = new Minerador("Minerador", gerenciador);
        Ferreiro ferreiro = new Ferreiro("Ferreiro", gerenciador);
        minerador.setFerreiro(ferreiro);

        gerenciador.registrar(minerador);
        gerenciador.registrar(ferreiro);

        gerenciador.executar(ticks);
    }
}
