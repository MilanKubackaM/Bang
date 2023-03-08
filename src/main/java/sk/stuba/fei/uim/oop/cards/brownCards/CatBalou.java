package sk.stuba.fei.uim.oop.cards.brownCards;
import sk.stuba.fei.uim.oop.playerUtils.PlayerHandler;
import java.util.Scanner;
import static sk.stuba.fei.uim.oop.GamePlay.*;

public class CatBalou extends BrownCard{
    public CatBalou(){}

    public void UseCatBalou( int playerPlayingId, PlayerHandler playerHandler, int playersAmount, int cisloKarty) throws InterruptedException {
        System.out.println("Pouzil si kartu CAT BALOU!\n");
        loading();
        System.out.print("-----------------------------");
        for(int i = 0; i < playersAmount; i++){
            if(i != playerPlayingId){
                writeCondition(playerHandler, i);
                writeCardsOnHand(playerHandler, i);
            }
        }
        System.out.println("\n-----------------------------\n");
        Scanner scanner = new Scanner(System.in);
        int playerTargetId;
        String cardTargetId;
        outerloop:
        while(true){
            System.out.println("Zvol cislo hraca, na ktoreho chces kartu pouzit, alebo zvol '0' pre zrusenie!");
            do{
                playerTargetId = makeInput(0, playersAmount);
                playerTargetId--;
            } while(playerTargetId < -2 || playerTargetId == playerPlayingId);

            if(playerTargetId == -1)
                break;
            if(playerHandler.getPlayers().get(playerTargetId) != null){
                System.out.println("V poriadku, teraz vyber kartu, ktoru mu chces vyhodit tak, ze zadas cely nazov danej karty!. Alebo zvol '0' pre zvolenie ineho hraca!");
                cardTargetId = String.valueOf(scanner.nextLine());
                if(cardTargetId.equals("0"))
                    break;
                for(int i = 0; i < playerHandler.getPlayers().get(playerTargetId).getHand().getNumberOfCards(); i++){
                    if(printCard(playerHandler.getPlayers().get(playerTargetId).getHand().getCards().get(i)).equals(cardTargetId)){
                        System.out.println("Karta " + printCard(playerHandler.getPlayers().get(playerTargetId).getHand().getCards().get(i)) + " bola odhodena!");
                        playerHandler.getPlayers().get(playerTargetId).getHand().getCards().remove(i);
                        playerHandler.getPlayers().get(playerPlayingId).getHand().getCards().remove(cisloKarty);
                        System.out.print("-----------------------------");

                        writeCondition(playerHandler, playerTargetId);
                        writeCardsOnHand(playerHandler, playerTargetId);
                        System.out.print("\n-----------------------------");
                        break outerloop;
                    }
                }
            }
            else{
                System.out.println("Takato moznost nieje v ponuke, opakuj svoj vyber!");
            }
        }
    }
}
