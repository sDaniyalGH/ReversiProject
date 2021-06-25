package model;
import Enum.*;

public class Users {

    private String username;
    private Color color;
    private int highScore;

    public Users(String username, String color){
        this.setUsername(username);

        if (color.equals("Black"))
            this.color = Color.black;
        else
            this.color = Color.white;

    }


    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
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

}
