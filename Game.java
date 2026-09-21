
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

    public int CartaUsada(ArrayList<Card> hand, int selected) {
        int index = selected - 1;

        if (index >= 0 && index < hand.size()) { // metodo de prueba
            Card usedCard = hand.get(index);
            System.out.println("Carta usada: " + usedCard);
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

        if(hand.size() <= 1){
            return 1;
        }else if(hand.size() == 2){
            int rand = (int) (Math.random()*2) +1;
            return rand;
        }

        int rand = (int) (Math.random() * 3) + 1;
        return rand;
    }


    public void showCard(ArrayList<Card> hand){
        if(hand.size() == 1){
            System.out.println(hand.get(0).toAscii());
        }

        for(int i=0; i < hand.size(); i++){
            System.out.println("("+(i+1)+")");
            System.out.println(hand.get(i).toAscii());
        }
    }

}