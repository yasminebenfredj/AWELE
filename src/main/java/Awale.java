public class Awale {
    public int minMaxValue(Position posCurrent, boolean computerPlay, int depth,int depthMax){
        // computerPlay is true if the computer has to play and false otherwise
        int[] tabValues = new int[12] ;
        Position posNext; // In C : created on the stack: = very fast
        if (finalPosition(posCurrent, computerPlay, depth)){
            // WRITE the code: returns VALUEMAX (=96) if the computer wins, -96 if it loses; 0 if draw
        }
        if (depth == depthMax) {
            return evaluation(posCurrent, computerPlay, depth);
            // the simplest evaluation function is the difference of the taken seeds

            // compare le nb de graine des joueur
        }
        for(int i=0;i<12;i++){
            // we play the move i
            // WRITE function validMove(posCurrent, computerPlay,i)
            // it checks whether we can select the seeds in cell i and play (if there is no seed the function returns false)
            if (validMove(posCurrent, computerPlay,i)){
                // WRITE function playMove(&posNext,posCurrent, computerPlay,i)
                // we play the move i from posCurrent and obtain the new position posNext
                playMove(posNext,posCurrent, computerPlay,i);
                // posNext is the new current position and we change the player
                tabValues[i]=minMaxValue(posNext,!computerPlay,depth+1,depthMax);
            } else {
                if (computerPlay) {
                    tabValues[i]=-100 ;
                }
                else {
                    tabValues[i]=+100;
                }
            }
        }
        int res;
        if (computerPlay){
            // WRITE the code: res contains the MAX of tabValues
        } else {
            // WRITE the code: res contains the MIN of tabValues
        }
        return res;
    }

    /**
     *
     * @param position
     * @param computerPlay
     * @param depth
     * @return -96 if the player wins , 96 if the computer wins , 0 if it's a draw
     */
    public int finalPosition(Position position , boolean computerPlay , int depth) {
        int nbSeedsPlayer = position.getPlayer().getSeeds(); // The player's number of taken seeds
        int nbSeedsPlayerComputer = position.getPlayerComputer().getSeeds(); // The computer's number of taken seeds

        int seedsDifference = nbSeedsPlayer - nbSeedsPlayerComputer ; 
        if (seedsDifference > 0){ // the player wins
            return  -96;
        }
        else if (seedsDifference < 0){ // the computer wins
            return 96;
        }
        else{ //it's a draw (nbSeedsPlayer = nbSeedsPlayerComputer)
            return 0 ;
        }
    }

    /**
     *
     * @param position
     * @param computerPlay
     * @param depth
     * @return
     */

    public int  evaluation(Position position,  boolean computerPlay , int depth)
    {
        return 0;
    }





    /**
     *
     * @param position
     * @param computerPlay
     * @param movement
     * @return true si le joueur peut gagner des graine avec ce mouvement
     */
    public boolean validMove(Position position,  boolean computerPlay , int movement)
    {
        return false ;
    }

    public void playMove(Position position,  boolean computerPlay , int movement )
    {

    }
}
