public class Dino extends Unity {

	public Dino() {
		super("Dino",12,8,20,10,1);
	}

	@Override
	public String toString() {
		return "Dino    {PV : " + this.getPv() + ",  PA : " + this.getPa() + ", COÛT : " + this.getCost() + ",  BUTIN : " + this.getLoot() + ",  VITESSE : " + this.getSpeed() + "}";
	}

	public static void infos() {
		System.out.println("Dino    {PV : 12, PA : 8, COÛT : 20, BUTIN : 10, VITESSE : 1}");
	}

}