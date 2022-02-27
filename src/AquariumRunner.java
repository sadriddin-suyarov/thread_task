import java.util.List;
import java.util.Random;

public class AquariumRunner {

    private static final Random random = new Random();
    private static final List<Fish> fishList = Aquarium.getInstance().getFishList();
    private static final int aquariumMaxCapacity = Aquarium.getInstance().getAquariumMaxCapacity();

    public static void init() {
        System.out.println("Aquarium max capacity is " + aquariumMaxCapacity);
        int male = 0;
        int female = 0;
        int range = random.nextInt(7);
        if (range == 0) {
            range = 3;
        }

        for (int i = 0; i < range; i++) {
            fishList.add(new Fish(Gender.MALE));
            ++male;
        }

        range = random.nextInt(7);
        if (range == 0) {
            range = 3;
        }

        for (int i = 0; i < range; i++) {
            fishList.add(new Fish(Gender.FEMALE));
            ++female;
        }
        System.out.println("There are " + male + " male and " + female + " female fishes in aquarium");
        fishList.forEach(Fish::start);
    }

    public static void main(String[] args) {

        init();

        while (fishList.size() > 0 && aquariumMaxCapacity > fishList.size()) {
            try {
                System.out.println("There are " +fishList.size() + " fishes in aquarium");
                Thread.sleep(2000);
                if (aquariumMaxCapacity <= fishList.size()) {
                    fishList.forEach(Fish::kill);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("There are " +fishList.size() + " fishes in aquarium");
        System.out.println("==========================================================");
        fishList.forEach(Fish::kill);
    }
}
