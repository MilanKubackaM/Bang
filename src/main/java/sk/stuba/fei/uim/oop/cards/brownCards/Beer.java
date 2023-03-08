package sk.stuba.fei.uim.oop.cards.brownCards;
import sk.stuba.fei.uim.oop.playerUtils.Player;

public class Beer extends BrownCard {
    public Beer(){};
    public void useBeer(Player player, int cardId){
        player.setHp(1);
        System.out.println("Pouzil si kartu PIVO, ziskavas jeden zivot!\n");
        player.getHand().getCards().remove(cardId);
    }
}