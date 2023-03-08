package sk.stuba.fei.uim.oop;
import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.blueCards.Barrel;
import sk.stuba.fei.uim.oop.cards.blueCards.Dynamite;
import sk.stuba.fei.uim.oop.cards.blueCards.Prison;
import sk.stuba.fei.uim.oop.cards.brownCards.*;
import sk.stuba.fei.uim.oop.playerUtils.Hand;
import sk.stuba.fei.uim.oop.playerUtils.PlayerHandler;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GamePlay {

    public GamePlay() throws InterruptedException {
        System.out.println("Zadaj pocet hracov (2-4):");
        int playersAmount = makeInput(2, 4);

        Card[] card = new Card[71];
        PlayerHandler playerHandler = new PlayerHandler();
        int numOfUsedCards = 0;
        int round = 0;
        int player = 0;
        int dynamite = 0;
        int cisloKarty = 0;

        for (int i = 0; i < 71; i++) {
            if (i < 10)
                card[i] = new Barrel();
            else if (i < 3)
                card[i] = new Dynamite();
            else if (i < 6)
                card[i] = new Prison();
            else if (i < 36)
                card[i] = new Bang();
            else if (i < 51)
                card[i] = new Miss();
            else if (i < 59)
                card[i] = new Beer();
            else if (i < 65)
                card[i] = new CatBalou();
            else if (i < 69)
                card[i] = new Stagecoach();
            else
                card[i] = new Indian();
        }
        List<Card> cardsShuffled = Arrays.asList(card);
        Collections.shuffle(cardsShuffled);

        for (int i = 0; i < playersAmount; i++) {
            playerHandler.createNewPlayer();
            for (int countCard = 0; countCard < 4; countCard++) {
                numOfUsedCards = i * 4 + countCard;
                playerHandler.getPlayers().get(i).getHand().addCard(cardsShuffled.get(numOfUsedCards));
            }
        }

        while (player <= playersAmount) {
            if (player >= playersAmount) {
                round++;
                player = 0;
            }
            Hand hand = playerHandler.getPlayers().get(player).getHand();

            System.out.println(" ->->->->->->->  Kolo cislo " + (round + 1) + "! <-<-<-<-<-<-<- \n");
            System.out.println("     -->  HRAC CISLO " + (player + 1) + " je na tahu! <--\n");
            System.out.println("Prebieha tahanie kariet");
            loading();
            for (int i = 0; i < 2; i++) {
                numOfUsedCards++;
                hand.addCard(cardsShuffled.get(numOfUsedCards));
            }
            outerloop:
            while (true) {
                for (int i = 0; i < hand.getOnBoardCards().getNumberOfCardsOnBoard(); i++) {
                    switch (hand.getOnBoardCards().getCardsOnBoard().get(i).getClass().getSimpleName()) {
                        case "Prison":
                            if (((Prison) hand.getOnBoardCards().getCardsOnBoard().get(i)).checkPrison(hand, player)) {
                                break outerloop;
                            }
                            break;
                        case "Dynamite":
                            if (dynamite != 0)
                                dynamite--;
                            else
                                dynamite = playersAmount - 1;
                            if (((Dynamite) hand.getOnBoardCards().getCardsOnBoard().get(i)).useDynamite(playerHandler.getPlayers().get(player))) {
                                playerHandler.getPlayers().get(player).setHp(-3);
                                if (checkIfIsDead(playerHandler, player)) {
                                    playersAmount--;
                                }
                                hand.getOnBoardCards().getCardsOnBoard().remove(i);
                                writeCondition(playerHandler, dynamite);
                            }
                            break;
                    }
                }

                System.out.println("\n-----------------------------");
                System.out.println("   Vylozene modre karty: ");
                for (int i = 0; i < hand.getOnBoardCards().getNumberOfCardsOnBoard(); i++) {
                    if (hand.getOnBoardCards().getCardsOnBoard().get(i) != null)
                        System.out.println("   " + (i + 1) + ": ->  " + printCard(hand.getOnBoardCards().getCardsOnBoard().get(i)));
                }

                System.out.println("\n    Karty v ruke: ");
                for (int i = 0; i < hand.getNumberOfCards(); i++) {
                    System.out.println("   " + (i + 1) + ": -> " + printCard(hand.getCards().get(i)));
                }

                System.out.println("-----------------------------");
                System.out.println("\nPouzi kartu z ruky, napis cislo riadku a stlac ENTER. Ak chces ukoncit kolo, zadaj '0' alebo '-1' pre zahodenie karty");
                cisloKarty = makeInput(-1, hand.getNumberOfCards());
                cisloKarty--;

                if (cisloKarty == -1) {
                    if (hand.getNumberOfCards() > playerHandler.getPlayers().get(player).getHp()) {
                        System.out.println("Mas viac kariet na ruke ako zivotov, ak chces nejaku kartu pouzit, zadaj '1', pre zahodenie karty zadaj '-1'!");
                        do{
                            cisloKarty = makeInput(-1, 1);
                        } while(cisloKarty == 0);

                        if (cisloKarty == 1) {
                            System.out.println("\nPouzi kartu z ruky, napis cislo riadku a stlac ENTER!");
                            cisloKarty = makeInput(1, hand.getNumberOfCards());
                            cisloKarty--;

                            if (cisloKarty == -1) {
                                System.out.println("Rozhodol som si sa zahodit kartu, napis cislo karty, ktoru chces odhodit!");
                                cisloKarty = makeInput(1, hand.getNumberOfCards());
                                cisloKarty--;
                            }
                            System.out.println("Karta " + printCard(hand.getCards().get(cisloKarty)) + " bola odhodena!");
                            hand.getCards().remove(cisloKarty);
                            continue;
                        }
                    }
                    break;
                } else if (cisloKarty == -2) {
                    System.out.println("Rozhodol som si sa zahodit kartu, napis cislo karty, ktoru chces odhodit!");
                    cisloKarty = makeInput(1, hand.getNumberOfCards());
                    cisloKarty--;
                    System.out.println("Karta " + printCard(hand.getCards().get(cisloKarty)) + " bola odhodena!");
                    hand.getCards().remove(cisloKarty);
                    break;
                }

                if (hand.getCards().get(cisloKarty) instanceof Prison) {
                    ((Prison) hand.getCards().get(cisloKarty)).usePrison(playerHandler, player, playersAmount, cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof Barrel) {
                    hand.getOnBoardCards().getCardsOnBoard().add(hand.getCards().get(cisloKarty));
                    hand.getCards().remove(cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof Dynamite) {
                    int i;
                    if (player == 0)
                        i = playersAmount - 1;
                    else
                        i = player - 1;
                    playerHandler.getPlayers().get(i).getHand().getOnBoardCards().getCardsOnBoard().add(hand.getCards().get(cisloKarty));
                    playerHandler.getPlayers().get(player).getHand().getCards().remove(cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof Bang) {
                    ((Bang) hand.getCards().get(cisloKarty)).BangBegin(playerHandler, playersAmount, player, cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof Beer) {
                    ((Beer) hand.getCards().get(cisloKarty)).useBeer(playerHandler.getPlayers().get(player), cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof CatBalou) {
                    ((CatBalou) hand.getCards().get(cisloKarty)).UseCatBalou(player, playerHandler, playersAmount, cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof Stagecoach) {
                    ((Stagecoach) hand.getCards().get(cisloKarty)).useStagecoach(playerHandler.getPlayers().get(player), cardsShuffled, numOfUsedCards, cisloKarty);
                } else if (hand.getCards().get(cisloKarty) instanceof Indian) {
                    ((Indian) hand.getCards().get(cisloKarty)).useIndian(playerHandler, player, playersAmount);
                    hand.getCards().remove(cisloKarty);
                } else {
                    System.out.println("Kartu " + printCard(hand.getCards().get(cisloKarty)) + " nemozes pouzit, zvol inu kartu!");
                    loading();
                }
                for(int i = 0; i < playersAmount; i++){
                    if (checkIfIsDead(playerHandler, i)) {
                        playersAmount--;
                    }
                }
                if (playersAmount == 1) {
                    System.out.println("\n\n_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                    System.out.println("             KONIEC HRY            ");
                    System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");
                    System.out.println("  Vytazom sa stava hrac " + player);
                    return;
                }
            }
            player++;
        }
    }


    public static String printCard(Card card) {
        if (card instanceof Barrel)
            return "Barrel";
        else if (card instanceof Dynamite) {
            return "Dynamite";
        } else if (card instanceof Prison) {
            return "Prison";
        } else if (card instanceof Bang) {
            return "Bang";
        } else if (card instanceof Beer) {
            return "Beer";
        } else if (card instanceof CatBalou) {
            return "Cat Balou";
        } else if (card instanceof Indian) {
            return "Indian";
        } else if (card instanceof Miss) {
            return "Miss";
        } else if (card instanceof Stagecoach) {
            return "Stagecoach";
        } else {
            System.out.println("Error printcard class HAND\n");
            return "";
        }
    }
    public static void loading() throws InterruptedException {
        System.out.print("      ");
        for (int i = 0; i < 6; i++) {
            System.out.print(".");
            TimeUnit.MILLISECONDS.sleep(400);
        }
        System.out.println("\n");

    }
    public static void writeCondition(PlayerHandler playerHandler, int id) {
        System.out.print("\nHrac->" + (id + 1) + "  HP:[" + playerHandler.getPlayers().get(id).getHp() + "]  vylozene modre karty: ");
        for (int x = 0; x < playerHandler.getPlayers().get(id).getHand().getOnBoardCards().getNumberOfCardsOnBoard(); x++) {
            System.out.print("|" + printCard(playerHandler.getPlayers().get(id).getHand().getOnBoardCards().getCardsOnBoard().get(x)));
        }

    }

    public static void writeCardsOnHand(PlayerHandler playerHandler, int id) {
        System.out.print("Karty na ruke: ");
        for (int x = 0; x < playerHandler.getPlayers().get(id).getHand().getNumberOfCards(); x++) {
            System.out.print("|" + printCard(playerHandler.getPlayers().get(id).getHand().getCards().get(x)));
            checkIfIsDead(playerHandler, x);
        }
    }

    public static boolean checkIfIsDead(PlayerHandler playerHandler, int id) {
        if (playerHandler.getPlayers().get(id).getHp() <= 0) {
            System.out.println("Hrac cislo [" + id + "]  nema ziadne ziadne zivoty a je vyradeny z hry!");
            playerHandler.getPlayers().remove(id);
            return true;
        }
        return false;
    }

    public static int makeInput(int min, int max) {
        int input = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Zadal si nespravny vstup!");
            }
        } while (input < min || input > max);
        return input;
    }
}