import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ThePhantomMenace extends PacmanGame implements Runnable {
    private PacmanGame.Move forbiddenMove = PacmanGame.Move.LEFT;

    @Override
    public void run() {

    }

    public void pathListMaker() { //SETTATO A VOID PERCHE' USANDO SETBESTPATH NON ABBIAMO BISOGNO DI RITORNO?
        ArrayList<ArrayList<Coordinate>> pathList = new ArrayList<>();
        for(int i = 0; i < super.phantom.size(); i++) {
            for(int j = 0; j < 100; j++){
                pathList.get(i).addAll(pathFinder(super.phantom.get(i))); //ADDALL SEMBRA RISOLVERE IL PROBLEMA
            }
            super.phantom.get(0).setBestPath(checkTheShorterPath(pathList));
            pathList.clear();
        }
    }


    public ArrayList pathFinder(Coordinate phantom) {
        PacmanGame.Move movement = PacmanGame.Move.LEFT; //mossa che sta facendo il fantasma
        ArrayList<Coordinate> supportList = new ArrayList<>(); //lista dove andiamo a memorizzare le coordinate delle mosse per raggiungere pacman
        int abscissaZero = phantom.getX(); //anzichè dare in input x e y ce le prendiamo dalle coordinate dei fantasmi
        int ordinateZero = phantom.getY();
        int abscissaSobstitute = 0, ordinateSobstitute = 0;
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
                supportList.add(new Coordinate(abscissaZero, ordinateZero)); //aggiungiamo in posizione i le coordinate del percorso
            }
        } while (!super.pacman.equals(supportList.get(supportList.size()-1)));
        return supportList; //usciti dal do-while dei cento percorsi ci facciamo tornare l'ArrayList di ArrayList
    }

    public ArrayList checkTheShorterPath(ArrayList pathList){
        ArrayList<ArrayList<Coordinate>> list = pathList;
        int bestPathIndex = list.get(0).size();
        for (int i = 1; i < list.size(); i++) {
            if(list.get(i).size()<bestPathIndex)
                bestPathIndex = list.get(i).size();
        }
        return list.get(bestPathIndex);
    }
}