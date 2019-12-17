import java.util.ArrayList;

public class Player {

	ArrayList<Unity> team;
	private int pv;
	private int budget;

	public Player(int pv, int budget){
		this.pv = pv;
		this.budget = budget;
		team = new ArrayList<>();
	}

	/**
	 * 
	 * @param unity
	 */
	public ArrayList<Unity> addUnity(Unity unity) {
		team.add(unity);
		return team;
	}

	/**
	 * 
	 * @param unity
	 */
	public ArrayList<Unity> removeUnity(Unity unity) {
		if (!team.isEmpty()){
			team.remove(unity);
		}
		return team;
	}

}