package model;
import Enum.*;



public class Cell {

    private int x; // start from zero
    private int y; // start from zero
    Type type;

    public Cell (int x , int y , Type type){

        this.type = type;

    }



    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
