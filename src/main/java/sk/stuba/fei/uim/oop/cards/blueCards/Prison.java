package sk.stuba.fei.uim.oop.cards.blueCards;
import sk.stuba.fei.uim.oop.playerUtils.Hand;
import sk.stuba.fei.uim.oop.playerUtils.PlayerHandler;
import java.util.Random;
import static sk.stuba.fei.uim.oop.GamePlay.*;

public class Prison extends BlueCard{
    public Prison(){}

    public void usePrison(PlayerHandler playerHandler, int playerPlayingId, int playersAmount, int cisloKarty){
        System.out.println("-----------------------------");
        System.out.println("Na uvaznenie mas na vyber tychto hracov: ");

        for(int i = 0; i < playersAmount; i++){
            if(i != playerPlayingId){
                writeCondition(playerHandler, i);
            }
        };
        System.out.println("\n-----------------------------");

        while(true){
            System.out.println("Na ktoreho hraca chces kartu pouzit?");
            int playerTargetId;
            do{
                playerTargetId = makeInput(0, playersAmount);
                playerTargetId--;
            } while(playerTargetId < 0 || playerTargetId == playerPlayingId);

            if(playerHandler.getPlayers().get(playerTargetId) != null && playerPlayingId != playerTargetId) {
                playerHandler.getPlayers().get(playerTargetId).getHand().getOnBoardCards().addCardToOnBoard(playerHandler.getPlayers().get(playerPlayingId).getHand().getCards().get(cisloKarty));
                playerHandler.getPlayers().get(playerPlayingId).getHand().getCards().remove(cisloKarty);
                System.out.println("-----------------------------");
                writeCondition(playerHandler, playerTargetId);
                System.out.println("\n");
                break;
            }
            else{
                System.out.println("Zadal si neplatny vstup, pre opakovanie vyberu, zadaj '1', pre ukoncenie zadaj '0'!");
                playerTargetId = makeInput(0,1);
                if(playerTargetId == 0)
                    break;
            }
        }
    }

    public boolean checkPrison(Hand hand, int id){
        for(int i = 0; i < hand.getOnBoardCards().getNumberOfCardsOnBoard(); i++)
            if(hand.getOnBoardCards().getCardsOnBoard().get(i) instanceof Prison){
                Random r = new Random();
                float chance = r.nextFloat();
                if (chance <= 0.75f) {
                    System.out.println("Hrac cislo " + (id+1) + " je uvezneny! Toto kolo nehra!");
                    System.out.println("-----------------------------");
                    hand.getOnBoardCards().getCardsOnBoard().remove(i);
                    return true;
                }
                else{
                    System.out.println("Karta 'Vazenie' sa neaktivovala, pokracujes v hre!");
                    System.out.println("-----------------------------");
                    hand.getOnBoardCards().getCardsOnBoard().remove(i);
                    return false;
                }
            }
        return false;
    }
}
