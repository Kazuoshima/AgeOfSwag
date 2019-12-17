public class Bonus {

    private double pvFactor;
    private double paFactor;
    private double speedFactor;
    private int cost;

    private Bonus(double pvFactor, double paFactor, double speedFactor, int cost) {
        this.pvFactor = pvFactor;
        this.paFactor = paFactor;
        this.speedFactor = speedFactor;
        this.cost = cost;
    }

    public Bonus LifeBonus() {
		return new Bonus(1.3, 1, 1, 7);
    }

    public Bonus StrengthBonus() {
		return new Bonus(1, 1.3, 1, 7);
    }

    public Bonus SpeedBonus() {
		return new Bonus(1, 1, 3, 7);
    }

    public double getPvFactor() {
        return pvFactor;
    }

    public void setPvFactor(double pvFactor) {
        this.pvFactor = pvFactor;
    }

    public double getPaFactor() {
        return paFactor;
    }

    public void setPaFactor(double paFactor) {
        this.paFactor = paFactor;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
