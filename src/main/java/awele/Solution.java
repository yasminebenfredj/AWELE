package awele;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {
    private  int score;
    private ArrayList solution;
    private int[] cells;

    public Solution(int score, ArrayList solution, int[] cells) {
        this.score = score;
        this.solution = solution;
        this.cells = cells;

    }

    public void addToScore (int sum) {
        this.score += sum;
    }

    public void addToSolution (int index) {
        this.solution.add(index);

    }

    public int getScore() {
        return score;
    }
    public ArrayList getSolution() {
        return solution;
    }
    public void setCells(int[] cells) {
        this.cells = cells;
    }

    public int[] getCells() {
        return this.cells;
    }


    @Override
    public String toString() {
        return "\nScore : "+score+" | Solution : " +solution+"| Case : "+ Arrays.toString(cells);
    }

    @Override
    public Solution clone() {
        return new Solution(this.score, (ArrayList) this.solution.clone(), this.cells);
    }
}
