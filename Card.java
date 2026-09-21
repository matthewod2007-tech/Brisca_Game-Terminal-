public abstract class Card {
    private final int rank;
    private final int points;
    private final int power;
    private final String suitName;

    public Card(int rank, int points, int power, String suitName) {
        this.rank = rank;
        this.points = points;
        this.power = power;
        this.suitName = suitName;
    }

    public int getRank() {
        return rank;
    }

    public int getPoints() {
        return points;
    }

    public int getPower() {
        return power;
    }

    public String getSuitName() {
        return suitName;
    }

    public String toAscii(){

        String symbol = switch(suitName.toLowerCase()) {
            case "oro" -> "o";
            case "espada" -> "U";
            case "batuco"->"†";
            case "copa"-> "I";
            default -> "?";            
        };

        String r = String.format("%-2d", rank);


        return "+-----+\n" +
               "|" + r + "   |\n" +
               "|  " + symbol + "  |\n" +
               "|   " + r.trim() +  " |\n" +
               "+-----+";
    }
    
    @Override
    public String toString() {
        return rank + " de " + suitName + " (" + points + " pts)";
    }
}