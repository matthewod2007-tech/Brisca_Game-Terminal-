# MainBrisca — Brisca (juego de cartas)

Implementación en Java del juego de cartas **Brisca**, un clásico juego de envite popular en España e Italia, que se juega con una baraja española de 40 cartas.

## Estado Actual

✅ **Jugable.** El programa implementa la lógica básica de la Brisca: repartir cartas, determinar el triunfo (vida), comparar bazas y permitir que un jugador humano compita contra una IA básica.

## Lo que hay hasta ahora

### La baraja

Una baraja española estándar de 40 cartas con cuatro palos:

| Palo     | Clase           | Descripción         |
|----------|-----------------|---------------------|
| Oro      | `Oro.java`      | Monedas de oro      |
| Espada   | `Espada.java`   | Espadas             |
| Copa     | `Copa.java`     | Copas               |
| Batuco   | `Batuco.java`   | Bastos              |

Cada palo tiene 10 valores: **1, 2, 3, 4, 5, 6, 7, 10, 11, 12** (no hay 8s ni 9s — es la baraja española tradicional).

### Valores de las cartas

Cada carta tiene tres atributos:

| Atributo  | Descripción |
|-----------|-------------|
| **Rank**  | El valor nominal de la carta (1–12, sin 8–9) |
| **Points** | Puntos que vale en la Brisca (solo las figuras y los 1 valen puntos) |
| **Power** | Fuerza para ganar una baza (determina qué carta gana un envite) |

El mapeo actual de puntos y poder:

| Valor | Puntos | Poder |
|-------|--------|-------|
| 1     | 11     | 10    |
| 2     | 0      | 1     |
| 3     | 0      | 9     |
| 4     | 0      | 2     |
| 5     | 0      | 3     |
| 6     | 0      | 4     |
| 7     | 0      | 5     |
| 10    | 2      | 6     |
| 11    | 3      | 7     |
| 12    | 4      | 8     |

### Lógica del juego

#### 1. Reparto de cartas (`DarCartas`)
Cada jugador recibe 3 cartas del mazo barajado.

#### 2. Determinación del triunfo (`vida`)
Una carta del mazo determina el palo de triunfo (llamado "vida" en el código). Las cartas del mismo palo que la vida tienen prioridad sobre las demás.

#### 3. Resolución de bazas (`GanadorDeMano`)
La lógica para determinar el ganador de una baza sigue estas reglas (en orden):

1. **Triunfo gana**: Si una carta es del palo de triunfo y la otra no, gana la carta de triunfo.
2. **Mismo palo**: Si ambas cartas son del mismo palo (o ambas son triunfo), gana la de mayor poder (`power`).
3. **Palos diferentes (sin triunfo)**: Si las cartas son de palos diferentes y ninguna es triunfo, gana la carta del jugador que inició la baza (el usuario en este caso).

#### 4. Robar cartas (`refill`)
Después de cada baza, ambos jugadores roban cartas hasta tener 3 en la mano nuevamente.

#### 5. Cartas usadas (`CartaUsada`)
Las cartas jugadas se eliminan de la mano del jugador.

### Lo que falta (próximos pasos)

- [ ] **Conteo de puntos** — acumular los puntos de las bazas ganadas
- [ ] **Fin de ronda** — detectar cuando se acaban las cartas
- [ ] **Determinar ganador del juego** — comparar puntos totales
- [ ] **IA mejorada** — la IA actualmente elige cartas al azar
- [ ] **Múltiples rondas** — jugar varias manos completas
- [ ] **Interfaz mejorada** — mostrar información de forma más clara

## Estructura del proyecto

```
MainBrisca/
├── Card.java        — Clase base abstracta para todas las cartas
├── Oro.java         — Palo de oros
├── Espada.java      — Palo de espadas
├── Copa.java        — Palo de copas
├── Batuco.java      — Palo de bastos
├── Baraja.java      — Creador y barajador de la baraja
├── Game.java        — Lógica principal del juego
├── PlayGame.java    — Punto de entrada y bucle principal
└── README.md
```

## Cómo ejecutar

```bash
javac *.java
java PlayGame
```

### Flujo del juego

1. Se crea y baraja la baraja de 40 cartas
2. Se determina el palo de triunfo (vida) seleccionando una carta al azar del mazo
3. Se reparten 3 cartas a cada jugador (humano e IA)
4. **Bucle principal:**
   - Se muestran las manos de ambos jugadores y el palo de triunfo
   - El jugador humano elige una carta (1, 2 o 3)
   - La IA elige una carta al azar
   - Se determina el ganador de la baza usando `GanadorDeMano`
   - Las cartas jugadas se eliminan
   - Ambos jugadores roban hasta tener 3 cartas nuevamente
   - El juego continúa hasta que el jugador elige "No"

### Ejemplo de salida

```
----------Brisca!----------
VIDA ->Espada
Tu mano ->
1)3 de Oro (0 pts)
2)1 de Copa (11 pts)
3)12 de Batuco (4 pts)

Manode de IA ->
1)7 de Espada (0 pts)
2)10 de Oro (2 pts)
3)2 de Copa (0 pts)

Elige la carta: 2

You won the hand
Quieres continuar? Yes/No
```

## Acerca de la Brisca

La Brisca es un popular juego de cartas de envite en España, Italia y Latinoamérica. El objetivo es ganar bazas que contengan cartas de alto valor. Las cartas que más valen son los 1 (11 puntos cada uno), seguidas de las figuras (3, 2 y 1 puntos respectivamente). Normalmente juegan de 2 a 4 jugadores.

---

> *Este README fue actualizado con ayuda de inteligencia artificial (Claude Code) el 20 de agosto de 2026.*

*Este proyecto es un ejercicio de aprendizaje de POO en Java — herencia, polimorfismo y gestión de una baraja.*
