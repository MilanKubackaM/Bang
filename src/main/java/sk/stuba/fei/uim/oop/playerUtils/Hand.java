package sk.stuba.fei.uim.oop.playerUtils;

import sk.stuba.fei.uim.oop.cards.Card;
import sk.stuba.fei.uim.oop.cards.OnBoardCards;
import java.util.ArrayList;

public class Hand {
    public Hand(){};
    OnBoardCards onBoardCards = new OnBoardCards(){};
    public OnBoardCards getOnBoardCards() {
        return onBoardCards;
    }
    private final ArrayList<Card> cards = new ArrayList<Card>();
    public void addCard(Card card){
        this.cards.add(card);
    };
    public ArrayList<Card> getCards() {
        return cards;
    }
    public int getNumberOfCards() {
        return cards.size();
    }
}