package sk.stuba.fei.uim.oop.cards;
import sk.stuba.fei.uim.oop.cards.blueCards.BlueCard;
import java.util.ArrayList;

public class OnBoardCards {
    public OnBoardCards(){};
    ArrayList<Card> cardsOnBoard = new ArrayList<Card>();
    public void addCardToOnBoard(Card card) {
        if(card instanceof BlueCard) {
            this.cardsOnBoard.add(card);
        }
    }
    public int getNumberOfCardsOnBoard() {
        return cardsOnBoard.size();
    }
    public ArrayList<Card> getCardsOnBoard() {
        return cardsOnBoard;
    }
}
