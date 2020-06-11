import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PacmanGame g = new PacmanGame();
        System.out.println(g);
        do {
            System.out.println("Premi 8 per andare sopra");
            System.out.println("Premi 4 per andare a sinistra");
            System.out.println("Premi 2 per andare sotto");
            System.out.println("Premi 6 per andare a destra");
            Scanner scan = new Scanner(System.in);
            int choice = scan.nextInt();
            switch (choice){
                case 2: g.move(PacmanGame.Move.BOTTOM);
                    break;
                case 8: g.move(PacmanGame.Move.TOP);
                    break;
                case 4: g.move(PacmanGame.Move.LEFT);
                    break;
                case 6: g.move(PacmanGame.Move.RIGHT);
                    break;
            }
            System.out.println(g);
            System.out.println("Parziale:" + g.getPartialPoint());
            System.out.println("PUNTI: " + g.getPoint());
        } while (g.getCurrentStatus() == PacmanGame.Status.IN_GAME);
    }
}