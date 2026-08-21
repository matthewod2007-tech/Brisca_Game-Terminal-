import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {

    ArrayList<Card> manoDelJugador = new ArrayList<>();
    ArrayList<Card> manoDeIA = new ArrayList<>();

    int inicioDeMano = 0;
    boolean continuar = true;

    public static void main(String[] args) {
        PlayGame app = new PlayGame();
        app.runapp();
    }

    public void runapp() {
        boolean ganaste = false;
        int seleccion;
        Baraja deck = new Baraja();
        ArrayList<Card> baraja = deck.HacerBaraja();
        ArrayList<Card> Mezcla = deck.MezclarCartas(baraja);
        Scanner scan = new Scanner(System.in);
        Game game = new Game();

        Card vida = Mezcla.get(game.numeroRandom());
        manoDelJugador = game.DarCartas(Mezcla, inicioDeMano);
        manoDeIA = game.DarCartas(Mezcla, inicioDeMano);

        while (continuar) {
            System.out.println("-".repeat(10) + "Brisca!" + "-".repeat(10));
            System.out.print("VIDA ->");
            System.out.print(vida.getSuitName());
            System.out.println();
            System.out.println("Tu mano ->");
            game.motrarMano(manoDelJugador);
            System.out.println();

            System.out.println("Manode de IA ->");
            game.motrarMano(manoDeIA);

            System.out.print("Elige la carta: ");
            String ansString = scan.nextLine();
            seleccion = Integer.parseInt(ansString);
        
            int IaUsa = game.numeroRandom();
            
            System.out.println();
            System.out.println();
            boolean winnerOfHand = game.GanadorDeMano(manoDelJugador, manoDeIA, seleccion, IaUsa, vida);
            if (winnerOfHand) {
                System.out.println("Ganaste la mano");
                ganaste = true;

            } else {
                System.out.println("Perdiste la mano");
                ganaste = false;
            }
           
           try{
            game.CartaUsada(manoDelJugador, seleccion);
            game.CartaUsada(manoDeIA, IaUsa);}catch(Exception e){
            }

           if(manoDelJugador.size() !=1 && manoDeIA.size() != 1){ 
            game.refill(Mezcla, manoDelJugador);
            game.refill(Mezcla, manoDeIA);}

            scan.nextLine();
           
            clearScreen();

           if(Mezcla.isEmpty()&&manoDelJugador.isEmpty()&&manoDeIA.isEmpty())
           {
                System.out.println("Se termino el juego");
                continuar = false;

           }


        }

        scan.close();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
