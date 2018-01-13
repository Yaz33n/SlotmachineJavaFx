package app;

/**
 * @Entity : Player class to save the state of the player.
 */
public class Player implements java.io.Serializable {

    private String playerName; // Player Name
    private int totalBetted;
    // Statistics of the player
    private int totalWins;
    private int totalLoses;
    private int totalPartialWins;
    private int totalSpins;
    private int creditsWon;
    private int creditsLost;

    public Player(String playerName) {

        if (playerName.equals("")) {
            // if the player not entered the player name at the beginning of the game,
            // the program will automatically get the username from the System.
            this.playerName = System.getProperty("user.name");
        } else {
            // if the player has specified the name at the beginning of the game,
            // then use the user specified name.
            this.playerName = playerName;
        }
    }

    /**
     * @return average as a Integer
     */
    public static int calculateAverage(Player player) {
        // This calculates the average of a given player
        return (int) (((double)
                (player.getTotalWins() + player.getTotalPartialWins()) /
                (double) player.getTotalSpins()) * 100d);
    }

    /**
     * @param player object.
     * @return the coins that he/she has netted per game.
     */
    public static int coinsNettedPerGame(Player player) {
        return ((player.getCreditsWon() - player.getCreditsLost()) / player.getTotalSpins());
    }

    /**
     * @param avg of the player.
     * @return the feedback for the calculated average.
     */
    public static String getFeedback(int avg) {

        if (avg >= 75) {
            return "You've been winning most of the time. Excellent Job!";
        } else if (avg >= 50) {
            return "Your performance is positive. Great Job!";
        } else if (avg >= 25) {
            return "Your performance is average. You Did a Good Job!";
        } else {
            return "You've been losing all time. Let's try Again! :(";
        }
    }

    @Override
    public String toString() {
        String args = "%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n%-20s%s%n";
        return String.format(args,
                "Player Name", ":  " + playerName,
                "Total Wins", ":  " + totalWins,
                "Total Loses", ":  " + totalLoses,
                "Partial wins", ":  " + totalPartialWins,
                "Total Spins", ":  " + totalSpins,
                "Credits Lost", ":  $" + creditsLost,
                "Credits Won", ":  $" + creditsWon,
                "Credits Netted", ":  $" + Player.coinsNettedPerGame(this));
    }

    /*------------------------------------ Getters and Setters ------------------------------------ */

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLoses() {
        return totalLoses;
    }

    public int getTotalPartialWins() {
        return totalPartialWins;
    }

    public int getTotalSpins() {
        return totalSpins;
    }

    public int getCreditsWon() {
        return creditsWon;
    }

    public int getCreditsLost() {
        return creditsLost;
    }

    public int getTotalBetted() {
        return totalBetted;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public void setTotalLoses(int totalLoses) {
        this.totalLoses = totalLoses;
    }

    public void setTotalPartialWins(int totalPartialWins) {
        this.totalPartialWins = totalPartialWins;
    }

    public void setTotalSpins(int totalSpins) {
        this.totalSpins = totalSpins;
    }

    public void setCreditsWon(int creditsWon) {
        this.creditsWon = creditsWon;
    }

    public void setCreditsLost(int creditsLost) {
        this.creditsLost = creditsLost;
    }

    public void setTotalBetted(int totalBetted) {
        this.totalBetted = totalBetted;
    }
}
