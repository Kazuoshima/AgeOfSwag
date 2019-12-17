public class Unity {

	private String name;
	private int pv;
	private int pa;
	private int cost;
	private int loot;
	private int speed;

	public Unity(String name,int pv,int pa,int cost, int loot, int speed){
		this.name = name;
		this.pv = pv;
		this.pa = pa;
		this.cost = cost;
		this.loot = loot;
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public int getPa() {
		return pa;
	}

	public void setPa(int pa) {
		this.pa = pa;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getLoot() {
		return loot;
	}

	public void setLoot(int loot) {
		this.loot = loot;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}