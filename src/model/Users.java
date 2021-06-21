package model;
import Enum.*;

public class Users {

    private String username;
    private Color color;
    private int score = 2;

    public Users(String username, String color, int score){
        this.setUsername(username);
       // this.setColor(color);
        this.setScore(score);

        if (color.equals("Black"))
            this.color = Color.black;
        else
            this.color = Color.white;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
