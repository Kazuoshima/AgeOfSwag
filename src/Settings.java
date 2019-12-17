public class Settings {
    private int playerPv = 100;
    private int playerBudget = 30;
    private int minLoot = 15;
    private int maxUnities = 5;
    private int nbRounds = 10;

    public int getPlayerPv() {
        return playerPv;
    }

    public void setPlayerPv(int playerPv) {
        this.playerPv = playerPv;
    }

    public int getPlayerBudget() {
        return playerBudget;
    }

    public void setPlayerBudget(int playerBudget) {
        this.playerBudget = playerBudget;
    }

    public int getMinLoot() {
        return minLoot;
    }

    public void setMinLoot(int minLoot) {
        this.minLoot = minLoot;
    }

    public int getMaxUnities() {
        return maxUnities;
    }

    public void setMaxUnities(int maxUnities) {
        this.maxUnities = maxUnities;
    }

    public int getNbRounds() {
        return nbRounds;
    }

    public void setNbRounds(int nbRounds) {
        this.nbRounds = nbRounds;
    }
}