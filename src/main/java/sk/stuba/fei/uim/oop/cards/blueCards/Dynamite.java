package sk.stuba.fei.uim.oop.cards.blueCards;
import sk.stuba.fei.uim.oop.playerUtils.Player;
import java.util.Random;

public class Dynamite extends BlueCard{
    public Dynamite(){};
    int playerId;
    public boolean useDynamite(Player player){
        this.playerId = player.getId();
        Random r = new Random();
        float chance = r.nextFloat();
        if (chance <= 0.875f) {
            System.out.println("Dynamit nevybuchol, mas stastie!");
            return false;
        }
        System.out.println("Dynamit vybuchol! Stracas 3 zivoty!");
        return true;
    }
}