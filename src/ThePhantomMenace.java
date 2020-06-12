import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class ThePhantomMenace extends PacmanGame implements Runnable {
    private PacmanGame.Move forbiddenMove = PacmanGame.Move.LEFT;

    @Override
    public void run() {
        checkTheShorterPath();
    }

    public ArrayList playgroundShuffle(int numberOfPathToSimulate) {
        PacmanGame.Move movement = PacmanGame.Move.LEFT; //mossa che sta facendo il fantasma
        ArrayList<ArrayList<Coordinate>> supportList = new ArrayList<>(); //lista dove andiamo a memorizzare le coordinate delle mosse per raggiungere pacman
        for (int i = 0; i < super.phantom.size(); i++) { //cicliamo tutti i fantasmi (i fantasmi sono un ArrayList di coordinate
            int abscissaZero = super.phantom.get(i).getX(); //anzichè dare in input x e y ce le prendiamo dalle coordinate dei fantasmi
            int ordinateZero = super.phantom.get(i).getY();
            int abscissaSobstitute = 0, ordinateSobstitute = 0;
            numberOfPathToSimulate = 0;
            do { //do-while che controlla che le mosse siano 100
                do { //do-while che controlla che il ciclo continui fin quando il fantasma non trova pacman
                    switch (ThreadLocalRandom.current().nextInt(1, 5)) {
                        case 1: //sotto
                            abscissaSobstitute = abscissaZero;
                            ordinateSobstitute = ordinateZero + 1;
                            movement = PacmanGame.Move.BOTTOM;
                            this.forbiddenMove = PacmanGame.Move.TOP;
                            break;
                        case 2://destra
                            abscissaSobstitute = abscissaZero + 1;
                            ordinateSobstitute = ordinateZero;
                            movement = PacmanGame.Move.RIGHT;
                            this.forbiddenMove = PacmanGame.Move.LEFT;
                            break;
                        case 3://sopra
                            abscissaSobstitute = abscissaZero;
                            ordinateSobstitute = ordinateZero - 1;
                            movement = PacmanGame.Move.TOP;
                            this.forbiddenMove = PacmanGame.Move.BOTTOM;
                            break;
                        case 4://sinistra
                            abscissaSobstitute = abscissaZero - 1;
                            ordinateSobstitute = ordinateZero;
                            movement = PacmanGame.Move.LEFT;
                            this.forbiddenMove = PacmanGame.Move.RIGHT;
                            break;
                    }
                    if (grid[abscissaSobstitute][ordinateSobstitute] != 2 && movement != this.forbiddenMove) {
                        abscissaZero = abscissaSobstitute;
                        ordinateZero = ordinateSobstitute;
                        supportList.get(numberOfPathToSimulate).add(new Coordinate(abscissaZero, ordinateZero)); //aggiungiamo in posizione i le coordinate del percorso
                    }
                } while (super.pacman.equals(new Coordinate(super.phantom.get(numberOfPathToSimulate).getX(), super.phantom.get(numberOfPathToSimulate).getY())));
                playgroundShuffle(numberOfPathToSimulate++);//incrementiamo l'indice dell'array per il prossimo giro di playground
            } while (numberOfPathToSimulate == 100);
        } //chiusura ciclo for array di coordinate dei fantasmi
        return supportList; //usciti dal do-while dei cento percorsi ci facciamo tornare l'ArrayList di ArrayList
    }

    public ArrayList checkTheShorterPath(){
        ArrayList<ArrayList<Coordinate>> list = playgroundShuffle(0);
        ArrayList<Integer> listOfSize = new ArrayList<>();
        int bestPathIndex = 0;
        for (int i = 0; i < list.size(); i++) {
            listOfSize.add(list.get(i).size());
        }
        Collections.sort(listOfSize);
        for (int i = 0; i < list.size(); i++) {
            if(listOfSize.get(0) == list.get(i).size())
                bestPathIndex = i;
        }
        return list.get(bestPathIndex);
    }
}
