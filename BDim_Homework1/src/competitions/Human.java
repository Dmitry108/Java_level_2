package competitions;

public class Human implements Competitor {
    private String name;
    private int maxRunDistance;
    private double maxJumpHeight;
    private boolean onDistance = true;

    public Human (String name, int maxRunDistance, double maxJumpHeight){
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
    }
    //Конструктор стандартного человека
    public Human (String name){
        this.name = name;
        this.maxRunDistance = 3000;
        this.maxJumpHeight = 1.5;
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
        System.out.printf("%s %s %s %d м%n", name, result, "дистанцию", distance);
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
        System.out.printf("%s %s %s %.1f м%n", name, result, "стену", height);
    }

    @Override
    public boolean isOnDistance() {
        return onDistance;
    }
    @Override
    public void info() {
        System.out.printf("%s %s%n", name, onDistance? "на дистанции" : "выбыл из соревнований");
    }
}
