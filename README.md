# MainBrisca — Brisca (juego de cartas en terminal)

Implementación en Java del tradicional juego de cartas **Brisca**, un clásico juego de envite y bazas popular en España, Italia y Latinoamérica, jugado con una baraja española de 40 cartas.

---

## Estado Actual

  **Completamente jugable.** El proyecto permite disputar una partida completa de Brisca en la terminal contra una IA con toma de decisiones estratégica:
- **Partida de 40 cartas:** El juego fluye continuamente repartiendo, jugando bazas y robando cartas hasta agotar el mazo y las manos.
- **Sistema de puntuación acumulativa:** Registro y visualización en tiempo real de los puntos del jugador y de la IA (120 puntos totales en juego; 61+ puntos para ganar).
- **Motor de decisión inteligente para la IA:** Algoritmo heurístico que decide qué carta jugar evaluando el valor de la baza, puntos en disputa, conservación de triunfos y descarte óptimo.
- **Interfaz gráfica en terminal (ASCII Art):** Visualización de cartas en recuadros ASCII con sus rangos y los símbolos característicos de cada palo.
- **Cierre controlado:** Hilo de apagado (`ShutdownHook`) para capturar la interrupción del juego (ej. `Ctrl+C`) y mostrar un mensaje de agradecimiento y el logotipo estilizado.

---

## La Baraja y Valores de las Cartas

El juego utiliza una baraja española tradicional de 40 cartas distribuida en 4 palos (10 cartas por palo: 1 al 7, Sota (10), Caballo (11) y Rey (12)).

### Palos y Clases

| Palo | Clase | Símbolo ASCII | Descripción |
| :--- | :--- | :---: | :--- |
| **Oro** | `Oro.java` | `o` | Monedas de oro |
| **Espada** | `Espada.java` | `U` | Espadas |
| **Copa** | `Copa.java` | `I` | Copas |
| **Batuco** | `Batuco.java` | `†` | Bastos / Garrotes |

### Puntos y Poder de las Cartas

En la Brisca, el valor de las cartas no sigue necesariamente el orden numérico:
- **As (1)** y **Tres (3)** son las cartas más valiosas y de mayor fuerza.
- Las figuras (**Sota (10)**, **Caballo (11)** y **Rey (12)**) otorgan puntos intermedios.
- Las cartas del **2 al 7** no tienen valor en puntos (cartas blancas), pero tienen jerarquía de poder.

| Carta / Rango | Nombre Tradicional | Puntos | Poder de Baza |
| :---: | :--- | :---: | :---: |
| **1** | As | 11 | 10 |
| **3** | Tres | 10 | 9 |
| **12** | Rey | 4 | 8 |
| **11** | Caballo | 3 | 7 |
| **10** | Sota | 2 | 6 |
| **7** | Siete | 0 | 5 |
| **6** | Seis | 0 | 4 |
| **5** | Cinco | 0 | 3 |
| **4** | Cuatro | 0 | 2 |
| **2** | Dos | 0 | 1 |

> **Nota sobre puntuación:** Cada palo suma 30 puntos, lo que da un total de **120 puntos** en la baraja. El jugador que obtenga **61 puntos o más** gana la partida.

---

## Estructura del Proyecto y Documentación de Archivos

```
Brisca_Game-Terminal-/
├── main.java         — Clase principal, punto de entrada y ciclo de vida de la terminal
├── Game.java         — Motor de juego, resolución de bazas y lógica de decisión de la IA
├── Card.java         — Clase abstracta base de las cartas y renderizado ASCII
├── Baraja.java       — Generación y barajado de las 40 cartas españolas
├── Oro.java          — Subclase para el palo de Oros
├── Espada.java       — Subclase para el palo de Espadas
├── Copa.java         — Subclase para el palo de Copas
├── Batuco.java       — Subclase para el palo de Bastos (Batucos)
├── AI_GUIDE.txt      — Especificación técnica y arquitectura del motor de IA
└── README.md         — Documentación general del proyecto
```

### Documentación Detallada de Archivos

#### 1. `main.java`
Punto de entrada (`public static void main`) y gestor de la interfaz en terminal.
- **Bucle de juego (`runapp`):** Inicializa la baraja, mezcla las cartas, determina la carta de triunfo (`vida`), reparte las manos iniciales y ejecuta las rondas hasta que el mazo y las manos se vacíen.
- **Interacción y entrada:** Captura la selección de carta del jugador con validación de entradas vacías y formatos numéricos.
- **Visualización en consola:** Limpieza de pantalla mediante secuencias ANSI (`clearScreen()`), tiempos de espera controlados (`waitSeconds()`) y despliegue del banner estilizado (`printLogo()`).
- **Control de puntuación y fin de partida:** Suma los puntos tras cada baza, muestra el marcador acumulado y proclama al ganador al terminar todas las cartas.
- **Shutdown Hook:** Registra un hook de JVM para asegurar que si el usuario interrumpe la ejecución (ej. `Ctrl+C`), se muestre una despedida limpia y el logo.

#### 2. `Game.java`
Clase encargada de las reglas de la Brisca y de la inteligencia de la IA.
- **Reparto y recarga de manos:**
  - `DarCartas(shuffled, count)`: Extrae y entrega una mano inicial de 3 cartas.
  - `refill(shuffled, hand)`: Roba cartas del mazo restante para mantener 3 cartas en mano tras cada baza.
- **Resolución de bazas:**
  - `GanadorDeMano(...)` y `ganaCarta(...)`: Determina si una carta vence a otra evaluando si alguna es del palo de triunfo (`vida`), si coinciden en palo (comparando `power`), o si son de palos diferentes sin triunfo (gana quien abrió la baza).
- **Consumo de cartas:**
  - `CartaUsada(hand, selected)`: Muestra la carta jugada en formato ASCII y descripción textual, la retira de la mano del jugador y retorna sus puntos para el conteo de la baza.
- **Visualización:**
  - `showCard(hand)`: Imprime las cartas disponibles del jugador en recuadros ASCII numerados.
- **Motor de decisión de la IA (`decidirCartaIA`):**
  - **Chequeo de victoria inmediata:** Si ganar la baza permite a la IA alcanzar 61 puntos o más, juega inmediatamente la carta ganadora.
  - **Evaluación del valor de la baza:**
    - Si el jugador lanzó una carta de alto valor (As o Tres, $\ge 10$ pts), intenta ganarla con la carta de menor coste posible (`getLowestWinningCard`).
    - Si el jugador lanzó una figura ($\ge 2$ pts), intenta ganarla con una carta del mismo palo (`getWinningSameSuit`) o con un triunfo de 0 puntos (`getLowestTrumpWin`), sin quemar triunfos mayores.
    - Si el jugador lanzó una carta de 0 puntos, solo gana si tiene una carta del mismo palo de 0 puntos con mayor poder.
  - **Estrategia de descarte (`getBestDiscard`):** Si no puede o no le conviene ganar la baza, descarta la carta menos valiosa (priorizando cartas de 0 puntos de palos no triunfo y menor poder).

#### 3. `Card.java`
Clase abstracta que modela los atributos comunes de cualquier carta de la baraja española:
- **Atributos protegidos:** `rank` (valor numérico 1-12), `points` (puntos Brisca), `power` (jerarquía de fuerza) y `suitName` (nombre del palo).
- **`toAscii()`:** Genera una representación gráfica de la carta en arte ASCII dentro de un marco de 5 líneas, incorporando el rango y el símbolo característico del palo:
  ```
  +-----+
  |1    |
  |  o  |
  |   1 |
  +-----+
  ```
- **`toString()`:** Retorna una descripción textual amigable (ej. `1 de Oro (11 pts)`).

#### 4. `Baraja.java`
Generador y mezclador de la baraja:
- Define los arreglos constantes `RANK`, `POINTS` y `POWER` con la correlación exacta de las reglas de Brisca.
- `HacerBaraja()`: Instancia las 40 cartas creando 10 de cada palo (`Oro`, `Espada`, `Copa`, `Batuco`).
- `MezclarCartas(deck)`: Baraja la colección aleatoriamente mediante `Collections.shuffle`.

#### 5. Subclases de Palos (`Oro.java`, `Espada.java`, `Copa.java`, `Batuco.java`)
Clases concretas que heredan de `Card` e inicializan a través de `super(...)` su rango, puntos, poder y denominación de palo respectiva.

#### 6. `AI_GUIDE.txt`
Archivo de documentación técnica interna que contiene la guía de arquitectura y el diagrama de flujo del motor de toma de decisiones de la IA, explicando las prioridades heurísticas y firmas de los métodos implementados en `Game.java`.

---

## Cómo Compilar y Ejecutar

### Requisitos
- **Java Development Kit (JDK) 17** o superior (recomendado para soporte de expresiones `switch`).
- Terminal compatible con códigos de escape ANSI para limpieza de pantalla y caracteres UTF-8 para símbolos (`†`).

### Compilación y Ejecución

Desde el directorio raíz del proyecto:

```bash
# Compilar todos los archivos fuente
javac *.java

# Iniciar el juego
java main
```

---

## Flujo de una Partida

1. **Inicio:** Se muestra el logo ASCII de Brisca y se limpia la pantalla.
2. **Preparación:** Se crea la baraja de 40 cartas y se mezcla aleatoriamente.
3. **Triunfo (Vida):** Se revela la carta de triunfo que define el palo con máxima jerarquía durante toda la partida.
4. **Reparto inicial:** El jugador y la IA reciben 3 cartas cada uno.
5. **Bucle de bazas:**
   - Se muestra el palo de triunfo (`VIDA`) y las cartas de la mano del usuario con arte ASCII.
   - El jugador ingresa el número de la carta que desea jugar (1, 2 o 3).
   - La IA analiza la jugada y elige estratégicamente su carta mediante `decidirCartaIA`.
   - Se muestran las cartas jugadas en ASCII y se anuncia el ganador de la baza.
   - Los puntos de la baza se suman al ganador y se muestra el marcador actual (`Tus puntos` vs `Puntos de ia`).
   - Ambos jugadores roban una carta del mazo para reponer su mano a 3 cartas (mientras queden cartas disponibles).
6. **Fin del juego:**
   - Una vez agotado el mazo y jugadas todas las cartas de las manos, el sistema evalúa la puntuación acumulada.
   - Se proclama al ganador (`YOU WON!!!` o `La ia gano.`) y se muestra el logotipo final.

---

## Ejemplo de la Interfaz en Terminal

```
----------Brisca!----------
VIDA ->Oro
Tu mano ->
(1)
+-----+
|3    |
|  U  |
|   3 |
+-----+
(2)
+-----+
|1    |
|  I  |
|   1 |
+-----+
(3)
+-----+
|12   |
|  †  |
|   12|
+-----+

o = oro
u = espada
† = batuco
I = copa

Elige la carta: 2

Ganaste la mano

Used cards ->
+-----+
|1    |
|  I  |
|   1 |
+-----+
1 de Copa (11 pts)

Used cards ->
+-----+
|7    |
|  I  |
|   7 |
+-----+
7 de Copa (0 pts)

Tus puntos:11
Puntos de ia: 0
```


*Este archivo fue realizado con Inteligencia Artificial (IA).*
