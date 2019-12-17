import java.util.Scanner;

public class GameCtrl {
    static private Player p1;
    static private Settings settings = new Settings();
    static private Player p2;
    static private int actualRound;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Yoyoyo bienvenue sur Age Of Swag");
        startGame();
    }

    static private void fight() {
        // TODO - implement GameCtrl.fight
        throw new UnsupportedOperationException();
    }

    static private void startGame() {
        System.out.println("Voici les paramètres actuels de la partie :");
        displaySettings();
        System.out.println("Voulez vous modifier ces paramètres ? [y/N]");
        if(scanner.nextLine().toLowerCase().equals("y")){
            changeSettings();
        }
        prepareRound();
    }

    static private void prepareRound() {
        // TODO - implement GameCtrl.prepareRound
        throw new UnsupportedOperationException();
    }

    private static void buyBonus(Unity unit){
        System.out.println(Bonus.bonusInfos());
        System.out.println("Selectionnez un bonus ou tapez 'q' pour quitter.");
        boolean loop = true;
        Bonus bonus;
        while (loop){
            switch (scanner.nextLine().toLowerCase()){
                case "vie" :
                    loop = false;
                    bonus = Bonus.LifeBonus();
                    addBonusOnUnity(unit,bonus);
                    System.out.println("Bonus de vie appliqué !");
                    break;
                case "force" :
                    loop = false;
                    bonus = Bonus.StrengthBonus();
                    addBonusOnUnity(unit,bonus);
                    System.out.println("Bonus de force appliqué !");
                    break;
                case "speed" :
                    loop = false;
                    bonus = Bonus.SpeedBonus();
                    addBonusOnUnity(unit,bonus);
                    System.out.println("Bonus de vitesse appliqué !");
                    break;
                case "q" :
                    loop = false;
                    break;
                default:
                    loop = true;
                    break;
            }
        }
    }
    private static void addBonusOnUnity(Unity unit,Bonus bonus){
        unit.setPv((int)(unit.getPv()*bonus.getPvFactor()));
        unit.setPa((int)(unit.getPa()*bonus.getPaFactor()));
        unit.setSpeed((int)(unit.getSpeed()*bonus.getSpeedFactor()));
    }

    private static void endRound(int gainP1, int gainP2){
        actualRound++;
        if(actualRound > settings.getNbRounds() || (p1.getPv()==0 || p2.getPv()==0)){
            endGame();
            return;
        }
        //Prochain round
        //Addition gain tour aux 2 joueurs
        p1.setBudget(p1.getBudget()+settings.getMinLoot()+gainP1);
        p2.setBudget(p2.getBudget()+settings.getMinLoot()+gainP2);
        prepareRound();
    }

    static private void endGame() {
        if(p1.getPv()==0){
            System.out.println("Victoire du joueur 2 !");
        }else if(p2.getPv()==0){
            System.out.println("Victoire du joueur 1 !");
        }else{
            int vainqueur = p1.getPv() > p2.getPv() ? 1 : 2;
            System.out.println("Plus de round ! Victoire du joueur "+vainqueur+" !");
        }
    }

	private static void displaySettings() {
		//Display list of all settings current values

		System.out.println("1. Point de vie initiaux des joueurs : " + settings.getPlayerPv());
		System.out.println("2. Budget initial des joueurs : " + settings.getPlayerBudget());
		System.out.println("3. Gain au début de chaque tour : " + settings.getMinLoot());
		System.out.println("4. Nombre d'unités maximal dans une équipe : " + settings.getMaxUnities());
		System.out.println("5. Nombre de tours possibles (au maximum) : " + settings.getNbRounds());

	}

	private static void changeSettings() {
        String inputString;
        try{
            do {
                int newVal;
                System.out.println("Choissisez un des paramètres (1 à 5 / terminer avec q) :");
                inputString = scanner.nextLine();
                switch (inputString) {
                    case "1":
                        System.out.println("Nouvelle valeur (PV initiaux) : ");
                        inputString = scanner.nextLine();
                        newVal = Integer.parseInt(inputString);
                        if (newVal > 0) {
                            settings.setPlayerPv(newVal);
                        } else {
                            System.out.println("Valeur erronée.");
                        }
                        displaySettings();
                        break;
                    case "2":
                        System.out.println("Nouvelle valeur (Budget initial) : ");
                        inputString = scanner.nextLine();
                        newVal = Integer.parseInt(inputString);
                        if (newVal > 0) {
                            settings.setPlayerBudget(newVal);
                        } else {
                            System.out.println("Valeur erronée.");
                        }
                        displaySettings();
                        break;
                    case "3":
                        System.out.println("Nouvelle valeur (Gain par tour) : ");
                        inputString = scanner.nextLine();
                        newVal = Integer.parseInt(inputString);
                        if (newVal > 0) {
                            settings.setMinLoot(newVal);
                        } else {
                            System.out.println("Valeur erronée.");
                        }
                        displaySettings();
                        break;
                    case "4":
                        System.out.println("Nouvelle valeur (Unités max.) : ");
                        inputString = scanner.nextLine();
                        newVal = Integer.parseInt(inputString);
                        if (newVal > 0) {
                            settings.setMaxUnities(newVal);
                        } else {
                            System.out.println("Valeur erronée.");
                        }
                        displaySettings();
                        break;
                    case "5":
                        System.out.println("Nouvelle valeur (Nombre de tours max.) : ");
                        inputString = scanner.nextLine();
                        newVal = Integer.parseInt(inputString);
                        if (newVal > 0) {
                            settings.setNbRounds(newVal);
                        } else {
                            System.out.println("Valeur erronée.");
                        }
                        displaySettings();
                        break;
                    case "q":
                        break;
                    default:
                        System.out.println("Input non reconnu.");
                        displaySettings();
                        break;
                }
            } while (!inputString.equals("q"));
        }catch(NumberFormatException e){
            System.out.println("Erreur lors de l'entrée des données pour les paramètres.");
            System.out.println("Le jeu va continuer avec les paramètre actuels : ");
            displaySettings();
        }
	}

}
