import java.util.Scanner;

public class GameCtrl {
    static private Player p1;
    static private Settings settings;
    static private Player p2;
    static private int actualRound;
    private Scanner scanner = new Scanner(System.in);

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

    }

    static private void prepareRound() {
        // TODO - implement GameCtrl.prepareRound
        throw new UnsupportedOperationException();
    }

    static private void endGame() {
        // TODO - implement GameCtrl.endGame
        throw new UnsupportedOperationException();
    }

    static private void displaySettings() {
        // TODO - implement GameCtrl.displaySettings
        throw new UnsupportedOperationException();
    }

    static private void changeSettings() {
        // TODO - implement GameCtrl.changeSettings
        throw new UnsupportedOperationException();
    }

}
