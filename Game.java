
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

        if(shuffled.size() <= 0)
        {
            return;
        }
        while (hand.size() < 3) {
            hand.add(shuffled.remove(0));
        }
    }

    public boolean GanadorDeMano(ArrayList<Card> hand, ArrayList<Card> AIhand, int card1, int card2Ia,Card vida) {
    
        Card UserCard = hand.get(card1-1);
        Card AiCard = AIhand.get(card2Ia-1); // Logica para cual carta gana por ahora

        String VIDAjugador = UserCard.getSuitName();
        String VIDAia = AiCard.getSuitName();
        String VIDA = vida.getSuitName();

        boolean esVidaJugador = VIDAjugador.equalsIgnoreCase(VIDA);
        boolean esVidaIA = VIDAia.equalsIgnoreCase(VIDA);

        if(esVidaJugador && !esVidaIA)
        {
            return true;
        }
        else if (!esVidaJugador && esVidaIA)
        {
            return false;
        }else if(!esVidaJugador && !esVidaIA && !VIDAjugador.equals(VIDAia)){
            return true;
        }



 
        return UserCard.getPower() > AiCard.getPower();

    }

    public void CartaUsada(ArrayList<Card> hand, int selected) {
        int index = selected - 1;

        if (index >= 0 && index < hand.size()) { // metodo de prueba
            Card usedCard = hand.get(index);
            System.out.println("Carta usada: " + usedCard);

            hand.remove(index);
        } else {
            System.out.println("Seleccion invalida");
        }
    }

    public void motrarMano(ArrayList<Card> hand) {
        int count = 1;
        for (Card c : hand) {
            System.out.println(count + ")" + c);
            count++;
        }
    }

    public int numeroRandom() {
        int rand = (int) (Math.random() * 3) + 1;
        return rand;
    }
    public void ShowCardDesing(ArrayList<Card> hadn)
    {
        
    }



}
