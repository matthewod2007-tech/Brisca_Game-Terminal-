
import java.util.ArrayList;

public class Game {

    private final int cardlimit = 3;

    public ArrayList<Card> DarCartas(ArrayList<Card> shuffled, int playerCardsCount) {
        ArrayList<Card> card = new ArrayList<>();
        for (int i = 0; i < cardlimit; i++) {
            card.add(shuffled.remove(0));
        }
        return card;
    }

    public void refill(ArrayList<Card> shuffled, ArrayList<Card> hand) {

        if (shuffled.size() <= 0) {
            return;
        }
        while (hand.size() < 3) {
            hand.add(shuffled.remove(0));
        }
    }

    public boolean GanadorDeMano(ArrayList<Card> hand, ArrayList<Card> AIhand, int card1, int card2Ia, Card vida) {

        Card UserCard = hand.get(card1 - 1);
        Card AiCard = AIhand.get(card2Ia - 1); // Logica para cual carta gana por ahora

        String VIDAjugador = UserCard.getSuitName();
        String VIDAia = AiCard.getSuitName();
        String VIDA = vida.getSuitName();

        boolean esVidaJugador = VIDAjugador.equalsIgnoreCase(VIDA);
        boolean esVidaIA = VIDAia.equalsIgnoreCase(VIDA);

        if (esVidaJugador && !esVidaIA) {
            return true;
        } else if (!esVidaJugador && esVidaIA) {
            return false;
        } else if (!esVidaJugador && !esVidaIA && !VIDAjugador.equals(VIDAia)) {
            return true;
        }

        return UserCard.getPower() > AiCard.getPower();

    }

    public int CartaUsada(ArrayList<Card> hand, int selected) {
        int index = selected - 1;

        if (index >= 0 && index < hand.size()) { // metodo de prueba
            Card usedCard = hand.get(index);
            System.out.println("Used cards ->");
            System.out.println(usedCard.toAscii());
            System.out.println(usedCard.toString());
            int point = usedCard.getPoints();

            hand.remove(index);
            return point;

        } else {
            System.out.println("Seleccion invalida");
            return 0;
        }
    }

    public void motrarMano(ArrayList<Card> hand) {
        int count = 1;
        for (Card c : hand) {
            System.out.println(count + ")" + c);
            count++;
        }
    }

    public int numeroRandom(ArrayList<Card> hand) {

        if (hand.size() <= 1) {
            return 1;
        } else if (hand.size() == 2) {
            int rand = (int) (Math.random() * 2) + 1;
            return rand;
        }

        int rand = (int) (Math.random() * 3) + 1;
        return rand;
    }

    public void showCard(ArrayList<Card> hand) {
        if (hand.size() == 1) {
            System.out.println(hand.get(0).toAscii());
            return;
        }

        for (int i = 0; i < hand.size(); i++) {
            System.out.println("(" + (i + 1) + ")");
            System.out.println(hand.get(i).toAscii());
        }
    }

    // metodos de prueba ->

    public boolean ganaCarta(Card aiCard, Card playeCard, Card vida) {
        String vidaA = vida.getSuitName();
        boolean vidaDeIa = aiCard.getSuitName().equalsIgnoreCase(vidaA);
        boolean vidaDelJuagador = playeCard.getSuitName().equalsIgnoreCase(vidaA);

        if (vidaDeIa && !vidaDelJuagador)
            return true;
        if (!vidaDeIa && vidaDelJuagador)
            return false;

        if (aiCard.getSuitName().equalsIgnoreCase(playeCard.getSuitName())) {

            return aiCard.getPower() > playeCard.getPower();
        }
        return false;
    }

    public int decidirCartaIA(Card playerCard, ArrayList<Card> aiHand, Card vida, int playerPoints, int aiPoints) {
        if (aiHand.size() <= 1) {
            return 1;
        }

        String trumpSuit = vida.getSuitName();
        int playerPointsInTrick = playerCard.getPoints();

        ArrayList<Integer> winningIndices = new ArrayList<>();
        for (int i = 0; i < aiHand.size(); i++) {
            if (ganaCarta(aiHand.get(i), playerCard, vida)) {
                winningIndices.add(i);
            }
        }

        for (int idx : winningIndices) {
            Card c = aiHand.get(idx);
            if (aiPoints + playerPointsInTrick + c.getPoints() >= 61) {
                return idx + 1;
            }
        }

        if (!winningIndices.isEmpty()) {
            if (playerPointsInTrick >= 10) {
                return getLowestWinningCard(winningIndices, aiHand, trumpSuit) + 1;
            }
            if (playerPointsInTrick >= 2) {
                int sameSuitWin = getWinningSameSuit(winningIndices, aiHand, playerCard);
                if (sameSuitWin != -1)
                    return sameSuitWin + 1;

                int lowTrumpWin = getLowestTrumpWin(winningIndices, aiHand, trumpSuit);
                if (lowTrumpWin != -1 && aiHand.get(lowTrumpWin).getPoints() == 0) {
                    return lowTrumpWin + 1;
                }
            }
            if (playerPointsInTrick == 0) {
                for (int idx : winningIndices) {
                    Card c = aiHand.get(idx);
                    if (c.getSuitName().equalsIgnoreCase(playerCard.getSuitName()) && c.getPoints() == 0) {
                        return idx + 1;
                    }
                }
            }
        }

        return getBestDiscard(aiHand, trumpSuit) + 1;
    }

    private int getLowestWinningCard(ArrayList<Integer> winningIndices, ArrayList<Card> hand, String trumpSuit) {
        int bestIdx = winningIndices.get(0);
        int minCost = Integer.MAX_VALUE;
        for (int idx : winningIndices) {
            Card c = hand.get(idx);
            boolean isTrump = c.getSuitName().equalsIgnoreCase(trumpSuit);
            int cost = (isTrump ? 1000 : 0) + (c.getPoints() * 100) + c.getPower();
            if (cost < minCost) {
                minCost = cost;
                bestIdx = idx;
            }
        }
        return bestIdx;
    }

    private int getWinningSameSuit(ArrayList<Integer> winningIndices, ArrayList<Card> hand, Card playerCard) {
        int bestIdx = -1;
        int minPower = Integer.MAX_VALUE;
        for (int idx : winningIndices) {
            Card c = hand.get(idx);
            if (c.getSuitName().equalsIgnoreCase(playerCard.getSuitName()) && c.getPower() < minPower) {
                minPower = c.getPower();
                bestIdx = idx;
            }
        }
        return bestIdx;
    }

    private int getBestDiscard(ArrayList<Card> hand, String trumpSuit) {
        int bestIdx = 0;
        int lowestValue = Integer.MAX_VALUE;
        for (int i = 0; i < hand.size(); i++) {
            Card c = hand.get(i);
            boolean isTrump = c.getSuitName().equalsIgnoreCase(trumpSuit);
            int val = (isTrump ? 500 : 0) + (c.getPoints() * 20) + c.getPower();
            if (val < lowestValue) {
                lowestValue = val;
                bestIdx = i;
            }
        }
        return bestIdx;
    }

    private int getLowestTrumpWin(ArrayList<Integer> winningIndices, ArrayList<Card> hand, String trumpSuit) {
        int bestIdx = -1;
        int minCost = Integer.MAX_VALUE;
        for (int idx : winningIndices) {
            Card c = hand.get(idx);
            if (c.getSuitName().equalsIgnoreCase(trumpSuit)) {
                int cost = (c.getPoints() * 10) + c.getPower();
                if (cost < minCost) {
                    minCost = cost;
                    bestIdx = idx;
                }
            }
        }
        return bestIdx;
    }

}