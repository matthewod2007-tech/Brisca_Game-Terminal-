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

    @Override
    public String toString() {
        return rank + " de " + suitName + " (" + points + " pts)";
    }
}