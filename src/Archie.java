public class Archie extends Unity {

	public Archie() {
		super("Archie",8,6,10,6,2);
	}

	@Override
	public String toString() {
		return "Archie  {PV : " + this.getPv() + ",  PA : " + this.getPa() + ", COÛT : " + this.getCost() + ",  BUTIN : " + this.getLoot() + ",  VITESSE : " + this.getSpeed() + "}";
	}

	public static void infos() {
		System.out.println("Archie  {PV : 8,  PA : 6, COÛT : 10, BUTIN : 6,  VITESSE : 2}");
	}
}