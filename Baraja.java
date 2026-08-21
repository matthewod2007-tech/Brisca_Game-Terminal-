
import java.util.ArrayList;
import java.util.Collections;

public class Baraja {

    private final int[] RANK = {1, 2, 3, 4, 5, 6, 7, 10, 11, 12};
    private final int[] POINTS = {11, 0, 0, 0, 0, 0, 0, 2, 3, 4};
    private final int[] POWER = {10, 1, 9, 2, 3, 4, 5, 6, 7, 8};

    public ArrayList<Card> HacerBaraja() {
        ArrayList<Card> deck = new ArrayList<>();
        for (int i = 0; i < RANK.length; i++) {
            deck.add(new Oro(RANK[i], POINTS[i], POWER[i], "Oro"));
            deck.add(new Espada(RANK[i], POINTS[i], POWER[i], "Espada"));
            deck.add(new Copa(RANK[i], POINTS[i], POWER[i], "Copa"));
            deck.add(new Batuco(RANK[i], POINTS[i], POWER[i], "Batuco"));
        }

        return deck;
    }

    public ArrayList<Card> MezclarCartas(ArrayList deck) {
        Collections.shuffle(deck);
        return deck;
    }

}
