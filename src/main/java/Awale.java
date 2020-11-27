public class Awale {
    Joueur joueur1 = new Joueur();
    Joueur joueur2 = new Joueur();


    public int minMaxValue(Position posCurrent, boolean computerPlay, int depth,int depthMax){
        // computerPlay is true if the computer has to play and false otherwise
        int[] tab_values = new int[12] ;
        Position pos_next; // In C : created on the stack: = very fast
        if (finalPosition(posCurrent, computerPlay, depth)){
            // WRITE the code: returns VALMAX (=96) if the computer wins, -96 if it loses; 0 if draw
        }
        if (depth == depthMax) {
            return evaluation(posCurrent, computerPlay, depth);
            // the simplest evealution fucntion is the difference of the taken seeds
        }
        for(int i=0;i<12;i++){
            // we play the move i
            // WRITE function validMove(posCurrent, computerPlay,i)
            // it checks whether we can select the seeds in cell i and play (if there is no seed the function returns false)
            if (validMove(posCurrent, computerPlay,i)){
                // WRITE function playMove(&pos_next,posCurrent, computerPlay,i)
                // we play th emove i from posCurrent and obtain the new position pos_next
                playMove(pos_next,posCurrent, computerPlay,i);
                // pos_next is the new current poisition and we change the player
                tab_values[i]=minMaxValue(pos_next,!computerPlay,depth+1,depthMax);
            } else {
                if (computerPlay) {
                    tab_values[i]=-100 ;
                }
                else {
                    tab_values[i]=+100;
                }
            }
        }
        int res;
        if (computerPlay){
            // WRITE the code: res contains the MAX of tab_values
        } else {
            // WRITE the code: res contains the MIN of tab_valuess
        }
        return res;
    }


    public boolean finalPosition(Position position , boolean computer_play , int depth)
    {
        return false;
    }

    public int  evaluation(Position position,  boolean computer_play , int depth)
    {
        return 0;
    }

    public boolean validMove(Position position,  boolean computer_play , int depth)
    {
        return false ;
    }

    public void playMove(Position position,  boolean computer_play , int depth)
    {

    }
}
