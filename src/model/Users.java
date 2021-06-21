package model;

public class Users {

    private String username;
    private String Color;
    private int score = 2;

    public Users(String username, String color, int score){
        this.setUsername(username);
        this.setColor(color);
        this.setScore(score);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
