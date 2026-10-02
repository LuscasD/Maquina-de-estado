/**
 * Contrato que todo agente inteligente segue.
 * receiveMessage foi acrescentado para a comunicação entre agentes (bônus).
 */
public interface Character {
    void printStats(String state);
    void update();
    void setState(State state);
    void receiveMessage(Mensagem mensagem, Character remetente);
}
