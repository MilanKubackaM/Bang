package sk.stuba.fei.uim.oop.cards.blueCards;
import java.util.Random;

public class Barrel extends BlueCard{
    public Barrel(){}
    public boolean useBarrel(){
        Random r = new Random();
        float chance = r.nextFloat();
        if (chance <= 0.75f) {
            System.out.println("Super, Barel si netrafil, snad protivnik nema karticku 'Miss'!");
            return true;
        } else {
            System.out.println("Strela vedla!");
            return false;
        }
    }
}
