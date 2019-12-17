import javafx.scene.shape.Arc;

import java.util.Scanner;

public class GameCtrl {
    static private Player p1;
    static private Settings settings = new Settings();
    static private Player p2;
    static private int actualRound;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Yoyoyo bienvenue sur Age Of Swag");
        prepareRound();
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
    }

    static private void prepareRound() {
        p1 = new Player(100, 30);
        acheterUnite(p1);
        
    }

    static private void acheterUnite(Player p){
        TheRock.infos(); Archie.infos(); Dino.infos();
        while(p.team.size() < settings.getMaxUnities()){
            System.out.println("Votre bugdet : " + p.getBudget() + " Vos PV : " + p.getPv());
            System.out.println("Quel type d'unité souhaitez-vous acheter ? (Tapez Q pour finir les achats) (Tapez V pour vendre des unités)");
            String unite = scanner.nextLine();
            if(unite.equals("TheRock") || unite.equals("Archie") || unite.equals("Dino")){
                Unity unit;
                switch (unite){
                    case "TheRock": unit = new TheRock(); break;
                    case "Archie": unit = new Archie(); break;
                    default: unit = new Dino(); break;
                }
                System.out.println("Souhaitez-vous payer avec des $ ou des PV's ? ($ ou PV)");
                String payement = scanner.nextLine();
                if(payement.equals("$")){
                    if(p.getBudget() >= unit.getCost()){
                        p.setBudget(p.getBudget() - unit.getCost());
                        p.addUnity(unit);
                    }else{
                        System.out.println("Votre budget est insuffisant pour cette achat !");
                    }
                }else if(payement.equals("PV")){
                    if(p.getPv() >= unit.getCost()){
                        p.setPv(p.getPv() - unit.getCost());
                        p.addUnity(unit);
                    }else{
                        System.out.println("Vos PV sont insuffisant pour cette achat !");
                    }
                }else{
                    System.out.println("Erreur lors de l'écriture du moyen de payement !");
                }
            }else if (unite.equals("Q")){
                System.out.println("Fin des achats !");
                printTeam(p);
                return;
            }else if (unite.equals("V")){
                vendreUnite(p);
            }else{
                System.out.println("Erreur lors de l'écriture du nom de l'unité !");
            }
        }
        System.out.println("Vous avez atteint le nombre maximum d'unité dans votre équipe ! (" + settings.getMaxUnities() + ")");
        printTeam(p);
    }

    private static void vendreUnite(Player p){
        printTeam(p);
        System.out.println("Quel unité souhaitez-vous vendre, indiquez le numéro de celle ci ? (Tapez Q pour quitter)");
        String index = scanner.nextLine();
        if(index.equals("Q")){
            return;
        }else{
            try{
                int i = Integer.parseInt(index);
                Unity unit = p.team.get(i - 1);
                p.setBudget(p.getBudget() + unit.getCost());
                p.removeUnity(unit);
            }catch (NumberFormatException ex){
                System.out.println("Erreur lors de la sélection de l'unité à vendre !");
            }
        }
    }

    private static void printTeam(Player p){
        int index = 1;
        for (Unity unit : p.team){
            System.out.println(index + " : " + unit.toString());
            index++;
        }
    }

    static private void endGame() {
        // TODO - implement GameCtrl.endGame
        throw new UnsupportedOperationException();
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
                int newVal = 0;
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
                    case "d":
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
