package competitions;

public class Cat implements Competitor {
    private String type = "Кот";
    private String name;
    private int maxRunDistance;
    private double maxJumpHeight;
    private boolean onDistance = true;

    public Cat (String name, int maxRunDistance, double maxJumpHeight){
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }
    //Конструктор стандартного кота
    public Cat (String name){
        this.name = name;
        this.maxRunDistance = 500;
        this.maxJumpHeight = 1;
    }

    @Override
    public void run(int distance) {
        String result;
        if (distance <= maxRunDistance) {
            result = "успешно преодолел";
        } else {
            result = "не смог преодолеть";
            onDistance = false;
        }
        System.out.printf("%s %s %s %s %d м%n", type, name, result, "дистанцию", distance);
    }

    @Override
    public void jump(double height) {
        String result;
        if (height <= maxJumpHeight) {
            result = "успешно перепрыгнул";
        } else {
            result = "не смог перепрынгуть";
            onDistance = false;
        }
        System.out.printf("%s %s %s %s %.1f м%n", type, name, result, "стену", height);
    }

    @Override
    public boolean isOnDistance() {
        return onDistance;
    }

    @Override
    public void info() {
        info(type, name, onDistance);
    }
}
