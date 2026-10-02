package core;

/** Tipos de mensagem trocados entre os agentes. */
public enum Mensagem {
    /** Minerador -> Ferreiro: a picareta quebrou e precisa de conserto. */
    PICARETA_QUEBRADA,
    /** Ferreiro -> Minerador: o conserto terminou, a picareta está pronta. */
    PICARETA_CONSERTADA
}
