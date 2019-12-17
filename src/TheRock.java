public class TheRock extends Unity {

	public TheRock() {
		super("The Rock",6,4,5,3,3);
	}

	@Override
	public String toString() {
		return "TheRock {PV : " + this.getPv() + ",  PA : " + this.getPa() + ", COÛT : " + this.getCost() + ",  BUTIN : " + this.getLoot() + ",  VITESSE : " + this.getSpeed() + "}";
	}

	public static void infos() {
		System.out.println("TheRock {PV : 6,  PA : 4, COÛT : 5,  BUTIN : 3,  VITESSE : 3}");
	}
}