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

Requer JDK 17+ (testado com JDK 21).

**Linux / macOS**

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out Main
```

**Windows (PowerShell)**

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out Main
```

Parâmetros opcionais: `java -cp out Main [ticks] [pausaMs]`
(padrão: 30 ticks, 300 ms entre ticks). Ex.: `java -cp out Main 40 0` roda 40 ticks sem pausa.

## Como observar as transições nos logs

Cada linha mostra o tick e o agente:

```
[Tick 14] Minerador  | Para de minerar e guarda a picareta no cinto.        <- leave()
[Tick 14] Minerador  | >>> TRANSICAO: Minerando -> AguardandoPicareta       <- troca de estado
[Tick 14] Minerador  | A picareta quebrou! Leva a picareta ao Ferreiro.     <- enter()
[Tick 14] Minerador  | *** MENSAGEM Minerador -> Ferreiro: PICARETA_QUEBRADA
[Tick 14] Ferreiro   | >>> TRANSICAO: Ocioso -> ConsertandoPicareta
```

- `>>> TRANSICAO` marca toda troca de estado (filtre com `java -cp out Main | grep TRANSICAO`).
- `*** MENSAGEM` marca a comunicação entre os agentes.
- A linha antes de `>>> TRANSICAO` vem do `leave()` do estado antigo; a linha depois vem do `enter()` do novo.

## Estrutura

```
src/
├── Main.java                       # cria agentes e inicia o loop
├── core/
│   ├── Estado.java                 # interface do padrão State (enter/execute/leave)
│   ├── MaquinaDeEstados.java       # guarda o estado atual e faz as trocas
│   ├── Agente.java                 # classe base dos agentes
│   ├── GerenciadorDeAgentes.java   # loop principal + entrega de mensagens
│   ├── Mensagem.java               # tipos de mensagem
│   └── Log.java                    # saída padronizada no console
├── minerador/                      # Agente A e seus 4 estados
└── ferreiro/                       # Agente B e seus 2 estados
docs/
├── diagramas/                      # .dot, .png e .svg dos diagramas
└── Trabalho_Maquina_de_Estados.pdf
```
