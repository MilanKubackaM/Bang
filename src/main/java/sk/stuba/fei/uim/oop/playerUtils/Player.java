package sk.stuba.fei.uim.oop.playerUtils;
public class Player {
    private final int id;
    private int hp;
    Hand hand = new Hand();
    public Hand getHand() {
        return hand;
    }
    public Player(int id){
        this.id = id;
        this.hp = 4;
    }
    public void setHp(int change) {
        this.hp = hp+change;
    }
    public int getHp() {
        return hp;
    }
    public int getId() {
        return id;
    }
}
