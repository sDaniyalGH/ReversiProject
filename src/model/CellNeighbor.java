package model;
import Enum.*;

import java.util.ArrayList;

public class CellNeighbor {

    int i;
    int j;
    Direction move;
    ArrayList<CellNeighbor> reverseBaze;

    public CellNeighbor(int i , int j){
        this.i = i;
        this.j = j;
        reverseBaze = new ArrayList<>();
    }
    public CellNeighbor(int i , int j , Direction move){
        this.i = i;
        this.j = j;
        this.move = move;
        reverseBaze = new ArrayList<>();
    }

    public int getI() {
        return i;
    }
    public void setI(int i) {
        this.i = i;
    }
    public int getJ() {
        return j;
    }
    public void setJ(int j) {
        this.j = j;
    }
    public Direction getMove() {
        return move;
    }
    public void setMove(Direction move) {
        this.move = move;
    }
    public ArrayList<CellNeighbor> getReverseBaze() {
        return reverseBaze;
    }
    public void setReverseBaze(ArrayList<CellNeighbor> reverseBaze) {
        this.reverseBaze = reverseBaze;
    }
}
