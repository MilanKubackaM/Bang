package sk.stuba.fei.uim.oop.cards.brownCards;
import sk.stuba.fei.uim.oop.cards.blueCards.Barrel;
import sk.stuba.fei.uim.oop.playerUtils.Player;
import sk.stuba.fei.uim.oop.playerUtils.PlayerHandler;
import static sk.stuba.fei.uim.oop.GamePlay.*;

public class Bang extends BrownCard{
    public Bang(){};
    public void BangBegin(PlayerHandler playerHandler, int playersAmount, int playerIdAtt, int cardId) throws InterruptedException {
        int playerIdDeff;
        System.out.println("Napis cislo hraca na ktoreho chces utocit! Tvoje cislo je: " + (playerIdAtt+1) + " a mas na vyber nasledujucich protivnikov\n");
        System.out.print("-----------------------------");

        for(int i = 0; i<playersAmount; i++){
            if(playerHandler.getPlayers().get(i).getHp() > 0 && i != playerIdAtt){
                writeCondition(playerHandler, i);
            }
        }
        System.out.println("\n-----------------------------");
        System.out.print("\nVloz cislo hraca: ");

        do{
            playerIdDeff = makeInput(0, playersAmount);
            playerIdDeff--;
        } while(playerIdDeff < 0 || playerIdDeff == playerIdAtt);

        Player player  = playerHandler.getPlayers().get(playerIdDeff);
        boolean shooted = true;
        for(int i = 0; i < player.getHand().getOnBoardCards().getNumberOfCardsOnBoard(); i++) {
            if (player.getHand().getOnBoardCards().getCardsOnBoard().get(i) instanceof Barrel) {
                shooted = ((Barrel) player.getHand().getOnBoardCards().getCardsOnBoard().get(i)).useBarrel();
            }
        }
        boolean check = false;
        for(int i = 0; i < player.getHand().getNumberOfCards(); i++) {
            if (shooted && player.getHand().getCards().get(i) instanceof Miss) {
                check = ((Miss) player.getHand().getCards().get(i)).useMiss(player, i);
                break;
            }
        }
        if(!check){
            System.out.println("Trafil si! Protivnik straca zivot!");
            player.setHp(-1);
        }
        playerHandler.getPlayers().get(playerIdAtt).getHand().getCards().remove(cardId);
        loading();
        System.out.print("-----------------------------");
        for(int x = 0; x < playersAmount; x++) {
            writeCondition(playerHandler, x);
        }
        System.out.println("\n-----------------------------");
    }
}
