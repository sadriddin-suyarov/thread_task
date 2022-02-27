import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Fish extends Thread {
    private final Gender gender;
    private volatile long timeToLive;
    private final int fishId = index++;
    private static int index;
    private static final List<Fish> fishList = Aquarium.getInstance().getFishList();
    private static final int aquariumMaxCapacity = Aquarium.getInstance().getAquariumMaxCapacity();

    {
        this.timeToLive = TimeUnit.SECONDS.toMillis(new Random().nextInt(20))
                + System.currentTimeMillis();
    }

    public Fish() {
        int i = new Random().nextInt(2);
        gender = i == 1 ? Gender.MALE : Gender.FEMALE;
    }

    public Fish(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getFishId() {
        return fishId;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "gender=" + gender +
                ", timeToLive=" + timeToLive +
                ", fishId=" + fishId +
                '}';
    }


    void match() {
        Fish randomFish = getRandomFish();
        if (randomFish.getGender().code != this.getGender().code) {
            int newFishAmount = new Random().nextInt(7) + 1;
            System.out.println("Fish-" + randomFish.getFishId() + " and " + "Fish-" + this.getFishId() + " are met\n" +
                    "New born fish quantity: " + newFishAmount);
            leaveOffspring(newFishAmount);
            System.out.println();
        }
    }

    public Fish getRandomFish() {
        int randomFishNum = new Random().nextInt(fishList.size()-1);
        return fishList.get(randomFishNum);
    }

    public void leaveOffspring(int newFishNum) {
        for (int i = 0; i < newFishNum; i++) {
            Fish newFish = new Fish();
            fishList.add(newFish);
            newFish.start();
        }
    }

    public void kill() {
        this.setTimeToLive(System.currentTimeMillis());
    }

    private boolean check() {
        return System.currentTimeMillis() < timeToLive;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (check()) {
            try {
                if (aquariumMaxCapacity <= fishList.size()) {
                    fishList.forEach(Fish::kill);
                }
                match();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fish-" + this.getFishId() + " is dead");
        fishList.remove(this);
    }
}
