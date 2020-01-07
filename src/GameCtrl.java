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

    private static void printOutlined(String s, int totalWidth, int frameWidth) {
        for (int i = 0; i < (totalWidth - (frameWidth + 4)) / 2; i++) System.out.print(" ");
        StringBuilder sB = new StringBuilder();
        sB.append("| ");
        for (int i = 0; i < (frameWidth - s.length()) / 2; i++) sB.append(" ");
        sB.append(s);
        for (int i = 0; i < (frameWidth - s.length()) / 2; i++) sB.append(" ");
        sB.append(" |");
        System.out.println(sB);
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
        printOutlined(s, maxLength, s.length());
        printBorder(maxLength, length);
    }

    static private void fight() {
        outline("! FIGHT !");
        printTeam(p1);
        outline("VS");
        printTeam(p2);

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
                        printTeam(p1);
                        printTeam(p2);
                        break;
                    }
                    System.out.println(p2Unity.getName() + " (P2) inflige " + p2Unity.getPa() + " points de dégâts à " + p1Unity.getName() + " (J1) !");
                    p1Unity.setPv(p1Unity.getPv() - p2Unity.getPa());
                    if (p1Unity.getPv() <= 0) {
                        System.out.println(p1Unity.getName() + " a clamse et donne " + p1Unity.getLoot() + "$ au J2");
                        p2Loot += p1Unity.getLoot();
                        p1.removeUnity(p1Unity);
                        printTeam(p1);
                        printTeam(p2);
                        break;
                    }
                } else {
                    System.out.println(p2Unity.getName() + " (P2) inflige " + p2Unity.getPa() + " points de dégâts à " + p1Unity.getName() + " (J1) !");
                    p1Unity.setPv(p1Unity.getPv() - p2Unity.getPa());
                    if (p1Unity.getPv() <= 0) {
                        System.out.println(p1Unity.getName() + " a clamse et donne " + p1Unity.getLoot() + "$ au J2");
                        p2Loot += p1Unity.getLoot();
                        p1.removeUnity(p1Unity);
                        printTeam(p1);
                        printTeam(p2);
                        break;
                    }
                    System.out.println(p1Unity.getName() + " (P1) inflige " + p1Unity.getPa() + " points de dégâts à " + p2Unity.getName() + " (J2) !");
                    p2Unity.setPv(p2Unity.getPv() - p1Unity.getPa());
                    if (p2Unity.getPv() <= 0) {
                        System.out.println(p2Unity.getName() + " a clamse et donne " + p2Unity.getLoot() + "$ au J1");
                        p1Loot += p2Unity.getLoot();
                        p2.removeUnity(p2Unity);
                        printTeam(p1);
                        printTeam(p2);
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
        outline("Round " + (actualRound + 1) + "/" + settings.getNbRounds());
        prepareRound();
    }

    static private void prepareRound() {
        acheterUnite(p1);
        acheterUnite(p2);
        fight();
    }

    static private void acheterUnite(Player p) {
        while (p.team.size() < settings.getMaxUnities()) {
            outline("Budget J" + (p == p1 ? 1 : 2) + " : " + p.getBudget() + "$ - " + p.getPv() + " PV");

            String s[] = new String[3];
            int length = 0;
            s[0] = TheRock.infos();
            s[1] = Archie.infos();
            s[2] = Dino.infos();
            for (String value : s) if (value.length() > length) length = value.length();
            printBorder(80, length);
            for (String value : s) printOutlined(value, 80, length);
            printBorder(80, length);

            System.out.println("\nQuel type d'unité souhaitez-vous acheter ? (Tapez Q pour terminer les achats, V pour vendre des unités, S pour afficher l'équipe)");
            String unite = scanner.nextLine().toLowerCase();
            if (unite.equals("therock") || unite.equals("archie") || unite.equals("dino")) {
                Unity unit;
                switch (unite) {
                    case "therock":
                        unit = new TheRock();
                        break;
                    case "archie":
                        unit = new Archie();
                        break;
                    default:
                        unit = new Dino();
                        break;
                }
                System.out.println("Souhaitez-vous payer avec des $ ou des PV's ? ($ ou PV)");
                String payement = scanner.nextLine().toLowerCase();
                if (payement.equals("$")) {
                    if (p.getBudget() >= unit.getCost()) {
                        p.setBudget(p.getBudget() - unit.getCost());
                        buyBonus(unit, p);
                        p.addUnity(unit);
                    } else {
                        System.out.println("Votre budget est insuffisant pour cet achat !");
                    }
                } else if (payement.equals("pv")) {
                    if (p.getPv() >= unit.getCost()) {
                        p.setPv(p.getPv() - unit.getCost());
                        buyBonus(unit, p);
                        p.addUnity(unit);
                    } else {
                        System.out.println("Vos PV sont insuffisant pour cet achat !");
                    }
                } else {
                    System.out.println("Erreur lors de l'écriture du moyen de payement !");
                }
            } else if (unite.equals("q")) {
                System.out.println("Fin des achats !");
                printTeam(p);
                printBorder(80, 80);
                return;
            } else if (unite.equals("v")) {
                vendreUnite(p);
            } else if (unite.equals("s")) {
                printTeam(p);
            } else {
                System.out.println("Erreur lors de l'écriture du nom de l'unité !");
            }
        }
        System.out.println("Vous avez atteint le nombre maximum d'unité dans votre équipe ! (" + settings.getMaxUnities() + ")");
        printTeam(p);
        printBorder(80, 80);
    }

    private static void vendreUnite(Player p) {
        printTeam(p);
        System.out.println("Quel unité souhaitez-vous vendre, indiquez le numéro de celle ci ? (Tapez Q pour quitter)");
        String index = scanner.nextLine();
        if (!index.equals("Q")) {
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
        System.out.println("Team " + (p == p1 ? 1 : 2) + " :");
        String[] unities = new String[p.team.size()];
        int width = 0;
        int index = 1;
        for (Unity unit : p.team) {
//            System.out.println(index + " : " + unit.toString());
            unities[index - 1] = index + " : " + unit.toString();
            width = unities[index - 1].length() > width ? unities[index - 1].length() : width;
            index++;
        }
        printBorder(80, width);
        for (String s : unities) printOutlined(s, 80, width);
        printBorder(80, width);
    }

    private static void buyBonus(Unity unit, Player player) {
        String s[] = Bonus.bonusInfos();
        int length = 0;
        for (String value : s) if (value.length() > length) length = value.length();
        printBorder(80, length);
        for (String value : s) printOutlined(value, 80, length);
        printBorder(80, length);

        System.out.println("Selectionnez un bonus ou tapez 'q' pour quitter.");
        boolean loop = true;
        Bonus bonus = null;
        while (loop) {
            switch (scanner.nextLine().toLowerCase()) {
                case "vie":
                    loop = false;
                    bonus = Bonus.LifeBonus();
                    System.out.println("Bonus de vie demandé...");
                    break;
                case "force":
                    loop = false;
                    bonus = Bonus.StrengthBonus();
                    System.out.println("Bonus de force demandé...");
                    break;
                case "speed":
                    loop = false;
                    bonus = Bonus.SpeedBonus();
                    System.out.println("Bonus de vitesse demandé...");
                    break;
                case "q":
                    loop = false;
                    break;
                default:
                    loop = true;
                    break;
            }
        }
        if (bonus != null) {
            if (player.getBudget() < bonus.getCost()) {
                System.out.println("Budget insuffisant pour acheter ce bonus");
                return;
            }
            player.setBudget(player.getBudget() - bonus.getCost());
            addBonusOnUnity(unit, bonus);
            System.out.println("Bonus appliqué !");
        }
    }

    private static void addBonusOnUnity(Unity unit, Bonus bonus) {
        unit.setPv((int) Math.round(unit.getPv() * bonus.getPvFactor()));
        unit.setPa((int) Math.round((unit.getPa()) * bonus.getPaFactor()));
        unit.setSpeed((int) Math.round((unit.getSpeed() * bonus.getSpeedFactor())));
    }

    private static void endRound(int totalLootP1, int totalLootP2) {
        actualRound++;
        if (actualRound >= settings.getNbRounds() || (p1.getPv() <= 0 || p2.getPv() <= 0)) {
            endGame();
            return;
        }
        //Prochain round
        //Addition gain tour aux 2 joueurs
        p1.setBudget(p1.getBudget() + totalLootP1);
        p2.setBudget(p2.getBudget() + totalLootP2);

        printBorder(80, 80);
        outline("Round " + (actualRound + 1) + "/" + settings.getNbRounds());

//        outline("Score actuel:");
        System.out.println("Score actuel:");
        String s1 = "J1 : PV = " + p1.getPv() + " | $ = " + p1.getBudget() + " (+" + totalLootP1 + ")";
        String s2 = "J2 : PV = " + p2.getPv() + " | $ = " + p2.getBudget() + " (+" + totalLootP2 + ")";
        int w = Math.max(s1.length(), s2.length());
        printBorder(80, w);
        printOutlined(s1, 80, w);
        printOutlined(s2, 80, w);
        printBorder(80, w);

        printBorder(80, 80);
        prepareRound();
    }

    static private void endGame() {
        if (p1.getPv() <= 0) {
            System.out.println("Victoire du joueur 2 !");
        } else if (p2.getPv() <= 0) {
            System.out.println("Victoire du joueur 1 !");
        } else if (p1.getPv() == p2.getPv()) {
            System.out.println("Plus de round ! Egalité !");
        } else {
            int vainqueur = p1.getPv() > p2.getPv() ? 1 : 2;
            System.out.println("Plus de round ! Victoire du joueur " + vainqueur + " !");
        }
        String s1 = "J1 : PV = " + p1.getPv() + " - $ = " + p1.getBudget();
        String s2 = "J2 : PV = " + p2.getPv() + " - $ = " + p2.getBudget();
        int w = Math.max(s1.length(), s2.length());
        printBorder(80, w);
        printOutlined(s1, 80, w);
        printOutlined(s2, 80, w);
        printBorder(80, w);
    }

    private static void displaySettings() {
        String s[] = new String[5];
        int length = 0;
        s[0] = ("1. Point de vie initiaux des joueurs : " + settings.getPlayerPv());
        s[1] = ("2. Budget initial des joueurs : " + settings.getPlayerBudget());
        s[2] = ("3. Gain au début de chaque tour : " + settings.getMinLoot());
        s[3] = ("4. Nombre d'unités maximal dans une équipe : " + settings.getMaxUnities());
        s[4] = ("5. Nombre de tours possibles (au maximum) : " + settings.getNbRounds());
        for (String value : s) if (value.length() > length) length = value.length();
        printBorder(80, length);
        for (String value : s) printOutlined(value, 80, length);
        printBorder(80, length);
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
            } while (!inputString.equals("q") && !inputString.equals("Q"));
        } catch (NumberFormatException e) {
            System.out.println("Erreur lors de l'entrée des données pour les paramètres.");
            System.out.println("Le jeu va continuer avec les paramètre actuels : ");
            displaySettings();
        }
    }

}
