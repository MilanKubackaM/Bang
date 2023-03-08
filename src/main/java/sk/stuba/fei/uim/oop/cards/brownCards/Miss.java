package sk.stuba.fei.uim.oop.cards.brownCards;
import sk.stuba.fei.uim.oop.playerUtils.Player;

public class Miss extends BrownCard{
    public Miss(){};
    public boolean useMiss(Player player, int id){
        System.out.println("Protivnik pouzil kartu MISS, netrafil si, skus to znova ak mas s cim!");
        player.getHand().getCards().remove(id);
        return true;
    }
}
