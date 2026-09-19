import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main {

    ArrayList<Card> manoDelJugador = new ArrayList<>();
    ArrayList<Card> manoDeIA = new ArrayList<>();

    int inicioDeMano = 0;
    boolean continuar = true;

    public static void main(String[] args) {
        main app = new main();
        String logo = """
              ||  ||  
              \\\\()//  
             //(__)\\\\ 
             ||    || 
             """;

        System.out.println(logo);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        clearScreen();
        app.runapp();// runs all the
    }

    public void runapp() {
        boolean ganaste = false;
        int seleccion =0;
        Baraja deck = new Baraja();
        ArrayList<Card> baraja = deck.HacerBaraja();
        ArrayList<Card> Mezcla = deck.MezclarCartas(baraja);
        Scanner scan = new Scanner(System.in);
        Game game = new Game();

        int pointP =0;
        int pointA=0;


        Card vida = Mezcla.get(game.numeroRandom(manoDeIA));
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

            // System.out.println("Manode de IA ->");
            // game.motrarMano(manoDeIA);

            System.out.print("Elige la carta: ");
            String ansString = scan.nextLine();
            if(ansString.isEmpty()){
                System.out.println("Elige algo");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                
                clearScreen();
                continue;}
            try{
            seleccion = Integer.parseInt(ansString);
            }catch(Exception e){}
            int IaUsa = game.numeroRandom(manoDeIA);
            
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
            int playerpointnew = game.CartaUsada(manoDelJugador, seleccion);
            int iaPointnew = game.CartaUsada(manoDeIA, IaUsa);

            int RoundPoints= playerpointnew + iaPointnew;

            if(winnerOfHand){
            pointP = pointP + RoundPoints;

            }else{
            pointA = pointA + RoundPoints;

            }
        
            }catch(Exception e){
            }



            System.out.println();
            System.out.println("Tus puntos:"+pointP);
            System.out.println("Puntos de ia: "+pointA);




           if(manoDelJugador.size() !=1 && manoDeIA.size() != 1){ 
            game.refill(Mezcla, manoDelJugador);
            game.refill(Mezcla, manoDeIA);}

            scan.nextLine();
           
          clearScreen();

           if(Mezcla.isEmpty()&&manoDelJugador.isEmpty()&&manoDeIA.isEmpty())
           {

            if(pointP>pointA){
                System.out.println("YOU WON!!!");
            }else{System.out.println("La ia gano.");}

                System.out.println("Se termino el juego!!!!!");
                String logo = """
              ||  ||  
              \\\\()//  
             //(__)\\\\ 
             ||    || 
             """;

            System.out.println(logo);   
                continuar = false;

           }


        }

        scan.close();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }                                       
//This project will be trasnlated to python.

}
