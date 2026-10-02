# Trabalho I — Máquina de Estados: Minerador & Ferreiro

Disciplina: **Inteligência Artificial e Ilusão de Inteligência em Jogos** — PUCPR
Prof.ª Marina de Lara

Simulação em Java puro (sem bibliotecas externas) de dois agentes controlados pelo
**padrão State** (`enter` / `execute` / `leave`), com **comunicação entre agentes** (bônus).

## Cenário

Uma pequena mina medieval. O **Minerador** (Agente A) extrai minério, leva a carga ao
depósito e descansa quando cansa. Sua picareta se desgasta a cada golpe; quando quebra,
ele pede conserto ao **Ferreiro** (Agente B) e só volta ao trabalho quando o Ferreiro avisa
que terminou.

## Agentes e estados

| Agente | Estados | Comentário |
|---|---|---|
| **Minerador** (A) | `Minerando` → `EntregandoMinerio`, `Descansando`, `AguardandoPicareta` | 4 estados, transições por limiares de energia, mochila e durabilidade |
| **Ferreiro** (B) | `Ocioso` ↔ `ConsertandoPicareta` | 2 estados, transição por flag de mensagem e contador |

Comunicação: `Minerador → Ferreiro: PICARETA_QUEBRADA` e `Ferreiro → Minerador: PICARETA_CONSERTADA`.

Diagramas: [`docs/diagramas/minerador.png`](docs/diagramas/minerador.png) e
[`docs/diagramas/ferreiro.png`](docs/diagramas/ferreiro.png). Documento completo em
[`docs/Trabalho_Maquina_de_Estados.pdf`](docs/Trabalho_Maquina_de_Estados.pdf).

## Como compilar e rodar

Requer JDK 17+ (testado com JDK 21). Todos os arquivos estão em `src/`.

```bash
javac -encoding UTF-8 -d out src/*.java
java -cp out StateMachine
```

Parâmetros opcionais: `java -cp out StateMachine [ticks] [pausaMs]`
(padrão: 30 ticks, 1000 ms entre ticks). `ticks = 0` roda para sempre, como no exemplo da aula.
Ex.: `java -cp out StateMachine 40 0` roda 40 ticks sem pausa.

## Como observar as transições nos logs

Cada tick começa com `===== TICK n =====`, e cada linha mostra o agente:

```
[MINERADOR] Para de minerar e guarda a picareta no cinto.      <- leave()
[MINERADOR] >>> TRANSICAO: Minerando -> AguardandoPicareta     <- troca de estado
[MINERADOR] A picareta quebrou! Leva a picareta ao Ferreiro.   <- enter()
[MINERADOR] *** MENSAGEM para o Ferreiro: PICARETA_QUEBRADA
[FERREIRO]  >>> TRANSICAO: Ocioso -> ConsertandoPicareta
```

- `>>> TRANSICAO` marca toda troca de estado (filtre com `java -cp out StateMachine 30 0 | grep TRANSICAO`;
  no Windows, `| findstr TRANSICAO`).
- `*** MENSAGEM` marca a comunicação entre os agentes.
- A linha antes de `>>> TRANSICAO` vem do `leave()` do estado antigo; a linha depois vem do `enter()` do novo.
- As linhas com `|` são o `printStats()` de cada agente, com as variáveis atuais.

## Estrutura

Segue a FSM genérica vista em aula (exemplo Juca/Bob):

```
src/
├── State.java                 # interface State<C>: getCharacter, enter, execute, leave
├── AbstractState.java         # guarda o personagem; enter/leave vazios por padrão
├── Character.java             # interface dos agentes: update, setState, printStats, receiveMessage
├── StateMachine.java          # lista de characters + loop principal (main)
├── Mensagem.java              # PICARETA_QUEBRADA / PICARETA_CONSERTADA
├── Minerador.java             # Agente A
├── Minerando.java             # \
├── EntregandoMinerio.java     #  | estados do Minerador
├── Descansando.java           #  |
├── AguardandoPicareta.java    # /
├── Ferreiro.java              # Agente B
├── Ocioso.java                # \ estados do Ferreiro
└── ConsertandoPicareta.java   # /
docs/
├── diagramas/                 # .dot, .png e .svg dos diagramas
└── Trabalho_Maquina_de_Estados.pdf
```
