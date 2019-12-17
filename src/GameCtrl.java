import java.util.Random;
import java.util.Scanner;

public class GameCtrl {
    static private Player p1;
    static private Settings settings = new Settings();
    static private Player p2;
    static private int actualRound;
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        outline("Yoyoyo bienvenue sur Age Of Swag");
        startGame();
    }

    private static void printOutlined(String s, int totalWidth) {
        for (int i = 0; i < (totalWidth - (s.length() + 4)) / 2; i++) System.out.print(" ");
        System.out.println("| " + s + " |");
    }

    private static void printBorder(int maxLength, int width) {
        StringBuilder o = new StringBuilder(width);
        for (int i = 0; i < (maxLength - (width + 4)) / 2; i++) o.append(" ");
        o.append("+");
        for (int i = 0; i < width + 2; i++) o.append('-');
        o.append('+');
        System.out.println(o);
    }

    private static void outline(String s) {
        int maxLength = 80;
        int length = s.length();
        printBorder(maxLength, length);
        printOutlined(s, maxLength);
        printBorder(maxLength, length);
    }

    static private void fight() {
        int p1Loot = settings.getMinLoot();
        int p2Loot = settings.getMinLoot();
        while (!p1.team.isEmpty() && !p2.team.isEmpty()) {
            Unity p1Unity = p1.team.get(0);
            Unity p2Unity = p2.team.get(0);

            boolean p1UnityToBegin = (p1Unity.getSpeed() == p2Unity.getSpeed()) ? random.nextBoolean() : (p1Unity.getSpeed() > p2Unity.getSpeed());

            while (p1Unity.getPv() > 0 && p2Unity.getPv() > 0) {
                if (p1UnityToBegin) {
                    System.out.println(p1Unity.getName() + " (P1) inflige " + p1Unity.getPa() + " points de dégâts à " + p2Unity.getName() + " (J2) !");
                    p2Unity.setPv(p2Unity.getPv() - p1Unity.getPa());
                    if (p2Unity.getPv() <= 0) {
                        System.out.println(p2Unity.getName() + " a clamse et donne " + p2Unity.getLoot() + "$ au J1");
                        p1Loot += p2Unity.getLoot();
                        p2.removeUnity(p2Unity);
                        break;
                    }
                    System.out.println(p2Unity.getName() + " (P2) inflige " + p2Unity.getPa() + " points de dégâts à " + p1Unity.getName() + " (J1) !");
                    p1Unity.setPv(p1Unity.getPv() - p2Unity.getPa());
                    if (p1Unity.getPv() <= 0) {
                        System.out.println(p1Unity.getName() + " a clamse et donne " + p1Unity.getLoot() + "$ au J2");
                        p2Loot += p1Unity.getLoot();
                        p1.removeUnity(p1Unity);
                        break;
                    }
                } else {
                    System.out.println(p2Unity.getName() + " (P2) inflige " + p2Unity.getPa() + " points de dégâts à " + p1Unity.getName() + " (J1) !");
                    p1Unity.setPv(p1Unity.getPv() - p2Unity.getPa());
                    if (p1Unity.getPv() <= 0) {
                        System.out.println(p1Unity.getName() + " a clamse et donne " + p1Unity.getLoot() + "$ au J2");
                        p2Loot += p1Unity.getLoot();
                        p1.removeUnity(p1Unity);
                        break;
                    }
                    System.out.println(p1Unity.getName() + " (P1) inflige " + p1Unity.getPa() + " points de dégâts à " + p2Unity.getName() + " (J2) !");
                    p2Unity.setPv(p2Unity.getPv() - p1Unity.getPa());
                    if (p2Unity.getPv() <= 0) {
                        System.out.println(p2Unity.getName() + " a clamse et donne " + p2Unity.getLoot() + "$ au J1");
                        p1Loot += p2Unity.getLoot();
                        p2.removeUnity(p2Unity);
                        break;
                    }
                }
            }
        }
        // one team has 0 unities
        int damage = 0;
        boolean p1Won = false;
        while (!p1.team.isEmpty()) {
            p1Won = true;
            Unity u = p1.team.get(0);
            damage += u.getPv() + u.getPa();
            p1.team.remove(u);
        }

        while (!p2.team.isEmpty()) {
            p1Won = false;
            Unity u = p2.team.get(0);
            damage += u.getPv() + u.getPa();
            p2.team.remove(u);
        }

        if (damage == 0) System.out.println("Aucun joueur n'a subit de dégât lors de ce tour");
        else {
            if (p1Won) {
                System.out.println("Les unités restantes de J1 ont infligé " + damage + " points de dégât à J2 !");
                p2.setPv(p2.getPv() - damage);
            } else {
                System.out.println("Les unités restantes de J2 ont infligé " + damage + " points de dégât à J1 !");
                p1.setPv(p1.getPv() - damage);
            }
        }

        endRound(p1Loot, p2Loot);
    }

    static private void startGame() {
//        System.out.println("Voici les paramètres actuels de la partie :");
        outline("Voici les paramètres actuels de la partie :");
        displaySettings();
        System.out.println("Voulez vous modifier ces paramètres ? [y/N]");
        String answer = scanner.nextLine();
        if (answer.equals("y") || answer.equals("Y")) changeSettings();

        p1 = new Player(settings.getPlayerPv(), settings.getPlayerBudget());
        p2 = new Player(settings.getPlayerPv(), settings.getPlayerBudget());
        // game begins
        for (actualRound = 0; actualRound < settings.getNbRounds(); actualRound++) {
            System.out.println("+----- Round " + (actualRound + 1) + " -----+");
            prepareRound();
            fight();
        }
    }

    static private void prepareRound() {
        acheterUnite(p1);
        acheterUnite(p2);
    }

    static private void acheterUnite(Player p) {
        TheRock.infos();
        Archie.infos();
        Dino.infos();
        while (p.team.size() < settings.getMaxUnities()) {
            System.out.println("Votre bugdet : " + p.getBudget() + " Vos PV : " + p.getPv());
            System.out.println("Quel type d'unité souhaitez-vous acheter ? (Tapez Q pour finir les achats) (Tapez V pour vendre des unités)");
            String unite = scanner.nextLine();
            if (unite.equals("TheRock") || unite.equals("Archie") || unite.equals("Dino")) {
                Unity unit;
                switch (unite) {
                    case "TheRock":
                        unit = new TheRock();
                        break;
                    case "Archie":
                        unit = new Archie();
                        break;
                    default:
                        unit = new Dino();
                        break;
                }
                System.out.println("Souhaitez-vous payer avec des $ ou des PV's ? ($ ou PV)");
                String payement = scanner.nextLine();
                if (payement.equals("$")) {
                    if (p.getBudget() >= unit.getCost()) {
                        p.setBudget(p.getBudget() - unit.getCost());
                        p.addUnity(unit);
                    } else {
                        System.out.println("Votre budget est insuffisant pour cette achat !");
                    }
                } else if (payement.equals("PV")) {
                    if (p.getPv() >= unit.getCost()) {
                        p.setPv(p.getPv() - unit.getCost());
                        p.addUnity(unit);
                    } else {
                        System.out.println("Vos PV sont insuffisant pour cette achat !");
                    }
                } else {
                    System.out.println("Erreur lors de l'écriture du moyen de payement !");
                }
            } else if (unite.equals("Q")) {
                System.out.println("Fin des achats !");
                printTeam(p);
                return;
            } else if (unite.equals("V")) {
                vendreUnite(p);
            } else {
                System.out.println("Erreur lors de l'écriture du nom de l'unité !");
            }
        }
        System.out.println("Vous avez atteint le nombre maximum d'unité dans votre équipe ! (" + settings.getMaxUnities() + ")");
        printTeam(p);
    }

    private static void vendreUnite(Player p) {
        printTeam(p);
        System.out.println("Quel unité souhaitez-vous vendre, indiquez le numéro de celle ci ? (Tapez Q pour quitter)");
        String index = scanner.nextLine();
        if (index.equals("Q")) {
            return;
        } else {
            try {
                int i = Integer.parseInt(index);
                Unity unit = p.team.get(i - 1);
                p.setBudget(p.getBudget() + unit.getCost());
                p.removeUnity(unit);
            } catch (NumberFormatException ex) {
                System.out.println("Erreur lors de la sélection de l'unité à vendre !");
            }
        }
    }

    private static void printTeam(Player p) {
        int index = 1;
        for (Unity unit : p.team) {
            System.out.println(index + " : " + unit.toString());
            index++;
        }
    }

    private static void buyBonus(Unity unit) {
        System.out.println(Bonus.bonusInfos());
        System.out.println("Selectionnez un bonus ou tapez 'q' pour quitter.");
        boolean loop = true;
        Bonus bonus;
        while (loop) {
            switch (scanner.nextLine().toLowerCase()) {
                case "vie":
                    loop = false;
                    bonus = Bonus.LifeBonus();
                    addBonusOnUnity(unit, bonus);
                    System.out.println("Bonus de vie appliqué !");
                    break;
                case "force":
                    loop = false;
                    bonus = Bonus.StrengthBonus();
                    addBonusOnUnity(unit, bonus);
                    System.out.println("Bonus de force appliqué !");
                    break;
                case "speed":
                    loop = false;
                    bonus = Bonus.SpeedBonus();
                    addBonusOnUnity(unit, bonus);
                    System.out.println("Bonus de vitesse appliqué !");
                    break;
                case "q":
                    loop = false;
                    break;
                default:
                    loop = true;
                    break;
            }
        }
    }

    private static void addBonusOnUnity(Unity unit, Bonus bonus) {
        unit.setPv((int) (unit.getPv() * bonus.getPvFactor()));
        unit.setPa((int) (unit.getPa() * bonus.getPaFactor()));
        unit.setSpeed((int) (unit.getSpeed() * bonus.getSpeedFactor()));
    }

    private static void endRound(int gainP1, int gainP2) {
        actualRound++;
        if (actualRound > settings.getNbRounds() || (p1.getPv() == 0 || p2.getPv() == 0)) {
            endGame();
            return;
        }
        //Prochain round
        //Addition gain tour aux 2 joueurs
        p1.setBudget(p1.getBudget() + settings.getMinLoot() + gainP1);
        p2.setBudget(p2.getBudget() + settings.getMinLoot() + gainP2);
        prepareRound();
    }

    static private void endGame() {
        if (p1.getPv() == 0) {
            System.out.println("Victoire du joueur 2 !");
        } else if (p2.getPv() == 0) {
            System.out.println("Victoire du joueur 1 !");
        } else {
            int vainqueur = p1.getPv() > p2.getPv() ? 1 : 2;
            System.out.println("Plus de round ! Victoire du joueur " + vainqueur + " !");
        }
    }

    private static void displaySettings() {
        //Display list of all settings current values

		System.out.println("1. Point de vie initiaux des joueurs : " + settings.getPlayerPv());
		System.out.println("2. Budget initial des joueurs : " + settings.getPlayerBudget());
		System.out.println("3. Gain au début de chaque tour : " + settings.getMinLoot());
		System.out.println("4. Nombre d'unités maximal dans une équipe : " + settings.getMaxUnities());
		System.out.println("5. Nombre de tours possibles (au maximum) : " + settings.getNbRounds());

        /*String s[] = new String[5];
        int length = 0;
        s[0] = ("1. Point de vie initiaux des joueurs : " + settings.getPlayerPv());
        s[1] = ("2. Budget initial des joueurs : " + settings.getPlayerBudget());
        s[2] = ("3. Gain au début de chaque tour : " + settings.getMinLoot());
        s[3] = ("4. Nombre d'unités maximal dans une équipe : " + settings.getMaxUnities());
        s[4] = ("5. Nombre de tours possibles (au maximum) : " + settings.getNbRounds());
        for (String value : s) if (value.length() > length) length = value.length();
        printBorder(80, length);
        for (String value : s) printOutlined(value, length);
        printBorder(80, length);*/
    }

    private static void changeSettings() {
        String inputString;
        try {
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
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de l'entrée des données pour les paramètres.");
            System.out.println("Le jeu va continuer avec les paramètre actuels : ");
            displaySettings();
        }
    }

}
