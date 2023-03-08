package sk.stuba.fei.uim.oop.playerUtils;
import java.util.ArrayList;

public class PlayerHandler {
    private int playerAmount;
     ArrayList<Player> players = new ArrayList<Player>();
    public void createNewPlayer() {
        players.add(new Player(this.playerAmount));
        this.playerAmount = this.playerAmount + 1;
    }
    public PlayerHandler() {
        this.playerAmount = 0;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
}