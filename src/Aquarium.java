import java.util.List;
import java.util.Random;
import java.util.Vector;

public class Aquarium {
    private Aquarium() {}

    private static class Holder {

        private static final Aquarium INSTANCE = new Aquarium();
    }
    public static Aquarium getInstance() {
        return Holder.INSTANCE;
    }

    private static final int aquariumMaxCapacity = new Random().nextInt(25)+25;
    private final List<Fish> fishList = new Vector<>();

    public List<Fish> getFishList() {
        return fishList;
    }

    public int getAquariumMaxCapacity() {
        return aquariumMaxCapacity;
    }
}
