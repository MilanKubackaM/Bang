package sk.stuba.fei.uim.oop.cards.brownCards;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.playerUtils.Player;
import java.util.List;
import static sk.stuba.fei.uim.oop.GamePlay.makeInput;

public class Stagecoach extends BrownCard{
    public Stagecoach(){}

    public void useStagecoach(Player player, List<Card> cardsShuffled, int numOfUsedCards, int cisloKarty){
        System.out.println("Pouzil si dostavnik, chces si potiahnut dve karty? Zvol nenulovu hodnotu pre ANO alebo zadaj '0' pre NIE!");
        if(makeInput(-100, 100) != 0){
            for(int i = 0; i < 2; i++)
                player.getHand().addCard(cardsShuffled.get(++numOfUsedCards));
            System.out.println("Pouzil si dostavnik, tahas si dve karty!");
        } else {
            System.out.println("Rozhodol si sa nepotiahnut si karty, pokracuj v tahu!");
        };
        player.getHand().getCards().remove(cisloKarty);
    }
}
