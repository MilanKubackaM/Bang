package sk.stuba.fei.uim.oop.cards.brownCards;
import sk.stuba.fei.uim.oop.playerUtils.PlayerHandler;

public class Indian extends BrownCard{
    public Indian(){};
    public void useIndian(PlayerHandler playerHandler, int playerId, int playersAmount){
        boolean check = false;
        System.out.println("Pouzil so kartu Indian, kazdemu protovnikovi sa odhodi karta Bang, ak ju nema, prichadza o zivot!");
        for(int i = 0; i < playersAmount; i++){
            if(playerId != i){
                for(int x = 0; x < playerHandler.getPlayers().get(i).getHand().getNumberOfCards(); x++){
                    if(playerHandler.getPlayers().get(i).getHand().getCards().get(i) instanceof Bang){
                        System.out.println("Hracovi cislo [" + i + "] bola odstranena karta Bang!");
                        playerHandler.getPlayers().get(i).getHand().getCards().remove(x);
                        check = true;
                        break;
                    }
                    check = false;
                }
                if(!check){
                    playerHandler.getPlayers().get(i).setHp(-1);
                    System.out.println("Hracovi cislo [" + i + "] bol odobrany zivot! HP [" + playerHandler.getPlayers().get(i).getHp() + "]");
                }
            }
        }
    }
}
